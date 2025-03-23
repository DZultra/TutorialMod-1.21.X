package net.dzultra.tutorialmod.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public record GrowthChamberRecipe(Ingredient inputItem, ItemStack output, int duration) implements Recipe<GrowthChamberRecipeInput> {
    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.of();
        list.add(this.inputItem);
        return list;
    }

    @Override
    public boolean matches(GrowthChamberRecipeInput input, World world) {
        if (world.isClient()) {
            return false;
        }
        return inputItem.test(input.getStackInSlot(0));
    }

    @Override
    public ItemStack craft(GrowthChamberRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return output.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.GROWTH_CHAMBER_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.GROWTH_CHAMBER_TYPE;
    }

    public static class Serializer implements RecipeSerializer<GrowthChamberRecipe> {
        public static final MapCodec<GrowthChamberRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient").forGetter(GrowthChamberRecipe::inputItem),
                ItemStack.CODEC.fieldOf("result").forGetter(GrowthChamberRecipe::output),
                Codec.INT.fieldOf("duration").forGetter(GrowthChamberRecipe::duration)
        ).apply(inst, GrowthChamberRecipe::new));

        @Override
        public MapCodec<GrowthChamberRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, GrowthChamberRecipe> packetCodec() {
            return new PacketCodec<>() {
                @Override
                public GrowthChamberRecipe decode(RegistryByteBuf buf) {
                    // Deserialize the recipe from the buffer
                    Ingredient inputItem = Ingredient.PACKET_CODEC.decode(buf);
                    ItemStack output = ItemStack.PACKET_CODEC.decode(buf);
                    int duration = buf.readVarInt(); // Read the duration as a VarInt
                    return new GrowthChamberRecipe(inputItem, output, duration);
                }

                @Override
                public void encode(RegistryByteBuf buf, GrowthChamberRecipe recipe) {
                    // Serialize the recipe to the buffer
                    Ingredient.PACKET_CODEC.encode(buf, recipe.inputItem());
                    ItemStack.PACKET_CODEC.encode(buf, recipe.output());
                    buf.writeVarInt(recipe.duration()); // Write the duration as a VarInt
                }
            };
        }
    }
}
