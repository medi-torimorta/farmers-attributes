package medi.makiba.farmers_attributes.mixin.farmersdelight;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import medi.makiba.farmers_attributes.attribute.ZestyCulinary;
import net.minecraft.world.item.ItemStack;
import vectorwing.farmersdelight.common.block.entity.container.CookingPotResultSlot;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

@Mixin(CookingPotResultSlot.class)
public class CookingPotResultSlotMixin {

    @Shadow
    protected Player player;

    /*
     * apply Appetite effect to self if player has zesty_culinary attribute
     * base method:
     * protected void checkTakeAchievements(ItemStack stack) {
      stack.onCraftedBy(this.player.level(), this.player, this.removeCount);
      if (!this.player.level().isClientSide) {
         this.tileEntity.awardUsedRecipes(this.player, this.tileEntity.getDroppableInventory());
      }

      this.removeCount = 0;
      //inject here
   }
     */

    @Inject(method = "checkTakeAchievements", at = @org.spongepowered.asm.mixin.injection.At("TAIL"))
    private void applyEffect(ItemStack stack, CallbackInfo ci) {
        if (this.player instanceof ServerPlayer serverPlayer && stack.getFoodProperties(serverPlayer) != null) {
            ZestyCulinary.applyAppetiteOnCrafting(serverPlayer);
        }
    }
}
