package com.yosh.cyphdux.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Item;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }
    public static void offerSmithingUpgradeRecipe(RecipeExporter exporter, Item input, Item addition, RecipeCategory category, Item result) {
        //offerSmithingUpgradeRecipe(exporter,Items.AIR,input,addition,category,result);
    }
    public static void offerSmithingUpgradeRecipe(RecipeExporter exporter, Item template, Item input, Item addition, RecipeCategory category, Item result) {
        /*SmithingTransformRecipeJsonBuilder.create(
                        Ingredient.ofItems(template), Ingredient.ofItems(input), Ingredient.ofItems(addition), category, result
                )
                .criterion(hasItem(addition), conditionsFromItem(addition))
                .offerTo(exporter, getItemPath(result) + "_smithing");*/
    }
    @Override
    public void generate(RecipeExporter exporter) {
        /*
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.DIVING_HELMET).pattern("ggg").pattern("gpg")
                .input('g', Ingredient.ofItems(Items.GLASS))
                .input('p',Items.POTION)
                .criterion(FabricRecipeProvider.hasItem(Items.POTION),FabricRecipeProvider.conditionsFromItem(Items.POTION))
                .criterion(FabricRecipeProvider.hasItem(Items.GLASS),FabricRecipeProvider.conditionsFromItem(Items.GLASS))
                .offerTo(exporter);*/
    }
}
