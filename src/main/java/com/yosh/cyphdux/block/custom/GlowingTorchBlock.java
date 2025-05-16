package com.yosh.cyphdux.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractTorchBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class GlowingTorchBlock extends AbstractTorchBlock implements Waterloggable {
    public static final MapCodec<GlowingTorchBlock> CODEC = GlowingTorchBlock.createCodec(GlowingTorchBlock::new);
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public GlowingTorchBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false));
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if ((Boolean)state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    protected MapCodec<? extends GlowingTorchBlock> getCodec() {
        return CODEC;
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        boolean bl = fluidState.getFluid() == Fluids.WATER;
        return (BlockState)super.getPlacementState(ctx).with(WATERLOGGED, bl);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{WATERLOGGED});
    }

    protected FluidState getFluidState(BlockState state) {
        return (Boolean)state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if ((Boolean)state.get(WATERLOGGED)) {
            double d = (double)pos.getX() + (double)0.5F + (random.nextDouble() - (double)0.5F) * 0.2;
            double e = (double)pos.getY() + 0.7 + (random.nextDouble() - (double)0.5F) * 0.2;
            double f = (double)pos.getZ() + (double)0.5F + (random.nextDouble() - (double)0.5F) * 0.2;
            world.addParticle(ParticleTypes.GLOW, d, e, f, (double)0.0F, (double)0.0F, (double)0.0F);
        }
    }
}
