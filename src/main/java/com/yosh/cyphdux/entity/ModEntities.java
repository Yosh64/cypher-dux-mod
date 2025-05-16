package com.yosh.cyphdux.entity;

import com.yosh.cyphdux.CypherDuxMod;
import com.yosh.cyphdux.entity.custom.SitEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static void registerModEntities(){

    }
    public static final EntityType<SitEntity> SIT_ENTITY = Registry.register(Registries.ENTITY_TYPE, Identifier.of(CypherDuxMod.MOD_ID,"sit"),EntityType.Builder.create(SitEntity::new, SpawnGroup.MISC).dimensions(0.5f,0.5f).build());
}
