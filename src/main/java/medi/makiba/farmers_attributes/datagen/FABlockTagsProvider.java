package medi.makiba.farmers_attributes.datagen;

import java.util.concurrent.CompletableFuture;

import medi.makiba.farmers_attributes.FarmersAttributes;
import medi.makiba.farmers_attributes.registry.FATags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class FABlockTagsProvider extends BlockTagsProvider {
    public FABlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, FarmersAttributes.MODID, existingFileHelper);
    }

    // Add your tag entries here.
    @Override
    protected void addTags(HolderLookup.Provider lookupProvider) {
        // Create a tag builder for our tag. This could also be e.g. a vanilla or NeoForge tag.
            tag(FATags.Blocks.CROUCH_BONEMEAL_WHITELIST)
                .addTags(BlockTags.CROPS, BlockTags.SAPLINGS);
        }
}