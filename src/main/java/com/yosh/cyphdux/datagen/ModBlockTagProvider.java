package com.yosh.cyphdux.datagen;

import com.yosh.cyphdux.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.CHARCOAL_BLOCK)
                .add(ModBlocks.COPPER_PLATED_COAL_BLOCK)
                .add(ModBlocks.ENRICHED_COPPER_PLATED_COAL_BLOCK)
                .add(ModBlocks.ROSE_GOLD_BLOCK)
                .add(ModBlocks.ENRICHING_FURNACE);

        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.ROSE_GOLD_BLOCK);
        getOrCreateTagBuilder(BlockTags.BEACON_BASE_BLOCKS)
                .add(ModBlocks.ROSE_GOLD_BLOCK);
        getOrCreateTagBuilder(BlockTags.GUARDED_BY_PIGLINS)
                .add(ModBlocks.ROSE_GOLD_BLOCK);

        getOrCreateTagBuilder(ConventionalBlockTags.STORAGE_BLOCKS)
                .add(ModBlocks.CHARCOAL_BLOCK)
                .add(ModBlocks.COPPER_PLATED_COAL_BLOCK)
                .add(ModBlocks.ENRICHED_COPPER_PLATED_COAL_BLOCK)
                .add(ModBlocks.ROSE_GOLD_BLOCK);
    }
}
