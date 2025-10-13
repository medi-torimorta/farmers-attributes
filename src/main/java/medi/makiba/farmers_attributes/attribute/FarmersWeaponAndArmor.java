package medi.makiba.farmers_attributes.attribute;

import java.text.DecimalFormat;
import java.util.List;

import medi.makiba.farmers_attributes.registry.FAAttributes;
import medi.makiba.farmers_attributes.registry.FATags;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.RenderTooltipEvent.Color;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

public class FarmersWeaponAndArmor {

    public static void addTooltipToFarmersTools(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack == null || stack.isEmpty()) {
            return;
        }
        boolean isFarmersWeapon = stack.is(FATags.Items.FARMERS_WEAPON);
        boolean isFarmersArmor = stack.is(FATags.Items.FARMERS_ARMOR);
        if (!isFarmersWeapon && !isFarmersArmor) {
            return;
        }
        Player player = event.getEntity();
        double weaponBonus = player != null && isFarmersWeapon ? player.getAttributeValue(FAAttributes.FARMERS_WEAPON) : 0.0;
        double armorBonus = player != null && isFarmersArmor ? player.getAttributeValue(FAAttributes.FARMERS_ARMOR) : 0.0;
        
        
        List<Component> tooltip = event.getToolTip();

        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        int tooltipIndex = 0;
        tooltipIndex = tooltip.size();
        if(event.getFlags().isAdvanced()){
            tooltipIndex -= 2;
        }
        if (tooltipIndex < 0) {
            tooltipIndex = tooltip.size();
        }
        
        
        if(isFarmersArmor){
            MutableComponent tooltipText;
            if (player != null) {
                tooltipText = Component.literal("+" + decimalFormat.format(armorBonus) + " ");
            }else{
                tooltipText = Component.literal("+? ");
            }
            tooltipText.append(Component.translatable("tooltip.farmers_attributes.armor_bonus"));
            tooltip.add(tooltipIndex, tooltipText.withStyle(ChatFormatting.BLUE));
        }

        if(isFarmersWeapon){
            MutableComponent tooltipText;
            if (player != null) {
                tooltipText = Component.literal("+" + decimalFormat.format(weaponBonus) + " ");
            }else{
                tooltipText = Component.literal("+? ");
            }
            tooltipText.append(Component.translatable("tooltip.farmers_attributes.weapon_bonus"));
            tooltip.add(tooltipIndex, tooltipText.withStyle(ChatFormatting.BLUE));
        }
   }
}
