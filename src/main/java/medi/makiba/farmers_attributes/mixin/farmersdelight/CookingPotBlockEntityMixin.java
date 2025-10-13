package medi.makiba.farmers_attributes.mixin.farmersdelight;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import medi.makiba.farmers_attributes.attribute.ShortOrderCooking;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.crafting.RecipeHolder;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;
import vectorwing.farmersdelight.common.crafting.CookingPotRecipe;

@Mixin(CookingPotBlockEntity.class)
public class CookingPotBlockEntityMixin {
    /*
     * multiply cookTime increment based on nearby players' Short Order Cooking attribute
     * base method:
     * private boolean processCooking(RecipeHolder<CookingPotRecipe> recipe, CookingPotBlockEntity cookingPot) {
         if (this.level == null) {
            return false;
         } else {
            ++this.cookTime;// modify this line
            this.cookTimeTotal = ((CookingPotRecipe)recipe.value()).getCookTime();
            if (this.cookTime < this.cookTimeTotal) {
               return false;
            } else {
               this.cookTime = 0;
               this.mealContainerStack = ((CookingPotRecipe)recipe.value()).getOutputContainer();
               ItemStack resultStack = ((CookingPotRecipe)recipe.value()).assemble(new RecipeWrapper(this.inventory), this.level.registryAccess());
               ItemStack storedMealStack = this.inventory.getStackInSlot(6);
               if (storedMealStack.isEmpty()) {
                  this.inventory.setStackInSlot(6, resultStack.copy());
               } else if (ItemStack.isSameItem(storedMealStack, resultStack)) {
                  storedMealStack.grow(resultStack.getCount());
               }

               cookingPot.setRecipeUsed(recipe);

               for(int i = 0; i < 6; ++i) {
                  ItemStack slotStack = this.inventory.getStackInSlot(i);
                  if (slotStack.hasCraftingRemainingItem()) {
                     this.ejectIngredientRemainder(slotStack.getCraftingRemainingItem());
                  } else if (INGREDIENT_REMAINDER_OVERRIDES.containsKey(slotStack.getItem())) {
                     this.ejectIngredientRemainder(((Item)INGREDIENT_REMAINDER_OVERRIDES.get(slotStack.getItem())).getDefaultInstance());
                  }

                  if (!slotStack.isEmpty()) {
                     slotStack.shrink(1);
                  }
               }
               return true;
            }
         }
      }
     */
   @ModifyExpressionValue(method = "processCooking", at = @At(value = "FIELD", target = "Lvectorwing/farmersdelight/common/block/entity/CookingPotBlockEntity;cookTime:I", opcode = Opcodes.GETFIELD, shift = Shift.AFTER, ordinal = 0))
   private int modifyCookTimeIncrement(int original, RecipeHolder<CookingPotRecipe> recipe, CookingPotBlockEntity instance) {
      if (!(instance.getLevel() instanceof ServerLevel serverLevel)) {
         return original;
      }
      return ShortOrderCooking.getModifiedCookTimeIncrement(serverLevel, instance, original);
   }
}