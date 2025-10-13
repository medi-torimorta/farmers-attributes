package medi.makiba.farmers_attributes.attribute;

import java.util.List;

import org.joml.Math;

import medi.makiba.farmers_attributes.FAConfig;
import medi.makiba.farmers_attributes.registry.FAAttributes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;

public class ShortOrderCooking {
    private static double getCookingSpeedMultiplier(ServerLevel level, BlockPos pos){
        int range = FAConfig.SHORT_ORDER_COOKING_RANGE.get();
        AABB area = new AABB(
            pos.getX() - range, pos.getY() - range, pos.getZ() - range,
            pos.getX() + range, pos.getY() + range, pos.getZ() + range);

        double multiplier = 1.0;
        for (Player player : level.players()) {
            if (area.contains(player.getX(), player.getY(), player.getZ())) {
                double attributeValue = player.getAttributeValue(FAAttributes.SHORT_ORDER_COOKING);
                multiplier *= attributeValue;
            }
        }
        return multiplier;
    }

    private static int multiplyCookTimeIncrement(int originalCookTimeInc, double multiplier, Level level) {
        double newCookTime = originalCookTimeInc * multiplier;
        int integerPart = (int) Math.floor(newCookTime);
        double fractionalPart = newCookTime - integerPart;
        if (level.getRandom().nextDouble() < fractionalPart) {
            integerPart += 1;
        }
        return integerPart;
    }

    public static int getModifiedCookTimeIncrement(ServerLevel level, BlockEntity entity, int originalCookTimeInc) {
        double multiplier = getCookingSpeedMultiplier(level, entity.getBlockPos());
        return multiplyCookTimeIncrement(originalCookTimeInc, multiplier, level);
    }

    public static int getModifiedCookingTimeDuration(ServerPlayer player, int originalCookingTime) {
        double attributeValue = player.getAttributeValue(FAAttributes.SHORT_ORDER_COOKING);
        return attributeValue <= 0.001 ? originalCookingTime * 1000 : (int) Math.round(originalCookingTime / attributeValue);
    }
}
