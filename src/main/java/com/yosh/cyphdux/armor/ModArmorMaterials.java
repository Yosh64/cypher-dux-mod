package com.yosh.cyphdux.armor;

import com.yosh.cyphdux.CypherDuxMod;
import net.minecraft.item.*;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ModArmorMaterials {

    public static RegistryEntry<ArmorMaterial> registerArmorMaterial(String id, Supplier<ArmorMaterial> material){
        return Registry.registerReference(Registries.ARMOR_MATERIAL, CypherDuxMod.of(id),material.get());
    }

    public static final int ROSE_GOLD_DURABILITY_MULTIPLIER = 30;
    public static final RegistryEntry<ArmorMaterial> ROSE_GOLD = registerArmorMaterial("rose_gold",
            ()->new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map ->{
                map.put(ArmorItem.Type.BOOTS,3);
                map.put(ArmorItem.Type.LEGGINGS,5);
                map.put(ArmorItem.Type.CHESTPLATE,6);
                map.put(ArmorItem.Type.HELMET,3);
                map.put(ArmorItem.Type.BODY,10);
            }),17,SoundEvents.ITEM_ARMOR_EQUIP_GOLD,()->Ingredient.ofItems(Items.GOLD_INGOT),
                    List.of(new ArmorMaterial.Layer(CypherDuxMod.of("rose_gold"))),1.0F,0));

    public static final RegistryEntry<ArmorMaterial> DIVING = registerArmorMaterial("diving",
            ()->new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map ->{
                map.put(ArmorItem.Type.BOOTS,0);
                map.put(ArmorItem.Type.LEGGINGS,0);
                map.put(ArmorItem.Type.CHESTPLATE,0);
                map.put(ArmorItem.Type.HELMET,0);
                map.put(ArmorItem.Type.BODY,0);
            }),9,SoundEvents.ITEM_ARMOR_EQUIP_TURTLE, Ingredient::empty,
                    List.of(new ArmorMaterial.Layer(CypherDuxMod.of("diving"))),0,0));

    public static final RegistryEntry<ArmorMaterial> DIVING_2 = registerArmorMaterial("diving_2",
            ()->new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map ->{
                map.put(ArmorItem.Type.BOOTS,0);
                map.put(ArmorItem.Type.LEGGINGS,0);
                map.put(ArmorItem.Type.CHESTPLATE,0);
                map.put(ArmorItem.Type.HELMET,0);
                map.put(ArmorItem.Type.BODY,0);
            }),9,SoundEvents.ITEM_ARMOR_EQUIP_TURTLE, Ingredient::empty,
                    List.of(new ArmorMaterial.Layer(CypherDuxMod.of("diving_2"))),0,0));

    public static final RegistryEntry<ArmorMaterial> DIVING_3 = registerArmorMaterial("diving_3",
            ()->new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map ->{
                map.put(ArmorItem.Type.BOOTS,0);
                map.put(ArmorItem.Type.LEGGINGS,0);
                map.put(ArmorItem.Type.CHESTPLATE,0);
                map.put(ArmorItem.Type.HELMET,0);
                map.put(ArmorItem.Type.BODY,0);
            }),9,SoundEvents.ITEM_ARMOR_EQUIP_TURTLE, Ingredient::empty,
                    List.of(new ArmorMaterial.Layer(CypherDuxMod.of("diving_3"))),0,0));

    public static final int WARDEN_DURABILITY_MULTIPLIER = 40;
    public static final RegistryEntry<ArmorMaterial> WARDEN = registerArmorMaterial("warden",
            ()->new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map ->{
                map.put(ArmorItem.Type.BOOTS,5);
                map.put(ArmorItem.Type.LEGGINGS,8);
                map.put(ArmorItem.Type.CHESTPLATE,10);
                map.put(ArmorItem.Type.HELMET,5);
                map.put(ArmorItem.Type.BODY,12);
            }),18,SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE,()->Ingredient.ofItems(Items.ECHO_SHARD),
                    List.of(new ArmorMaterial.Layer(CypherDuxMod.of("warden"))),4.0F,0.2F));
}
