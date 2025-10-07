package medi.makiba.farmers_attributes.block;

import javax.annotation.Nullable;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LargeCropBlock extends Block{
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final BooleanProperty IN_GROUND = BooleanProperty.create("in_ground");
    private static final VoxelShape SHAPE_IN_GROUND = Block.box(0.0D, -12.0D, 0.0D, 16.0D, 4.0D, 16.0D);
    public BlockState originalCropState;

    public LargeCropBlock(BlockState originalCropState) {
        super(BlockBehaviour.Properties.of()
            .destroyTime(1.0f)
            .explosionResistance(1.0f)
            .sound(SoundType.WOOD)
        );
        this.originalCropState = originalCropState;
        registerDefaultState(stateDefinition.any()
            .setValue(FACING, Direction.UP)
            .setValue(IN_GROUND, false)
        );
    }

    @Override
    public ItemInteractionResult useItemOn(ItemStack heldStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (heldStack.is(ItemTags.SHOVELS)) {
            if (state.getValue(IN_GROUND)) {
                unEarth(player, state, level, pos);
                if (!level.isClientSide) {
                    heldStack.hurtAndBreak(1, player, Player.getSlotForHand(hand));
                }
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            } else if (getEarthedState(state).canSurvive(level, pos)) {
                earth(player, state, level, pos);
                if (!level.isClientSide) {
                    heldStack.hurtAndBreak(1, player, Player.getSlotForHand(hand));
                }
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        return super.useItemOn(heldStack, state, level, pos, player, hand, result);
    }
    
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, IN_GROUND);
    }

    @Override
    protected boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(IN_GROUND)) {
            return SHAPE_IN_GROUND;
        }else{
            return Shapes.block();
        }
    }
    
    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState()
            .setValue(FACING, pContext.getNearestLookingDirection().getOpposite());
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        if (facing == Direction.DOWN && !state.canSurvive(level, currentPos)) {
            level.scheduleTick(currentPos, this, 1);
        }
        return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.canSurvive(level, pos)) {
           unEarth(null, state, level, pos);
        }
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        if (state.getValue(IN_GROUND)) {
            BlockState blockstate = level.getBlockState(pos.below());
            return blockstate.getBlock() instanceof FarmBlock;
        }else{
            return true;
        }
    }

    private static void unEarth(@Nullable Entity entity, BlockState state, Level level, BlockPos pos) {
        level.playSound(entity, pos, SoundEvents.CROP_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
        if (!level.isClientSide) {
            BlockState blockstate = pushEntitiesUp(state, state.setValue(IN_GROUND, false), level, pos);
            level.setBlockAndUpdate(pos, blockstate);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(entity, blockstate));
        }
    }

    private static void earth(@Nullable Entity entity, BlockState state, Level level, BlockPos pos) {
        level.playSound(entity, pos, SoundEvents.CROP_PLANTED, SoundSource.BLOCKS, 1.0F, 1.0F);
        if (!level.isClientSide) {
            BlockState blockstate = getEarthedState(state);
            level.setBlockAndUpdate(pos, blockstate);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(entity, blockstate));
        }
    }

    public static BlockState getEarthedState(BlockState state) {
        return state.setValue(IN_GROUND, true).setValue(FACING, Direction.UP);
    }

    public static boolean inGround(BlockState state) {
        return state.getValue(IN_GROUND);
    }
}
