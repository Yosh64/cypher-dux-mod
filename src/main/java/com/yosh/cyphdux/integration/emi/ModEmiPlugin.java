package com.yosh.cyphdux.integration.emi;

import com.yosh.cyphdux.integration.emi.handler.BlingPressEmiRecipeHandler;
import com.yosh.cyphdux.integration.emi.handler.EnrichingFurnaceEmiRecipeHandler;
import com.yosh.cyphdux.integration.emi.recipe.BlingPressEmiRecipe;
import com.yosh.cyphdux.integration.emi.recipe.EnrichingRecipeEmiRecipe;
import com.yosh.cyphdux.recipe.EnrichingRecipe;
import com.yosh.cyphdux.recipe.ModRecipes;
import com.yosh.cyphdux.recipe.PressingRecipe;
import com.yosh.cyphdux.sceenhandler.ScreenHandlerTypes;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeManager;

public class ModEmiPlugin implements EmiPlugin {

    @Override
    public void register(EmiRegistry registry) {
        // Tell EMI to add a tab for your category
        registry.addCategory(ModRecipeCategories.ENRICHING);
        registry.addCategory(ModRecipeCategories.PRESSING);

        // Add all the workstations your category uses
        registry.addWorkstation(ModRecipeCategories.ENRICHING, ModRecipeWorkstations.ENRICHING_FURNACE);
        registry.addRecipeHandler(ScreenHandlerTypes.ENRICHING_FURNACE_SCREEN_HANDLER,new EnrichingFurnaceEmiRecipeHandler());

        registry.addWorkstation(ModRecipeCategories.PRESSING, ModRecipeWorkstations.BLING_PRESS);
        registry.addRecipeHandler(ScreenHandlerTypes.BLING_PRESS_SCREEN_HANDLER,new BlingPressEmiRecipeHandler());

        RecipeManager manager = registry.getRecipeManager();

        // Use vanilla's concept of your recipes and pass them to your EmiRecipe representation
        for (RecipeEntry<EnrichingRecipe> recipe : manager.listAllOfType(EnrichingRecipe.Type.INSTANCE)) {
            registry.addRecipe(new EnrichingRecipeEmiRecipe(recipe));
        }
        for (RecipeEntry<PressingRecipe> recipe : manager.listAllOfType(ModRecipes.PRESSING_TYPE)) {
            registry.addRecipe(new BlingPressEmiRecipe(recipe));
        }
    }
}