package com.yosh.cyphdux;

import com.yosh.cyphdux.datagen.*;
import com.yosh.cyphdux.trim.ModTrimMaterials;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class CypherDuxModDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		//pack.addProvider(ModLanguageProvider::new);
		//pack.addProvider(ModBlockTagProvider::new);
		//pack.addProvider(ModItemTagProvider::new);
		//pack.addProvider(ModLootTableProvider::new);
		pack.addProvider(ModModelProvider::new);
		//pack.addProvider(ModRecipeProvider::new);
		//pack.addProvider(ModRegistryDataGenerator::new);
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.TRIM_MATERIAL, ModTrimMaterials::bootstrap);
	}
}
