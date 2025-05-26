package com.yosh.cyphdux.screen;

import com.yosh.cyphdux.CypherDuxMod;
import com.yosh.cyphdux.sceenhandler.CardboardBoxScreenHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CardboardBoxScreen extends HandledScreen<CardboardBoxScreenHandler> {
    private static final Identifier GUI_TEXTURE = Identifier.of(CypherDuxMod.MOD_ID,"textures/gui/container/cardboard_box.png");

    public CardboardBoxScreen(CardboardBoxScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();

        this.titleX = (this.backgroundWidth-this.textRenderer.getWidth(this.title))/2;

    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        context.drawText(this.textRenderer, this.title, this.titleX, this.titleY, 4210752, false);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int i = this.x;
        int j = this.y;
        context.drawTexture(GUI_TEXTURE, i, j, 0,0,this.backgroundWidth,this.backgroundHeight);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
