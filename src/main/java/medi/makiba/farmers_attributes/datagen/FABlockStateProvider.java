package medi.makiba.farmers_attributes.datagen;

import medi.makiba.farmers_attributes.FarmersAttributes;
import medi.makiba.farmers_attributes.block.LargeCropBlock;
import medi.makiba.farmers_attributes.registry.FABlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class FABlockStateProvider extends BlockStateProvider{

    public FABlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, FarmersAttributes.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        
        largeCropBlock(FABlocks.LARGE_BEETROOT);
    }

    private void largeCropBlock(DeferredBlock<?> block) {
        ResourceLocation top = this.modLoc("block/" + block.getId().getPath() + "_top");
        ResourceLocation side = this.modLoc("block/" + block.getId().getPath() + "_side");
        ResourceLocation bottom = this.modLoc("block/" + block.getId().getPath() + "_bottom");
        ResourceLocation leaves = this.modLoc("block/" + block.getId().getPath() + "_leaves");
        ModelFile model_up = models()
            .withExistingParent(block.getId().toString(), this.mcLoc("block/cube_bottom_top"))
            .texture("top", top)
            .texture("side", side)
            .texture("bottom", bottom)
            .texture("particle", side);
        ModelFile model_in_ground = models()
            .withExistingParent(block.getId().toString() + "_in_ground", this.modLoc("block/large_crops_in_ground"))
            .texture("top", top)
            .texture("side", side)
            .texture("bottom", bottom)
            .texture("leaves", leaves)
            .texture("particle", side);
        VariantBlockStateBuilder variantBuilder = getVariantBuilder(block.get());
        variantBuilder
            .addModels(variantBuilder.partialState().with(LargeCropBlock.IN_GROUND, true), ConfiguredModel.builder().modelFile(model_in_ground).build())
            .addModels(variantBuilder.partialState().with(LargeCropBlock.IN_GROUND, false).with(LargeCropBlock.FACING, Direction.UP), ConfiguredModel.builder().modelFile(model_up).build())
            .addModels(variantBuilder.partialState().with(LargeCropBlock.IN_GROUND, false).with(LargeCropBlock.FACING, Direction.DOWN), ConfiguredModel.builder().modelFile(model_up).rotationX(180).build())
            .addModels(variantBuilder.partialState().with(LargeCropBlock.IN_GROUND, false).with(LargeCropBlock.FACING, Direction.NORTH), ConfiguredModel.builder().modelFile(model_up).rotationX(90).build())
            .addModels(variantBuilder.partialState().with(LargeCropBlock.IN_GROUND, false).with(LargeCropBlock.FACING, Direction.SOUTH), ConfiguredModel.builder().modelFile(model_up).rotationX(90).rotationY(180).build())
            .addModels(variantBuilder.partialState().with(LargeCropBlock.IN_GROUND, false).with(LargeCropBlock.FACING, Direction.WEST), ConfiguredModel.builder().modelFile(model_up).rotationX(90).rotationY(270).build())
            .addModels(variantBuilder.partialState().with(LargeCropBlock.IN_GROUND, false).with(LargeCropBlock.FACING, Direction.EAST), ConfiguredModel.builder().modelFile(model_up).rotationX(90).rotationY(90).build());
        itemModels().getBuilder(this.modLoc(block.getId().getPath()).toString()).parent(model_up);
    }

    
}
