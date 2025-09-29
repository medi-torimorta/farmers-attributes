package medi.makiba.farmers_attributes.registry;

import medi.makiba.farmers_attributes.FarmersAttributes;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.common.BooleanAttribute;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FAAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(
    BuiltInRegistries.ATTRIBUTE, FarmersAttributes.MODID);

    public static final Holder<Attribute> ANTI_FARMLAND_TRAMPLING = ATTRIBUTES.register("anti_farmland_trampling", () -> new BooleanAttribute(
    // The translation key to use.
    "attributes.farmers_attributes.anti_farmland_trampling",
    // The default value.
    false));

    public static final Holder<Attribute> CROUCH_BONEMEAL_CHANCE = ATTRIBUTES.register("crouch_bonemeal_chance", () -> new RangedAttribute(
    // The translation key to use.
    "attributes.farmers_attributes.crouch_bonemeal_chance",
    // The default value.
    0,
    // Min and max values.
    0,
    100
));

}
