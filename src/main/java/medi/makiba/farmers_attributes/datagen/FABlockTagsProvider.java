package medi.makiba.farmers_attributes.datagen;

import java.util.concurrent.CompletableFuture;

import medi.makiba.farmers_attributes.FarmersAttributes;
import medi.makiba.farmers_attributes.registry.FATags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import vectorwing.farmersdelight.FarmersDelight;

public class FABlockTagsProvider extends BlockTagsProvider {
    public FABlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, FarmersAttributes.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookupProvider) {
        tag(FATags.Blocks.CROUCH_BONEMEAL_WHITELIST)
            .addTag(BlockTags.CROPS)
            .addTag(BlockTags.SAPLINGS);
        
        tag(FATags.Blocks.DELICIOUS_SMELLING_BLOCKS)
            .add(Blocks.CAKE)
            .addTag(BlockTags.CANDLE_CAKES)
            .addOptional(ResourceLocation.fromNamespaceAndPath(FarmersDelight.MODID, "roast_chicken_block"))
            .addOptional(ResourceLocation.fromNamespaceAndPath(FarmersDelight.MODID, "stuffed_pumpkin_block"))
            .addOptional(ResourceLocation.fromNamespaceAndPath(FarmersDelight.MODID, "honey_glazed_ham_block"))
            .addOptional(ResourceLocation.fromNamespaceAndPath(FarmersDelight.MODID, "shepherds_pie_block"))
            .addOptional(ResourceLocation.fromNamespaceAndPath(FarmersDelight.MODID, "rice_roll_medley_block"));
        
    }
}