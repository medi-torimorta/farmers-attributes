package medi.makiba.farmers_attributes.datagen;

import medi.makiba.farmers_attributes.registry.FABlocks;
import net.minecraft.data.PackOutput;

public class FALangProviderEnUs extends FALangProviderBase {
    public FALangProviderEnUs(PackOutput output) {
        super(output, "en_us");
    }

    @Override
    protected void addTranslations() {
        // Configs
        addConfigLabels("Configs", "Farmer's Attributes", "Farmer's Attributes");
        addConfig("crouch_bonemeal_range", "Crouch Bonemeal Range");
        addConfig("crouch_bonemeal_cooldown", "Bonemeal Effect Cooldown when holding crouch key");
        addConfig("appetite_effect_duration_multiplier", "Appetite Effect Duration Multiplier");
        addConfig("zesty_aoe_radius_cook", "Appetite Effect AoE Radius from Campfire Cooking");
        addConfig("zesty_aoe_radius_place", "Appetite Effect AoE Radius from Food Placement");
        
        // Attributes
        addAttribute("anti_farmland_trampling", "Anti Farmland Trampling", "Prevents farmland trampling.");
        addAttribute("crouch_bonemeal_chance", "Crouch Bonemeal Chance", "Chance to apply bonemeal effect when spamming or holding crouch.");
        addAttribute("zesty_culinary", "Zesty Culinary", "Applies the Appetite effect when cooking food. When cooking with campfires and some other cooking blocks, or placing food as blocks, applies the effect to nearby players as well.");
        addAttribute("green_thumb", "Green Thumb", "Chance to grow crops into large variants, or increase their drop.");

        // Effects
        addEffect("appetite", "Appetite", "Never get full, and extends the duration of beneficial effects when eating food at higher levels.");
        // Tags
        addTag("crouch_bonemeal_whitelist", "Can be bonemealed by crouching");
        addTag("delicious_smelling_blocks", "Delicious Smelling Blocks");
        addTag("source_foods", "Source Foods");

        // Blocks
        addBlock(FABlocks.LARGE_BEETROOT, "Large Beetroot");
    }
}
