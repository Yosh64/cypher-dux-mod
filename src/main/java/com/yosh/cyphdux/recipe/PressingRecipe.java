package com.yosh.cyphdux.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.yosh.cyphdux.recipe.input.DoubleStackRecipeInput;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public record PressingRecipe(Ingredient leftItem, Ingredient rightItem, ItemStack output, double experience, int pressingTime) implements Recipe<DoubleStackRecipeInput> {
    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.of();
        list.add(this.leftItem);
        list.add(this.rightItem);
        return list;
    }

    @Override
    public boolean matches(DoubleStackRecipeInput input, World world) {
        if (world.isClient()){
            return false;
        }

        return leftItem.test(input.getStackInSlot(0)) && rightItem.test(input.getStackInSlot(1));
    }

    @Override
    public ItemStack craft(DoubleStackRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return output.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.PRESSING_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.PRESSING_TYPE;
    }

    public boolean isInRecipe(ItemStack stack, int slotId, World world) {
        if (world.isClient()){
            return false;
        }
        return switch (slotId) {
            case 0 -> leftItem.test(stack);
            case 1 -> rightItem.test(stack);
            default -> false;
        };
    }

    public static class Serializer implements RecipeSerializer<PressingRecipe>{
        public static final MapCodec<PressingRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("left_item").forGetter(recipe -> recipe.leftItem),
                                Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("right_item").forGetter(recipe -> recipe.rightItem),
                                ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter(recipe -> recipe.output),
                                Codec.DOUBLE.fieldOf("experience").orElse(0.35).forGetter(recipe -> recipe.experience),
                                Codec.INT.fieldOf("pressing_time").orElse(200).forGetter(recipe -> recipe.pressingTime)
                        )
                        .apply(instance, PressingRecipe::new)
        );
        public static final PacketCodec<RegistryByteBuf, PressingRecipe> PACKET_CODEC = PacketCodec.tuple(
                Ingredient.PACKET_CODEC, PressingRecipe::leftItem,
                Ingredient.PACKET_CODEC, PressingRecipe::rightItem,
                ItemStack.PACKET_CODEC, PressingRecipe::output,
                PacketCodecs.DOUBLE,PressingRecipe::experience,
                PacketCodecs.INTEGER,PressingRecipe::pressingTime,
                PressingRecipe::new
        );
        @Override
        public MapCodec<PressingRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, PressingRecipe> packetCodec() {
            return PACKET_CODEC;
        }
    }
}
