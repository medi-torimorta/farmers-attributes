package medi.makiba.farmers_attributes.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import medi.makiba.farmers_attributes.attribute.FarmersWeaponAndArmor;
import medi.makiba.farmers_attributes.registry.FAMobEffects;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;



@Mixin(Player.class)
public class PlayerMixin {
    /*
     * return true if the player has the appetite effect
     * base method:
     * public boolean canEat(boolean canAlwaysEat) {
     *     return this.abilities.invulnerable || canAlwaysEat || this.foodData.needsFood();// modify here
     * }
     */
    @ModifyExpressionValue(method = "canEat", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/food/FoodData;needsFood()Z"))
    private boolean canEat(boolean original) {
        Player player = (Player)(Object)this;
        return original || player.hasEffect(FAMobEffects.APPETITE_EFFECT);
    }
}
