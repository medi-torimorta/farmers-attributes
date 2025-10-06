package medi.makiba.farmers_attributes;

import org.slf4j.Logger;

import medi.makiba.farmers_attributes.registry.FAAttachmentTypes;
import medi.makiba.farmers_attributes.registry.FAAttributes;
import medi.makiba.farmers_attributes.registry.FABlocks;
import medi.makiba.farmers_attributes.registry.FAItems;
import medi.makiba.farmers_attributes.registry.FAMobEffects;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;


// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(FarmersAttributes.MODID)
public class FarmersAttributes {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "farmers_attributes";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    
    
    public FarmersAttributes(IEventBus modEventBus, ModContainer modContainer) {
        FABlocks.BLOCKS.register(modEventBus);
        FAItems.ITEMS.register(modEventBus);
        FAAttributes.ATTRIBUTES.register(modEventBus);
        FAAttachmentTypes.ATTACHMENT_TYPES.register(modEventBus);
        FAMobEffects.MOB_EFFECTS.register(modEventBus);
        modContainer.registerConfig(ModConfig.Type.COMMON, FAConfig.SPEC);
    }

}
