package medi.makiba.farmers_attributes.client;

import medi.makiba.farmers_attributes.FarmersAttributes;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = FarmersAttributes.MODID, dist = Dist.CLIENT)
public class FarmersAttributesClient {
    public FarmersAttributesClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}
