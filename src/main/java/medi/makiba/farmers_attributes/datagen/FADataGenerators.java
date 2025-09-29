package medi.makiba.farmers_attributes.datagen;

import java.util.concurrent.CompletableFuture;

import medi.makiba.farmers_attributes.FarmersAttributes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
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
        generator.addProvider(event.includeServer(), new FABlockTagsProvider(output, lookupProvider, existingFileHelper));
    }
}

