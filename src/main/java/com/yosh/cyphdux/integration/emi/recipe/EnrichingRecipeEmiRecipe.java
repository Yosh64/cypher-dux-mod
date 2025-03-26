package com.yosh.cyphdux.integration.emi.recipe;

import com.yosh.cyphdux.integration.emi.ModRecipeCategories;
import com.yosh.cyphdux.recipe.EnrichingRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.SlotWidget;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class EnrichingRecipeEmiRecipe implements EmiRecipe {
    private final Identifier id;
    private final List<EmiIngredient> input;
    private final List<EmiStack> output;
    private final float experience;
    private final int cookingTime;
    private final EmiTexture ENRICHING_FUEL = new EmiTexture(Identifier.ofVanilla("textures/item/redstone.png"),0,0,16,16);

    public EnrichingRecipeEmiRecipe(RecipeEntry<EnrichingRecipe> recipe) {
        this.id = recipe.id();
        this.input = List.of(EmiIngredient.of(recipe.value().getIngredients().get(1)),EmiIngredient.of(recipe.value().getIngredients().get(0)));
        this.output = List.of(EmiStack.of(recipe.value().getResult(null)));
        this.cookingTime = recipe.value().getCookingTime();
        this.experience = recipe.value().getExperience();
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return ModRecipeCategories.ENRICHING;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return input;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return output;
    }

    @Override
    public int getDisplayWidth() {
        return 100;
    }

    @Override
    public int getDisplayHeight() {
        return 40;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        // Add an arrow texture to indicate processing
        widgets.addFillingArrow(44,5,this.cookingTime*50);

        widgets.addText(Text.of(this.experience+" XP"),44,30,16777215,true);
        if (this.cookingTime >= 1000) {
            if (this.cookingTime > 6400){
                widgets.addSlot(EmiIngredient.of(Ingredient.ofStacks(new ItemStack(Items.REDSTONE_BLOCK,Math.max(this.cookingTime/1000,1)))), 0, 22).drawBack(false);
            }
            else widgets.addSlot(EmiIngredient.of(Ingredient.ofStacks(new ItemStack(Items.REDSTONE_BLOCK,Math.max((int)Math.ceil(this.cookingTime/1000.0) ,1)),new ItemStack(Items.REDSTONE,Math.max(this.cookingTime/100,1)))), 0, 22).drawBack(false);
        }
        else widgets.addSlot(EmiIngredient.of(Ingredient.ofStacks(new ItemStack(Items.REDSTONE,Math.max(this.cookingTime/100,1)),new ItemStack(Items.REDSTONE_BLOCK,Math.max((int)Math.ceil(this.cookingTime/1000.0) ,1)))), 0, 22).drawBack(false);
        // Adds flame widget
        widgets.addTexture(EmiTexture.EMPTY_FLAME,20,24);
        widgets.addAnimatedTexture(EmiTexture.FULL_FLAME,20,24,5000,false,true,true);
        // Adds an input slot on the left
        widgets.addSlot(input.get(0), 0, 4);
        widgets.addSlot(input.get(1), 18, 4);

        // Adds an output slot on the right
        // Note that output slots need to call `recipeContext` to inform EMI about their recipe context
        // This includes being able to resolve recipe trees, favorite stacks with recipe context, and more
        widgets.addSlot(output.get(0), 74, 0).large(true).recipeContext(this);
    }
}