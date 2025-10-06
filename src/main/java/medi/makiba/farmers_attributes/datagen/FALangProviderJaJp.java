package medi.makiba.farmers_attributes.datagen;

import medi.makiba.farmers_attributes.registry.FABlocks;
import net.minecraft.data.PackOutput;

public class FALangProviderJaJp extends FALangProviderBase {

    
    public FALangProviderJaJp(PackOutput output) {
        super(output, "ja_jp"); 
    }
    @Override
    protected void addTranslations() {
        // Configs
        addConfigLabels("設定", "Farmer's Attributes", "Farmer's Attributes");
        addConfig("crouch_bonemeal_range", "しゃがみで骨粉効果を適用する範囲");
        addConfig("crouch_bonemeal_cooldown", "しゃがみ長押し時の骨粉効果適用間隔");
        addConfig("appetite_effect_duration_multiplier", "食欲エフェクトによる持続時間倍率");
        addConfig("zesty_aoe_radius_cook", "焚き火で調理した際の食欲エフェクト発生半径");
        addConfig("zesty_aoe_radius_place", "食べ物設置時の食欲エフェクト発生半径");
        // Attributes
        addAttribute("anti_farmland_trampling", "農地踏み荒らし防止", "農地を踏み荒らしません。");
        addAttribute("crouch_bonemeal_chance", "しゃがみ骨粉効果確率", "しゃがみを連打または長押しすると確率で周囲の植物に骨粉効果を適用します。");
        addAttribute("zesty_culinary", "料理上手", "食べ物を調理すると食欲エフェクトを付与します。焚き火調理などの一部の調理法や、食べ物をブロックとして設置した際には周囲のプレイヤーにもエフェクトを付与します。");
        addAttribute("green_thumb", "グリーンサム", "作物を大きく成長させたり、ドロップ量を増やしたりする確率。");
 
        // Effects
        addEffect("appetite", "食欲増進", "満腹でも食事ができ、高レベルでは有益な食事効果の持続時間が延びます。");
        // Tags
        addTag("crouch_bonemeal_whitelist", "しゃがみで成長促進可能");
        addTag("delicious_smelling_blocks", "美味しそうな匂いのするブロック");
        addTag("source_foods", "原材料の食べ物");
        // Blocks
        addBlock(FABlocks.LARGE_BEETROOT, "大きなビートルート");
    }
}
