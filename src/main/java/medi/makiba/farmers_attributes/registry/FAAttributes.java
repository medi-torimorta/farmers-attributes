package medi.makiba.farmers_attributes.registry;

import medi.makiba.farmers_attributes.FarmersAttributes;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.common.BooleanAttribute;
import net.neoforged.neoforge.common.PercentageAttribute;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FAAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(
    BuiltInRegistries.ATTRIBUTE, FarmersAttributes.MODID);

    public static final Holder<Attribute> ANTI_FARMLAND_TRAMPLING = ATTRIBUTES.register("anti_farmland_trampling", () -> new BooleanAttribute(
        "attributes.farmers_attributes.anti_farmland_trampling",
        false
    ));

    public static final Holder<Attribute> EASY_HARVEST = ATTRIBUTES.register("easy_harvest", () -> new BooleanAttribute(
        "attributes.farmers_attributes.easy_harvest",
        false
    ));

    public static final Holder<Attribute> CROUCH_BONEMEAL_CHANCE = ATTRIBUTES.register("crouch_bonemeal_chance", () -> new PercentageAttribute(
        "attributes.farmers_attributes.crouch_bonemeal_chance",
        0, 0, 1
    ));

    public static final Holder<Attribute> ZESTY_CULINARY = ATTRIBUTES.register("zesty_culinary", () -> new RangedAttribute(
        "attributes.farmers_attributes.zesty_culinary",
        0,
        0,
        127
    ));

    public static final Holder<Attribute> GREEN_THUMB = ATTRIBUTES.register("green_thumb", () -> new PercentageAttribute(
        "attributes.farmers_attributes.green_thumb",
        0, 0, 1
    ));

}
