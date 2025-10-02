package medi.makiba.farmers_attributes.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import medi.makiba.farmers_attributes.registry.FAMobEffects;
import net.minecraft.world.entity.player.Player;


/*
 * return true if the player has the appetite effect
 * base method:
 * public boolean canEat(boolean canAlwaysEat) {
 *     return this.abilities.invulnerable || canAlwaysEat || this.foodData.needsFood();// modify here
 * }
 */
@Mixin(Player.class)
public class PlayerMixin {
    @ModifyExpressionValue(method = "canEat", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/food/FoodData;needsFood()Z"))
    private boolean canEat(boolean original) {
        Player player = (Player)(Object)this;
        return original || player.hasEffect(FAMobEffects.APPETITE_EFFECT);
    }
}
