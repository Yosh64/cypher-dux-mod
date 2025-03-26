package com.yosh.cyphdux.integration.emi.handler;

import com.yosh.cyphdux.integration.emi.ModRecipeCategories;
import com.yosh.cyphdux.sceenhandler.EnrichingFurnaceScreenHandler;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.handler.StandardRecipeHandler;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EnrichingFurnaceEmiRecipeHandler implements StandardRecipeHandler<EnrichingFurnaceScreenHandler> {

    @Override
    public List<Slot> getInputSources(EnrichingFurnaceScreenHandler handler) {
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
    public List<Slot> getCraftingSlots(EnrichingFurnaceScreenHandler handler) {
        List<Slot> slots = new ArrayList<>();
        for (int i = 0; i < 2; ++i) {
            slots.add(handler.getSlot(i));
        }
        return slots;
    }

    @Override
    public @Nullable Slot getOutputSlot(EnrichingFurnaceScreenHandler handler) {
        return handler.slots.get(3);
    }

    @Override
    public boolean supportsRecipe(EmiRecipe recipe) {
        return recipe.getCategory() == ModRecipeCategories.ENRICHING && recipe.supportsRecipeTree();
    }
}
