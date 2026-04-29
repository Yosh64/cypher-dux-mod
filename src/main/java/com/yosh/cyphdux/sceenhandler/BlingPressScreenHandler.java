package com.yosh.cyphdux.sceenhandler;

import com.yosh.cyphdux.block.entity.BlingPressBlockEntity;
import com.yosh.cyphdux.recipe.ModRecipes;
import com.yosh.cyphdux.recipe.PressingRecipe;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class BlingPressScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    private final BlingPressBlockEntity blockEntity;
    private final RecipeType<PressingRecipe> recipeType = ModRecipes.PRESSING_TYPE;
    private final World world;

    public BlingPressScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos pos) {
        this(syncId, playerInventory, playerInventory.player.getWorld().getBlockEntity(pos), new ArrayPropertyDelegate(4));
    }

    public BlingPressScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity blockEntity, PropertyDelegate arrayPropertyDelegate) {
        super(ScreenHandlerTypes.BLING_PRESS_SCREEN_HANDLER,syncId);
        this.inventory = ((Inventory) blockEntity);
        this.blockEntity = ((BlingPressBlockEntity) blockEntity);
        this.propertyDelegate = arrayPropertyDelegate;
        this.world = playerInventory.player.getWorld();

        //Left Slot
        this.addSlot(new Slot(inventory,0,38,17));
        //Right Slot
        this.addSlot(new Slot(inventory,1,56,17));
        //Fuel Slot
        this.addSlot(new Slot(inventory,2,56,53));
        //Output
        this.addSlot(new Slot(inventory,3,116,35){
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }
        });

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        addProperties(arrayPropertyDelegate);
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

    public boolean isCrafting(){
        return propertyDelegate.get(0) > 0;
    }

    public boolean isFuelTicking(){
        return propertyDelegate.get(2)>0;
    }

    public float getScaledArrowProgress(){
        int progress = this.propertyDelegate.get(0);
        int maxProgress = this.propertyDelegate.get(1);

        return MathHelper.clamp((float)progress / maxProgress, 0.0F, 1.0F);
    }

    public float getScaledFireProgress(){
        int maxTickingFuel = this.propertyDelegate.get(3);
        int tickingFuel = this.propertyDelegate.get(2);
        if (maxTickingFuel == 0) {
            maxTickingFuel = 100;
        }

        return MathHelper.clamp((float)tickingFuel / (float)maxTickingFuel, 0.0F, 1.0F);
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
                if (this.isInRecipeSlot(itemStack2,0)) {
                    if (!this.insertItem(itemStack2, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (this.isInRecipeSlot(itemStack2,1)) {
                    if (!this.insertItem(itemStack2, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (this.isFuel(itemStack2)) {
                    if (!this.insertItem(itemStack2, 2, 3, false)) {
                        return ItemStack.EMPTY;
                    }
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

    private boolean isInRecipeSlot(ItemStack stack, int slotId) {
        return this.world.getRecipeManager().listAllOfType(this.recipeType).stream().anyMatch(recipeEntry -> recipeEntry.value().isInRecipe(stack,slotId, this.world));
    }

    private boolean isFuel(ItemStack itemStack) {
        return AbstractFurnaceBlockEntity.canUseAsFuel(itemStack);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }
}
