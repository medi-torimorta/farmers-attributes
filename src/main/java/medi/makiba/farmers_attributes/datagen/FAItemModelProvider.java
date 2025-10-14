package medi.makiba.farmers_attributes.datagen;

import medi.makiba.farmers_attributes.FarmersAttributes;
import medi.makiba.farmers_attributes.registry.FAItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class FAItemModelProvider extends ItemModelProvider{
    public FAItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, FarmersAttributes.MODID, existingFileHelper);
    }
    
    @Override
    protected void registerModels() {
        basicItem(FAItems.SHARP_CARROT.get());
    }
}
