package medi.makiba.farmers_attributes.datagen;


import java.util.concurrent.CompletableFuture;

import medi.makiba.farmers_attributes.FarmersAttributes;
import medi.makiba.farmers_attributes.registry.FAItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public class FARecipeProvider extends RecipeProvider{
    public FARecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}
   @Override
	protected void buildRecipes(RecipeOutput output) {
        largeCropUnpacking(output, FAItems.LARGE_BEETROOT.get(), Items.BEETROOT, 6);
        largeCropUnpacking(output, FAItems.LARGE_CARROT.get(), Items.CARROT, 9);
    }

    public static void largeCropUnpacking(RecipeOutput output, Item largeCropItem, Item result, int count) {
        String large_crop_key = BuiltInRegistries.ITEM.getKey(largeCropItem).getPath();
        String result_key = BuiltInRegistries.ITEM.getKey(result).getPath();
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result, count)
            .requires(largeCropItem)
            .unlockedBy("has_" + large_crop_key, TriggerInstance.hasItems(new ItemLike[]{largeCropItem}))
            .save(output, ResourceLocation.fromNamespaceAndPath(FarmersAttributes.MODID, result_key + "_from_" + large_crop_key));
    }
}
