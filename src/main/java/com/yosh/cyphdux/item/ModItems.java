package com.yosh.cyphdux.item;

import com.yosh.cyphdux.CypherDuxMod;
import com.yosh.cyphdux.armor.DivingArmorItem;
import com.yosh.cyphdux.armor.ModArmorMaterials;
import com.yosh.cyphdux.block.ModBlocks;
import com.yosh.cyphdux.sound.ModSounds;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.resource.featuretoggle.FeatureFlag;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.Direction;

import java.util.List;

public class ModItems {

    public static void initialize() {
        CypherDuxMod.LOGGER.info("Registering Items");
        FuelRegistry.INSTANCE.add(Items.CHARCOAL,1200);
        FuelRegistry.INSTANCE.add(ModItems.COPPER_PLATED_COAL,2400);
        FuelRegistry.INSTANCE.add(ModItems.ENRICHED_COPPER_PLATED_COAL,4000);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(itemGroup -> {
            itemGroup.addAfter(Items.GOLD_INGOT,ROSE_GOLD_INGOT);
            itemGroup.addAfter(Items.CHARCOAL,COPPER_PLATED_COAL,ENRICHED_COPPER_PLATED_COAL);
            itemGroup.addAfter(Items.AMETHYST_SHARD,SYNTHETIC_AMETHYST,KAYBER_KRYSTAL);
            itemGroup.addAfter(Items.EMERALD,SYNTHETIC_EMERALD);
            itemGroup.addAfter(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,ModItems.ROSE_GOLD_UPGRADE);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(itemGroup -> {
            itemGroup.addAfter(Items.GOLDEN_BOOTS,ROSE_GOLD_HELMET,ROSE_GOLD_CHESTPLATE,ROSE_GOLD_LEGGINGS,ROSE_GOLD_BOOTS);
            itemGroup.addAfter(Items.GOLDEN_SWORD,ROSE_GOLD_SWORD);
            itemGroup.addAfter(Items.GOLDEN_AXE,ROSE_GOLD_AXE);
            itemGroup.addAfter(Items.TURTLE_HELMET,DIVING_HELMET_MK1,DIVING_HELMET_MK2,DIVING_HELMET_MK3);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(itemGroup -> {
            itemGroup.addAfter(Items.GOLDEN_HOE,ROSE_GOLD_SHOVEL,ROSE_GOLD_PICKAXE,ROSE_GOLD_AXE,ROSE_GOLD_HOE);
            itemGroup.addAfter(Items.MUSIC_DISC_PIGSTEP,ZINZIN_DISC,WILSON_DISC,BOUTICA_DISC,KINGCHAM_DISC);
        });

        ItemGroupEvents.modifyEntriesEvent(CYPHER_DUX_ITEM_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(ROSE_GOLD_UPGRADE);
            itemGroup.add(ROSE_GOLD_INGOT);
            itemGroup.add(ROSE_GOLD_HELMET);
            itemGroup.add(ROSE_GOLD_CHESTPLATE);
            itemGroup.add(ROSE_GOLD_LEGGINGS);
            itemGroup.add(ROSE_GOLD_BOOTS);
            itemGroup.add(ROSE_GOLD_SWORD);
            itemGroup.add(ROSE_GOLD_AXE);
            itemGroup.add(ROSE_GOLD_SHOVEL);
            itemGroup.add(ROSE_GOLD_PICKAXE);
            itemGroup.add(ROSE_GOLD_AXE);
            itemGroup.add(ROSE_GOLD_HOE);
            itemGroup.add(COPPER_PLATED_COAL);
            itemGroup.add(ENRICHED_COPPER_PLATED_COAL);
            itemGroup.add(SYNTHETIC_AMETHYST);
            itemGroup.add(SYNTHETIC_EMERALD);
            itemGroup.add(KAYBER_KRYSTAL);
            itemGroup.add(DIVING_HELMET_MK1);
            itemGroup.add(DIVING_HELMET_MK2);
            itemGroup.add(DIVING_HELMET_MK3);
            itemGroup.add(ZINZIN_DISC);
            itemGroup.add(WILSON_DISC);
            itemGroup.add(BOUTICA_DISC);
            itemGroup.add(KINGCHAM_DISC);
        });
    }

    public static final RegistryKey<ItemGroup> CYPHER_DUX_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(CypherDuxMod.MOD_ID, "item_group"));
    public static final ItemGroup CYPHER_DUX_ITEM_GROUP = Registry.register(Registries.ITEM_GROUP,Identifier.of(CypherDuxMod.MOD_ID,"item_group"),FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.ROSE_GOLD_INGOT))
            .displayName(Text.translatable("itemGroup.cypher-dux-mod"))
            .build());
    public static final List<Identifier> EMPTY_BASE_SLOT = ((SmithingTemplateItem)Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE.asItem()).getEmptyBaseSlotTextures();
    public static final List<Identifier> EMPTY_ADDITION_SLOT = ((SmithingTemplateItem)Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE.asItem()).getEmptyAdditionsSlotTextures();

    public static Item register(Item item, String id) {
        // Create the identifier for the item.
        Identifier itemID = Identifier.of(CypherDuxMod.MOD_ID, id);
        // Return the registered item!
        return Registry.register(Registries.ITEM, itemID, item);
    }
    public static Item register(BlockItem item){
        return Registry.register(Registries.ITEM,Registries.BLOCK.getId(item.getBlock()),item);
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
public static final Item ROSE_GOLD_UPGRADE = register(new SmithingTemplateItem(
        Text.translatable("smithing_template."+ CypherDuxMod.MOD_ID+".rose_gold_upgrade.applies_to").formatted(Formatting.BLUE),
        Text.translatable("smithing_template."+ CypherDuxMod.MOD_ID+".rose_gold_upgrade.ingredients").formatted(Formatting.BLUE),
        Text.translatable("upgrade."+ CypherDuxMod.MOD_ID+".rose_gold_upgrade").formatted(Formatting.GRAY),
        Text.translatable("smithing_template."+ CypherDuxMod.MOD_ID+".rose_gold_upgrade.base_slot_description"),
        Text.translatable("smithing_template."+ CypherDuxMod.MOD_ID+".rose_gold_upgrade.additions_slot_description"),
        EMPTY_BASE_SLOT,EMPTY_ADDITION_SLOT,new FeatureFlag[0]),
        "rose_gold_upgrade_smithing_template");

    public static final Item COPPER_PLATED_COAL = register(new Item(new Item.Settings()),"copper_plated_coal");
    public static final Item ENRICHED_COPPER_PLATED_COAL = register(new Item(new Item.Settings()),"enriched_copper_plated_coal");
    public static final Item SYNTHETIC_AMETHYST = register(new Item(new Item.Settings()),"synthetic_amethyst");
    public static final Item SYNTHETIC_EMERALD = register(new Item(new Item.Settings()),"synthetic_emerald");
    public static final Item KAYBER_KRYSTAL = register(new Item(new Item.Settings()),"kayber_krystal");

    public static final Item DIVING_HELMET_MK1 = register(new DivingArmorItem(ModArmorMaterials.DIVING,ArmorItem.Type.HELMET, new Item.Settings().maxDamage(30)),"diving_helmet_mk1");

    public static final Item DIVING_HELMET_MK2 = register(new DivingArmorItem(ModArmorMaterials.DIVING_2,ArmorItem.Type.HELMET, new Item.Settings().maxDamage(72)
            .attributeModifiers(AttributeModifiersComponent.builder()
                    .add(EntityAttributes.GENERIC_MOVEMENT_SPEED,new EntityAttributeModifier(Identifier.ofVanilla("armor." + ArmorItem.Type.HELMET.getName()),(double)-0.05,EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),AttributeModifierSlot.forEquipmentSlot(ArmorItem.Type.HELMET.getEquipmentSlot()))
                    .build())),"diving_helmet_mk2");

    public static final Item DIVING_HELMET_MK3 = register(new DivingArmorItem(ModArmorMaterials.DIVING_3,ArmorItem.Type.HELMET, new Item.Settings().maxDamage(180)
            .attributeModifiers(AttributeModifiersComponent.builder()
                    .add(EntityAttributes.GENERIC_MOVEMENT_SPEED,new EntityAttributeModifier(Identifier.ofVanilla("armor." + ArmorItem.Type.HELMET.getName()),(double)-0.1,EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),AttributeModifierSlot.forEquipmentSlot(ArmorItem.Type.HELMET.getEquipmentSlot()))
                    .build())),"diving_helmet_mk3");

    public static final Item GLOWING_TORCH = register((BlockItem)(new VerticallyAttachableBlockItem(ModBlocks.GLOWING_TORCH, ModBlocks.WALL_GLOWING_TORCH, new Item.Settings(), Direction.DOWN)));

    public static final Item CARDBOARD_BOX = register(new CardboardBoxBlockItem(ModBlocks.CARDBOARD_BOX, new Item.Settings().maxCount(1).fireproof()));

    public static final Item ZINZIN_DISC = register(new Item(new Item.Settings().maxCount(1).rarity(Rarity.RARE).jukeboxPlayable(ModSounds.ZINZIN_KEY)),"music_disc_zinzin");
    public static final Item BOUTICA_DISC = register(new Item(new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON).jukeboxPlayable(ModSounds.BOUTICA_KEY)),"music_disc_boutica");
    public static final Item WILSON_DISC = register(new Item(new Item.Settings().maxCount(1).rarity(Rarity.COMMON).jukeboxPlayable(ModSounds.WILSON_KEY)),"music_disc_wilson");
    public static final Item KINGCHAM_DISC = register(new Item(new Item.Settings().maxCount(1).rarity(Rarity.EPIC).jukeboxPlayable(ModSounds.KINGCHAM_KEY)),"music_disc_kingcham");
            //register("music_disc_strad", new Item((new Item.Settings()).maxCount(1).rarity(Rarity.RARE).jukeboxPlayable(JukeboxSongs.STRAD)));

}
