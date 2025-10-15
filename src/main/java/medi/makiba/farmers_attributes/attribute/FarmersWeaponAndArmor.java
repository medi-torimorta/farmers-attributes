package medi.makiba.farmers_attributes.attribute;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import medi.makiba.farmers_attributes.registry.FAAttributes;
import medi.makiba.farmers_attributes.registry.FATags;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.AddAttributeTooltipsEvent;

public class FarmersWeaponAndArmor {

    public static void addTooltipToFarmersTools(AddAttributeTooltipsEvent event) {
        ItemStack stack = event.getStack();
        if (stack == null || stack.isEmpty() || !event.shouldShow()) {
            return;
        }
        boolean isFarmersWeapon = stack.is(FATags.Items.FARMERS_WEAPON);
        boolean isFarmersArmor = stack.is(FATags.Items.FARMERS_ARMOR);
        if (!isFarmersWeapon && !isFarmersArmor) {
            return;
        }
        Player player = event.getContext().player();
        double weaponBonus = player != null && isFarmersWeapon ? player.getAttributeValue(FAAttributes.FARMERS_WEAPON) : -1;
        double armorBonus = player != null && isFarmersArmor ? player.getAttributeValue(FAAttributes.FARMERS_ARMOR) : -1;

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setRoundingMode(RoundingMode.CEILING);

        if(isFarmersArmor && armorBonus != 0){
            MutableComponent tooltipText;
            String valueString = decimalFormat.format(armorBonus);
            if (player != null) {
                tooltipText = Component.literal("+" + valueString + " ");
            }else{
                tooltipText = Component.literal("+? ");
            }
            tooltipText.append(Component.translatable("tooltip.farmers_attributes.armor_bonus"));
            if (event.getContext().flag().isAdvanced()) {
                tooltipText.append(Component.literal(" [+" + valueString + "]").withStyle(ChatFormatting.GRAY));
            }
            event.addTooltipLines(tooltipText.withStyle(ChatFormatting.BLUE));
        }

        if(isFarmersWeapon && weaponBonus != 0){
            MutableComponent tooltipText;
            String valueString = decimalFormat.format(weaponBonus);
            if (player != null) {
                tooltipText = Component.literal("+" + valueString + " ");
            }else{
                tooltipText = Component.literal("+? ");
            }
            tooltipText.append(Component.translatable("tooltip.farmers_attributes.weapon_bonus"));
            if (event.getContext().flag().isAdvanced()) {
                tooltipText.append(Component.literal(" [+" + valueString + "]").withStyle(ChatFormatting.GRAY));
            }
            event.addTooltipLines(tooltipText.withStyle(ChatFormatting.BLUE));
        }
   }
}
