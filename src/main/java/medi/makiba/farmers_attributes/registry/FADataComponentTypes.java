package medi.makiba.farmers_attributes.registry;

import medi.makiba.farmers_attributes.FarmersAttributes;
import medi.makiba.farmers_attributes.datacomponents.ZestyAmplifierRecord;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FADataComponentTypes {
    public static final DeferredRegister.DataComponents DATA_COMPONENT_TYPES = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, FarmersAttributes.MODID);
    

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ZestyAmplifierRecord>> ZESTY_AMPLIFIER = DATA_COMPONENT_TYPES.registerComponentType(
        "amplifier",
        builder -> builder.persistent(ZestyAmplifierRecord.CODEC).networkSynchronized(ZestyAmplifierRecord.STREAM_CODEC)
    );
}
