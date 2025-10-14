package medi.makiba.farmers_attributes.datagen;

import medi.makiba.farmers_attributes.registry.FABlocks;
import medi.makiba.farmers_attributes.registry.FAItems;
import net.minecraft.data.PackOutput;

public class FALangProviderEnUs extends FALangProviderBase {
    public FALangProviderEnUs(PackOutput output) {
        super(output, "en_us");
    }

    @Override
    protected void addTranslations() {
        // Configs
        addConfigTitle("Configs");
        addConfigLabelsServer("Farmer's Attributes", "Farmer's Attributes");
        addConfigGroup("attributes", "Attributes");
        addConfigGroup("effects", "Effects");

        addConfigGroup("crouch_bonemeal_chance", "Crouch Bonemeal Chance");
        addConfig("crouch_bonemeal_range", "Range");
        addConfig("crouch_bonemeal_cooldown", "Cooldown");

        addConfig("appetite_effect_duration_multiplier", "Duration Multiplier");

        addConfigGroup("zesty_culinary", "Zesty Culinary");
        addConfig("zesty_aoe_radius_cook", "Campfire AoE Radius");
        addConfig("zesty_aoe_radius_place", "Placement AoE Radius");

        addConfigGroup("green_thumb", "Green Thumb");
        addConfig("green_thumb_blacklist", "Blacklisted Crops");
        addConfig("green_thumb_large_crops_allowed", "Allowed Large Crops");
        addConfig("green_thumb_drop_multiplier", "Drop Multiplier");
        
        addConfigGroup("anti_farmland_trampling", "Anti Farmland Trampling");
        addConfig("force_anti_farmland_trampling", "Force enabled for everyone");

        addConfigGroup("easy_harvest", "Easy Harvest");
        addConfig("force_easy_harvest", "Force enabled for everyone");
        addConfig("easy_harvest_blacklist", "Blacklisted Crops");
        addConfig("easy_harvest_whole_plant", "Whole-plant Harvesting");

        addConfigGroup("short_order_cooking", "Short Order Cooking");
        addConfig("short_order_cooking_range", "Effect range");

        //tooltips
        addTooltip("weapon_bonus", "Farmer's Damage");
        addTooltip("armor_bonus", "Farmer's Armor");
        
        // Attributes
        addAttribute("anti_farmland_trampling", "Anti Farmland Trampling", "Prevents farmland trampling.");
        addAttribute("crouch_bonemeal_chance", "Crouch Bonemeal Chance", "Chance to apply bonemeal effect when spamming or holding crouch.");
        addAttribute("zesty_culinary", "Zesty Culinary", "Applies the Appetite effect when cooking food. When cooking with campfires and some other cooking blocks, or placing food as blocks, applies the effect to nearby players as well.");
        addAttribute("green_thumb", "Green Thumb", "Chance to grow crops into large variants, or increase their drop.");
        addAttribute("easy_harvest", "Easy Harvest", "Allows quick harvesting by right-clicking fully grown crops, and replant if seeds are available.");
        addAttribute("short_order_cooking", "Short Order Cooking", "Multiplier for cooking time in cooking stations.");
        addAttribute("farmers_weapon", "Farmer's Weapon", "Bonus damage value for weapons tagged as Farmer's Weapon.");
        addAttribute("farmers_armor", "Farmer's Armor", "Bonus armor for armor pieces tagged as Farmer's Armor.");

        // Effects
        addEffect("appetite", "Appetite", "Never get full, and extends the duration of beneficial effects when eating food at higher levels.");
        
        // Tags
        addTag("crouch_bonemeal_whitelist", "Can be bonemealed by crouching");
        addTag("delicious_smelling_blocks", "Delicious Smelling Blocks");
        addTag("source_foods", "Source Foods");
        addTag("farmers_weapon", "Farmer's Weapon");
        addTag("farmers_armor", "Farmer's Armor");

        // Blocks
        addBlock(FABlocks.LARGE_BEETROOT, "Large Beetroot");
        addBlock(FABlocks.LARGE_CARROT, "Large Carrot");

        // Items
        addItem(FAItems.SHARP_CARROT, "Sharp Carrot");
    }
}
