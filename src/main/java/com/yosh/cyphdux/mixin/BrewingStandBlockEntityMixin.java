package com.yosh.cyphdux.mixin;

import com.yosh.cyphdux.tags.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.BrewingStandBlockEntity;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrewingStandBlockEntity.class)
public abstract class BrewingStandBlockEntityMixin extends LockableContainerBlockEntity{

    protected BrewingStandBlockEntityMixin(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private static void onTick(World world, BlockPos pos, BlockState state, BrewingStandBlockEntity blockEntity, CallbackInfo ci){
        ItemStack fuelStack = blockEntity.inventory.get(4);
        if (blockEntity.fuel <= 0 && fuelStack.isIn(ModTags.Items.BREWING_FUEL)) {
            blockEntity.fuel = 20;
            fuelStack.decrement(1);
            markDirty(world, pos, state);
        }
    }
    @Inject(method = "isValid", at = @At(value = "RETURN"), cancellable = true)
    private void onIsValid(int slot, ItemStack stack,CallbackInfoReturnable<Boolean> cir){
        if (slot == 4 && stack.isIn(ModTags.Items.BREWING_FUEL)) cir.setReturnValue(true);
    }
}

