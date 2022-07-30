package forestry.util;

import java.util.function.BiConsumer;
import java.util.function.Function;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public interface RecipeSerializers {

    static <E> void write(FriendlyByteBuf buffer, NonNullList<E> list, BiConsumer<FriendlyByteBuf, E> consumer) {
        buffer.writeVarInt(list.size());

        for (var e : list) {
            consumer.accept(buffer, e);
        }
    }

    static <E> NonNullList<E> read(FriendlyByteBuf buffer, Function<FriendlyByteBuf, E> reader) {
        var size = buffer.readVarInt();
        NonNullList<E> list = NonNullList.createWithCapacity(size);

        for (var i = 0; i < size; i++) {
            list.add(reader.apply(buffer));
        }

        return list;
    }

    static FluidVariant deserializeFluid(JsonObject object) {
        return FluidVariant.fromNbt((CompoundTag) Dynamic.convert(JsonOps.INSTANCE, NbtOps.INSTANCE, object));
    }

    static JsonObject serializeFluid(FluidVariant fluid) {
        return (JsonObject) Dynamic.convert(NbtOps.INSTANCE, JsonOps.INSTANCE, fluid.toNbt());
    }

    static ItemStack item(JsonObject object) {
        return ItemStack.of((CompoundTag) Dynamic.convert(JsonOps.INSTANCE, NbtOps.INSTANCE, object));
    }

    static JsonObject item(ItemStack stack) {
        return (JsonObject) Dynamic.convert(NbtOps.INSTANCE, JsonOps.INSTANCE, stack.save(new CompoundTag()));
    }

    static Ingredient deserialize(JsonElement resource) {
        if (resource.isJsonArray() && resource.getAsJsonArray().size() == 0) {
            return Ingredient.EMPTY;
        }

        return Ingredient.fromJson(resource);
    }
}
