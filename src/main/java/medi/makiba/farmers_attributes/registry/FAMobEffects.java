package medi.makiba.farmers_attributes.registry;

import medi.makiba.farmers_attributes.FarmersAttributes;
import medi.makiba.farmers_attributes.effect.AppetiteEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FAMobEffects {
    public static DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, FarmersAttributes.MODID);
    public static final Holder<MobEffect> APPETITE_EFFECT = MOB_EFFECTS.register("appetite", () -> new AppetiteEffect(
        MobEffectCategory.BENEFICIAL,
        0xff8000
    ));
}