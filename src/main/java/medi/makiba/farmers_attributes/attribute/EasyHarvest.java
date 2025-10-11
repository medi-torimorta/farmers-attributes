package medi.makiba.farmers_attributes.attribute;

import java.util.List;

import medi.makiba.farmers_attributes.FAConfig;
import medi.makiba.farmers_attributes.FarmersAttributes;
import medi.makiba.farmers_attributes.block.LargeCropBlock;
import medi.makiba.farmers_attributes.registry.FAAttachmentTypes;
import medi.makiba.farmers_attributes.registry.FAAttributes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.GrowingPlantBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.common.Tags;

public class EasyHarvest {

    @SuppressWarnings("deprecation")
    public static boolean isEasyHarvestable(Level level, BlockPos pos, BlockState state, Direction face,
            ItemStack heldItem) {
        Boolean isHarvestable = false;
        Block block = state.getBlock();
        if (block instanceof CropBlock cropBlock) {
            isHarvestable = cropBlock.getAge(state) == cropBlock.getMaxAge();
        } else if (block instanceof SugarCaneBlock) {
            isHarvestable = level.getBlockState(pos.below()).getBlock() == block
                    && (face != Direction.UP || heldItem.getItem() != block.asItem());
        } else if (block instanceof GrowingPlantBlock growingPlantBlock) {
            Item item = growingPlantBlock.getCloneItemStack(level, pos, state).getItem();
            isHarvestable = level.getBlockState(pos.below()).getBlock().getCloneItemStack(level, pos, state)
                    .getItem() == item && (face != Direction.UP || heldItem.getItem() != item);
        } else {
            return false;
        }

        return isHarvestable
                && !FAConfig.EASY_HARVEST_BLACKLIST.get().contains(BuiltInRegistries.BLOCK.getKey(block).toString());
    }

    public static boolean tryHarvest(Level level, BlockPos pos, Player player, Direction face, ItemStack heldItem) {
        BlockState state = level.getBlockState(pos);
        if (!isEasyHarvestable(level, pos, state, face, heldItem)
                || player.isCrouching()
                || !level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
            return false;
        }

        if (!(level instanceof ServerLevel serverLevel)
                || !(FAConfig.FORCE_EASY_HARVEST.get() || player.getAttributeValue(FAAttributes.EASY_HARVEST) != 0)) {
            return true;
        }
        Block block = state.getBlock();
        BlockState replacementState = Blocks.AIR.defaultBlockState();
        if (block instanceof CropBlock cropBlock) {
            List<ItemStack> drops = Block.getDrops(state, serverLevel, pos, null, player, heldItem);
            int greenThumbMultiplier = serverLevel.getChunk(pos).hasData(FAAttachmentTypes.GREEN_THUMB)
                    ? FAConfig.GREEN_THUMB_DROP_MULTIPLIER.get()
                    : 1;
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
                } else {
                    drop.setCount(newCount);
                }

                if (ItemStack.isSameItem(drop, seed) && seed.getCount() == 1) {
                    seed.grow(1);
                    drop.shrink(1);
                }
            }
            if (seed.getCount() > 1) {
                replacementState = cropBlock.getStateForAge(0);
            } else if (player.getInventory().contains(seed)) {
                int slot = player.getInventory().findSlotMatchingItem(seed);
                player.getInventory().removeItem(slot, 1);
                replacementState = cropBlock.getStateForAge(0);
            }
            drops.addAll(extraDrops);
            harvestAndReplant(serverLevel, pos, state, replacementState, (ServerPlayer) player, drops, heldItem);
        } else if (block instanceof SugarCaneBlock) {
            harvestSugarCaneLike(serverLevel, pos, (ServerPlayer) player, state, replacementState, heldItem);
        } else if (block instanceof GrowingPlantBlock) {
            FluidState fluidState = state.getFluidState();
            if (!fluidState.isEmpty() && fluidState.isSource()) {
                replacementState = fluidState.createLegacyBlock();
            }
            harvestSugarCaneLike(serverLevel, pos, (ServerPlayer) player, state, replacementState, heldItem);
        }
        return true;
    }

    public static boolean tryHarvestLargeCrop(Level level, BlockPos pos, Player player, ItemStack heldItem) {
        BlockState state = level.getBlockState(pos);
        if (!(state.getBlock() instanceof LargeCropBlock largeCropBlock)
                || !level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) || player.isCrouching()) {
            return false;
        }
        if (!(level instanceof ServerLevel serverLevel)) {
            return true;
        }
        if (FAConfig.FORCE_EASY_HARVEST.getAsBoolean() || player.getAttributeValue(FAAttributes.EASY_HARVEST) != 0) {
            harvestAndReplant(serverLevel, pos, state, largeCropBlock.originalCropState, (ServerPlayer) player,
                    LargeCropBlock.getDrops(state, serverLevel, pos, null, player, heldItem), heldItem);
            return true;
        }
        return false;
    }

    private static void harvestAndReplant(ServerLevel level, BlockPos pos, BlockState originalState, BlockState replacementState, ServerPlayer player,
            List<ItemStack> drops, ItemStack heldItem) {
        if (level.setBlockAndUpdate(pos, replacementState)) {
            GreenThumb.removeData(level, pos);
            level.playSound(null, pos, SoundEvents.CROP_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!replacementState.is(Blocks.AIR)) {
                double attributeValue = player.getAttributeValue(FAAttributes.GREEN_THUMB);
                if (level.random.nextDouble() < attributeValue) {
                    GreenThumb.addData(level, pos);
                }
            }

            for (ItemStack drop : drops) {
                if (!player.addItem(drop)) {
                    Block.popResource(level, pos, drop);
                }
            }
            FarmersAttributes.PUFFISH_SKILLS_COMPAT.addBreakExp(player, originalState, heldItem);
        }
    }

    private static void harvestSugarCaneLike(ServerLevel level, BlockPos pos, ServerPlayer player,
        BlockState originalState, BlockState replacementState, ItemStack heldItem) {
        List<ItemStack> drops = Block.getDrops(originalState, level, pos, null, player, heldItem);
        if (!level.setBlockAndUpdate(pos, replacementState)) {
            return;
        }
        level.playSound(null, pos, SoundEvents.CROP_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);

        if (FAConfig.EASY_HARVEST_WHOLE_PLANT.get()) {// harvest whole plant
            BlockPos abovePos = pos.above();
            while (level.getBlockState(abovePos) == originalState) {
                List<ItemStack> newDrops = Block.getDrops(originalState, level, abovePos, null, player, heldItem);
                if (!level.setBlockAndUpdate(abovePos, replacementState)) {
                    break;
                }
                drops.addAll(newDrops);
                abovePos = abovePos.above();
            }
            BlockState state = level.getBlockState(abovePos);
            if (state.getBlock() instanceof GrowingPlantHeadBlock) {
                FluidState fluidState = state.getFluidState();
                if (!fluidState.isEmpty() && fluidState.isSource()) {
                    replacementState = fluidState.createLegacyBlock();
                }
                List<ItemStack> newDrops = Block.getDrops(level.getBlockState(abovePos), level, abovePos, null, player, heldItem);
                if (level.setBlockAndUpdate(abovePos, replacementState)) {
                    drops.addAll(newDrops);
                }
            }
        }

        for (ItemStack drop : drops) {
            if (!player.addItem(drop)) {
                Block.popResource(level, pos, drop);
            }
        }
        FarmersAttributes.PUFFISH_SKILLS_COMPAT.addBreakExp(player, originalState, heldItem);
    }
}
