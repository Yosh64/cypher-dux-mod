package com.yosh.cyphdux.tags;

import com.yosh.cyphdux.CypherDuxMod;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    public static class Blocks{
        private static TagKey<Block> createTag(String name){
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(CypherDuxMod.MOD_ID,name));
        }
    }
    public static class Items{
        public static final TagKey<Item> PIGLIN_SAFE_ARMOR = createTag("piglin_safe_armor");

        private static TagKey<Item> createTag(String name){
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(CypherDuxMod.MOD_ID,name));
        }
    }
}
