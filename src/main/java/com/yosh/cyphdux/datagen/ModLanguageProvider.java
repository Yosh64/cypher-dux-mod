package com.yosh.cyphdux.datagen;

import com.yosh.cyphdux.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class ModLanguageProvider extends FabricLanguageProvider {
    public ModLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    // function to capitalize the first letter of each word
    public static String capitalizeWords(String input) {
        // split the input string into an array of words
        String[] words = input.split("\\s");

        // StringBuilder to store the result
        StringBuilder result = new StringBuilder();

        // iterate through each word
        for (String word : words) {
            // capitalize the first letter, append the rest of the word, and add a space
            result.append(Character.toTitleCase(word.charAt(0))).append(word.substring(1)).append(" ");
        }

        // convert StringBuilder to String and trim leading/trailing spaces
        return result.toString().trim();
    }
    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
            String name;
        for (Item item : Arrays.asList(ModItems.ROSE_GOLD_INGOT, ModItems.ROSE_GOLD_AXE, ModItems.ROSE_GOLD_HOE, ModItems.ROSE_GOLD_PICKAXE, ModItems.ROSE_GOLD_SHOVEL, ModItems.ROSE_GOLD_SWORD, ModItems.ROSE_GOLD_BOOTS, ModItems.ROSE_GOLD_LEGGINGS, ModItems.ROSE_GOLD_CHESTPLATE, ModItems.ROSE_GOLD_HELMET, ModItems.COPPER_PLATED_COAL, ModItems.ENRICHED_COPPER_PLATED_COAL, ModItems.SYNTHETIC_AMETHYST, ModItems.SYNTHETIC_EMERALD, ModItems.KAYBER_KRYSTAL)) {
            name = Registries.ITEM.getId(item).getPath().replace('_',' ');
            translationBuilder.add(item, capitalizeWords(name));
        }
    }
}
