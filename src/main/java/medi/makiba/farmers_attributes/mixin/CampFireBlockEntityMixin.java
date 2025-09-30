package medi.makiba.farmers_attributes.mixin;

import java.util.logging.Logger;

import javax.annotation.Nullable;

import org.jline.utils.Log;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.jcraft.jorbis.Block;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;

import medi.makiba.farmers_attributes.attributes.ZestyCulinary;
import medi.makiba.farmers_attributes.datacomponents.ZestyAmplifierRecord;
import medi.makiba.farmers_attributes.registry.FAAttributes;
import medi.makiba.farmers_attributes.registry.FADataComponentTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;


@Mixin(CampfireBlockEntity.class)
public class CampFireBlockEntityMixin {
    /*
    * remove or modify ZESTY_AMPLIFIER if existent, and apply effect to players in range
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
                            //inject here: if itemstack1 is food and itemstack has ZESTY_AMPLIFIER, apply appetite effect to players in range
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
    private static void onCookTick(CallbackInfo ci, @Local(ordinal = 0) LocalRef<ItemStack> itemstack, @Local(ordinal = 1) LocalRef<ItemStack> itemstack1, @Local LocalRef<Level> level, @Local LocalRef<BlockPos> pos) {
        System.out.println("Campfire cookTick: cooked item");
        if (!(level.get() instanceof ServerLevel serverLevel)) {
            return;
        }
        ZestyAmplifierRecord amplifier = itemstack.get().get(FADataComponentTypes.ZESTY_AMPLIFIER);
        if (amplifier != null && itemstack1.get().getFoodProperties(null) != null) {
            ZestyCulinary.apply_aoe_appetite_on_drop(serverLevel, pos.get(), amplifier.value());
        }
    }


    /*
     * inject into placeFood to add ZESTY_AMPLIFIER when food is placed on campfire
     * base method:
     * public boolean placeFood(@Nullable LivingEntity entity, ItemStack food, int cookTime) {
        for (int i = 0; i < this.items.size(); i++) {
            ItemStack itemstack = this.items.get(i);
            if (itemstack.isEmpty()) {
                this.cookingTime[i] = cookTime;
                this.cookingProgress[i] = 0;
                
                this.items.set(i, food.consumeAndReturn(1, entity));//modify the result of food.consumeAndReturn(1, entity) here to add ZESTY_AMPLIFIER to food
                this.level.gameEvent(GameEvent.BLOCK_CHANGE, this.getBlockPos(), GameEvent.Context.of(entity, this.getBlockState()));
                this.markUpdated();
                return true;
            }
        }
        return false;
     * }
     */
    @ModifyVariable(method = "Lnet/minecraft/world/level/block/entity/CampfireBlockEntity;placeFood(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;I)Z", at = @At(value = "STORE"), ordinal = 1)
    private ItemStack modifyPlaceFood(ItemStack food, @Local LocalRef<LivingEntity> entity) {
        if (entity.get() != null && !entity.get().level().isClientSide) {
            AttributeMap attributes = entity.get().getAttributes();
            Double attribute_value = attributes.hasAttribute(FAAttributes.ZESTY_CULINARY) ? attributes.getValue(FAAttributes.ZESTY_CULINARY) : -1;
            if (attribute_value > 0 && food.getFoodProperties(null) != null) {
                food.set(FADataComponentTypes.ZESTY_AMPLIFIER, new ZestyAmplifierRecord(attribute_value.intValue()));
            }
        }
        return food;
    }
}
