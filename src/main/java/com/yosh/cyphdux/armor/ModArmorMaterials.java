package com.yosh.cyphdux.armor;

import com.yosh.cyphdux.CypherDuxMod;
import com.yosh.cyphdux.item.ModItems;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.item.*;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ModArmorMaterials {
    public static void initialize() {
        CypherDuxMod.LOGGER.info("Registering Armor Materials");
        potion.set(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(Potions.WATER_BREATHING));
    }
    private static final ItemStack potion = new ItemStack(Items.POTION);
    public static RegistryEntry<ArmorMaterial> registerMaterial(String id, Map<ArmorItem.Type, Integer> defensePoints, int enchantability, RegistryEntry<SoundEvent> equipSound, Supplier<Ingredient> repairIngredientSupplier, float toughness, float knockbackResistance, boolean dyeable) {
        // Get the supported layers for the armor material
        List<ArmorMaterial.Layer> layers = List.of(
                // The ID of the texture layer, the suffix, and whether the layer is dyeable.
                // We can just pass the armor material ID as the texture layer ID.
                // We have no need for a suffix, so we'll pass an empty string.
                // We'll pass the dyeable boolean we received as the dyeable parameter.
                new ArmorMaterial.Layer(Identifier.of(CypherDuxMod.MOD_ID, id), "", dyeable)
        );

        ArmorMaterial material = new ArmorMaterial(defensePoints, enchantability, equipSound, repairIngredientSupplier, layers, toughness, knockbackResistance);
        // Register the material within the ArmorMaterials registry.
        material = Registry.register(Registries.ARMOR_MATERIAL, Identifier.of(CypherDuxMod.MOD_ID, id), material);
        // The majority of the time, you'll want the RegistryEntry of the material - especially for the ArmorItem constructor.
        return RegistryEntry.of(material);
    }

    public static final int ROSE_GOLD_DURABILITY_MULTIPLIER = 22;
    public static final RegistryEntry<ArmorMaterial> ROSE_GOLD = registerMaterial("rose_gold",
            // Defense (protection) point values for each armor piece.
            Map.of(
                    ArmorItem.Type.HELMET, 2,
                    ArmorItem.Type.CHESTPLATE, 6,
                    ArmorItem.Type.LEGGINGS, 5,
                    ArmorItem.Type.BOOTS, 2
            ),
            // Enchant-ability. For reference, leather has 15, iron has 9, and diamond has 10.
            17,
            // The sound played when the armor is equipped.
            SoundEvents.ITEM_ARMOR_EQUIP_GOLD,
            // The ingredient(s) used to repair the armor.
            () -> Ingredient.ofItems(ModItems.ROSE_GOLD_INGOT),
            1.0F,
            0.0F,
            // NOT dye-able, so we will pass false.
            false);
    public static final RegistryEntry<ArmorMaterial> DIVING = registerMaterial("diving",
            // Defense (protection) point values for each armor piece.
            Map.of(
                    ArmorItem.Type.HELMET, 0,
                    ArmorItem.Type.CHESTPLATE, 4,
                    ArmorItem.Type.LEGGINGS, 3,
                    ArmorItem.Type.BOOTS, 0
            ),
            9, SoundEvents.ITEM_ARMOR_EQUIP_TURTLE, Ingredient::empty,
            0.0F,
            0.0F,false);
    public static final RegistryEntry<ArmorMaterial> DIVING_2 = registerMaterial("diving_2",
            // Defense (protection) point values for each armor piece.
            Map.of(
                    ArmorItem.Type.HELMET, 0,
                    ArmorItem.Type.CHESTPLATE, 4,
                    ArmorItem.Type.LEGGINGS, 3,
                    ArmorItem.Type.BOOTS, 0
            ),
            9, SoundEvents.ITEM_ARMOR_EQUIP_TURTLE, Ingredient::empty,
            0.0F,
            0.0F,false);

    public static final RegistryEntry<ArmorMaterial> DIVING_3 = registerMaterial("diving_3",
            // Defense (protection) point values for each armor piece.
            Map.of(
                    ArmorItem.Type.HELMET, 0,
                    ArmorItem.Type.CHESTPLATE, 4,
                    ArmorItem.Type.LEGGINGS, 3,
                    ArmorItem.Type.BOOTS, 0
            ),
            9, SoundEvents.ITEM_ARMOR_EQUIP_TURTLE, Ingredient::empty,
            0.0F,
            0.0F,false);
}
