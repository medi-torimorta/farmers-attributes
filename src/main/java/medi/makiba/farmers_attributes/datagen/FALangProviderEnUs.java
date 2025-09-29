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
        addConfig("sneak_bonemeal_range", "Sneak Bonemeal Range");
        addConfig("crouch_bonemeal_cooldown", "Bonemeal Effect Cooldown when holding crouch key");

        // Attributes
        addAttribute("anti_farmland_trampling", "Anti Farmland Trampling", "Prevents farmland trampling.");
        addAttribute("crouch_bonemeal_chance", "Crouch Bonemeal Chance", "Chance (in percent) to apply bonemeal effect when spamming or holding crouch.");

        //tags
        addTag("crouch_bonemeal_whitelist", "Can be bonemealed by crouching");
    }
}
