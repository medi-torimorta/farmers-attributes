package medi.makiba.farmers_attributes.compat;

import medi.makiba.farmers_attributes.FarmersAttributes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FarmersDelightCompat {
    public static final DeferredRegister<Attribute> DELIGHT_ATTRIBUTES = DeferredRegister.create(
    BuiltInRegistries.ATTRIBUTE, FarmersAttributes.MODID);
    public static void register(){
        DELIGHT_ATTRIBUTES.register(null);
    }
}
