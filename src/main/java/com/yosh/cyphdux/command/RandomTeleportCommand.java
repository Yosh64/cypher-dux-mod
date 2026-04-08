package com.yosh.cyphdux.command;

import com.mojang.brigadier.context.CommandContext;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class RandomTeleportCommand {

    public static int randomTeleport(CommandContext<ServerCommandSource> context) {
        return randomTeleport(context, 16, 8, 16);
    }

    public static int randomTeleport(CommandContext<ServerCommandSource> context, int width) {
        return randomTeleport(context, width, 8, 16);
    }

    public static int randomTeleport(CommandContext<ServerCommandSource> context, int width, int height) {
        return randomTeleport(context, width, height, 16);
    }

    public static int randomTeleport(CommandContext<ServerCommandSource> context, int width, int height, int maxAttempt){
        final PlayerEntity user = context.getSource().getPlayer();
        final ServerWorld world = context.getSource().getWorld();
        if (!world.isClient) {
            for (int i = 0; i < maxAttempt; i++) {
                double d = user.getX() + (user.getRandom().nextDouble() - 0.5) * width;
                double e = user.getY();
                double f = user.getZ() + (user.getRandom().nextDouble() - 0.5) * width;
                if (user.hasVehicle()) {
                    user.stopRiding();
                }

                Vec3d vec3d = user.getPos();
                if (teleport(d, e, f, user,height)) {
                    world.emitGameEvent(GameEvent.TELEPORT, vec3d, GameEvent.Emitter.of(user));
                    user.onLanding();
                    return 1;
                }
            }
        }
        return 0;

    }

    private static boolean teleport(double x, double y, double z, PlayerEntity entity, int spread) {
        World world = entity.getWorld();
        int maxYDistance = (int) Math.max(
                MathHelper.floor(y) + spread,
                (double) (world.getBottomY() + ((ServerWorld) world).getLogicalHeight() - 1)
        );
        int minYDistance = (int) Math.max(
                MathHelper.floor(y) - spread,
                (double) world.getBottomY()
        );
        double d = entity.getX();
        double e = entity.getY();
        double f = entity.getZ();
        double g = y;
        boolean bl = false;
        BlockPos blockPos = BlockPos.ofFloored(x, y, z);
        BlockPos blockPos1 = blockPos;
        if (world.isChunkLoaded(blockPos)) {
            boolean bl2 = false;

            while (!bl2 && blockPos1.getY() > minYDistance) {
                BlockPos blockPos2 = blockPos1.down();
                BlockState blockState = world.getBlockState(blockPos2);
                if (blockState.blocksMovement()) {
                    bl2 = true;
                } else {
                    g--;
                    blockPos1 = blockPos2;
                }
            }

            if (!bl2) {
                g = y;
            }

            blockPos1 = blockPos;
            while (!bl2 && blockPos1.getY() < maxYDistance) {
                BlockPos blockPos2 = blockPos1.up();
                BlockState blockState = world.getBlockState(blockPos2);
                if (blockState.blocksMovement()) {
                    bl2 = true;
                    g+=2;
                } else {
                    g++;
                    blockPos1 = blockPos2;
                }
            }

            if (bl2) {
                entity.requestTeleport(x, g, z);
                if (world.isSpaceEmpty(entity) && !world.containsFluid(entity.getBoundingBox())) {
                    bl = true;
                }
            }
        }

        if (!bl) {
            entity.requestTeleport(d, e, f);
            return false;
        } else {

            return true;
        }
    }

}
