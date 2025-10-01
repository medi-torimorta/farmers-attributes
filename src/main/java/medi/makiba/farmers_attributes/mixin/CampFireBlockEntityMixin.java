package medi.makiba.farmers_attributes.mixin;

import java.text.AttributedCharacterIterator.Attribute;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;

import medi.makiba.farmers_attributes.attribute.ZestyCulinary;
import medi.makiba.farmers_attributes.datacomponents.ZestyCulinaryRecord;
import medi.makiba.farmers_attributes.registry.FAAttachmentTypes;
import medi.makiba.farmers_attributes.registry.FAAttributes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;


@Mixin(CampfireBlockEntity.class)
public class CampFireBlockEntityMixin {
    /*
    * remove or modify ZESTY_CULINARY if existent, and apply effect to players in range
    * 
    * base method:
    *   public static void cookTick(Level level, BlockPos pos, BlockState state, CampfireBlockEntity blockEntity) {
            boolean flag = false;

            for (int i = 0; i < blockEntity.items.size(); i++) {
                ItemStack itemstack = blockEntity.items.get(i);
                if (!itemstack.isEmpty()) {
                    flag = true;
                    blockEntity.cookingProgress[i]++;
                    if (blockEntity.cookingProgress[i] >= blockEntity.cookingTime[i]) {
                        SingleRecipeInput singlerecipeinput = new SingleRecipeInput(itemstack);
                        ItemStack itemstack1 = blockEntity.quickCheck
                            .getRecipeFor(singlerecipeinput, level)
                            .map(p_344662_ -> p_344662_.value().assemble(singlerecipeinput, level.registryAccess()))
                            .orElse(itemstack);
                        if (itemstack1.isItemEnabled(level.enabledFeatures())) {
                            //inject here to apply effect to players in range if slot i has ZESTY_CULINARY
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
    private static void checkForAppetiteAoe(CallbackInfo ci, @Local int i, @Local Level level, @Local BlockPos pos, @Local LocalRef<CampfireBlockEntity> blockEntity) {
        System.out.println("Campfire cookTick: cooked item");
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }
        CampfireBlockEntity cbe = blockEntity.get();
        if (cbe.hasData(FAAttachmentTypes.ZESTY_CULINARY)) {
            ZestyCulinaryRecord cbeData = cbe.getData(FAAttachmentTypes.ZESTY_CULINARY);
            double attributeValue = cbeData.getAttValueForSlot(i);
            if (attributeValue >= 0) {
                ZestyCulinary.applyAppetiteAoeOnDrop(serverLevel, pos, attributeValue);
            }
            //remove ZESTY_CULINARY for the slot
            cbeData = cbeData.updatedWith(i, 0);
            if (cbeData == null) {
                cbe.removeData(FAAttachmentTypes.ZESTY_CULINARY);
            } else {
                cbe.setData(FAAttachmentTypes.ZESTY_CULINARY, cbeData);
            }
            blockEntity.set(cbe);
        }

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
        if (entity != null && !entity.level().isClientSide) {

            LivingEntity le = entity;
            AttributeMap attributes = le.getAttributes();
            if (attributes.hasAttribute(FAAttributes.ZESTY_CULINARY)) {
                int attributeValue = (int) attributes.getValue(FAAttributes.ZESTY_CULINARY);
                if (attributeValue >= 1) {
                    ZestyCulinary.addData(le, i, (BlockEntity)(Object)this);
                }
            }
        }
    }
}
