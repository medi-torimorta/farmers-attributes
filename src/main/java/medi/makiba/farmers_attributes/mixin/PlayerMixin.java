package medi.makiba.farmers_attributes.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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
    @Inject(method = "canEat", at = @At("RETURN"), cancellable = true)
    private void canEat(CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue() && ((Player)(Object)this).hasEffect(FAMobEffects.APPETITE_EFFECT)) {
            cir.setReturnValue(true);
        }
    }
}
