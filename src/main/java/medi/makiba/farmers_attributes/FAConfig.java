package medi.makiba.farmers_attributes;

import java.util.List;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class FAConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue FORCE_ANTI_FARMLAND_TRAMPLING;
    public static final ModConfigSpec.BooleanValue FORCE_EASY_HARVEST;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> EASY_HARVEST_BLACKLIST;
    public static final ModConfigSpec.BooleanValue EASY_HARVEST_WHOLE_PLANT;
    public static final ModConfigSpec.IntValue CROUCH_BONEMEAL_RANGE;
    public static final ModConfigSpec.IntValue CROUCH_BONEMEAL_COOLDOWN;
    public static final ModConfigSpec.IntValue ZESTY_AOE_RADIUS_COOK;
    public static final ModConfigSpec.IntValue ZESTY_AOE_RADIUS_PLACE;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> GREEN_THUMB_BLACKLIST;
    public static final ModConfigSpec.DoubleValue APPETITE_EFFECT_DURATION_MULTIPLIER;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> GREEN_THUMB_LARGE_CROPS_ALLOWED;
    public static final ModConfigSpec.IntValue GREEN_THUMB_DROP_MULTIPLIER;

    static final ModConfigSpec SPEC;

    static {
        BUILDER.push("attributes");

        BUILDER.push("anti_farmland_trampling");
        FORCE_ANTI_FARMLAND_TRAMPLING = BUILDER
            .comment("Force Anti Farmland Trampling to always be active for all players, ignoring the attribute level.")
            .translation("config.farmers_attributes.force_anti_farmland_trampling")
            .define("forceAntiFarmlandTrampling", false);
        BUILDER.pop();

        BUILDER.push("easy_harvest");
        FORCE_EASY_HARVEST = BUILDER
            .comment("Force Easy Harvest to always be active for all players, ignoring the attribute level.")
            .translation("config.farmers_attributes.force_easy_harvest")
            .define("forceEasyHarvest", false);
        
        EASY_HARVEST_BLACKLIST = BUILDER
            .comment("Blocks that are blacklisted from the Easy Harvesting. Use registry names, e.g. minecraft:wheat")
            .translation("config.farmers_attributes.easy_harvest_blacklist")
            .defineListAllowEmpty("easy_harvest_blacklist", List.of("minecraft:torchflower_crop"), () -> "", FAConfig::validateBlockName);
        
        EASY_HARVEST_WHOLE_PLANT = BUILDER
            .comment("If true, Easy Harvest will harvest the whole plant above the targeted block. Works for sugar canes and kelp-likes.")
            .translation("config.farmers_attributes.easy_harvest_whole_plant")
            .define("easyHarvestWholePlant", true);
        BUILDER.pop();

        BUILDER.push("crouch_bonemeal_chance");
        CROUCH_BONEMEAL_RANGE = BUILDER
            .comment("Crouch Bonemeal range in blocks")
            .translation("config.farmers_attributes.crouch_bonemeal_range")
            .defineInRange("crouchBonemealRange", 4, 0, 16);

        CROUCH_BONEMEAL_COOLDOWN = BUILDER
            .comment("Crouch Bonemeal cooldown in ticks, only used when holding the crouch key")
            .translation("config.farmers_attributes.crouch_bonemeal_cooldown")
            .defineInRange("crouchBonemealCooldown", 20, 0, 600);
        
        BUILDER.pop();

        BUILDER.push("zesty_culinary");
        ZESTY_AOE_RADIUS_COOK = BUILDER
            .comment("Radius in blocks for the appetite effect AoE from campfire cooking")
            .translation("config.farmers_attributes.zesty_aoe_radius_cook")
            .defineInRange("zestyAoeRadiusCook", 8, 0, 32);
        
        ZESTY_AOE_RADIUS_PLACE = BUILDER
            .comment("Radius in blocks for the appetite effect AoE from food placement")
            .translation("config.farmers_attributes.zesty_aoe_radius_place")
            .defineInRange("zestyAoeRadiusPlace", 5, 0, 32);
        
        BUILDER.pop();

        BUILDER.push("green_thumb");
        GREEN_THUMB_BLACKLIST = BUILDER
            .comment("Blocks that are blacklisted from the Green Thumb attribute, for both large crops and drop increase. Use registry names, e.g. minecraft:wheat")
            .translation("config.farmers_attributes.green_thumb_blacklist")
            .defineListAllowEmpty("green_thumb_blacklist", List.of("minecraft:torchflower_crop"), () -> "", FAConfig::validateBlockName);
        
        GREEN_THUMB_LARGE_CROPS_ALLOWED = BUILDER
            .comment("Blocks that are allowed to be converted into large crops by the Green Thumb attribute. Includes all available large crops by default, remove as needed.")
            .translation("config.farmers_attributes.green_thumb_large_crops_allowed")
            .defineListAllowEmpty("green_thumb_large_crops_allowed", List.of("minecraft:beetroots", "minecraft:carrots"), () -> "", FAConfig::validateBlockName);

        GREEN_THUMB_DROP_MULTIPLIER = BUILDER
            .comment("Used for crops not listed above. Multiplier for the crop drops which are affected by Green Thumb. set 1 to disable.")
            .translation("config.farmers_attributes.green_thumb_drop_multiplier")
            .defineInRange("green_thumb_drop_multiplier", 2, 1, 10);
        BUILDER.pop();

        BUILDER.pop();
        BUILDER.push("effects");

        APPETITE_EFFECT_DURATION_MULTIPLIER = BUILDER
            .comment("Multiplier for all beneficial food effects when the appetite effect is active when the amplifier > 1.\nthe formula is 1+(value*amplifier)\nE.g. set this to 0.5 for 1.5x duration at amplifier 1.")
            .translation("config.farmers_attributes.appetite_effect_duration_multiplier")
            .defineInRange("appetiteEffectDurationMultiplier", 0.5, 0.0, 10.0);
        
        BUILDER.pop();
        SPEC = BUILDER.build();

    }
    private static boolean validateBlockName(final Object obj) {
        return obj instanceof String blockName && BuiltInRegistries.BLOCK.containsKey(ResourceLocation.parse(blockName));
    }
}
