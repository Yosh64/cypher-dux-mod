package com.yosh.cyphdux.sceenhandler;

import com.yosh.cyphdux.block.ModBlocks;
import com.yosh.cyphdux.block.entity.EnrichingFurnaceBlockEntity;
import com.yosh.cyphdux.sceenhandler.slot.ModOutputSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EnrichingFurnaceScreenHandler extends ScreenHandler {
    private final EnrichingFurnaceBlockEntity blockEntity;
    private final ScreenHandlerContext context;
    private final Inventory inventory;
    private final World world;
    private final PropertyDelegate propertyDelegate;

    // Client Constructor (called from client)
    public EnrichingFurnaceScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos pos){
        this(syncId, playerInventory, (EnrichingFurnaceBlockEntity) playerInventory.player.getWorld().getBlockEntity(pos),new ArrayPropertyDelegate(4));
    }
    // Main Constructor (directly called from the server)
    public EnrichingFurnaceScreenHandler(int syncId, PlayerInventory playerInventory, EnrichingFurnaceBlockEntity blockEntity, PropertyDelegate arrayPropertyDelegate) {
        super(ScreenHandlerTypes.ENRICHING_FURNACE_SCREEN_HANDLER, syncId);
        checkSize(((Inventory)blockEntity),4);
        checkDataCount(arrayPropertyDelegate, 4);
        this.inventory = ((Inventory) blockEntity);
        inventory.onOpen(playerInventory.player);
        this.world = playerInventory.player.getWorld();
        this.propertyDelegate = arrayPropertyDelegate;
        this.blockEntity = blockEntity;
        this.context = ScreenHandlerContext.create(this.blockEntity.getWorld(),this.blockEntity.getPos());
        //Addition Slot
        this.addSlot(new Slot(inventory,0,38,17));
        //Base Slot
        this.addSlot(new Slot(inventory,1,56,17));
        //Fuel Slot
        this.addSlot(new Slot(inventory,2,56,53));
        //Output
        this.addSlot(new ModOutputSlot(playerInventory.player, inventory,3,116,35));

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        this.addProperties(arrayPropertyDelegate);
    }

    public boolean isCrafting(){
        return propertyDelegate.get(0)>0;
    }
    public float getCookProgress(){
        int progress = this.propertyDelegate.get(0);
        int maxProgress = this.propertyDelegate.get(1);

        return MathHelper.clamp((float)progress / maxProgress, 0.0F, 1.0F);
    }


    public boolean isFuelTicking(){
        return propertyDelegate.get(2)>0;
    }
    public float getFuelProgress(){
        int maxTickingFuel = this.propertyDelegate.get(3);
        int tickingFuel = this.propertyDelegate.get(2);
        if (maxTickingFuel == 0) {
            maxTickingFuel = 100;
        }

        return MathHelper.clamp((float)tickingFuel / (float)maxTickingFuel, 0.0F, 1.0F);
    }
    private void addPlayerHotbar(PlayerInventory playerInv){
        for (int column = 0; column<9; column++){
            addSlot(new Slot(playerInv,column,8+(column*18),142));
        }
    }
    private void addPlayerInventory(PlayerInventory playerInv){
        for (int row = 0; row<3; row++){
            for (int column = 0; column<9; column++){
                addSlot(new Slot(playerInv,9+(column+(row*9)),8+(column*18),84+(row*18)));
            }
        }
    }
    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot2 = this.slots.get(slot);
        if (slot2 != null && slot2.hasStack()) {
            ItemStack itemStack2 = slot2.getStack();
            itemStack = itemStack2.copy();
            if (slot == 3) {
                if (!this.insertItem(itemStack2, 4, 39, true)) {
                    return ItemStack.EMPTY;
                }

                slot2.onQuickTransfer(itemStack2, itemStack);
            }else if (slot == 2) {
                if (!this.insertItem(itemStack2, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }

                slot2.onQuickTransfer(itemStack2, itemStack);
            } else if (slot != 1 && slot != 0) {
                if (this.isFuel(itemStack2)) {
                    if (!this.insertItem(itemStack2, 2, 3, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.insertItem(itemStack2, 0, 2, false)) {
                    return ItemStack.EMPTY;
                } else if (slot >= 3 && slot < 30) {
                    if (!this.insertItem(itemStack2, 30, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slot >= 30 && slot < 39 && !this.insertItem(itemStack2, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(itemStack2, 4, 39, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot2.setStack(ItemStack.EMPTY);
            } else {
                slot2.markDirty();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot2.onTakeItem(player, itemStack2);
        }

        return itemStack;
    }

    protected boolean isFuel(ItemStack itemStack) {
        return itemStack.isOf(Items.REDSTONE) || itemStack.isOf(Items.REDSTONE_BLOCK);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return canUse(this.context,player, ModBlocks.ENRICHING_FURNACE);
    }
    public EnrichingFurnaceBlockEntity getBlockEntity(){
        return this.blockEntity;
    }


}
