package medi.makiba.farmers_attributes;

import org.slf4j.Logger;

import medi.makiba.farmers_attributes.registry.FAAttachmentTypes;
import medi.makiba.farmers_attributes.registry.FAAttributes;
import medi.makiba.farmers_attributes.registry.FAMobEffects;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;


// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(FarmersAttributes.MODID)
public class FarmersAttributes {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "farmers_attributes";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    
    

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public FarmersAttributes(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        FAAttributes.ATTRIBUTES.register(modEventBus);
        FAAttachmentTypes.ATTACHMENT_TYPES.register(modEventBus);
        FAMobEffects.MOB_EFFECTS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (FarmersAttributes) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);


        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, FAConfig.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        if (ModList.get().isLoaded("farmersdelight")) {
            LOGGER.info("Farmers Delight detected, loading compat");
            //FarmersDelightCompat.register();
        }

    }


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
}
