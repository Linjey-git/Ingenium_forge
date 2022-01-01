package com.linjey.ingenium.block.custom;

import com.linjey.ingenium.block.entity.LightningInfusionerBlockEntity;
import com.linjey.ingenium.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class LightningInfusionerBlock extends BaseEntityBlock {

    private static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public LightningInfusionerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    private static final VoxelShape SHAPE_N = Stream.of(
            Block.box(5, 2, 1, 16, 9, 15),
            Block.box(0, 0, 0, 16, 2, 16),
            Block.box(7, 9, 3, 14, 11, 13),
            Block.box(10, 11, 9, 12, 19, 11),
            Block.box(11, 12, 6, 12, 13, 9),
            Block.box(11, 10, 6, 12, 12, 7),
            Block.box(2, 5, 12, 3, 25, 13),
            Block.box(1, 3, 11, 5, 5, 14),
            Block.box(1, 4, 3, 4, 7, 8),
            Block.box(2, 3, 5, 3, 4, 6),
            Block.box(1, 2, 4, 4, 3, 7)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_W = Stream.of(
            Block.box(1, 2, 0, 15, 9, 11),
            Block.box(0, 0, 0, 16, 2, 16),
            Block.box(3, 9, 2, 13, 11, 9),
            Block.box(9, 11, 4, 11, 19, 6),
            Block.box(6, 12, 4, 9, 13, 5),
            Block.box(6, 10, 4, 7, 12, 5),
            Block.box(12, 5, 13, 13, 25, 14),
            Block.box(11, 3, 11, 14, 5, 15),
            Block.box(3, 4, 12, 8, 7, 15),
            Block.box(5, 3, 13, 6, 4, 14),
            Block.box(4, 2, 12, 7, 3, 15)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();


    private static final VoxelShape SHAPE_S = Stream.of(
            Block.box(0, 2, 1, 11, 9, 15),
            Block.box(0, 0, 0, 16, 2, 16),
            Block.box(2, 9, 3, 9, 11, 13),
            Block.box(4, 11, 5, 6, 19, 7),
            Block.box(4, 12, 7, 5, 13, 10),
            Block.box(4, 10, 9, 5, 12, 10),
            Block.box(13, 5, 3, 14, 25, 4),
            Block.box(11, 3, 2, 15, 5, 5),
            Block.box(12, 4, 8, 15, 7, 13),
            Block.box(13, 3, 10, 14, 4, 11),
            Block.box(12, 2, 9, 15, 3, 12)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_E = Stream.of(
            Block.box(1, 2, 5, 15, 9, 16),
            Block.box(0, 0, 0, 16, 2, 16),
            Block.box(3, 9, 7, 13, 11, 14),
            Block.box(5, 11, 10, 7, 19, 12),
            Block.box(7, 12, 11, 10, 13, 12),
            Block.box(9, 10, 11, 10, 12, 12),
            Block.box(3, 5, 2, 4, 25, 3),
            Block.box(2, 3, 1, 5, 5, 5),
            Block.box(8, 4, 1, 13, 7, 4),
            Block.box(10, 3, 2, 11, 4, 3),
            Block.box(9, 2, 1, 12, 3, 4)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();


    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING)) {
            case NORTH:
                return SHAPE_N;
            case EAST:
                return SHAPE_E;
            case SOUTH:
                return SHAPE_S;
            case WEST:
                return SHAPE_W;
            default:
                return SHAPE_N;
        }
    }


    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor world, BlockPos pos, Rotation direction) {
        return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof LightningInfusionerBlockEntity) {
                ((LightningInfusionerBlockEntity) blockEntity).drops();
            }
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide()) {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof LightningInfusionerBlockEntity) {
                NetworkHooks.openGui(((ServerPlayer) player), (LightningInfusionerBlockEntity) entity, pos);
            } else {
                throw new IllegalStateException("Our Container Provider is missing!");
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new LightningInfusionerBlockEntity(pos, state);
    }


    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, ModBlockEntities.LIGHTNING_INFUSIONER.get(), LightningInfusionerBlockEntity::tick);
    }


}
