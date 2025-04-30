package com.yosh.cyphdux.sceenhandler;

import com.yosh.cyphdux.block.entity.ItemDisplayBoardBlockEntity;
import com.yosh.cyphdux.network.DisplayItemC2SPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class ItemDisplayBoardScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    public final List<ItemStack> itemList = new ArrayList<>();
    private final ItemDisplayBoardBlockEntity displayBoardBlockEntity;
    public ItemDisplayBoardScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos pos) {
        this(syncId, playerInventory, (ItemDisplayBoardBlockEntity) playerInventory.player.getWorld().getBlockEntity(pos));
    }

    public ItemDisplayBoardScreenHandler(int syncId, PlayerInventory playerInventory, ItemDisplayBoardBlockEntity blockEntity) {
        super(ScreenHandlerTypes.ITEM_DISPLAY_BOARD_SCREEN_HANDLER, syncId);
        this.inventory = ((Inventory) blockEntity);
        this.displayBoardBlockEntity = blockEntity;
        this.itemList.addAll(ItemGroups.getSearchGroup().getSearchTabStacks());
        this.addSlot(new DisplaySlot(inventory,0,36,24));
        this.drawSearchSlot();

    }

    private void drawSearchSlot() {
        int x = 9;
        int y = 49;

        for (int i = 0; i < 45 && i < (this.getItemListCount());++i){
            int xSlot = x + i % 9 * 18;
            int ySlot = y + (i / 9) * 18 + 2;
            this.addSlot(new Slot(this.inventory,i+1 ,xSlot, ySlot){
                @Override
                public boolean canTakeItems(PlayerEntity playerEntity) {
                    return false;
                }
            });

        }
    }

    public List<ItemStack> getItemList(){
        return this.itemList;
    }
    public int getItemListCount(){
        return this.itemList.size();
    }
    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player) {
        if(player.getWorld().isClient && slotIndex > 0 && slotIndex <= 45) {
            ItemStack selectedItem =this.getSlot(slotIndex).getStack();
            if(selectedItem.isEmpty()){
                selectedItem = new ItemStack(Items.BARRIER);
            }
            DisplayItemC2SPayload payload = new DisplayItemC2SPayload(selectedItem);
            ClientPlayNetworking.send(payload);
        } else if (slotIndex == 0) {
            if(!player.getWorld().isClient){
                boolean isSlotTheSame = this.displayBoardBlockEntity.getStack(0) == this.displayBoardBlockEntity.getStack(slotIndex);
                this.displayBoardBlockEntity.setStack(0, ItemStack.EMPTY);
                if (!isSlotTheSame) {
                    player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.ENTITY_ITEM_FRAME_REMOVE_ITEM, SoundCategory.PLAYERS);
                }
            }
        }
        super.onSlotClick(slotIndex, button, actionType, player);
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
        public boolean canTakeItems(PlayerEntity playerEntity) {
            return false;
        }

        @Override
        public int getMaxItemCount() {
            return 1;
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
