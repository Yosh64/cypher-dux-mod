package com.yosh.cyphdux.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

public class CardboardBoxBlockItem extends BlockItem {
    public CardboardBoxBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public boolean canBeNested() {
        return false;
    }
}
