package com.yosh.cyphdux;

import com.yosh.cyphdux.sceenhandler.ScreenHandlerTypes;
import com.yosh.cyphdux.screen.EnrichingFurnaceScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class CypherDuxModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Bind Screen <-> ScreenHandler
        HandledScreens.register(ScreenHandlerTypes.ENRICHING_FURNACE_SCREEN_HANDLER, EnrichingFurnaceScreen::new);
    }
}
