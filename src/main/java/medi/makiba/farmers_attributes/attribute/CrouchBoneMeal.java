package medi.makiba.farmers_attributes.attribute;

import medi.makiba.farmers_attributes.FAConfig;
import medi.makiba.farmers_attributes.registry.FAAttachmentTypes;
import medi.makiba.farmers_attributes.registry.FAAttributes;
import medi.makiba.farmers_attributes.registry.FATags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

public class CrouchBoneMeal {

    public static void tickCrouchBoneMeal(ServerLevel level, ServerPlayer player){
        if (player.isCrouching()){
            tryGrow(level, player);
        }else{
            resetCooldown(player);
        }
    }

    public static void resetCooldown(ServerPlayer player) {
        if (player.hasData(FAAttachmentTypes.CROUCH_BONEMEAL_COOLDOWN)){
            player.setData(FAAttachmentTypes.CROUCH_BONEMEAL_COOLDOWN, 0);
        }
    }

    //runs on crouching
    public static void tryGrow(ServerLevel level, ServerPlayer player) {
        double chance = player.getAttributeValue(FAAttributes.CROUCH_BONEMEAL_CHANCE);
        if (chance <= 0 || chance < level.random.nextDouble()) {
            return;
        }
        
        int current_cooldown = player.getData(FAAttachmentTypes.CROUCH_BONEMEAL_COOLDOWN);

        //decrement cooldown
        if (0 < current_cooldown) {
            player.setData(FAAttachmentTypes.CROUCH_BONEMEAL_COOLDOWN, current_cooldown - 1);
            return;
        }

        //reset cooldown and try growing
        player.setData(FAAttachmentTypes.CROUCH_BONEMEAL_COOLDOWN, FAConfig.CROUCH_BONEMEAL_COOLDOWN.get());
  
        int range = FAConfig.CROUCH_BONEMEAL_RANGE.get();
        
        BlockPos pos = player.blockPosition();

        for (int x = -range; x <= range; x++) {
            for (int z = -range; z <= range; z++) {
                for (int y = -1; y <= 1; y++) {
                    

                    BlockPos offsetLocation = pos.offset(x, y, z);
                    BlockState offsetState = level.getBlockState(offsetLocation);

                    if (offsetState.isAir() || !offsetState.is(FATags.Blocks.CROUCH_BONEMEAL_WHITELIST)) {
                        continue;
                    }
                    Block block = offsetState.getBlock();
                    if (block instanceof BonemealableBlock bonemealableBlock
                            && bonemealableBlock.isValidBonemealTarget(level, offsetLocation, offsetState)) {
                        Boolean result = BoneMealItem.applyBonemeal(new ItemStack(Items.BONE_MEAL), level, offsetLocation, player);
                        if (result) {
                            int count = 2;
                            switch (bonemealableBlock.getType()) {
                                case NEIGHBOR_SPREADER:
                                    ParticleUtils.spawnParticles(player, offsetLocation, count * 3, 3.0, 1.0, false, ParticleTypes.HAPPY_VILLAGER);
                                    break;
                                case GROWER:
                                    ParticleUtils.spawnParticleInBlock(player, offsetLocation, count, ParticleTypes.HAPPY_VILLAGER);
                                    break;
                            }
                            player.level().playSound(null, offsetLocation, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 0.5F, 1.0F);
                        }
                    }
                }
            }
        }
    }


    
}
