package com.yosh.cyphdux.sceenhandler;

import com.yosh.cyphdux.CypherDuxMod;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class ScreenHandlerTypes {
    public static final ScreenHandlerType<EnrichingFurnaceScreenHandler> ENRICHING_FURNACE_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER, Identifier.of(CypherDuxMod.MOD_ID,"enriching_furnace"), new ExtendedScreenHandlerType<>(EnrichingFurnaceScreenHandler::new, BlockPos.PACKET_CODEC));

    public static final ScreenHandlerType<ItemDisplayBoardScreenHandler> ITEM_DISPLAY_BOARD_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER, Identifier.of(CypherDuxMod.MOD_ID, "item_display_board"), new ExtendedScreenHandlerType<>(ItemDisplayBoardScreenHandler::new, BlockPos.PACKET_CODEC));
    public static final ScreenHandlerType<CardboardBoxScreenHandler> CARDBOARD_BOX_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER,Identifier.of(CypherDuxMod.MOD_ID,"cardboard_box"), new ExtendedScreenHandlerType<>(CardboardBoxScreenHandler::new, BlockPos.PACKET_CODEC));

    public static void initialize(){
        CypherDuxMod.LOGGER.info("Registering ScreenHandlerTypes");
    }
}
