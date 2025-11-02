package com.yosh.cyphdux.command;

import com.mojang.brigadier.context.CommandContext;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.event.GameEvent;

public class RandomTeleportCommand {

    public static int randomTeleport(CommandContext<ServerCommandSource> context) {
        return randomTeleport(context, 16, 8, 16);
    }

    public static int randomTeleport(CommandContext<ServerCommandSource> context, float width) {
        return randomTeleport(context, width, 8, 16);
    }

    public static int randomTeleport(CommandContext<ServerCommandSource> context, float width, float height) {
        return randomTeleport(context, width, height, 16);
    }

    public static int randomTeleport(CommandContext<ServerCommandSource> context, float width, float height, int maxAttempt){
        final PlayerEntity user = context.getSource().getPlayer();
        final ServerWorld world = context.getSource().getWorld();
        if (!world.isClient) {
            for (int i = 0; i < maxAttempt; i++) {
                double d = user.getX() + (user.getRandom().nextDouble() - 0.5) * width;
                double e = MathHelper.clamp(
                        user.getY() + (double) (user.getRandom().nextInt(MathHelper.ceil((int) height))),
                        (double) world.getBottomY(),
                        (double) (world.getBottomY() + ((ServerWorld) world).getLogicalHeight() - 1)
                );
                double f = user.getZ() + (user.getRandom().nextDouble() - 0.5) * width;
                if (user.hasVehicle()) {
                    user.stopRiding();
                }

                Vec3d vec3d = user.getPos();
                if (user.teleport(d, e, f, false)) {
                    world.emitGameEvent(GameEvent.TELEPORT, vec3d, GameEvent.Emitter.of(user));
                    user.onLanding();
                    return 1;
                }
            }
        }
        return 0;

    }
}
