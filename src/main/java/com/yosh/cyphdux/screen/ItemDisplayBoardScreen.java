package com.yosh.cyphdux.screen;


import com.yosh.cyphdux.CypherDuxMod;
import com.yosh.cyphdux.sceenhandler.ItemDisplayBoardScreenHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ItemDisplayBoardScreen extends HandledScreen<ItemDisplayBoardScreenHandler> {
    private static final Identifier GUI_TEXTURE = Identifier.of(CypherDuxMod.MOD_ID,"textures/gui/container/item_display_board.png");
    private static final Identifier SCROLL_TEXTURE = Identifier.ofVanilla("container/stonecutter/scroller");
    private static final int ITEM_LIST_COLUMNS = 9;
    private static final int ITEM_LIST_ROWS = 5;
    private static final int SCROLLBAR_AREA_HEIGHT = 106;
    private float scrollAmount;
    private boolean mouseClicked;
    private int scrollOffset;
    private TextFieldWidget searchWidget;
    private boolean ignoreTypedCharacter;

    public ItemDisplayBoardScreen(ItemDisplayBoardScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundHeight = 147;
        this.backgroundWidth = 195;
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
        int k = (int)(91.0F * this.scrollAmount);
        context.drawGuiTexture(SCROLL_TEXTURE, i + 175, j + 33 + k, 12, 15);

        int n = this.scrollOffset + 45; //scrollOffset = nb of item displayed. (stoneCutter.sO = 12)
        this.renderItemList(n);
    }

    @Override
    protected void init() {
        super.init();
        this.searchWidget = new TextFieldWidget(client.textRenderer,this.x+80,this.y+19,108,12,Text.of("container."+ CypherDuxMod.MOD_ID+".item_display_board.search"));
        this.searchWidget.setMaxLength(50);
        this.searchWidget.setDrawsBackground(true);
        this.setInitialFocus(this.searchWidget);
        this.searchWidget.setText("");
        addDrawableChild(this.searchWidget);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    private void renderItemList(int scrollOffset){
        List<ItemStack> list = this.handler.getItemList();
        for (int i = this.scrollOffset; i < scrollOffset;++i){
            if (i < (this.handler.getItemListCount())){
                this.handler.getSlot(i+1-this.scrollOffset).setStack(list.get(i));
            }else{
                this.handler.getSlot(i+1-this.scrollOffset).setStack(ItemStack.EMPTY);
            }
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (this.shouldScroll()) {
            int i = this.getMaxScroll();
            float f = (float)verticalAmount / (float)i;
            this.scrollAmount = MathHelper.clamp(this.scrollAmount - f, 0.0F, 1.0F);
            this.scrollOffset = (int)((double)(this.scrollAmount * (float)i) + (double)0.5F) * ITEM_LIST_COLUMNS;
        }

        return true;
    }

    private int getMaxScroll() {
        return (this.handler.getItemListCount()+ ITEM_LIST_COLUMNS -1)/ ITEM_LIST_COLUMNS - ITEM_LIST_ROWS;
    }

    private boolean shouldScroll() {
        return this.handler.getItemListCount()>45;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        this.mouseClicked = false;

        int i = this.x + 175;
        int j = this.y + 33;
        if (mouseX >= (double)i && mouseX < (double)(i + 45) && mouseY >= (double)j && mouseY < (double)(j + SCROLLBAR_AREA_HEIGHT)) {
            this.mouseClicked = true;
        }
        i = this.x + 80;
        j = this.y + 19;
        if (mouseX >= (double)i && mouseX < (double)(i + 108) && mouseY >= (double)j && mouseY < (double)(j + 12)) {
            this.setFocused(false);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (this.mouseClicked && this.shouldScroll()) {
            int i = this.y + 32;
            int j = i + SCROLLBAR_AREA_HEIGHT;
            this.scrollAmount = ((float)mouseY - (float)i - 7.5F) / ((float)(j - i) - 15.0F);
            this.scrollAmount = MathHelper.clamp(this.scrollAmount, 0.0F, 1.0F);
            this.scrollOffset = (int)((double)(this.scrollAmount * (float)this.getMaxScroll()) + (double)0.5F) * ITEM_LIST_COLUMNS;
            return true;
        } else {
            return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        }
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        String string = this.searchWidget.getText();
        if (this.searchWidget.charTyped(chr, modifiers)) {
            if (!Objects.equals(string, this.searchWidget.getText())) {
                this.search();
            }

            return true;
        } else {
            return false;
        }
    }

    private void search(){
        this.handler.itemList.clear();
        String string = this.searchWidget.getText();
        if (string.isEmpty()){
            this.handler.itemList.addAll(ItemGroups.getSearchGroup().getSearchTabStacks());
        }else{
            List<ItemStack> searchResult = new ArrayList<>(ItemGroups.getSearchGroup().getSearchTabStacks());

            searchResult = searchResult.stream().filter(itemStack -> itemStack.getName().getString().toLowerCase(Locale.ROOT).contains(string)).toList();

            this.handler.itemList.addAll(searchResult);
        }

        this.scrollAmount = 0.0F;
        this.scrollOffset = 0;
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        this.ignoreTypedCharacter = false;
        boolean bl2 = InputUtil.fromKeyCode(keyCode, scanCode).toInt().isPresent();
        if (bl2 && this.handleHotbarKeyPressed(keyCode, scanCode)) {
            this.ignoreTypedCharacter = true;
            return true;
        } else {
            String string = this.searchWidget.getText();
            if (this.searchWidget.keyPressed(keyCode, scanCode, modifiers)) {
                if (!Objects.equals(string, this.searchWidget.getText())) {
                    this.search();
                }

                return true;
            } else {
                return this.searchWidget.isFocused() && this.searchWidget.isVisible() && keyCode != 256 || super.keyPressed(keyCode, scanCode, modifiers);
            }
        }
    }
}
