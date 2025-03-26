package com.yosh.cyphdux.recipe.input;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.RecipeInput;

public record DoubleStackRecipeInput(ItemStack addition, ItemStack base) implements RecipeInput {
    @Override
    public ItemStack getStackInSlot(int slot) {
        return switch (slot) {
            case 0 -> this.addition;
            case 1 -> this.base;
            default -> throw new IllegalArgumentException("Recipe does not contain slot " + slot);
        };
    }

    @Override
    public int getSize() { return 2; }

    @Override
    public boolean isEmpty() {
        return this.addition.isEmpty() && this.base.isEmpty();
    }
}
