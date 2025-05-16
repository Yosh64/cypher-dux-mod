package com.yosh.cyphdux;

import com.yosh.cyphdux.block.entity.ModBlockEntityTypes;
import com.yosh.cyphdux.block.entity.renderer.ItemDisplayBoardBlockEntityRenderer;
import com.yosh.cyphdux.entity.ModEntities;
import com.yosh.cyphdux.entity.client.SitRenderer;
import com.yosh.cyphdux.entity.custom.SitEntity;
import com.yosh.cyphdux.sceenhandler.ScreenHandlerTypes;
import com.yosh.cyphdux.screen.EnrichingFurnaceScreen;
import com.yosh.cyphdux.screen.ItemDisplayBoardScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class CypherDuxModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(ModBlockEntityTypes.ITEM_DISPLAY_BOARD_BLOCK_ENTITY, ItemDisplayBoardBlockEntityRenderer::new);

        // Bind Screen <-> ScreenHandler
        HandledScreens.register(ScreenHandlerTypes.ENRICHING_FURNACE_SCREEN_HANDLER, EnrichingFurnaceScreen::new);
        HandledScreens.register(ScreenHandlerTypes.ITEM_DISPLAY_BOARD_SCREEN_HANDLER, ItemDisplayBoardScreen::new);

        EntityRendererRegistry.register(ModEntities.SIT_ENTITY, SitRenderer::new);
    }
}
