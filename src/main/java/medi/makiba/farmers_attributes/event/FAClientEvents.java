package medi.makiba.farmers_attributes.event;

import medi.makiba.farmers_attributes.FarmersAttributes;
import medi.makiba.farmers_attributes.registry.FAItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@EventBusSubscriber(modid = FarmersAttributes.MODID, value = net.neoforged.api.distmarker.Dist.CLIENT)
public class FAClientEvents {
    @SubscribeEvent
	private static void addItemToCreativeTab(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.COMBAT) {
			event.accept(FAItems.SHARP_CARROT.get());
		}
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(FAItems.LARGE_CARROT.get());// follow vanilla order
			event.accept(FAItems.LARGE_BEETROOT.get());
			event.accept(FAItems.LARGE_POTATO.get());
		}
	}
}