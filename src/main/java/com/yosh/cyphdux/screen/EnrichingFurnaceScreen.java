package com.yosh.cyphdux.screen;

import com.yosh.cyphdux.CypherDuxMod;
import com.yosh.cyphdux.sceenhandler.EnrichingFurnaceScreenHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class EnrichingFurnaceScreen extends HandledScreen<EnrichingFurnaceScreenHandler> {
    private static final Identifier GUI_TEXTURE = Identifier.of(CypherDuxMod.MOD_ID,"textures/gui/container/enriching_furnace.png");
    private static final Identifier PROGRESS_TEXTURE = Identifier.of(CypherDuxMod.MOD_ID,"textures/gui/sprites/container/enriching_furnace/burn_progress.png");
    private static final Identifier BURN_TEXTURE = Identifier.of(CypherDuxMod.MOD_ID,"textures/gui/sprites/container/enriching_furnace/lit_progress.png");

    public EnrichingFurnaceScreen(EnrichingFurnaceScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();

        this.titleX = (this.backgroundWidth-this.textRenderer.getWidth(this.title))/2;

    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int i = this.x;
        int j = this.y;
        context.drawTexture(GUI_TEXTURE, i, j, 0,0,this.backgroundWidth,this.backgroundHeight);
        if (this.handler.isFuelTicking()) {
            int l = MathHelper.ceil(this.handler.getFuelProgress() * 13.0F) + 1;
            context.drawTexture(BURN_TEXTURE, i+56, j+36+14-l, 0,14-l,14,l, 14, 14);
        }
        if (this.handler.isCrafting()) {
            int l = MathHelper.ceil(this.handler.getCookProgress() * 24.0F);
            context.drawTexture(PROGRESS_TEXTURE, i + 79, j + 34, 0, 0, l, 16, 24, 16);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
