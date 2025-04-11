package com.yosh.cyphdux.network;

import com.yosh.cyphdux.CypherDuxMod;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record DisplayItemC2SPayload(ItemStack itemStack) implements CustomPayload {
    public static final Identifier DISPLAY_ITEM_PAYLOAD_ID = Identifier.of(CypherDuxMod.MOD_ID,"display_item");
    public static final CustomPayload.Id<DisplayItemC2SPayload> ID = new CustomPayload.Id<>(DISPLAY_ITEM_PAYLOAD_ID);
    public static final PacketCodec<RegistryByteBuf, DisplayItemC2SPayload> CODEC = PacketCodec.tuple(ItemStack.PACKET_CODEC, DisplayItemC2SPayload::itemStack, DisplayItemC2SPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
