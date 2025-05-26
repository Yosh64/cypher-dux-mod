package com.yosh.cyphdux.mixin.client;

import com.yosh.cyphdux.tags.ModTags;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin<T extends Entity>{

    @Inject(method = "renderLabelIfPresent", at = @At("HEAD"), cancellable = true)
    private void cancelLabelRender(T entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, float tickDelta, CallbackInfo ci) {
        if(!(entity instanceof PlayerEntity player)) return;

        if(player.getInventory().getArmorStack(3).isIn(ModTags.Items.NO_LABEL_RENDER)||player.getType().isIn(ModTags.EntityTypes.NO_LABEL_RENDER)) {
            ci.cancel();
        }
    }

}
