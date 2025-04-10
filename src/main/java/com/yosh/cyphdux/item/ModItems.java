package com.yosh.cyphdux.item;

import com.yosh.cyphdux.CypherDuxMod;
import com.yosh.cyphdux.armor.ModArmorMaterials;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItems {

    public static void initialize() {
        CypherDuxMod.LOGGER.info("Registering Items");
        FuelRegistry.INSTANCE.add(Items.CHARCOAL,1200);
        FuelRegistry.INSTANCE.add(ModItems.COPPER_PLATED_COAL,2400);
        FuelRegistry.INSTANCE.add(ModItems.ENRICHED_COPPER_PLATED_COAL,4000);
        addToItemGroup(ROSE_GOLD_INGOT, ItemGroups.INGREDIENTS);
        addToItemGroup(ROSE_GOLD_HELMET, ItemGroups.COMBAT);
        addToItemGroup(ROSE_GOLD_CHESTPLATE, ItemGroups.COMBAT);
        addToItemGroup(ROSE_GOLD_LEGGINGS, ItemGroups.COMBAT);
        addToItemGroup(ROSE_GOLD_BOOTS, ItemGroups.COMBAT);
        addToItemGroup(ROSE_GOLD_SWORD, ItemGroups.COMBAT);
        addToItemGroup(ROSE_GOLD_AXE, ItemGroups.COMBAT);
        addToItemGroup(ROSE_GOLD_SHOVEL, ItemGroups.TOOLS);
        addToItemGroup(ROSE_GOLD_PICKAXE, ItemGroups.TOOLS);
        addToItemGroup(ROSE_GOLD_AXE, ItemGroups.TOOLS);
        addToItemGroup(ROSE_GOLD_HOE, ItemGroups.TOOLS);
        addToItemGroup(COPPER_PLATED_COAL, ItemGroups.INGREDIENTS);
        addToItemGroup(ENRICHED_COPPER_PLATED_COAL, ItemGroups.INGREDIENTS);
        addToItemGroup(SYNTHETIC_AMETHYST, ItemGroups.INGREDIENTS);
        addToItemGroup(SYNTHETIC_EMERALD, ItemGroups.INGREDIENTS);
        addToItemGroup(KAYBER_KRYSTAL, ItemGroups.INGREDIENTS);

        addToItemGroup(ROSE_GOLD_INGOT);
        addToItemGroup(ROSE_GOLD_HELMET);
        addToItemGroup(ROSE_GOLD_CHESTPLATE);
        addToItemGroup(ROSE_GOLD_LEGGINGS);
        addToItemGroup(ROSE_GOLD_BOOTS);
        addToItemGroup(ROSE_GOLD_SWORD);
        addToItemGroup(ROSE_GOLD_AXE);
        addToItemGroup(ROSE_GOLD_SHOVEL);
        addToItemGroup(ROSE_GOLD_PICKAXE);
        addToItemGroup(ROSE_GOLD_AXE);
        addToItemGroup(ROSE_GOLD_HOE);
        addToItemGroup(COPPER_PLATED_COAL);
        addToItemGroup(ENRICHED_COPPER_PLATED_COAL);
        addToItemGroup(SYNTHETIC_AMETHYST);
        addToItemGroup(SYNTHETIC_EMERALD);
        addToItemGroup(KAYBER_KRYSTAL);
    }

    public static final RegistryKey<ItemGroup> CYPHER_DUX_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(CypherDuxMod.MOD_ID, "item_group"));
    public static final ItemGroup CYPHER_DUX_ITEM_GROUP = Registry.register(Registries.ITEM_GROUP,Identifier.of(CypherDuxMod.MOD_ID,"item_group"),FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.ROSE_GOLD_INGOT))
            .displayName(Text.translatable("itemGroup.cypher-dux-mod"))
            .build());

    public static void addToItemGroup(Item item){
        ItemGroupEvents.modifyEntriesEvent(CYPHER_DUX_ITEM_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(item);
        });
    }
    public static void addToItemGroup(Item item, RegistryKey<ItemGroup> itemGroupRegistryKey) {
        // register the item in the item group!
        ItemGroupEvents.modifyEntriesEvent(itemGroupRegistryKey).register(itemGroup -> {
            itemGroup.add(item);
        });
    }

    public static Item register(Item item, String id) {
        // Create the identifier for the item.
        Identifier itemID = Identifier.of(CypherDuxMod.MOD_ID, id);
        // Return the registered item!
        return Registry.register(Registries.ITEM, itemID, item);
    }

    public static final Item ROSE_GOLD_INGOT = register(new Item(new Item.Settings()),"rose_gold_ingot");

    public static final Item ROSE_GOLD_HELMET = register(new ArmorItem(ModArmorMaterials.ROSE_GOLD, ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(ModArmorMaterials.ROSE_GOLD_DURABILITY_MULTIPLIER))), "rose_gold_helmet");
    public static final Item ROSE_GOLD_CHESTPLATE = register(new ArmorItem(ModArmorMaterials.ROSE_GOLD, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(ModArmorMaterials.ROSE_GOLD_DURABILITY_MULTIPLIER))), "rose_gold_chestplate");
    public static final Item ROSE_GOLD_LEGGINGS = register(new ArmorItem(ModArmorMaterials.ROSE_GOLD, ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(ModArmorMaterials.ROSE_GOLD_DURABILITY_MULTIPLIER))), "rose_gold_leggings");
    public static final Item ROSE_GOLD_BOOTS = register(new ArmorItem(ModArmorMaterials.ROSE_GOLD, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(ModArmorMaterials.ROSE_GOLD_DURABILITY_MULTIPLIER))), "rose_gold_boots");

    public static final Item ROSE_GOLD_SWORD = register(new SwordItem(ModToolMaterials.ROSE_GOLD, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolMaterials.ROSE_GOLD,3,-2.4F))),"rose_gold_sword");
    public static final Item ROSE_GOLD_AXE = register(new AxeItem(ModToolMaterials.ROSE_GOLD, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolMaterials.ROSE_GOLD,5.5F,-3.1F))),"rose_gold_axe");
    public static final Item ROSE_GOLD_PICKAXE = register(new PickaxeItem(ModToolMaterials.ROSE_GOLD, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolMaterials.ROSE_GOLD,1F,-2.8F))),"rose_gold_pickaxe");
    public static final Item ROSE_GOLD_SHOVEL = register(new ShovelItem(ModToolMaterials.ROSE_GOLD, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolMaterials.ROSE_GOLD,1.5F,-3.0F))),"rose_gold_shovel");
    public static final Item ROSE_GOLD_HOE = register(new HoeItem(ModToolMaterials.ROSE_GOLD, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolMaterials.ROSE_GOLD,-2F,-1.0F))),"rose_gold_hoe");

    public static final Item COPPER_PLATED_COAL = register(new Item(new Item.Settings()),"copper_plated_coal");
    public static final Item ENRICHED_COPPER_PLATED_COAL = register(new Item(new Item.Settings()),"enriched_copper_plated_coal");
    public static final Item SYNTHETIC_AMETHYST = register(new Item(new Item.Settings()),"synthetic_amethyst");
    public static final Item SYNTHETIC_EMERALD = register(new Item(new Item.Settings()),"synthetic_emerald");
    public static final Item KAYBER_KRYSTAL = register(new Item(new Item.Settings()),"kayber_krystal");
}
