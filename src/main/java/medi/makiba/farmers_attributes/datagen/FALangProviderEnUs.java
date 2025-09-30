package medi.makiba.farmers_attributes.datagen;

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
        addConfig("config.farmers_attributes.appetite_effect_duration_multiplier", "Appetite Effect Duration Multiplier");
        // Attributes
        addAttribute("anti_farmland_trampling", "Anti Farmland Trampling", "Prevents farmland trampling.");
        addAttribute("crouch_bonemeal_chance", "Crouch Bonemeal Chance", "Chance in percent to apply bonemeal effect when spamming or holding crouch.");
        addAttribute("zesty_culinary", "Zesty Culinary", "Applies the Appetite effect when cooking food.");
        // Effects
        addEffect("appetite", "Appetite", "Extends the duration of beneficial effects when eating food.");
        //tags
        addTag("crouch_bonemeal_whitelist", "Can be bonemealed by crouching");
    }
}
