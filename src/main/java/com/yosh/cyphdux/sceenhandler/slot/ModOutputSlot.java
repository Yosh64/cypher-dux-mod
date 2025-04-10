package com.yosh.cyphdux.sceenhandler.slot;

import com.yosh.cyphdux.block.entity.EnrichingFurnaceBlockEntity;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;

public class ModOutputSlot extends Slot {
    private final PlayerEntity player;
    private int amount;
    public ModOutputSlot(PlayerEntity player, Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        this.player = player;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return false;
    }

    @Override
    public ItemStack takeStack(int amount) {
        if (this.hasStack()) {
            this.amount = this.amount + Math.min(amount, this.getStack().getCount());
        }

        return super.takeStack(amount);
    }

    @Override
    public void onTakeItem(PlayerEntity player, ItemStack stack) {
        this.onCrafted(stack);
        super.onTakeItem(player, stack);
    }

    @Override
    protected void onCrafted(ItemStack stack, int amount) {
        this.amount += amount;
        this.onCrafted(stack);
    }

    @Override
    protected void onCrafted(ItemStack stack) {
        stack.onCraftByPlayer(this.player.getWorld(), this.player, this.amount);
        if (this.player instanceof ServerPlayerEntity serverPlayerEntity && this.inventory instanceof EnrichingFurnaceBlockEntity enrichingFurnaceBlockEntity) {
            enrichingFurnaceBlockEntity.dropExperienceForRecipesUsed(serverPlayerEntity);
        }

        this.amount = 0;
    }
}
