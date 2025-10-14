package medi.makiba.farmers_attributes.datagen;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import medi.makiba.farmers_attributes.FarmersAttributes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = FarmersAttributes.MODID)
public class FADataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        generator.addProvider(event.includeClient(), new FALangProviderEnUs(output));
        generator.addProvider(event.includeClient(), new FALangProviderJaJp(output));
        generator.addProvider(event.includeClient(), new FABlockStateProvider(output, existingFileHelper));
        generator.addProvider(event.includeClient(), new FAItemModelProvider(output, existingFileHelper));
        FABlockTagsProvider blockTags = new FABlockTagsProvider(output, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new FAItemTagsProvider(output, lookupProvider, blockTags.contentsGetter(), existingFileHelper));
        generator.addProvider(event.includeServer(), new FARecipeProvider(output, lookupProvider));
        generator.addProvider(event.includeServer(), new LootTableProvider(output, Collections.emptySet(), List.of(
            new LootTableProvider.SubProviderEntry(FABlockLootSubProvider::new, LootContextParamSets.BLOCK)
        ), lookupProvider));
    }
}

