package com.yosh.cyphdux.datagen;

import com.yosh.cyphdux.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.FabricTagKey;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ConventionalItemTags.AMETHYST_GEMS).add(ModItems.SYNTHETIC_AMETHYST);
        getOrCreateTagBuilder(ConventionalItemTags.EMERALD_GEMS).add(ModItems.SYNTHETIC_EMERALD);
        getOrCreateTagBuilder(ConventionalItemTags.GEMS).add(ModItems.SYNTHETIC_AMETHYST);
        getOrCreateTagBuilder(ConventionalItemTags.GEMS).add(ModItems.SYNTHETIC_EMERALD);
        getOrCreateTagBuilder(ItemTags.TRIM_MATERIALS).add(ModItems.SYNTHETIC_AMETHYST);
        getOrCreateTagBuilder(ItemTags.TRIM_MATERIALS).add(ModItems.SYNTHETIC_EMERALD);

        getOrCreateTagBuilder(ItemTags.TRIM_MATERIALS).add(ModItems.ROSE_GOLD_INGOT);

        getOrCreateTagBuilder(ConventionalItemTags.MELEE_WEAPONS_TOOLS)
                .add(ModItems.ROSE_GOLD_AXE)
                .add(ModItems.ROSE_GOLD_SWORD);
        getOrCreateTagBuilder(ConventionalItemTags.MELEE_WEAPON_TOOLS)
                .add(ModItems.ROSE_GOLD_AXE)
                .add(ModItems.ROSE_GOLD_SWORD);

        getOrCreateTagBuilder(ConventionalItemTags.MINING_TOOL_TOOLS)
                .add(ModItems.ROSE_GOLD_PICKAXE);
        getOrCreateTagBuilder(ConventionalItemTags.MINING_TOOLS)
                .add(ModItems.ROSE_GOLD_PICKAXE);

        getOrCreateTagBuilder(ConventionalItemTags.INGOTS)
                .add(ModItems.ROSE_GOLD_INGOT);

        getOrCreateTagBuilder(ItemTags.COALS)
                .add(ModItems.COPPER_PLATED_COAL)
                .add(ModItems.ENRICHED_COPPER_PLATED_COAL);
    }
}
