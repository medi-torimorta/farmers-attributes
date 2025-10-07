package medi.makiba.farmers_attributes.attribute;


import java.util.List;

import medi.makiba.farmers_attributes.FAConfig;
import medi.makiba.farmers_attributes.block.LargeCropBlock;
import medi.makiba.farmers_attributes.registry.FAAttachmentTypes;
import medi.makiba.farmers_attributes.registry.FAAttributes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.Tags;

public class EasyHarvest {

    public static boolean isReplantable(BlockState state){
        Boolean isCrop = false;
        Block block = state.getBlock();
        if (block instanceof CropBlock cropBlock){
            isCrop = cropBlock.getAge(state) == cropBlock.getMaxAge();
        }else if (block instanceof LargeCropBlock){
            return LargeCropBlock.inGround(state);
        }else{
            return false;
        }

        return isCrop && !FAConfig.EASY_HARVEST_BLACKLIST.get().contains(BuiltInRegistries.BLOCK.getKey(block).toString());
    }

    public static boolean tryHarvest(Level level, BlockPos pos, Player player){
        BlockState state = level.getBlockState(pos);
        if (!isReplantable(state)
            || player.isCrouching()
            || !level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)){
            return false;
        }

        if (!(level instanceof ServerLevel serverLevel) || !(FAConfig.FORCE_EASY_HARVEST.get() || player.getAttributeValue(FAAttributes.EASY_HARVEST) != 0)){
            return true;
        }
        Block block = state.getBlock();
        BlockState replacementState = Blocks.AIR.defaultBlockState();
        List<ItemStack> drops = Block.getDrops(state, serverLevel, pos, null, player, player.getMainHandItem());
        if (block instanceof CropBlock cropBlock){
            int greenThumbMultiplier = serverLevel.getChunk(pos).hasData(FAAttachmentTypes.GREEN_THUMB) ? FAConfig.GREEN_THUMB_DROP_MULTIPLIER.get() : 1;
            List<ItemStack> extraDrops = List.of();
            ItemStack seed = cropBlock.getCloneItemStack(serverLevel, pos, state);
            for (ItemStack drop : drops) {     
                int newCount = drop.getCount();
                if (!(drop.is(Tags.Items.SEEDS))) {
                    newCount *= greenThumbMultiplier;
                }
                int maxStackSize = drop.getMaxStackSize();
                if (newCount > maxStackSize) {
                    drop.setCount(maxStackSize);
                    newCount -= maxStackSize;
                    while (newCount > 0) {
                        ItemStack newStack = drop.copy();
                        int count = Math.min(newCount, maxStackSize);
                        newStack.setCount(count);
                        newCount -= count;
                        extraDrops.add(newStack);
                    }
                }else{
                    drop.setCount(newCount);
                }

                if(ItemStack.isSameItem(drop, seed) && seed.getCount() == 1){
                    seed.grow(1);
                    drop.shrink(1);
                }
            }
            if (seed.getCount() > 1){
                replacementState = cropBlock.getStateForAge(0);
            }else if(player.getInventory().contains(seed)){
                int slot = player.getInventory().findSlotMatchingItem(seed);
                player.getInventory().removeItem(slot, 1);
                replacementState = cropBlock.getStateForAge(0);
            }
            drops.addAll(extraDrops);
        }
        else if (block instanceof LargeCropBlock largeCropBlock){
            replacementState = largeCropBlock.originalCropState;
        }
        
        if (serverLevel.setBlockAndUpdate(pos, replacementState)){
            GreenThumb.removeData(serverLevel, pos);
            serverLevel.playSound(null, pos, SoundEvents.CROP_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!replacementState.is(Blocks.AIR)){
                double attributeValue = player.getAttributeValue(FAAttributes.GREEN_THUMB);
                if (serverLevel.random.nextDouble() < attributeValue) {
                    GreenThumb.addData(serverLevel, pos);
                }
            }
            
            for (ItemStack drop : drops) {
                if(!player.addItem(drop)){
                    Block.popResource(serverLevel, pos, drop);
                }
            }
        }
        return true;
    }
}
