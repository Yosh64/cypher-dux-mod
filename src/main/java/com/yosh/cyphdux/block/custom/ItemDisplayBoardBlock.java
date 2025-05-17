package com.yosh.cyphdux.block.custom;

import com.mojang.serialization.MapCodec;
import com.yosh.cyphdux.block.entity.ItemDisplayBoardBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class ItemDisplayBoardBlock extends BlockWithEntity implements BlockEntityProvider {
    private static final VoxelShape SOUTH_SHAPE = ItemDisplayBoardBlock.createCuboidShape(1,1,0,15,15,3);
    private static final VoxelShape NORTH_SHAPE = ItemDisplayBoardBlock.createCuboidShape(1,1,13,15,15,16);
    private static final VoxelShape EAST_SHAPE = ItemDisplayBoardBlock.createCuboidShape(0,1,1,3,15,15);
    private static final VoxelShape WEST_SHAPE = ItemDisplayBoardBlock.createCuboidShape(13,1,1,16,15,15);

    public static final MapCodec<ItemDisplayBoardBlock> CODEC = ItemDisplayBoardBlock.createCodec(ItemDisplayBoardBlock::new);
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;


    public ItemDisplayBoardBlock(Settings settings) {
        super(settings);
        setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    private VoxelShape getShape(BlockState state) {
        Direction direction = state.get(FACING);
        switch (direction){
            case NORTH -> {
                return NORTH_SHAPE;
            }
            case SOUTH -> {
                return SOUTH_SHAPE;
            }
            case EAST -> {
                return EAST_SHAPE;
            }
            case WEST -> {
                return WEST_SHAPE;
            }
            default -> {
                return NORTH_SHAPE;
            }
        }
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.getShape(state);
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = Blocks.WALL_TORCH.getPlacementState(ctx);
        return blockState == null ? null : (BlockState)this.getDefaultState().with(FACING, (Direction)blockState.get(FACING));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ItemDisplayBoardBlockEntity(pos, state);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    protected int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof ItemDisplayBoardBlockEntity displayBoardBlock) {
            return displayBoardBlock.getStack(0).isEmpty()? 0:15;
        }
        return 0;
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof ItemDisplayBoardBlockEntity) {
                    world.updateComparators(pos, this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return canPlaceAt(world, pos, (Direction)state.get(FACING));
    }
    public static boolean canPlaceAt(WorldView world, BlockPos pos, Direction facing) {
        BlockPos blockPos = pos.offset(facing.getOpposite());
        BlockState blockState = world.getBlockState(blockPos);
        return blockState.isSideSolidFullSquare(world, blockPos, facing);
    }

    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return direction.getOpposite() == state.get(FACING) && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : state;
    }

    @Override
    public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
        if(world.getBlockEntity(pos) instanceof ItemDisplayBoardBlockEntity itemDisplayBoardBlockEntity) {
            if(!itemDisplayBoardBlockEntity.getStack(0).isEmpty()) {
                return new ItemStack(itemDisplayBoardBlockEntity.getStack(0).getItem());
            }
        }
        return super.getPickStack(world, pos, state);
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(world.getBlockEntity(pos) instanceof ItemDisplayBoardBlockEntity itemDisplayBoardBlockEntity) {
            ItemStack displayStack = itemDisplayBoardBlockEntity.getStack(0);
            if(!stack.isEmpty() && !player.isSneaking() && !(displayStack==stack)) {
                itemDisplayBoardBlockEntity.setStack(0, stack.copyWithCount(1));
                world.playSound(player, pos, SoundEvents.ENTITY_ITEM_FRAME_ADD_ITEM, SoundCategory.BLOCKS, 1f, 1f);

                itemDisplayBoardBlockEntity.markDirty();
                world.updateListeners(pos, state, state, 3);
            } else if(stack.isEmpty() && player.isSneaking() && !displayStack.isEmpty()) {
                world.playSound(player, pos, SoundEvents.ENTITY_ITEM_FRAME_REMOVE_ITEM, SoundCategory.BLOCKS, 1f, 1f);
                itemDisplayBoardBlockEntity.clear();

                itemDisplayBoardBlockEntity.markDirty();
                world.updateListeners(pos, state, state, 3);
            } else if(!world.isClient()) {
                player.openHandledScreen(itemDisplayBoardBlockEntity);
            }
        }

        return ItemActionResult.SUCCESS;
    }
}
