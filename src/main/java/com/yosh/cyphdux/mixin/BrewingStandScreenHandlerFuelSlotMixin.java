package com.yosh.cyphdux.mixin;

import com.yosh.cyphdux.tags.ModTags;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.BrewingStandScreenHandler.FuelSlot;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FuelSlot.class)
public abstract class BrewingStandScreenHandlerFuelSlotMixin extends Slot {

    public BrewingStandScreenHandlerFuelSlotMixin(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Inject(method = "matches", at = @At(value = "RETURN"), cancellable = true)
    private static void onMatches(ItemStack stack, CallbackInfoReturnable<Boolean> cir){
        if (stack.isIn(ModTags.Items.BREWING_FUEL)) cir.setReturnValue(true);
    }
}
