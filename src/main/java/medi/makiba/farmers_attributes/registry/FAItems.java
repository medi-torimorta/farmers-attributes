package medi.makiba.farmers_attributes.registry;

import java.util.function.Supplier;

import medi.makiba.farmers_attributes.FarmersAttributes;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FAItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(FarmersAttributes.MODID);

    public static final Supplier<BlockItem> LARGE_BEETROOT = ITEMS.registerSimpleBlockItem(FABlocks.LARGE_BEETROOT, new Item.Properties()
        .food((new FoodProperties.Builder()).nutrition(6).saturationModifier(0.6F).build()));
    public static final Supplier<BlockItem> LARGE_CARROT = ITEMS.registerSimpleBlockItem(FABlocks.LARGE_CARROT, new Item.Properties()
        .food((new FoodProperties.Builder()).nutrition(12).saturationModifier(0.6F).build()));
}
