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
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ItemDisplayBoardBlock extends BlockWithEntity implements BlockEntityProvider {
    private static final VoxelShape SOUTH_SHAPE = ItemDisplayBoardBlock.createCuboidShape(0,0,0,16,16,2);
    private static final VoxelShape NORTH_SHAPE = ItemDisplayBoardBlock.createCuboidShape(0,0,14,16,16,16);
    private static final VoxelShape EAST_SHAPE = ItemDisplayBoardBlock.createCuboidShape(0,0,0,2,16,16);
    private static final VoxelShape WEST_SHAPE = ItemDisplayBoardBlock.createCuboidShape(14,0,0,16,16,16);

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
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
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
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof ItemDisplayBoardBlockEntity) {
                    ItemScatterer.spawn(world, pos, (ItemDisplayBoardBlockEntity)blockEntity);
                    world.updateComparators(pos, this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(world.getBlockEntity(pos) instanceof ItemDisplayBoardBlockEntity itemDisplayBoardBlockEntity) {
            if(itemDisplayBoardBlockEntity.isEmpty() && !stack.isEmpty()) {
                itemDisplayBoardBlockEntity.setStack(0, stack.copyWithCount(1));
                world.playSound(player, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1f, 2f);
                stack.decrement(1);

                itemDisplayBoardBlockEntity.markDirty();
                world.updateListeners(pos, state, state, 0);
            } else if(stack.isEmpty() && !player.isSneaking()) {
                ItemStack stackOnPedestal = itemDisplayBoardBlockEntity.getStack(0);
                player.setStackInHand(Hand.MAIN_HAND, stackOnPedestal);
                world.playSound(player, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1f, 1f);
                itemDisplayBoardBlockEntity.clear();

                itemDisplayBoardBlockEntity.markDirty();
                world.updateListeners(pos, state, state, 0);
            }
        }

        return ItemActionResult.SUCCESS;
    }
}
