package medi.makiba.farmers_attributes.effects;

import medi.makiba.farmers_attributes.FAConfig;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class AppetiteEffect extends MobEffect {
    /*
     * Allows the player to eat food even when full, and at higher modifiers, extends the duration of beneficial food effects when a living entity eats food.
     * The duration formula is as follows, where X is the base multiplier defined in the config (default 0.5):
     * 0: 1x duration (only able to eat when not full)
     * 1+: (X * amplifier)x duration
     */
    public AppetiteEffect(MobEffectCategory category, int color) {
        super(category, color);
    }
    
    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        return true;
    }

    public static MobEffectInstance getModifiedEffect(MobEffectInstance effect, int amplifier) {
        MobEffect effectType = effect.getEffect().value();

        //apply to effects that are beneficial, not instant and not infinite duration
        if (!effectType.getCategory().equals(MobEffectCategory.BENEFICIAL) || effectType.isInstantenous() || effect.isInfiniteDuration() || amplifier < 1) {
            return effect;
        }
        //extend duration
        int new_duration = (int) (effect.getDuration() * getDurationMultiplier(amplifier));

        return new MobEffectInstance(
            effect.getEffect(),
            new_duration,
            effect.getAmplifier(),
            effect.isAmbient(),
            effect.isVisible(),
            effect.showIcon()
        );
    }

    public static double getDurationMultiplier(int amplifier) {
        double base_value = FAConfig.APPETITE_EFFECT_DURATION_MULTIPLIER.get();
        return 1 + (base_value * amplifier);
    }
}
