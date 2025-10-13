package medi.makiba.farmers_attributes.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import medi.makiba.farmers_attributes.effect.AppetiteEffect;
import medi.makiba.farmers_attributes.registry.FAAttributes;
import medi.makiba.farmers_attributes.registry.FAMobEffects;
import medi.makiba.farmers_attributes.registry.FATags;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;


@Mixin(LivingEntity.class)
public class LivingEntityMixin {
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
    @SuppressWarnings("null")
    @ModifyExpressionValue(method = "addEatEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/food/FoodProperties$PossibleEffect;effect()Lnet/minecraft/world/effect/MobEffectInstance;"))
    private MobEffectInstance modifyEffectDuration(MobEffectInstance effectInstance) {
        if (((LivingEntity)(Object)this).hasEffect(FAMobEffects.APPETITE_EFFECT)) {
            return AppetiteEffect.getModifiedEffect(effectInstance, ((LivingEntity)(Object)this).getEffect(FAMobEffects.APPETITE_EFFECT).getAmplifier());
        }else{
            return effectInstance;
        }
    }


    @ModifyVariable(method = "hurt", at = @At("HEAD"))
	private float modifyFarmerDamage(float damage, DamageSource source) {
		if (damage < 0) {
			return damage;
		}

		if (source.getEntity() instanceof ServerPlayer player) {
            ItemStack weapon = source.getWeaponItem();
            if (weapon != null && weapon.is(FATags.Items.FARMERS_WEAPON)){
                damage += (float) player.getAttributeValue(FAAttributes.FARMERS_WEAPON);
            }
        }

		return damage;
	}

    /*
     * base method:
     * public int getArmorValue() {
     *     return Mth.floor(this.getAttributeValue(Attributes.ARMOR));//wrap here
     * }
     */
    @ModifyExpressionValue(method = "getArmorValue", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getAttributeValue(Lnet/minecraft/core/Holder;)D"))
    private double modifyFarmerArmor(double armorAttributeValue) {
        if ((Object)this instanceof Player player) {
            double attValue = player.getAttributeValue(FAAttributes.FARMERS_ARMOR);
            if (attValue == 0) {
                return armorAttributeValue;
            }
            double equipped = 0;
            for (ItemStack armorItem : player.getArmorSlots()) {
                if (armorItem != null && armorItem.is(FATags.Items.FARMERS_ARMOR)){
                    equipped++;
                }
            }
            return armorAttributeValue + attValue * equipped;
        }
        return armorAttributeValue;
    }
}
