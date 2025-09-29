package medi.makiba.farmers_attributes;

import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class FAConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue SNEAK_BONEMEAL_RANGE = BUILDER
        .comment("Sneak Bonemeal range (in blocks)")
        .translation("config.farmers_attributes.sneak_bonemeal_range")
        .defineInRange("sneakBonemealRange", 4, 0, 16);

    public static final ModConfigSpec.IntValue CROUCH_BONEMEAL_COOLDOWN = BUILDER
        .comment("Crouch Bonemeal cooldown in ticks, when holding the crouch key")
        .translation("config.farmers_attributes.crouch_bonemeal_cooldown")
        .defineInRange("crouchBonemealCooldown", 20, 0, 600);

    static final ModConfigSpec SPEC = BUILDER.build();
}
