package medi.makiba.farmers_attributes.registry;

import medi.makiba.farmers_attributes.FarmersAttributes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class FATags {
    public static class Blocks {
        public static final TagKey<Block> CROUCH_BONEMEAL_WHITELIST = tag("crouch_bonemeal_whitelist");
        public static final TagKey<Block> DELICIOUS_SMELLING_BLOCKS = tag("delicious_smelling_blocks");
        private static TagKey<Block> tag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(FarmersAttributes.MODID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> SOURCE_FOODS = tag("source_foods");
        public static final TagKey<Item> FARMERS_WEAPON = tag("farmers_weapon");
        public static final TagKey<Item> FARMERS_ARMOR = tag("farmers_armor");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(FarmersAttributes.MODID, name));
        }
    }
}
