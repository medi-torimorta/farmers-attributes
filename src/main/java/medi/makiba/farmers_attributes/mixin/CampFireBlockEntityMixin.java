package medi.makiba.farmers_attributes.mixin;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;

import medi.makiba.farmers_attributes.attribute.ShortOrderCooking;
import medi.makiba.farmers_attributes.attribute.ZestyCulinary;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;
import vectorwing.farmersdelight.common.crafting.CookingPotRecipe;


@Mixin(CampfireBlockEntity.class)
public class CampFireBlockEntityMixin {
    /*
    * 1. remove or modify ZESTY_CULINARY if existent, and apply effect to players in range
    * 2. modify cookTime increment based on nearby players' Short Order Cooking attribute
    * base method:
    *   public static void cookTick(Level level, BlockPos pos, BlockState state, CampfireBlockEntity blockEntity) {
            boolean flag = false;

            for (int i = 0; i < blockEntity.items.size(); i++) {
                ItemStack itemstack = blockEntity.items.get(i);
                if (!itemstack.isEmpty()) {
                    flag = true;
                    blockEntity.cookingProgress[i]++; // modify here ( 2 )
                    if (blockEntity.cookingProgress[i] >= blockEntity.cookingTime[i]) {
                        SingleRecipeInput singlerecipeinput = new SingleRecipeInput(itemstack);
                        ItemStack itemstack1 = blockEntity.quickCheck
                            .getRecipeFor(singlerecipeinput, level)
                            .map(p_344662_ -> p_344662_.value().assemble(singlerecipeinput, level.registryAccess()))
                            .orElse(itemstack);
                        if (itemstack1.isItemEnabled(level.enabledFeatures())) {
                            //inject here to apply effect to players in range if slot i has ZESTY_CULINARY ( 1 )
                            Containers.dropItemStack(level, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), itemstack1);
                            blockEntity.items.set(i, ItemStack.EMPTY);
                            level.sendBlockUpdated(pos, state, state, 3);
                            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(state));
                        }
                    }
                }
            }

            if (flag) {
                setChanged(level, pos, state);
            }
        }
    */
    @Inject(method = "Lnet/minecraft/world/level/block/entity/CampfireBlockEntity;cookTick(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/entity/CampfireBlockEntity;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/Containers;dropItemStack(Lnet/minecraft/world/level/Level;DDDLnet/minecraft/world/item/ItemStack;)V"))
    private static void checkForAppetiteAoe(CallbackInfo ci, @Local int i, @Local CampfireBlockEntity cbe, @Local(ordinal = 1) ItemStack itemstack1) {
        ZestyCulinary.checkAoeUponDrop(cbe, i, true, itemstack1);
    }

    @ModifyExpressionValue(method = "cookTick", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/block/entity/CampfireBlockEntity;cookingProgress:[I", opcode = Opcodes.GETFIELD, ordinal = 0, shift = At.Shift.BY, by = 4))
    private static int modifyCookTimeIncrement(int original, Level level, BlockPos pos, BlockState state, CampfireBlockEntity instance) {
      if (!(level instanceof ServerLevel serverLevel)) {
         return original;
      }
      return ShortOrderCooking.getModifiedCookTimeIncrement(serverLevel, instance, original);
   }

    /*
     * inject into placeFood to add ZESTY_CULINARY when food is placed on campfire
     * base method:
     * public boolean placeFood(@Nullable LivingEntity entity, ItemStack food, int cookTime) {
        for (int i = 0; i < this.items.size(); i++) {
            ItemStack itemstack = this.items.get(i);
            if (itemstack.isEmpty()) {
                this.cookingTime[i] = cookTime;
                this.cookingProgress[i] = 0;
                this.items.set(i, food.consumeAndReturn(1, entity));
                this.level.gameEvent(GameEvent.BLOCK_CHANGE, this.getBlockPos(), GameEvent.Context.of(entity, this.getBlockState()));
                //insert here to add ZESTY_CULINARY to the slot i if entity has ZESTY_CULINARY attribute
                this.markUpdated();
                return true;
            }
        }
        return false;
     * }
     */
    @Inject(method = "Lnet/minecraft/world/level/block/entity/CampfireBlockEntity;placeFood(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;I)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/CampfireBlockEntity;markUpdated()V"))
    private void addZestyRecord(CallbackInfoReturnable<Void> cir, @Local LivingEntity entity, @Local(ordinal = 1) int i) {
        if (entity != null) {
            ZestyCulinary.addData(entity, i, (BlockEntity)(Object)this);
        }
    }
}
