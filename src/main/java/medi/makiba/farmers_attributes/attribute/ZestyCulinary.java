package medi.makiba.farmers_attributes.attribute;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import medi.makiba.farmers_attributes.FAConfig;
import medi.makiba.farmers_attributes.datacomponent.ZestyCulinaryRecord;
import medi.makiba.farmers_attributes.registry.FAAttachmentTypes;
import medi.makiba.farmers_attributes.registry.FAAttributes;
import medi.makiba.farmers_attributes.registry.FAMobEffects;
import medi.makiba.farmers_attributes.registry.FATags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Tuple;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class ZestyCulinary {

    public static Tuple<Integer, Integer> getValues(double attributeValue) {
        int amplifier = (int) Math.floor(attributeValue) -1;
        int duration = (int) (((attributeValue - amplifier -1) * 300 + 30) * 20);
        return new Tuple<>(amplifier, duration);
    }

    public static void applyAppetiteOnCrafting(ServerPlayer player, ItemStack resultItem) {
        if (!isCraftedFood(resultItem)) {
            return;
        }
        double attribute_value = player.getAttributeValue(FAAttributes.ZESTY_CULINARY);
        if (attribute_value >= 1) {
            Tuple<Integer, Integer> values = getValues(attribute_value);
            int amplifier = values.getA();
            int duration = values.getB();
            applyAppetiteSelf(player, duration, amplifier);
        }
    }

    public static void applyAppetiteAoeOnPlacement(LivingEntity entity, ServerLevel level, BlockPos pos, BlockState state) {
        if (!state.is(FATags.Blocks.DELICIOUS_SMELLING_BLOCKS) || !entity.getAttributes().hasAttribute(FAAttributes.ZESTY_CULINARY)) {
            return;
        }
        double attribute_value = entity.getAttributeValue(FAAttributes.ZESTY_CULINARY);
        if (attribute_value >= 1) {
            Tuple<Integer, Integer> values = getValues(attribute_value);
            int amplifier = values.getA();
            int duration = values.getB();
            int range = FAConfig.ZESTY_AOE_RADIUS_PLACE.get();
            applyAppetiteAoe(level, pos, range, duration, amplifier);
        }
    }

    public static void applyAppetiteAoeOnCooking(ServerPlayer player, ItemStack resultItem) {
        if (!isCraftedFood(resultItem)) {
            return;
        }
        double attribute_value = player.getAttributeValue(FAAttributes.ZESTY_CULINARY);
        if (attribute_value >= 1) {
            Tuple<Integer, Integer> values = getValues(attribute_value);
            int amplifier = values.getA();
            int duration = values.getB();
            int range = FAConfig.ZESTY_AOE_RADIUS_PLACE.get();
            ServerLevel level = player.serverLevel();
            BlockPos pos = player.blockPosition();
            applyAppetiteAoe(level, pos, range, duration, amplifier);
        }
    }

    public static void checkAoeUponDrop(BlockEntity blockEntity, int slot, boolean lastInSlot, ItemStack resultItem){
        if (blockEntity.getLevel() instanceof ServerLevel serverLevel && blockEntity.hasData(FAAttachmentTypes.ZESTY_CULINARY)) {
            BlockPos pos = blockEntity.getBlockPos();
            ZestyCulinaryRecord data = blockEntity.getData(FAAttachmentTypes.ZESTY_CULINARY);
            double attributeValue = data.getAttValueForSlot(slot);
            if (attributeValue >= 0 && isCraftedFood(resultItem)) {
                ZestyCulinary.applyAppetiteAoeOnDrop(serverLevel, pos, attributeValue);   
            }
            if (lastInSlot){
                ZestyCulinaryRecord dataAfter = data.updatedWith(slot, 0);
                if (dataAfter == null) {
                    blockEntity.removeData(FAAttachmentTypes.ZESTY_CULINARY);
                } else {
                    blockEntity.setData(FAAttachmentTypes.ZESTY_CULINARY, dataAfter);
                }
            }
        }
    }

    private static void applyAppetiteAoeOnDrop(ServerLevel level, BlockPos blockpos, double attributeValue) {
        if (attributeValue >= 1) {
            int range = FAConfig.ZESTY_AOE_RADIUS_COOK.get();
            Tuple <Integer, Integer> values = getValues(attributeValue);
            int amplifier = values.getA();
            int duration = values.getB();
            applyAppetiteAoe(level, blockpos, range, duration, amplifier);
        }
    }

    public static void applyAppetiteAoe(ServerLevel level, BlockPos blockpos, int range, int duration,
            int amplifier) {
        List<Player> list = Lists.newArrayList();
        for (Player player : level.players()) {
            if (new AABB(blockpos).inflate(range).contains(player.getX(), player.getY(), player.getZ())) {
                list.add(player);
            }
        }
        for (Player target : list) {
            target.addEffect(new MobEffectInstance(FAMobEffects.APPETITE_EFFECT, duration, amplifier));
        }

    }

    public static void applyAppetiteSelf(ServerPlayer player, int duration, int amplifier) {
        player.addEffect(new MobEffectInstance(FAMobEffects.APPETITE_EFFECT, duration, amplifier));
    }

    @SuppressWarnings("null")
    public static void addData(double attributeValue, int slot, BlockEntity be) {
        if (attributeValue >= 1) {
            if (be.hasData(FAAttachmentTypes.ZESTY_CULINARY)) {
                ZestyCulinaryRecord existing = be.getData(FAAttachmentTypes.ZESTY_CULINARY);
                ZestyCulinaryRecord updated = existing.updatedWith(slot, attributeValue);
                be.setData(FAAttachmentTypes.ZESTY_CULINARY, updated);
            }else{
                ZestyCulinaryRecord new_record = new ZestyCulinaryRecord(slot, attributeValue);
                be.setData(FAAttachmentTypes.ZESTY_CULINARY, new_record);
            }
        }else{
            if (be.hasData(FAAttachmentTypes.ZESTY_CULINARY)) {
                ZestyCulinaryRecord existing = be.getData(FAAttachmentTypes.ZESTY_CULINARY);
                ZestyCulinaryRecord updated = existing.updatedWith(slot, 0);
                if (updated == null) {
                    be.removeData(FAAttachmentTypes.ZESTY_CULINARY);
                } else {
                    be.setData(FAAttachmentTypes.ZESTY_CULINARY, updated);
                }
            }
        }
    }

    public static void addData(LivingEntity entity, int slot, BlockEntity be) {
        if (entity.level().isClientSide) {
            return;
        }
        AttributeMap attributes = entity.getAttributes();
        Double attribute_value = attributes.hasAttribute(FAAttributes.ZESTY_CULINARY)
                ? attributes.getValue(FAAttributes.ZESTY_CULINARY)
                : 0;
        addData(attribute_value, slot, be);
    }

    public static void setData(Map<String, Double> slot_amps, BlockEntity be) {
        ZestyCulinaryRecord new_record = new ZestyCulinaryRecord(slot_amps);
        be.setData(FAAttachmentTypes.ZESTY_CULINARY, new_record);
    }

    @SuppressWarnings("null")
    public static boolean isCraftedFood(ItemStack itemStack) {
        return itemStack.getItem().getFoodProperties(itemStack, null) != null && !itemStack.is(FATags.Items.SOURCE_FOODS);
    }

}
