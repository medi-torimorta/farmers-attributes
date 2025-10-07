package medi.makiba.farmers_attributes.event;

import medi.makiba.farmers_attributes.FAConfig;
import medi.makiba.farmers_attributes.FarmersAttributes;
import medi.makiba.farmers_attributes.attribute.AntiFarmlandTrampling;
import medi.makiba.farmers_attributes.attribute.CrouchBoneMeal;
import medi.makiba.farmers_attributes.attribute.EasyHarvest;
import medi.makiba.farmers_attributes.attribute.GreenThumb;
import medi.makiba.farmers_attributes.attribute.ZestyCulinary;
import medi.makiba.farmers_attributes.registry.FAAttributes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.ItemCraftedEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.ItemSmeltedEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.neoforged.neoforge.event.level.BlockDropsEvent;
import net.neoforged.neoforge.event.level.BlockEvent.EntityPlaceEvent;
import net.neoforged.neoforge.event.level.BlockEvent.FarmlandTrampleEvent;
import net.neoforged.neoforge.event.level.ChunkEvent;
import net.neoforged.neoforge.event.level.block.CropGrowEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = FarmersAttributes.MODID)
public class FAEvents {

    @SubscribeEvent
    public static void modifyDefaultAttributes(EntityAttributeModificationEvent event) {
        event.add(
            EntityType.PLAYER,
            FAAttributes.ANTI_FARMLAND_TRAMPLING);
        event.add(EntityType.PLAYER,
            FAAttributes.EASY_HARVEST);
        event.add(
            EntityType.PLAYER,
            FAAttributes.CROUCH_BONEMEAL_CHANCE);
        event.add(
            EntityType.PLAYER,
            FAAttributes.ZESTY_CULINARY);
        event.add(
            EntityType.PLAYER,
            FAAttributes.GREEN_THUMB);
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
            ZestyCulinary.applyAppetiteOnCrafting((ServerPlayer) player, new ItemStack(item));
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
            ZestyCulinary.applyAppetiteOnCrafting((ServerPlayer) player, new ItemStack(item));
        }
    }

    @SubscribeEvent
    public static void onPlaceBlock(EntityPlaceEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity livingEntity && event.getLevel() instanceof ServerLevel level) {
            ZestyCulinary.applyAppetiteAoeOnPlacement(livingEntity, level, event.getPos(), event.getPlacedBlock());
            GreenThumb.checkAndAddData(livingEntity, level, event.getPos());
        }
    }

    @SubscribeEvent
    public static void checkEasyHarvestOnRightClick(RightClickBlock event) {
        if(EasyHarvest.tryHarvest(event.getLevel(), event.getPos(), event.getEntity())){
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.SUCCESS);
        }        
    }

    @SubscribeEvent
    public static void checkGreenThumbOnCropGrowth(CropGrowEvent.Post event) {
        if (event.getLevel() instanceof ServerLevel level) {
            GreenThumb.checkAndApplyGreenThumbOnGrowth(level, event.getPos(), event.getState());
        }
    }

    @SubscribeEvent
    public static void checkGreenThumbOnCropHarvest(BlockDropsEvent event) {
        if (event.getLevel() instanceof ServerLevel level) {
            GreenThumb.checkAndApplyGreenThumbOnHarvest(level, event.getPos(), event.getState(), event.getDrops());
        }
    }

    @SubscribeEvent
    public static void removeNonApplicableGreenThumbDataOnChunkLoad(ChunkEvent.Load event) {
        if (!event.isNewChunk() && event.getLevel() instanceof ServerLevel) {
            GreenThumb.removeIrrelevantData(event.getChunk());
        }
    }
}