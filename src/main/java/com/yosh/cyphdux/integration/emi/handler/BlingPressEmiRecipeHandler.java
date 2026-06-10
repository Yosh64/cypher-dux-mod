package com.yosh.cyphdux.integration.emi.handler;

import com.yosh.cyphdux.integration.emi.ModRecipeCategories;
import com.yosh.cyphdux.sceenhandler.BlingPressScreenHandler;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.handler.StandardRecipeHandler;
import net.minecraft.screen.slot.Slot;

import java.util.ArrayList;
import java.util.List;

public class BlingPressEmiRecipeHandler implements StandardRecipeHandler<BlingPressScreenHandler> {
    @Override
    public List<Slot> getInputSources(BlingPressScreenHandler handler) {
        List<Slot> slots = new ArrayList<>();
        for (int i = 0; i < 2; ++i) {
            slots.add(handler.getSlot(i));
        }

        for (int i = 3; i < 3 + 36; ++i) {
            slots.add(handler.getSlot(i));
        }

        return slots;
    }

    @Override
    public List<Slot> getCraftingSlots(BlingPressScreenHandler handler) {
        List<Slot> slots = new ArrayList<>();
        for (int i = 0; i < 2; ++i) {
            slots.add(handler.getSlot(i));
        }
        return slots;
    }

    @Override
    public boolean supportsRecipe(EmiRecipe emiRecipe) {
        return emiRecipe.getCategory() == ModRecipeCategories.PRESSING && emiRecipe.supportsRecipeTree();
    }
}
