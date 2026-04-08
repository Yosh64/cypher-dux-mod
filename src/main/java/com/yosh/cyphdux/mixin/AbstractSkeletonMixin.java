package com.yosh.cyphdux.mixin;

import java.util.Objects;
import java.util.function.Predicate;

import com.yosh.cyphdux.tags.ModTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin({AbstractSkeletonEntity.class})
public class AbstractSkeletonMixin extends HostileEntity {

    protected AbstractSkeletonMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(
            at = {@At("TAIL")},
            method = {"initGoals"}
    )
    private void addGoals(CallbackInfo info) {
        Predicate isInEntityTags = entity -> ((Entity)entity).getType().isIn(ModTags.EntityTypes.BONES_LOVERS);
        Objects.requireNonNull(isInEntityTags);
        Predicate isntInCreativeOrSpectator = EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR;
        Objects.requireNonNull(isntInCreativeOrSpectator);
        Goal goal = new FleeEntityGoal(this, PlayerEntity.class, isInEntityTags, 6.0F, (double)1.0F, 1.2, isntInCreativeOrSpectator);
        this.goalSelector.add(3, goal);
    }

    @Redirect(
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/ai/goal/GoalSelector;add(ILnet/minecraft/entity/ai/goal/Goal;)V",
                    ordinal = 7
            ),
            method = {"initGoals"}
    )
    private void redirectTargetGoal(GoalSelector goalSelector, int priority, Goal goal) {
        Goal newGoal = new ActiveTargetGoal(this, PlayerEntity.class, 10, true, false, (e) -> !((Entity)e).getType().isIn(ModTags.EntityTypes.BONES_LOVERS));
        goalSelector.add(priority, newGoal);
    }
}
