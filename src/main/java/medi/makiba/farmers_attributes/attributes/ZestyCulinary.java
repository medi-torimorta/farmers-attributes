package medi.makiba.farmers_attributes.attributes;

import java.util.List;

import com.google.common.collect.Lists;

import medi.makiba.farmers_attributes.registry.FAAttributes;
import medi.makiba.farmers_attributes.registry.FAMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;

import net.minecraft.world.phys.AABB;

public class ZestyCulinary {


    public static void apply_appetite_on_crafting(ServerPlayer player) {
        int attribute_value = (int) player.getAttributeValue(FAAttributes.ZESTY_CULINARY);
        if (attribute_value > 0) {
            int duration = 600; //30 seconds
            int amplifier = attribute_value;
            apply_appetite_self(player, duration, amplifier);
        }
    }

    public static void apply_aoe_appetite_on_crafting(ServerPlayer player) {
        int attribute_value = (int) player.getAttributeValue(FAAttributes.ZESTY_CULINARY);
        if (attribute_value > 0) {
            int duration = 600; //30 seconds
            int amplifier = attribute_value;
            apply_appetite_aoe(player.serverLevel(), player.blockPosition(), 8, duration, amplifier);
        }
    }

    public static void apply_aoe_appetite_on_drop(ServerLevel level, BlockPos blockpos, int amplifier_value) {
        if (amplifier_value > 0) {
            int range = 8;
            int duration = 600; //30 seconds
            int amplifier = amplifier_value;
            apply_appetite_aoe(level, blockpos, range, duration, amplifier);
        }
    }


    public static void apply_appetite_aoe(ServerLevel level, BlockPos blockpos, int range, int duration, int amplifier) {
        List<ServerPlayer> list = Lists.newArrayList();

        for (ServerPlayer player : level.players()) {
            if (new AABB(blockpos).inflate(range).contains(player.getX(), player.getY(), player.getZ())) {
                list.add(player);
            }
        }
        for (ServerPlayer target : list) {
            target.addEffect(new MobEffectInstance(FAMobEffects.APPETITE_EFFECT, duration, amplifier));
        }

    }

    public static void apply_appetite_self(ServerPlayer player, int duration, int amplifier) {
        player.addEffect(new MobEffectInstance(FAMobEffects.APPETITE_EFFECT, duration, amplifier));
    }
}
