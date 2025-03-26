package com.yosh.cyphdux.block;

import com.yosh.cyphdux.CypherDuxMod;
import com.yosh.cyphdux.block.custom.EnrichingFurnaceBlock;
import com.yosh.cyphdux.item.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static <T extends Block> T register(T block, String name, boolean shouldRegisterItem){
        return register(block,name,shouldRegisterItem,64);
    }
    public static <T extends Block> T register(T block, String name, boolean shouldRegisterItem,int maxStack) {
        // Register the block and its item.
        Identifier id = Identifier.of(CypherDuxMod.MOD_ID, name);

        // Sometimes, you may not want to register an item for the block.
        // Eg: if it's a technical block like `minecraft:air` or `minecraft:end_gateway`
        if (shouldRegisterItem) {
            BlockItem blockItem = new BlockItem(block, new Item.Settings().maxCount(maxStack));
            Registry.register(Registries.ITEM, id, blockItem);
        }

        return Registry.register(Registries.BLOCK, id, block);
    }

    public static final Block ROSE_GOLD_BLOCK = register(new Block(AbstractBlock.Settings.create().mapColor(MapColor.GOLD).instrument(NoteBlockInstrument.BELL).requiresTool().strength(3.0F, 6.0F).sounds(BlockSoundGroup.METAL)),"rose_gold_block",true);
    public static final Block CHARCOAL_BLOCK = register(new Block(AbstractBlock.Settings.create().mapColor(MapColor.BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(5.0F, 6.0F)),"charcoal_block",true);
    public static final Block COPPER_PLATED_COAL_BLOCK= register(new Block(AbstractBlock.Settings.create().mapColor(MapColor.BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(5.0F, 6.0F)),"copper_plated_coal_block",true);
    public static final Block ENRICHED_COPPER_PLATED_COAL_BLOCK = register(new Block(AbstractBlock.Settings.create().mapColor(MapColor.BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(5.0F, 6.0F).luminance(state -> 5)),"enriched_copper_plated_coal_block",true,16);

    public static final EnrichingFurnaceBlock ENRICHING_FURNACE = register(new EnrichingFurnaceBlock(AbstractBlock.Settings.create().luminance(EnrichingFurnaceBlock::getLuminance).mapColor(MapColor.DULL_RED).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(3.5F)),"enriching_furnace",true);
    public static void initialize() {
        CypherDuxMod.LOGGER.info("Registering Blocks");
        FuelRegistry.INSTANCE.add(ModBlocks.CHARCOAL_BLOCK,12000);
        FuelRegistry.INSTANCE.add(ModBlocks.COPPER_PLATED_COAL_BLOCK,24000);
        FuelRegistry.INSTANCE.add(ModBlocks.ENRICHED_COPPER_PLATED_COAL_BLOCK,40000);

        ItemGroupEvents.modifyEntriesEvent(ModItems.CYPHER_DUX_ITEM_GROUP_KEY).register((itemGroup) -> {
            itemGroup.add(ModBlocks.CHARCOAL_BLOCK.asItem());
            itemGroup.add(ModBlocks.COPPER_PLATED_COAL_BLOCK.asItem());
            itemGroup.add(ModBlocks.ENRICHED_COPPER_PLATED_COAL_BLOCK.asItem());
            itemGroup.add(ModBlocks.ENRICHING_FURNACE.asItem());
            itemGroup.add(ModBlocks.ROSE_GOLD_BLOCK.asItem());
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register((itemGroup) -> {
            itemGroup.add(ModBlocks.CHARCOAL_BLOCK.asItem());
            itemGroup.add(ModBlocks.COPPER_PLATED_COAL_BLOCK.asItem());
            itemGroup.add(ModBlocks.ENRICHED_COPPER_PLATED_COAL_BLOCK.asItem());
            itemGroup.add(ModBlocks.ROSE_GOLD_BLOCK.asItem());
        });
    }
}
