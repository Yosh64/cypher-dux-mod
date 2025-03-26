package com.yosh.cyphdux.integration.emi;

import com.yosh.cyphdux.CypherDuxMod;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import net.minecraft.util.Identifier;

public class ModRecipeCategories {
    public static final EmiRecipeCategory ENRICHING = new EmiRecipeCategory(Identifier.of(CypherDuxMod.MOD_ID,"enriching"),ModRecipeWorkstations.ENRICHING_FURNACE);
}
