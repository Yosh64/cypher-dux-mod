package com.yosh.cyphdux.sceenhandler;

import com.yosh.cyphdux.block.entity.ItemDisplayBoardBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

public class ItemDisplayBoardScreenHandler extends ScreenHandler {
    private final Inventory inventory;

    public ItemDisplayBoardScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos pos) {
        this(syncId, playerInventory, (ItemDisplayBoardBlockEntity) playerInventory.player.getWorld().getBlockEntity(pos));
    }

    public ItemDisplayBoardScreenHandler(int syncId, PlayerInventory playerInventory, ItemDisplayBoardBlockEntity blockEntity) {
        super(ScreenHandlerTypes.ITEM_DISPLAY_BOARD_SCREEN_HANDLER, syncId);
        this.inventory = ((Inventory) blockEntity);

        this.addSlot(new DisplaySlot(inventory,0,80,20));

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }

    private void addPlayerHotbar(PlayerInventory playerInv){
        for (int column = 0; column<9; column++){
            addSlot(new Slot(playerInv,column,8+(column*18),109));
        }
    }
    private void addPlayerInventory(PlayerInventory playerInv){
        for (int row = 0; row<3; row++){
            for (int column = 0; column<9; column++){
                addSlot(new Slot(playerInv,9+(column+(row*9)),8+(column*18),51+(row*18)));
            }
        }
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }
    static class DisplaySlot extends Slot {

        public DisplaySlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public int getMaxItemCount() {
            return 1;
        }

        @Override
        public ItemStack takeStack(int amount) {
            super.takeStack(amount);
            return ItemStack.EMPTY;

        }

        // ↓ ↓ override methods from Spud's shops "https://github.com/Milo-Cat/spuds-shops" ↓ ↓
        @Override
        public boolean canInsert(ItemStack stack) {
            return this.getStack().getItem() == stack.getItem() || this.getStack().isEmpty();
        }
        @Override
        public ItemStack insertStack(ItemStack newStack, int count) {

            if (newStack.isEmpty()) {return newStack;}

            ItemStack thisStack = this.getStack();

            if (thisStack.isEmpty() || thisStack.getItem() != newStack.getItem()) {
                this.setStack(newStack.copy());
                this.getStack().setCount(1);
                return newStack;
            }

            thisStack.setCount(1);

            this.setStack(thisStack);

            return newStack;
        }
        @Override
        public boolean canTakePartial(PlayerEntity player) {
            return false;
        }
        // ↑ ↑ override methods from Spud's shops "https://github.com/Milo-Cat/spuds-shops" ↑ ↑
    }
}
