package medi.makiba.farmers_attributes.registry;

import medi.makiba.farmers_attributes.FarmersAttributes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class FATags {
    public static class Blocks {
        public static final TagKey<Block> CROUCH_BONEMEAL_WHITELIST = tag("crouch_bonemeal_whitelist");
        public static final TagKey<Block> DELICIOUS_SMELLING_BLOCKS = tag("delicious_smelling_blocks");
    
        private static TagKey<Block> tag(String name) {
			return BlockTags.create(ResourceLocation.fromNamespaceAndPath(FarmersAttributes.MODID, name));
        }


    }
}
