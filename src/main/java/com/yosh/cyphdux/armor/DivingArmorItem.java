package com.yosh.cyphdux.armor;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;

public class DivingArmorItem extends ArmorItem {
    private static final Map<List<RegistryEntry<ArmorMaterial>>, List<StatusEffectInstance>> MATERIAL_TO_EFFECT_MAP =
            (new ImmutableMap.Builder<List<RegistryEntry<ArmorMaterial>>, List<StatusEffectInstance>>())
                    .put(List.of(ModArmorMaterials.DIVING,ModArmorMaterials.DIVING_2,ModArmorMaterials.DIVING_3),
                            List.of(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 200, 0, true, true)

                            )).build();

    public DivingArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
        super(material, type, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(!world.isClient()) {
            if(entity instanceof PlayerEntity player) {
                if(hasPieceOfArmorOn(player)) {
                    evaluateArmorEffects(player);
                }
            }
        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    private void evaluateArmorEffects(PlayerEntity player) {
        for (Map.Entry<List<RegistryEntry<ArmorMaterial>>, List<StatusEffectInstance>> entry : MATERIAL_TO_EFFECT_MAP.entrySet()) {
            List<RegistryEntry<ArmorMaterial>> mapArmorMaterial = entry.getKey();
            List<StatusEffectInstance> mapStatusEffects = entry.getValue();

            if(hasCorrectArmorOn(mapArmorMaterial, player)) {
                addStatusEffectForMaterial(player, mapStatusEffects);
            }
        }
    }

    private void addStatusEffectForMaterial(PlayerEntity player, List<StatusEffectInstance> mapStatusEffect) {
        boolean hasPlayerEffect = mapStatusEffect.stream().allMatch(statusEffectInstance -> player.hasStatusEffect(statusEffectInstance.getEffectType()));

        if (!hasPlayerEffect && player.isSubmergedInWater()) {
            player.getInventory().getArmorStack(3).damage(1,player, player.getPreferredEquipmentSlot(player.getInventory().getArmorStack(3)));
            for (StatusEffectInstance instance : mapStatusEffect) {
                player.addStatusEffect(new StatusEffectInstance(instance.getEffectType(),
                        instance.getDuration(), instance.getAmplifier(), instance.isAmbient(), instance.shouldShowParticles()));
            }
        }
    }

    private boolean hasPieceOfArmorOn(PlayerEntity player) {
        ItemStack helmet = player.getInventory().getArmorStack(3);

        return !helmet.isEmpty();
    }

    private boolean hasCorrectArmorOn(List<RegistryEntry<ArmorMaterial>> material, PlayerEntity player) {

        if(!(player.getInventory().getArmorStack(3).getItem() instanceof ArmorItem helmet)) {
            return false;
        }

        return material.contains(helmet.getMaterial());
    }
}