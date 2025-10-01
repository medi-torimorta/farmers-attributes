package medi.makiba.farmers_attributes.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

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
                    this.addEffect(foodproperties$possibleeffect.effect());// modify here
                }
            }
        }
    }
 */
@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @ModifyArg(method = "addEatEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;addEffect(Lnet/minecraft/world/effect/MobEffectInstance;)Z"), index = 0)
    private MobEffectInstance modifyEffectDuration(MobEffectInstance effectInstance) {
        if (((LivingEntity)(Object)this).hasEffect(FAMobEffects.APPETITE_EFFECT)) {
            return AppetiteEffect.getModifiedEffect(effectInstance, ((LivingEntity)(Object)this).getEffect(FAMobEffects.APPETITE_EFFECT).getAmplifier());
        }else{
            return effectInstance;
        }
    }
}
