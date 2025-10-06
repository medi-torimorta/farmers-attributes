package medi.makiba.farmers_attributes.registry;

import medi.makiba.farmers_attributes.FarmersAttributes;
import medi.makiba.farmers_attributes.block.LargeCropBlock;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FABlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(FarmersAttributes.MODID);

    public static final DeferredBlock<Block> LARGE_BEETROOT = BLOCKS.register(
        "large_beetroot", 
        () -> new LargeCropBlock());
}
