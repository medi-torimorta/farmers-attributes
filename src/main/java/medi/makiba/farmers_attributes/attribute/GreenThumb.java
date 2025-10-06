package medi.makiba.farmers_attributes.attribute;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mojang.serialization.Codec;

import medi.makiba.farmers_attributes.FAConfig;
import medi.makiba.farmers_attributes.block.LargeCropBlock;
import medi.makiba.farmers_attributes.registry.FAAttachmentTypes;
import medi.makiba.farmers_attributes.registry.FAAttributes;
import medi.makiba.farmers_attributes.registry.FABlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;

public class GreenThumb {
    public static void checkAndAddData(LivingEntity entity, ServerLevel level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (state.isAir() || !(state.getBlock() instanceof CropBlock cropBlock) || cropBlock.getAge(state) != 0) {
            return;
        }
        if (!entity.getAttributes().hasAttribute(FAAttributes.GREEN_THUMB)) {
            return;
        }
        double attributeValue = entity.getAttributeValue(FAAttributes.GREEN_THUMB);
        if (level.random.nextDouble() < attributeValue) {
            addData(level, pos);
        }
    }

    private static final Map<Block, BlockState> largeCrops = Map.of(
        Blocks.BEETROOTS, LargeCropBlock.getEarthedState(FABlocks.LARGE_BEETROOT.get().defaultBlockState())
    );

    // called on CropGrowEvent.Post
    public static void checkAndApplyGreenThumbOnGrowth(ServerLevel level, BlockPos pos, BlockState state) {
        if (!checkShouldApplyGreenThumbResult(level, pos, state)) {
            return;
        }
        Block block = state.getBlock();
        if (largeCrops.containsKey(block) && level.setBlockAndUpdate(pos, largeCrops.get(block))) {
            removeData(level, pos);
        }
    }

    public static void checkAndApplyGreenThumbOnHarvest(ServerLevel level, BlockPos pos, BlockState state, List<ItemEntity> drops) {
        if (!checkShouldApplyGreenThumbResult(level, pos, state)) {
            return;
        }
        drops.addAll(drops);
        removeData(level, pos);
    }

    // on chunk load
    public static void removeDataIfNonApplicable(ChunkAccess chunk) {
        if (chunk.hasData(FAAttachmentTypes.GREEN_THUMB)) {
            Set<BlockPos> data = chunk.getData(FAAttachmentTypes.GREEN_THUMB);
            data.removeIf(pos -> {
                BlockState state = chunk.getBlockState(pos);
                return state.isAir() || !(state.getBlock() instanceof CropBlock) || FAConfig.GREEN_THUMB_BLACKLIST.get().contains(state.getBlock().toString());
            });
        }
    }

    private static boolean checkShouldApplyGreenThumbResult(ServerLevel level, BlockPos pos, BlockState state) {
        System.out.println("Checking Green Thumb effect at " + pos + " with state " + state);
        if (!(state.getBlock() instanceof CropBlock cropBlock) || !cropBlock.isMaxAge(state)
                || FAConfig.GREEN_THUMB_BLACKLIST.get().contains(state.getBlock().toString())) {
            return false;
        }
        System.out.println("Green Thumb effect is applicable at " + pos);
        if (!level.getChunk(pos).hasData(FAAttachmentTypes.GREEN_THUMB)
                || !level.getChunk(pos).getData(FAAttachmentTypes.GREEN_THUMB).contains(pos)) {
            return false;
        }
        return true;
    }

    public static Set<BlockPos> newData() {
        return new HashSet<BlockPos>();
    }

    public static void addData(ServerLevel level, BlockPos pos) {
        ChunkAccess chunk = level.getChunk(pos);
        Set<BlockPos> data;
        if (chunk.hasData(FAAttachmentTypes.GREEN_THUMB)) {
            data = chunk.getData(FAAttachmentTypes.GREEN_THUMB);
        } else {
            data = new HashSet<>();
        }
        data.add(pos);
        chunk.setData(FAAttachmentTypes.GREEN_THUMB, data);
    }

    public static void removeData(ServerLevel level, BlockPos pos) {
        ChunkAccess chunk = level.getChunk(pos);
        if (!chunk.hasData(FAAttachmentTypes.GREEN_THUMB)) {
            return;
        }
        Set<BlockPos> data = chunk.getData(FAAttachmentTypes.GREEN_THUMB);
        if (data.contains(pos)) {
            data.remove(pos);
            if (data.isEmpty()) {
                chunk.removeData(FAAttachmentTypes.GREEN_THUMB);
            } else {
                chunk.setData(FAAttachmentTypes.GREEN_THUMB, data);
            }
        }
    }

    public static Codec<Set<BlockPos>> CODEC = Codec.list(BlockPos.CODEC).xmap(list -> new HashSet<>(list),
            set -> List.copyOf(set));
}
