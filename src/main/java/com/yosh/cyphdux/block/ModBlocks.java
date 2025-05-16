package com.yosh.cyphdux.block;

import com.yosh.cyphdux.CypherDuxMod;
import com.yosh.cyphdux.block.custom.*;
import com.yosh.cyphdux.item.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
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

    public static final Block ROSE_GOLD_BLOCK = register(new Block(AbstractBlock.Settings.create().mapColor(MapColor.PINK).instrument(NoteBlockInstrument.BELL).requiresTool().strength(4.5F, 6.0F).sounds(BlockSoundGroup.METAL)),"rose_gold_block",true);
    public static final Block CHARCOAL_BLOCK = register(new Block(AbstractBlock.Settings.create().mapColor(MapColor.BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(5.0F, 6.0F)),"charcoal_block",true);
    public static final Block COPPER_PLATED_COAL_BLOCK= register(new Block(AbstractBlock.Settings.create().mapColor(MapColor.BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(5.0F, 6.0F)),"copper_plated_coal_block",true);
    public static final Block ENRICHED_COPPER_PLATED_COAL_BLOCK = register(new Block(AbstractBlock.Settings.create().mapColor(MapColor.BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(5.0F, 6.0F).luminance(state -> 5)),"enriched_copper_plated_coal_block",true,16);

    public static final EnrichingFurnaceBlock ENRICHING_FURNACE = register(new EnrichingFurnaceBlock(AbstractBlock.Settings.create().luminance(EnrichingFurnaceBlock::getLuminance).mapColor(MapColor.DULL_RED).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(3.5F)),"enriching_furnace",true);

    public static final ItemDisplayBoardBlock ITEM_DISPLAY_BOARD = register(new ItemDisplayBoardBlock(AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN).strength(2.0F,3.0F).sounds(BlockSoundGroup.WOOD)),"item_display_board",true);

    public static final GlowingTorchBlock GLOWING_TORCH = register(new GlowingTorchBlock(AbstractBlock.Settings.copy(Blocks.TORCH).luminance((state) -> (Boolean)state.get(Properties.WATERLOGGED) ? 14 : 0)),"glowing_torch",false);
    public static final WallGlowingTorchBlock WALL_GLOWING_TORCH = register(new WallGlowingTorchBlock(AbstractBlock.Settings.copy(Blocks.WALL_TORCH).luminance((state) -> (Boolean)state.get(Properties.WATERLOGGED) ? 14 : 0)),"glowing_wall_torch",false);

    public static final StoolBlock OAK_STOOL = register(new StoolBlock(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)),"oak_stool",true);
    public static final StoolBlock SPRUCE_STOOL = register(new StoolBlock(AbstractBlock.Settings.copy(Blocks.SPRUCE_PLANKS)),"spruce_stool",true);
    public static final StoolBlock BIRCH_STOOL = register(new StoolBlock(AbstractBlock.Settings.copy(Blocks.BIRCH_PLANKS)),"birch_stool",true);
    public static final StoolBlock ACACIA_STOOL = register(new StoolBlock(AbstractBlock.Settings.copy(Blocks.ACACIA_PLANKS)),"acacia_stool",true);
    public static final StoolBlock JUNGLE_STOOL = register(new StoolBlock(AbstractBlock.Settings.copy(Blocks.JUNGLE_PLANKS)),"jungle_stool",true);
    public static final StoolBlock DARK_OAK_STOOL = register(new StoolBlock(AbstractBlock.Settings.copy(Blocks.DARK_OAK_PLANKS)),"dark_oak_stool",true);
    public static final StoolBlock MANGROVE_STOOL = register(new StoolBlock(AbstractBlock.Settings.copy(Blocks.MANGROVE_PLANKS)),"mangrove_stool",true);
    public static final StoolBlock CHERRY_STOOL = register(new StoolBlock(AbstractBlock.Settings.copy(Blocks.CHERRY_PLANKS)),"cherry_stool",true);
    public static final StoolBlock BAMBOO_STOOL = register(new StoolBlock(AbstractBlock.Settings.copy(Blocks.BAMBOO_PLANKS)),"bamboo_stool",true);
    public static final StoolBlock WARPED_STOOL = register(new StoolBlock(AbstractBlock.Settings.copy(Blocks.WARPED_PLANKS)),"warped_stool",true);
    public static final StoolBlock CRIMSON_STOOL = register(new StoolBlock(AbstractBlock.Settings.copy(Blocks.CRIMSON_PLANKS)),"crimson_stool",true);

    public static void initialize() {
        CypherDuxMod.LOGGER.info("Registering Blocks");
        FuelRegistry.INSTANCE.add(ModBlocks.CHARCOAL_BLOCK,12000);
        FuelRegistry.INSTANCE.add(ModBlocks.COPPER_PLATED_COAL_BLOCK,24000);
        FuelRegistry.INSTANCE.add(ModBlocks.ENRICHED_COPPER_PLATED_COAL_BLOCK,40000);
        FuelRegistry.INSTANCE.add(ModBlocks.ITEM_DISPLAY_BOARD,200);
        FuelRegistry.INSTANCE.add(ModBlocks.OAK_STOOL,300);
        FuelRegistry.INSTANCE.add(ModBlocks.SPRUCE_STOOL,300);
        FuelRegistry.INSTANCE.add(ModBlocks.BIRCH_STOOL,300);
        FuelRegistry.INSTANCE.add(ModBlocks.ACACIA_STOOL,300);
        FuelRegistry.INSTANCE.add(ModBlocks.JUNGLE_STOOL,300);
        FuelRegistry.INSTANCE.add(ModBlocks.DARK_OAK_STOOL,300);
        FuelRegistry.INSTANCE.add(ModBlocks.MANGROVE_STOOL,300);
        FuelRegistry.INSTANCE.add(ModBlocks.CHERRY_STOOL,300);
        FuelRegistry.INSTANCE.add(ModBlocks.BAMBOO_STOOL,300);

        ItemGroupEvents.modifyEntriesEvent(ModItems.CYPHER_DUX_ITEM_GROUP_KEY).register((itemGroup) -> {
            itemGroup.add(ModBlocks.CHARCOAL_BLOCK.asItem());
            itemGroup.add(ModBlocks.COPPER_PLATED_COAL_BLOCK.asItem());
            itemGroup.add(ModBlocks.ENRICHED_COPPER_PLATED_COAL_BLOCK.asItem());
            itemGroup.add(ModBlocks.ENRICHING_FURNACE.asItem());
            itemGroup.add(ModBlocks.ROSE_GOLD_BLOCK.asItem());
            itemGroup.add(ModBlocks.ITEM_DISPLAY_BOARD.asItem());
            itemGroup.add(ModBlocks.GLOWING_TORCH.asItem());
            itemGroup.add(ModBlocks.OAK_STOOL.asItem());
            itemGroup.add(ModBlocks.SPRUCE_STOOL.asItem());
            itemGroup.add(ModBlocks.BIRCH_STOOL.asItem());
            itemGroup.add(ModBlocks.ACACIA_STOOL.asItem());
            itemGroup.add(ModBlocks.JUNGLE_STOOL.asItem());
            itemGroup.add(ModBlocks.DARK_OAK_STOOL.asItem());
            itemGroup.add(ModBlocks.MANGROVE_STOOL.asItem());
            itemGroup.add(ModBlocks.CHERRY_STOOL.asItem());
            itemGroup.add(ModBlocks.BAMBOO_STOOL.asItem());
            itemGroup.add(ModBlocks.CRIMSON_STOOL.asItem());
            itemGroup.add(ModBlocks.WARPED_STOOL.asItem());
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register((itemGroup) -> {
            itemGroup.addAfter(Items.COAL_BLOCK,ModBlocks.CHARCOAL_BLOCK.asItem(),ModBlocks.COPPER_PLATED_COAL_BLOCK.asItem(),ModBlocks.ENRICHED_COPPER_PLATED_COAL_BLOCK.asItem());
            itemGroup.addAfter(Items.LIGHT_WEIGHTED_PRESSURE_PLATE,ModBlocks.ROSE_GOLD_BLOCK.asItem());
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register((itemGroup)->{
            itemGroup.addAfter(Items.BLAST_FURNACE,ModBlocks.ENRICHING_FURNACE.asItem());
            itemGroup.addBefore(Items.ITEM_FRAME,ModBlocks.ITEM_DISPLAY_BOARD.asItem());
            itemGroup.addAfter(Items.REDSTONE_TORCH,ModBlocks.GLOWING_TORCH.asItem());
            itemGroup.add(ModBlocks.OAK_STOOL.asItem());
            itemGroup.add(ModBlocks.SPRUCE_STOOL.asItem());
            itemGroup.add(ModBlocks.BIRCH_STOOL.asItem());
            itemGroup.add(ModBlocks.ACACIA_STOOL.asItem());
            itemGroup.add(ModBlocks.JUNGLE_STOOL.asItem());
            itemGroup.add(ModBlocks.DARK_OAK_STOOL.asItem());
            itemGroup.add(ModBlocks.MANGROVE_STOOL.asItem());
            itemGroup.add(ModBlocks.CHERRY_STOOL.asItem());
            itemGroup.add(ModBlocks.BAMBOO_STOOL.asItem());
            itemGroup.add(ModBlocks.CRIMSON_STOOL.asItem());
            itemGroup.add(ModBlocks.WARPED_STOOL.asItem());
        });
    }
}
