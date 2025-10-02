package medi.makiba.farmers_attributes.mixin.farmersdelight;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.sugar.Local;

import medi.makiba.farmers_attributes.attribute.ZestyCulinary;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import org.spongepowered.asm.mixin.injection.At;

import vectorwing.farmersdelight.common.block.CookingPotBlock;

@Mixin(CookingPotBlock.class)
public class CookingPotBlockMixin {
    /*
     * apply Appetite effect to self if player has zesty_culinary attribute
     * base method:
     * public ItemInteractionResult useItemOn(ItemStack heldStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
      if (heldStack.isEmpty() && player.isShiftKeyDown()) {
         level.setBlockAndUpdate(pos, (BlockState)state.setValue(SUPPORT, ((CookingPotSupport)state.getValue(SUPPORT)).equals(CookingPotSupport.HANDLE) ? this.getTrayState(level, pos) : CookingPotSupport.HANDLE));
         level.playSound((Player)null, pos, SoundEvents.LANTERN_PLACE, SoundSource.BLOCKS, 0.7F, 1.0F);
      } else if (!level.isClientSide) {
         BlockEntity tileEntity = level.getBlockEntity(pos);
         if (tileEntity instanceof CookingPotBlockEntity) {
            CookingPotBlockEntity cookingPotEntity = (CookingPotBlockEntity)tileEntity;
            ItemStack servingStack = cookingPotEntity.useHeldItemOnMeal(heldStack);
            if (servingStack != ItemStack.EMPTY) {
               if (!player.getInventory().add(servingStack)) {
                  player.drop(servingStack, false);
               }
                //inject here
               level.playSound((Player)null, pos, (SoundEvent)SoundEvents.ARMOR_EQUIP_GENERIC.value(), SoundSource.BLOCKS, 1.0F, 1.0F);
            } else {
               player.openMenu(cookingPotEntity, pos);
            }
         }

         return ItemInteractionResult.SUCCESS;
      }

      return ItemInteractionResult.SUCCESS;
   }
     */
   @Inject(method = "useItemOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/core/BlockPos;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"))
   private void applyEffect(CallbackInfoReturnable<Void> cir, @Local Player player) {
      if (player instanceof ServerPlayer serverPlayer) {
         ZestyCulinary.applyAppetiteOnCrafting(serverPlayer);
      }
   }
}
