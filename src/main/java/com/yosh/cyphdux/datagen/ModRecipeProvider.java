package com.yosh.cyphdux.datagen;

import com.yosh.cyphdux.block.ModBlocks;
import com.yosh.cyphdux.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.data.server.recipe.SmithingTransformRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }
    public static void offerSmithingUpgradeRecipe(RecipeExporter exporter, Item input, Item addition, RecipeCategory category, Item result) {
        offerSmithingUpgradeRecipe(exporter,Items.AIR,input,addition,category,result);
    }
    public static void offerSmithingUpgradeRecipe(RecipeExporter exporter, Item template, Item input, Item addition, RecipeCategory category, Item result) {
        SmithingTransformRecipeJsonBuilder.create(
                        Ingredient.ofItems(template), Ingredient.ofItems(input), Ingredient.ofItems(addition), category, result
                )
                .criterion(hasItem(addition), conditionsFromItem(addition))
                .offerTo(exporter, getItemPath(result) + "_smithing");
    }
    @Override
    public void generate(RecipeExporter exporter) {

    }
}
