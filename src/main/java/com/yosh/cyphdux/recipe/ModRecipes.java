package com.yosh.cyphdux.recipe;

import com.yosh.cyphdux.CypherDuxMod;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {
    public static final RecipeSerializer<PressingRecipe> PRESSING_SERIALIZER = Registry.register(Registries.RECIPE_SERIALIZER, CypherDuxMod.of("pressing"),new PressingRecipe.Serializer());
    public static final RecipeType<PressingRecipe> PRESSING_TYPE = Registry.register(Registries.RECIPE_TYPE, CypherDuxMod.of("pressing"), new RecipeType<PressingRecipe>() {
        @Override
        public String toString() {
            return "pressing";
        }
    });

    public static final RecipeType<EnrichingRecipe> ENRICHING_TYPE = Registry.register(Registries.RECIPE_TYPE, Identifier.of(CypherDuxMod.MOD_ID,EnrichingRecipe.Type.ID),EnrichingRecipe.Type.INSTANCE);
    public static final RecipeSerializer<EnrichingRecipe> ENRICHING_SERIALIZER = Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(CypherDuxMod.MOD_ID,EnrichingRecipe.Serializer.ID),EnrichingRecipe.Serializer.INSTANCE);

    public static void initialize(){
    }
}
