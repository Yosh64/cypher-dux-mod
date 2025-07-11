package com.yosh.cyphdux.sceenhandler;

import com.yosh.cyphdux.block.entity.CardboardBoxBlockEntity;
import com.yosh.cyphdux.item.ModItems;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

public class CardboardBoxScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    public CardboardBoxScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos pos) {
        this(syncId, playerInventory, (CardboardBoxBlockEntity) playerInventory.player.getWorld().getBlockEntity(pos));
    }

    public CardboardBoxScreenHandler(int syncId, PlayerInventory playerInventory, CardboardBoxBlockEntity blockEntity){
        super(ScreenHandlerTypes.CARDBOARD_BOX_SCREEN_HANDLER, syncId);
        this.inventory = ((Inventory)blockEntity);
        this.addSlot(new Slot(inventory,0,80,17){
            @Override
            public boolean canInsert(ItemStack stack) {
                return !(stack.isIn(ConventionalItemTags.SHULKER_BOXES)||stack.isOf(ModItems.CARDBOARD_BOX));
            }
        });
        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 48 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 106));
        }
    }
}
