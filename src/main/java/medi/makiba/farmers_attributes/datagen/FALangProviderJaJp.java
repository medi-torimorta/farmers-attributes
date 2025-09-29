package medi.makiba.farmers_attributes.datagen;

import net.minecraft.data.PackOutput;

public class FALangProviderJaJp extends FALangProviderBase {

    
    public FALangProviderJaJp(PackOutput output) {
        super(output, "ja_jp"); 
    }
    @Override
    protected void addTranslations() {
        // Configs
        addConfigLabels("設定", "Farmer's Attributes", "Farmer's Attributes");
        addConfig("sneak_bonemeal_range", "しゃがみで骨粉効果を適用する範囲");
        addConfig("crouch_bonemeal_cooldown", "しゃがみ長押し時の骨粉効果適用間隔");

        // Attributes
        addAttribute("anti_farmland_trampling", "農地踏み荒らし防止", "農地を踏み荒らしません。");
        addAttribute("crouch_bonemeal_chance", "しゃがみ骨粉効果確率", "しゃがみを連打または長押しすると確率で周囲の植物に骨粉効果を適用します。");
        
        // Tags
        addTag("crouch_bonemeal_whitelist", "しゃがみで成長促進可能");
    }
}
