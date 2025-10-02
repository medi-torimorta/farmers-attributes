package medi.makiba.farmers_attributes.mixin.farmersdelight;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;

import medi.makiba.farmers_attributes.attribute.ZestyCulinary;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import vectorwing.farmersdelight.common.block.entity.container.CookingPotMenu;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CookingPotMenu.class)
public class CookingPotMenuMixin {

    /*
     * quick fix for CookingPot not calling onQuickCraft when shift-clicking output slot
     * while waiting for https://github.com/vectorwing/FarmersDelight/pull/1196 to be merged
     * base method:
     * public ItemStack quickMoveStack(Player playerIn, int index) {
      int indexMealDisplay = 6;
      int indexContainerInput = 7;
      int indexOutput = 8;
      int startPlayerInv = indexOutput + 1;
      int endPlayerInv = startPlayerInv + 36;
      ItemStack slotStackCopy = ItemStack.EMPTY;
      Slot slot = (Slot)this.slots.get(index);
      if (slot.hasItem()) {
         ItemStack slotStack = slot.getItem();
         slotStackCopy = slotStack.copy();
         if (index == indexOutput) {
            if (!this.moveItemStackTo(slotStack, startPlayerInv, endPlayerInv, true)//inject here) {
               return ItemStack.EMPTY;
            }
            
         } else if (index <= indexOutput) {
            if (!this.moveItemStackTo(slotStack, startPlayerInv, endPlayerInv, false)) {
               return ItemStack.EMPTY;
            }
         }...
     */
    @WrapOperation(method = "quickMoveStack", at = @At(value = "INVOKE", target = "Lvectorwing/farmersdelight/common/block/entity/container/CookingPotMenu;moveItemStackTo(Lnet/minecraft/world/item/ItemStack;IIZ)Z", ordinal = 0))
    private boolean onQuickMoveStack(CookingPotMenu instance, ItemStack slotStack, int startIndex, int endIndex, boolean reverseDirection, Operation<Boolean> original, @Local(ordinal = 0) ItemStack slotStackCopy, @Local Player playerIn) {
        System.out.println("Injected quickMoveStack");
        boolean result = original.call(instance, slotStack, startIndex, endIndex, reverseDirection);
        if (result){
            int amount = slotStackCopy.getCount() - slotStack.getCount();
            if (amount > 0) {
                if (playerIn instanceof ServerPlayer serverPlayer) {
                    ZestyCulinary.applyAppetiteOnCrafting(serverPlayer);
                }
            }
        }
        return result;
    }
     
}
