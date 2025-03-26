package com.yosh.cyphdux.mixin;


import com.yosh.cyphdux.tags.ModTags;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;

@Mixin(PiglinBrain.class)
public class PiglinBrainMixin
{
    @Inject(method = "wearsGoldArmor", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private static void wearsGoldArmor(LivingEntity entity, CallbackInfoReturnable<Boolean> info, Iterable<ItemStack> iterable, Iterator<ItemStack> iterator, ItemStack stack)
    {
        if (stack.isIn(ModTags.Items.PIGLIN_SAFE_ARMOR)) info.setReturnValue(true);
    }
}
/*
public static boolean wearsGoldArmor(LivingEntity entity) {
		for (ItemStack itemStack : entity.getAllArmorItems()) {
			Item item = itemStack.getItem();
			if (item instanceof ArmorItem && ((ArmorItem)item).getMaterial().matches(ArmorMaterials.GOLD)) {
				return true;
			}
		}

		return false;
	}
*/