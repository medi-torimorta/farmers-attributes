package medi.makiba.farmers_attributes.mixin.farmersdelight;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;

import medi.makiba.farmers_attributes.attribute.ShortOrderCooking;
import medi.makiba.farmers_attributes.attribute.ZestyCulinary;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.ItemStackHandler;
import vectorwing.farmersdelight.common.block.entity.SkilletBlockEntity;

@Mixin(SkilletBlockEntity.class)
public class SkilletBlockEntityMixin {
   @Shadow @Final
   private ItemStackHandler inventory;
    /*
     * 1. apply Appetite AoE effect if the blockentity has ZESTY_CULINARY
     * 2. modify cookTime increment based on nearby players' Short Order Cooking attribute
     * base method:
     *private void cookAndOutputItems(ItemStack cookingStack, Level level) {
      ++this.cookingTime; // modify here ( 2 )
      if (this.cookingTime >= this.cookingTimeTotal) {
         Optional<RecipeHolder<CampfireCookingRecipe>> recipe = this.getMatchingRecipe(cookingStack);
         if (recipe.isPresent()) {
            ItemStack resultStack = ((CampfireCookingRecipe)((RecipeHolder)recipe.get()).value()).assemble(new SingleRecipeInput(cookingStack), level.registryAccess());
            Direction direction = ((Direction)this.getBlockState().getValue(SkilletBlock.FACING)).getClockWise();
            //inject here ( 1)
            ItemUtils.spawnItemEntity(level, resultStack.copy(), (double)this.worldPosition.getX() + 0.5, (double)this.worldPosition.getY() + 0.3, (double)this.worldPosition.getZ() + 0.5, (double)((float)direction.getStepX() * 0.08F), 0.25, (double)((float)direction.getStepZ() * 0.08F));
            this.cookingTime = 0;
            this.inventory.extractItem(0, 1, false);
         }
      }

   }
     */
   @Inject(method = "cookAndOutputItems", at = @At(value = "INVOKE", target = "Lvectorwing/farmersdelight/common/utility/ItemUtils;spawnItemEntity(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;DDDDDD)V"))
   public void applyAoeEffect(ItemStack cookingStack, Level level, CallbackInfo ci, @Local(ordinal = 1) ItemStack resultStack) {
      SkilletBlockEntity self = (SkilletBlockEntity)(Object)this;
      ZestyCulinary.checkAoeUponDrop(self, 0, this.inventory.getStackInSlot(0).getCount() <= 1, resultStack);
   }

   @ModifyExpressionValue(method = "cookAndOutputItems", at = @At(value = "FIELD", target = "Lvectorwing/farmersdelight/common/block/entity/SkilletBlockEntity;cookingTime:I", opcode = Opcodes.GETFIELD, ordinal = 0, shift = Shift.AFTER))
   private int modifyCookTimeIncrement(int original, ItemStack cookingStack, Level level) {
      if (!(level instanceof ServerLevel serverLevel)) {
         return original;
      }
      SkilletBlockEntity self = (SkilletBlockEntity)(Object)this;
      return ShortOrderCooking.getModifiedCookTimeIncrement(serverLevel, self, original);
   }

   /*
    * add Data to BE from player attribute
    *base method:
    *public ItemStack addItemToCook(ItemStack addedStack, Player player) {
      Optional<RecipeHolder<CampfireCookingRecipe>> recipe = this.getMatchingRecipe(addedStack);
      if (recipe.isPresent() && this.getStoredStack().isEmpty()) {
         if ((Boolean)this.getBlockState().getValue(SkilletBlock.WATERLOGGED)) {
            player.displayClientMessage(TextUtils.getTranslation("block.skillet.underwater", new Object[0]), true);
            return addedStack;
         }

         boolean wasEmpty = this.getStoredStack().isEmpty();
         ItemStack remainderStack = this.inventory.insertItem(0, addedStack.copy(), false);
         if (!ItemStack.matches(remainderStack, addedStack)) {
            //Inject here
            this.cookingTimeTotal = SkilletBlock.getSkilletCookingTime(((CampfireCookingRecipe)((RecipeHolder)recipe.get()).value()).getCookingTime(), this.fireAspectLevel);
            this.cookingTime = 0;
            if (wasEmpty && this.level != null && this.isHeated(this.level, this.worldPosition)) {
               this.level.playSound((Player)null, (double)((float)this.worldPosition.getX() + 0.5F), (double)((float)this.worldPosition.getY() + 0.5F), (double)((float)this.worldPosition.getZ() + 0.5F), (SoundEvent)ModSounds.BLOCK_SKILLET_ADD_FOOD.get(), SoundSource.BLOCKS, 0.8F, 1.0F);
            }

            return remainderStack;
         }
      } else {
         player.displayClientMessage(TextUtils.getTranslation("block.skillet.invalid_item", new Object[0]), true);
      }

      return addedStack;
   }
    */
   @Inject(method = "addItemToCook", at = @At(value = "INVOKE", target = "Lvectorwing/farmersdelight/common/block/SkilletBlock;getSkilletCookingTime(I I)I"))
   private void addZestyRecord(ItemStack addedStack, Player player, CallbackInfoReturnable<ItemStack> cir) {
      ZestyCulinary.addData(player, 0, (SkilletBlockEntity)(Object)this);
   }

    /*
     * remove Data from BE
     * base method:
     * public ItemStack removeItem() {
     *    //Inject here
     *    return this.inventory.extractItem(0, this.getStoredStack().getMaxStackSize(), false);
     * }
     */
   @Inject(method = "removeItem", at = @At("HEAD"))
   private void removeZestyRecord(CallbackInfoReturnable<ItemStack> cir) {
         ZestyCulinary.addData(0, 0,(SkilletBlockEntity)(Object)this);
   }
   
}
