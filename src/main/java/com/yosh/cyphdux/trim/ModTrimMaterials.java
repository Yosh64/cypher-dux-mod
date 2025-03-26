package com.yosh.cyphdux.trim;

import com.yosh.cyphdux.CypherDuxMod;
import com.yosh.cyphdux.armor.ModArmorMaterials;
import com.yosh.cyphdux.item.ModItems;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.Item;
import net.minecraft.item.trim.ArmorTrimMaterial;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class ModTrimMaterials {
    public static final RegistryKey<ArmorTrimMaterial> ROSE_GOLD = RegistryKey.of(RegistryKeys.TRIM_MATERIAL, Identifier.of(CypherDuxMod.MOD_ID,"rose_gold"));

    public static void bootstrap(Registerable<ArmorTrimMaterial> registerable){
        register(registerable, ROSE_GOLD, Registries.ITEM.getEntry(ModItems.ROSE_GOLD_INGOT), Style.EMPTY.withColor(TextColor.parse("#DF7377").getOrThrow()),0.85F,Map.of(ModArmorMaterials.ROSE_GOLD, "rose_gold_darker"));
    }
    private static void register(Registerable<ArmorTrimMaterial> registerable, RegistryKey<ArmorTrimMaterial> armorTrimKey, RegistryEntry<Item> item, Style style, float itemModelIndex, Map<RegistryEntry<ArmorMaterial>, String> map){
        ArmorTrimMaterial trimMaterial = new ArmorTrimMaterial(armorTrimKey.getValue().getPath(), item,itemModelIndex, map, Text.translatable(Util.createTranslationKey("trim_material", armorTrimKey.getValue())).fillStyle(style));

        registerable.register(armorTrimKey, trimMaterial);
    }
}
