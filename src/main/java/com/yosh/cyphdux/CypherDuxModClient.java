package com.yosh.cyphdux;

import com.yosh.cyphdux.block.entity.ModBlockEntityTypes;
import com.yosh.cyphdux.block.entity.renderer.ItemDisplayBoardBlockEntityRenderer;
import com.yosh.cyphdux.sceenhandler.ScreenHandlerTypes;
import com.yosh.cyphdux.screen.EnrichingFurnaceScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class CypherDuxModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Bind Screen <-> ScreenHandler
        HandledScreens.register(ScreenHandlerTypes.ENRICHING_FURNACE_SCREEN_HANDLER, EnrichingFurnaceScreen::new);
        BlockEntityRendererFactories.register(ModBlockEntityTypes.ITEM_DISPLAY_BOARD_BLOCK_ENTITY, ItemDisplayBoardBlockEntityRenderer::new);
    }
}
