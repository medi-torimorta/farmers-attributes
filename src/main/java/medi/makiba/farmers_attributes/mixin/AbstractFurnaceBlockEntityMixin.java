package medi.makiba.farmers_attributes.mixin;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import medi.makiba.farmers_attributes.attribute.ShortOrderCooking;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.SmokerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(AbstractFurnaceBlockEntity.class)
public class AbstractFurnaceBlockEntityMixin {
    /*
     * multiply cookTime increment based on nearby players' Short Order Cooking attribute
     * base method:
     * public static void serverTick(Level level, BlockPos pos, BlockState state, AbstractFurnaceBlockEntity blockEntity) {
        boolean flag = blockEntity.isLit();
        boolean flag1 = false;
        if (blockEntity.isLit()) {
            blockEntity.litTime--;
        }

        ItemStack itemstack = blockEntity.items.get(1);
        ItemStack itemstack1 = blockEntity.items.get(0);
        boolean flag2 = !itemstack1.isEmpty();
        boolean flag3 = !itemstack.isEmpty();
        if (blockEntity.isLit() || flag3 && flag2) {
            RecipeHolder<?> recipeholder;
            if (flag2) {
                recipeholder = blockEntity.quickCheck.getRecipeFor(new SingleRecipeInput(itemstack1), level).orElse(null);
            } else {
                recipeholder = null;
            }

            int i = blockEntity.getMaxStackSize();
            if (!blockEntity.isLit() && canBurn(level.registryAccess(), recipeholder, blockEntity.items, i, blockEntity)) {
                blockEntity.litTime = blockEntity.getBurnDuration(itemstack);
                blockEntity.litDuration = blockEntity.litTime;
                if (blockEntity.isLit()) {
                    flag1 = true;
                    if (itemstack.hasCraftingRemainingItem())
                        blockEntity.items.set(1, itemstack.getCraftingRemainingItem());
                    else
                    if (flag3) {
                        Item item = itemstack.getItem();
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            blockEntity.items.set(1, itemstack.getCraftingRemainingItem());
                        }
                    }
                }
            }

            if (blockEntity.isLit() && canBurn(level.registryAccess(), recipeholder, blockEntity.items, i, blockEntity)) {
                blockEntity.cookingProgress++; // modify this line
                if (blockEntity.cookingProgress == blockEntity.cookingTotalTime) {
                    blockEntity.cookingProgress = 0;
                    blockEntity.cookingTotalTime = getTotalCookTime(level, blockEntity);
                    if (burn(level.registryAccess(), recipeholder, blockEntity.items, i, blockEntity)) {
                        blockEntity.setRecipeUsed(recipeholder);
                    }

                    flag1 = true;
                }
            } else {
            ...
     */
    @Shadow
    protected int cookingProgress;
    
    @Shadow
    protected int cookingTotalTime;

    @ModifyExpressionValue(method = "serverTick", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/block/entity/AbstractFurnaceBlockEntity;cookingProgress:I", opcode = Opcodes.GETFIELD, ordinal = 0, shift = Shift.AFTER))
    private static int modifyCookTimeIncrement(int original, Level level, BlockPos pos, BlockState state, AbstractFurnaceBlockEntity instance) {
        if (!(instance instanceof SmokerBlockEntity && level instanceof ServerLevel serverLevel)) {
            return original;
        }
        AbstractFurnaceBlockEntityMixin mixinInstance = (AbstractFurnaceBlockEntityMixin)(Object)instance;
        return Math.clamp(ShortOrderCooking.getModifiedCookTimeIncrement(serverLevel, instance, original), 0, mixinInstance.cookingTotalTime - mixinInstance.cookingProgress);
    }
}
