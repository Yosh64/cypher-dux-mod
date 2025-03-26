package com.yosh.cyphdux.recipe;

import com.yosh.cyphdux.CypherDuxMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {
    public static void initialize(){
        CypherDuxMod.LOGGER.info("Registering Mod Recipes");
        Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(CypherDuxMod.MOD_ID,EnrichingRecipe.Serializer.ID),EnrichingRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, Identifier.of(CypherDuxMod.MOD_ID,EnrichingRecipe.Type.ID),EnrichingRecipe.Type.INSTANCE);
    }
}
