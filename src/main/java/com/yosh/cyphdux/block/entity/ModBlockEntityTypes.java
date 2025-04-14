package com.yosh.cyphdux.block.entity;

import com.yosh.cyphdux.CypherDuxMod;
import com.yosh.cyphdux.block.ModBlocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntityTypes {
    public static final BlockEntityType<EnrichingFurnaceBlockEntity> ENRICHING_FURNACE_BLOCK_ENTITY = register("enriching_furnace_block_entity",BlockEntityType.Builder.create(EnrichingFurnaceBlockEntity::new, ModBlocks.ENRICHING_FURNACE).build());

    public static final BlockEntityType<ItemDisplayBoardBlockEntity> ITEM_DISPLAY_BOARD_BLOCK_ENTITY = register("item_display_board_block_entity",BlockEntityType.Builder.create(ItemDisplayBoardBlockEntity::new, ModBlocks.ITEM_DISPLAY_BOARD).build());

    public static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> type){
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(CypherDuxMod.MOD_ID,name), type);
    }

    public static void initialize(){
        CypherDuxMod.LOGGER.info("Registering BlockEntityTypes");
    }
}
