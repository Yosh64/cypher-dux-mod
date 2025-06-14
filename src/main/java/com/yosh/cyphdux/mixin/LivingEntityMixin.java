package com.yosh.cyphdux.mixin;

import com.yosh.cyphdux.block.ModBlocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Shadow protected abstract void playEquipmentBreakEffects(ItemStack stack);

    @Inject(method = "damageEquipment", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/entity/LivingEntity;getEquippedStack(Lnet/minecraft/entity/EquipmentSlot;)Lnet/minecraft/item/ItemStack;"),cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void damageEquipment(DamageSource source, float amount, EquipmentSlot[] slots, CallbackInfo ci){
        for (EquipmentSlot equipmentSlot : slots) {
            ItemStack itemStack = this.getEquippedStack(equipmentSlot);
            if (itemStack.isOf(ModBlocks.CARDBOARD_BOX.asItem())){
                playEquipmentBreakEffects(itemStack);
                itemStack.decrement(1);
            }
        }
    }
}
