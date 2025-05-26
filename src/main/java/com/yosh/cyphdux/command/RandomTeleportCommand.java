package com.yosh.cyphdux.command;

import com.mojang.brigadier.context.CommandContext;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
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
            for (int i = 0; i < 16; i++) {
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
                    SoundCategory soundCategory;
                    SoundEvent soundEvent;

                    soundEvent = SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;
                    soundCategory = SoundCategory.PLAYERS;


                    world.playSound(null, user.getX(), user.getY(), user.getZ(), soundEvent, soundCategory);
                    user.onLanding();
                    return 1;
                }
            }
        }
        return 0;

    }
}
