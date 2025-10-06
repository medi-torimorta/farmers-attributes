package medi.makiba.farmers_attributes.datagen;

import java.util.HashSet;
import java.util.Set;

import medi.makiba.farmers_attributes.registry.FABlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;


public class FABlockLootSubProvider extends BlockLootSubProvider {

    private final Set<Block> generatedLootTables = new HashSet<>();

	public FABlockLootSubProvider(HolderLookup.Provider holder) {
		super(Set.of(), FeatureFlags.REGISTRY.allFlags(), holder);
	}


    @Override
    protected void generate() {
        dropSelf(FABlocks.LARGE_BEETROOT.get());
    }

    @Override
	protected void add(Block block, LootTable.Builder builder) {
		this.generatedLootTables.add(block);
		this.map.put(block.getLootTable(), builder);
	}

	@Override
	protected Iterable<Block> getKnownBlocks() {
		return generatedLootTables;
	}
}
