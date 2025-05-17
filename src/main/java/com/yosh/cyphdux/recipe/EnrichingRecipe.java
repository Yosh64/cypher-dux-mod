package com.yosh.cyphdux.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.yosh.cyphdux.recipe.input.DoubleStackRecipeInput;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class EnrichingRecipe implements Recipe<DoubleStackRecipeInput> {
    private final ItemStack output;
    private final Ingredient base;
    private final Ingredient addition;
    private final float experience;
    private final int cookingTime;

    public EnrichingRecipe(Ingredient addition, Ingredient base, ItemStack output, float experience, int cookingTime) {
        this.addition = addition;
        this.base = base;
        this.output = output;
        this.experience = experience;
        this.cookingTime = cookingTime;
    }

    @Override
    public boolean matches(DoubleStackRecipeInput input, World world) {
        if (world.isClient()){
            return false;
        }

        return addition.test(input.getStackInSlot(0)) && base.test(input.getStackInSlot(1));
    }

    public boolean isInRecipe(ItemStack itemStack, int slot, World world) {
        if (world.isClient()){
            return false;
        }
        return switch (slot) {
            case 0 -> addition.test(itemStack);
            case 1 -> base.test(itemStack);
            default -> false;
        };
    }

    @Override
    public ItemStack craft(DoubleStackRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return output;
    }

    @Override
    public boolean fits(int width, int height) {
        return width >= 2 && height >= 1;
    }

    public float getExperience() {
        return this.experience;
    }
    public int getCookingTime() {return this.cookingTime;}

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> defaultedList = DefaultedList.of();
        defaultedList.add(this.base);
        defaultedList.add(this.addition);
        return defaultedList;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return output;
    }


    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }


    public static class Type implements RecipeType<EnrichingRecipe>{
        public static final Type INSTANCE = new Type();
        public static final String ID = "enriching";
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    public static class Serializer implements RecipeSerializer<EnrichingRecipe>{
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "enriching";
        public static final MapCodec<EnrichingRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("addition").forGetter(recipe -> recipe.addition),
                                Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("base").forGetter(recipe -> recipe.base),
                                ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter(recipe -> recipe.output),
                                Codec.FLOAT.fieldOf("experience").orElse(0.35F).forGetter(recipe -> recipe.experience),
                                Codec.INT.fieldOf("cookingtime").orElse(200).forGetter(recipe -> recipe.cookingTime)
                        )
                        .apply(instance, EnrichingRecipe::new)
        );
        public static final PacketCodec<RegistryByteBuf, EnrichingRecipe> PACKET_CODEC = PacketCodec.ofStatic(
                EnrichingRecipe.Serializer::write, EnrichingRecipe.Serializer::read
        );
        @Override
        public MapCodec<EnrichingRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, EnrichingRecipe> packetCodec() {
            return PACKET_CODEC;
        }

        private static EnrichingRecipe read(RegistryByteBuf buf) {
            Ingredient ingredient = Ingredient.PACKET_CODEC.decode(buf);
            Ingredient ingredient2 = Ingredient.PACKET_CODEC.decode(buf);
            ItemStack itemStack = ItemStack.PACKET_CODEC.decode(buf);
            float f = buf.readFloat();
            int i = buf.readVarInt();
            return new EnrichingRecipe(ingredient, ingredient2, itemStack, f, i);
        }

        private static void write(RegistryByteBuf buf, EnrichingRecipe recipe) {
            Ingredient.PACKET_CODEC.encode(buf, recipe.addition);
            Ingredient.PACKET_CODEC.encode(buf, recipe.base);
            ItemStack.PACKET_CODEC.encode(buf, recipe.output);
            buf.writeFloat(recipe.experience);
            buf.writeVarInt(recipe.cookingTime);
        }
    }
}
