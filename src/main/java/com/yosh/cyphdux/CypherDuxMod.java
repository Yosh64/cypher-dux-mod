package com.yosh.cyphdux;

import com.yosh.cyphdux.armor.ModArmorMaterials;
import com.yosh.cyphdux.block.ModBlocks;
import com.yosh.cyphdux.block.entity.ModBlockEntityTypes;
import com.yosh.cyphdux.item.ModItems;
import com.yosh.cyphdux.recipe.ModRecipes;
import com.yosh.cyphdux.sceenhandler.ScreenHandlerTypes;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CypherDuxMod implements ModInitializer {
	public static final String MOD_ID = "cypher-dux-mod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModArmorMaterials.initialize();
		ModBlocks.initialize();
		ModItems.initialize();
		ModBlockEntityTypes.initialize();
		ScreenHandlerTypes.initialize();
		ModRecipes.initialize();
	}
}