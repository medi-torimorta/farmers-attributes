package medi.makiba.farmers_attributes;

import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class FAConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue CROUCH_BONEMEAL_RANGE;
    public static final ModConfigSpec.IntValue CROUCH_BONEMEAL_COOLDOWN;
    public static final ModConfigSpec.DoubleValue APPETITE_EFFECT_DURATION_MULTIPLIER;
    static final ModConfigSpec SPEC;

    static {
        BUILDER.push("attributes");

        CROUCH_BONEMEAL_RANGE = BUILDER
            .comment("Crouch Bonemeal range in blocks")
            .translation("config.farmers_attributes.crouch_bonemeal_range")
            .defineInRange("crouchBonemealRange", 4, 0, 16);

        CROUCH_BONEMEAL_COOLDOWN = BUILDER
            .comment("Crouch Bonemeal cooldown in ticks, only used when holding the crouch key")
            .translation("config.farmers_attributes.crouch_bonemeal_cooldown")
            .defineInRange("crouchBonemealCooldown", 20, 0, 600);

        BUILDER.pop();
        BUILDER.push("effects");

        APPETITE_EFFECT_DURATION_MULTIPLIER = BUILDER
            .comment("Multiplier for all beneficial food effects when the appetite effect is active when the amplifier > 1.\nthe formula is 1+(value*amplifier)\nE.g. set this to 0.5 for 1.5x duration at amplifier 1.")
            .translation("config.farmers_attributes.appetite_effect_duration_multiplier")
            .defineInRange("appetiteEffectDurationMultiplier", 0.5, 0.0, 10.0);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
