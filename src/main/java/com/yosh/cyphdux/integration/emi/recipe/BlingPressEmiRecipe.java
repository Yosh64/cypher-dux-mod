package com.yosh.cyphdux.integration.emi.recipe;

import com.yosh.cyphdux.integration.emi.ModRecipeCategories;
import com.yosh.cyphdux.recipe.PressingRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlingPressEmiRecipe implements EmiRecipe {
    private final Identifier id;
    private final List<EmiIngredient> input;
    private final List<EmiStack> output;
    private final float experience;
    private final int pressingTime;

    public BlingPressEmiRecipe(RecipeEntry<PressingRecipe> recipe) {
        this.id = recipe.id();
        this.input = List.of(EmiIngredient.of(recipe.value().leftItem()),EmiIngredient.of(recipe.value().rightItem()));
        this.output = List.of(EmiStack.of(recipe.value().output()));
        this.experience = recipe.value().experience();
        this.pressingTime = recipe.value().pressingTime();
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return ModRecipeCategories.PRESSING;
    }

    @Override
    public @Nullable Identifier getId() {
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
    public void addWidgets(WidgetHolder widgetHolder) {
        // Add an arrow texture to indicate processing
        widgetHolder.addFillingArrow(44,5,this.pressingTime*50);
        widgetHolder.addTooltip(List.of(TooltipComponent.of(Text.translatable("emi.cooking.time",(float)pressingTime/20).asOrderedText())),44,5, 24, 17);

        widgetHolder.addText(Text.of(this.experience+" XP"),44,30,16777215,true);

        // Adds flame widget
        widgetHolder.addTexture(EmiTexture.EMPTY_FLAME,20,24);
        widgetHolder.addAnimatedTexture(EmiTexture.FULL_FLAME,20,24,5000,false,true,true);
        // Adds an input slot on the left
        widgetHolder.addSlot(input.get(0), 0, 4);
        widgetHolder.addSlot(input.get(1), 18, 4);

        // Adds an output slot on the right
        // Note that output slots need to call `recipeContext` to inform EMI about their recipe context
        // This includes being able to resolve recipe trees, favorite stacks with recipe context, and more
        widgetHolder.addSlot(output.get(0), 74, 0).large(true).recipeContext(this);
    }
}
