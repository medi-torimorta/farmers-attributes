package medi.makiba.farmers_attributes.datagen;

import java.util.concurrent.CompletableFuture;

import medi.makiba.farmers_attributes.FarmersAttributes;
import medi.makiba.farmers_attributes.registry.FATags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.tag.ModTags;

public class FAItemTagsProvider extends ItemTagsProvider {
    public FAItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, FarmersAttributes.MODID, existingFileHelper);
    }


    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(Provider provider) {
        tag(FATags.Items.SOURCE_FOODS)
            .addTags(Tags.Items.CROPS, Tags.Items.SEEDS, Tags.Items.FOODS_FRUIT, Tags.Items.FOODS_VEGETABLE, Tags.Items.FOODS_RAW_MEAT, Tags.Items.FOODS_RAW_FISH)
            .add(Items.ROTTEN_FLESH, Items.SPIDER_EYE, Items.HONEY_BOTTLE);
        tag(FATags.Items.FARMERS_WEAPON)
            .addTag(ItemTags.HOES)
            .addOptionalTag(ModTags.KNIVES)
            .addOptional(ResourceLocation.fromNamespaceAndPath(FarmersDelight.MODID, "skillet"));
        tag(FATags.Items.FARMERS_ARMOR)
            .add(Items.CARVED_PUMPKIN)
            .addOptional(ResourceLocation.fromNamespaceAndPath("farmers_wearable_cooking_pot", "cooking_pot_helmet_helmet"));
    }
}