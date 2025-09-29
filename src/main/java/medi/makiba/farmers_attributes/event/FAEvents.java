package medi.makiba.farmers_attributes.event;

import medi.makiba.farmers_attributes.FarmersAttributes;
import medi.makiba.farmers_attributes.attributes.AntiFarmlandTrampling;
import medi.makiba.farmers_attributes.attributes.CrouchBoneMeal;
import medi.makiba.farmers_attributes.registry.FAAttributes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.level.BlockEvent.FarmlandTrampleEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = FarmersAttributes.MODID)
public class FAEvents {
    //ModBus

    @SubscribeEvent
    public static void modifyDefaultAttributes(EntityAttributeModificationEvent event) {
        event.add(
            EntityType.PLAYER,
            FAAttributes.ANTI_FARMLAND_TRAMPLING);
        event.add(
            EntityType.PLAYER,
            FAAttributes.CROUCH_BONEMEAL_CHANCE);
    }

    @SubscribeEvent
    public static void checkCancelTrample(FarmlandTrampleEvent event){
        Entity entity = event.getEntity();
        
        if (entity instanceof ServerPlayer){
            ServerPlayer player = (ServerPlayer) entity;
            if (AntiFarmlandTrampling.checkCancelTrample(player)){
                event.setCanceled(true);
            }
        }
    }


    //EventBus
    @SubscribeEvent
    public static void tickCrouchBoneMeal(PlayerTickEvent.Post event){
        Player player = event.getEntity();
        if (player.level().isClientSide){
            return;
        }
        CrouchBoneMeal.tickCrouchBoneMeal((ServerLevel) event.getEntity().level(), (ServerPlayer) player);
    }
    
}