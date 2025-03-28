package com.yosh.cyphdux.screen;


import com.yosh.cyphdux.CypherDuxMod;
import com.yosh.cyphdux.sceenhandler.ItemDisplayBoardScreenHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ItemDisplayBoardScreen extends HandledScreen<ItemDisplayBoardScreenHandler> {
    private static final Identifier GUI_TEXTURE = Identifier.of(CypherDuxMod.MOD_ID,"textures/gui/container/item_display_board.png");
    public ItemDisplayBoardScreen(ItemDisplayBoardScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundHeight = 133;
        this.playerInventoryTitleY = this.backgroundHeight - 94;
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
