package forestry.recipe;

import com.google.gson.JsonObject;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundUpdateRecipesPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeSerializer;

import forestry.util.RecipeSerializers;

public record CarpenterRecipe(ResourceLocation id, int packagingTime,
        FluidVariant fluid, long fluidAmount,
        Ingredient box, CraftingRecipe recipe,
        ItemStack output) implements ForestryRecipe {

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public ForestryRecipes.Type<?> getRecipeType() {
        return ForestryRecipes.CARPENTER;
    }

    static class Serializer implements RecipeSerializer<CarpenterRecipe> {

        @Override
        public CarpenterRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            var packagingTime = GsonHelper.getAsInt(json, "time");
            var fluid = json.has("fluid")
                    ? RecipeSerializers.deserializeFluid(GsonHelper.getAsJsonObject(json, "fluid"))
                    : FluidVariant.blank();
            var fluidAmount = GsonHelper.getAsLong(json, "fluidAmount");
            var box = RecipeSerializers.deserialize(json.get("box"));
            var internal = (CraftingRecipe) RecipeManager.fromJson(recipeId,
                    GsonHelper.getAsJsonObject(json, "recipe"));
            var result = json.has("result") ? RecipeSerializers.item(GsonHelper.getAsJsonObject(json, "result"))
                    : internal.getResultItem();

            return new CarpenterRecipe(recipeId, packagingTime, fluid, fluidAmount, box, internal, result);
        }

        @Override
        public CarpenterRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            var packagingTime = buffer.readVarInt();
            var fluid = FluidVariant.fromPacket(buffer);
            var fluidAmount = buffer.readVarLong();
            var box = Ingredient.fromNetwork(buffer);
            var internal = (CraftingRecipe) ClientboundUpdateRecipesPacket.fromNetwork(buffer);
            var result = buffer.readItem();

            return new CarpenterRecipe(recipeId, packagingTime, fluid, fluidAmount, box, internal, result);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, CarpenterRecipe recipe) {
            buffer.writeVarInt(recipe.packagingTime);
            recipe.fluid.toPacket(buffer);
            buffer.writeVarLong(recipe.fluidAmount);
            recipe.box.toNetwork(buffer);
            ClientboundUpdateRecipesPacket.toNetwork(buffer, recipe.recipe);
            buffer.writeItem(recipe.output);
        }
    }
}
