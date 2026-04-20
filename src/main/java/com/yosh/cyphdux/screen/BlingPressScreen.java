package com.yosh.cyphdux.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.yosh.cyphdux.CypherDuxMod;
import com.yosh.cyphdux.sceenhandler.BlingPressScreenHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class BlingPressScreen extends HandledScreen<BlingPressScreenHandler> {
    private static final Identifier GUI_TEXTURE = Identifier.of(CypherDuxMod.MOD_ID,"textures/gui/container/enriching_furnace.png");
    private static final Identifier PROGRESS_TEXTURE = Identifier.of(CypherDuxMod.MOD_ID,"textures/gui/sprites/container/enriching_furnace/burn_progress.png");
    private static final Identifier BURN_TEXTURE = Identifier.of(CypherDuxMod.MOD_ID,"textures/gui/sprites/container/enriching_furnace/lit_progress.png");

    public BlingPressScreen(BlingPressScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        RenderSystem.setShaderTexture(0,GUI_TEXTURE);

        int x = (width-backgroundWidth)/2;
        int y = (height-backgroundHeight)/2;

        int i = this.x;
        int j = this.y;

        context.drawTexture(GUI_TEXTURE,x,y,0,0,backgroundWidth,backgroundHeight);
        if (this.handler.isFuelTicking()) {
            int l = MathHelper.ceil(this.handler.getScaledFireProgress() * 13.0F) + 1;
            context.drawTexture(BURN_TEXTURE, i+56, j+36+14-l, 0,14-l,14,l, 14, 14);
        }
        if (this.handler.isCrafting()) {
            int l = MathHelper.ceil(this.handler.getScaledArrowProgress() * 24.0F);
            context.drawTexture(PROGRESS_TEXTURE, i + 79, j + 34, 0, 0, l, 16, 24, 16);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context,mouseX,mouseY);
    }
}
