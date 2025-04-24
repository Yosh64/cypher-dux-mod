package com.yosh.cyphdux.block.entity.renderer;

import com.yosh.cyphdux.block.entity.ItemDisplayBoardBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class ItemDisplayBoardBlockEntityRenderer implements BlockEntityRenderer<ItemDisplayBoardBlockEntity> {
    public ItemDisplayBoardBlockEntityRenderer(BlockEntityRendererFactory.Context context){

    }
    @Override
    public void render(ItemDisplayBoardBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        ItemStack stack = entity.getStack(0);
        Direction direction = entity.getCachedState().get(Properties.HORIZONTAL_FACING);
        float rotation;
        matrices.push();
        switch (direction){
            default -> {
                matrices.translate(0.5f,0.5f,0.93f);
                matrices.scale(0.5f,0.5f,0.01f);
                rotation = 180F;
            }
            case SOUTH -> {
                matrices.translate(0.5f,0.5f,0.07f);
                matrices.scale(0.5f,0.5f,0.01f);
                rotation = 0F;
            }
            case EAST -> {
                matrices.translate(0.07f,0.5f,0.5f);
                matrices.scale(0.01f,0.5f,0.5f);
                rotation = 90F;
            }
            case WEST -> {
                matrices.translate(0.93f,0.5f,0.5f);
                matrices.scale(0.01f,0.5f,0.5f);
                rotation = 270F;
            }
        }
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(rotation));

        itemRenderer.renderItem(stack, ModelTransformationMode.GUI, getLightLevel(entity.getWorld(),entity.getPos()), OverlayTexture.DEFAULT_UV,matrices, vertexConsumers,entity.getWorld(),1);

        matrices.pop();
    }

    private int getLightLevel(World world, BlockPos pos){
        int bLight = world.getLightLevel(LightType.BLOCK,pos);
        int sLight = world.getLightLevel(LightType.SKY,pos);
        return LightmapTextureManager.pack(15,15);
    }
}
