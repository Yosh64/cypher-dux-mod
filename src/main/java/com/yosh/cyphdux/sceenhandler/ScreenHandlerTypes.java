package com.yosh.cyphdux.sceenhandler;

import com.yosh.cyphdux.CypherDuxMod;
import com.yosh.cyphdux.network.BlockPosPayload;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ScreenHandlerTypes {
    public static final ScreenHandlerType<EnrichingFurnaceScreenHandler> ENRICHING_FURNACE_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER, Identifier.of(CypherDuxMod.MOD_ID,"enriching_furnace"), new ExtendedScreenHandlerType<>(EnrichingFurnaceScreenHandler::new,BlockPosPayload.PACKET_CODEC));

    public static void initialize(){
        CypherDuxMod.LOGGER.info("Registering ScreenHandlerTypes");
    }
}
