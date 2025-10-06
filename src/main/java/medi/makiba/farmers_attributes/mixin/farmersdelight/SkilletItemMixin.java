package medi.makiba.farmers_attributes.mixin.farmersdelight;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.sugar.Local;

import medi.makiba.farmers_attributes.attribute.ZestyCulinary;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import vectorwing.farmersdelight.common.item.SkilletItem;

@Mixin(SkilletItem.class)
public class SkilletItemMixin {
    /*
     * add aoe appetite effect when cooking is finished
     * base method:
     * public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
      if (entity instanceof Player player) {
         ItemStackWrapper storedStack = (ItemStackWrapper)stack.getOrDefault(ModDataComponents.SKILLET_INGREDIENT, ItemStackWrapper.EMPTY);
         if (!storedStack.getStack().isEmpty()) {
            ItemStack cookingStack = storedStack.getStack();
            Optional<RecipeHolder<CampfireCookingRecipe>> cookingRecipe = getCookingRecipe(cookingStack, level);
            cookingRecipe.ifPresent((recipe) -> {
               ItemStack resultStack = ((CampfireCookingRecipe)recipe.value()).assemble(new SingleRecipeInput(cookingStack), level.registryAccess());
               //inject here
               if (!player.getInventory().add(resultStack)) {
                  player.drop(resultStack, false);
               }

               if (player instanceof ServerPlayer) {
                  CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)player, stack);
               }

            });
            stack.remove(ModDataComponents.SKILLET_INGREDIENT);
            stack.remove(ModDataComponents.COOKING_TIME_LENGTH);
         }
      }

      return stack;
   }
     */

   @Inject(method = "lambda$finishUsingItem$0", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/item/crafting/CampfireCookingRecipe;assemble(Lnet/minecraft/world/item/crafting/SingleRecipeInput;Lnet/minecraft/core/HolderLookup$Provider;)Lnet/minecraft/world/item/ItemStack;"))
   private static void checkForAppetiteAoe(CallbackInfo ci, @Local Player player, @Local(ordinal = 2) ItemStack stack) {
      if (player instanceof ServerPlayer serverPlayer) {
         ZestyCulinary.applyAppetiteAoeOnCooking(serverPlayer, stack);
      }
   }
}
