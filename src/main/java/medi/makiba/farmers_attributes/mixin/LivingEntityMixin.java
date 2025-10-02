package medi.makiba.farmers_attributes.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import medi.makiba.farmers_attributes.effect.AppetiteEffect;
import medi.makiba.farmers_attributes.registry.FAMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

/*
 * Replace the food effect with AppetiteEffect.getModifiedFoodProperties when a living entity eats food.
 * private void addEatEffect(FoodProperties foodProperties) {
        if (!this.level().isClientSide()) {
            for (FoodProperties.PossibleEffect foodproperties$possibleeffect : foodProperties.effects()) {
                if (this.random.nextFloat() < foodproperties$possibleeffect.probability()) {
                    this.addEffect(foodproperties$possibleeffect.effect()// modify here);
                }
            }
        }
    }
 */
@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @ModifyExpressionValue(method = "addEatEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/food/FoodProperties$PossibleEffect;effect()Lnet/minecraft/world/effect/MobEffectInstance;"))
    private MobEffectInstance modifyEffectDuration(MobEffectInstance effectInstance) {
        if (((LivingEntity)(Object)this).hasEffect(FAMobEffects.APPETITE_EFFECT)) {
            return AppetiteEffect.getModifiedEffect(effectInstance, ((LivingEntity)(Object)this).getEffect(FAMobEffects.APPETITE_EFFECT).getAmplifier());
        }else{
            return effectInstance;
        }
    }
}
