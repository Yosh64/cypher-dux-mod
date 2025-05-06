package com.yosh.cyphdux.mixin;

import com.yosh.cyphdux.tags.ModTags;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;


@Mixin(EndermanEntity.class)
public class EndermanEntityMixin {
    @Inject(method = "isPlayerStaring", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/util/collection/DefaultedList;get(I)Ljava/lang/Object;"),cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void isPlayerStaring(PlayerEntity player, CallbackInfoReturnable<Boolean> cir){
        if (player.getCommandTags().contains("enderman")||player.getType().isIn(ModTags.EntityTypes.ENDERMAN_FRIENDS)) cir.setReturnValue(false);
        else if (player.getInventory().armor.get(3).isIn(ModTags.Items.ENDERMAN_SAFE_HAT)) cir.setReturnValue(false);
    }

}
