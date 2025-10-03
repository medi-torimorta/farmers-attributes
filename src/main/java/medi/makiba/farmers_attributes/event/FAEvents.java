package medi.makiba.farmers_attributes.event;

import medi.makiba.farmers_attributes.FarmersAttributes;
import medi.makiba.farmers_attributes.attribute.AntiFarmlandTrampling;
import medi.makiba.farmers_attributes.attribute.CrouchBoneMeal;
import medi.makiba.farmers_attributes.attribute.ZestyCulinary;
import medi.makiba.farmers_attributes.registry.FAAttributes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ServerLevelAccessor;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.ItemCraftedEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.ItemSmeltedEvent;
import net.neoforged.neoforge.event.level.BlockEvent.EntityPlaceEvent;
import net.neoforged.neoforge.event.level.BlockEvent.FarmlandTrampleEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = FarmersAttributes.MODID)
public class FAEvents {

    @SubscribeEvent
    public static void modifyDefaultAttributes(EntityAttributeModificationEvent event) {
        event.add(
            EntityType.PLAYER,
            FAAttributes.ANTI_FARMLAND_TRAMPLING);
        event.add(
            EntityType.PLAYER,
            FAAttributes.CROUCH_BONEMEAL_CHANCE);
        event.add(
            EntityType.PLAYER,
            FAAttributes.ZESTY_CULINARY);
    }

    @SubscribeEvent
    public static void checkCancelTrample(FarmlandTrampleEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof ServerPlayer player) {
            if (AntiFarmlandTrampling.checkCancelTrample(player)) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void tickCrouchBoneMeal(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) {
            return;
        }
        CrouchBoneMeal.tickCrouchBoneMeal((ServerLevel) event.getEntity().level(), (ServerPlayer) player);
    }

    @SuppressWarnings("null")
    @SubscribeEvent
    public static void craftZestyCulinary(ItemCraftedEvent event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) {
            return;
        }
        Item item = event.getCrafting().getItem();
        if (item != null) {
            if (new ItemStack(item).getFoodProperties(null) != null) {
                ZestyCulinary.applyAppetiteOnCrafting((ServerPlayer) player);
            }
        }
    }


    @SuppressWarnings("null")
    @SubscribeEvent
    public static void smeltZestyCulinary(ItemSmeltedEvent event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) {
            return;
        }
        Item item = event.getSmelting().getItem();
        if (item != null) {
            if (new ItemStack(item).getFoodProperties(null) != null) {
                ZestyCulinary.applyAppetiteOnCrafting((ServerPlayer) player);
            }
        }
    }

    @SubscribeEvent
    public static void placeZestyCulinary(EntityPlaceEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof ServerPlayer player && event.getLevel() instanceof ServerLevelAccessor level) {
            if (new ItemStack(event.getPlacedBlock().getBlock().asItem()).is(Tags.Items.FOODS_EDIBLE_WHEN_PLACED)) {
                ZestyCulinary.applyAppetiteAoeOnPlacement(player, level, event.getPos(), event.getPlacedBlock());
            }
        }  
    }
}