package forestry.data.builder;

import java.util.function.Consumer;

import com.google.gson.JsonObject;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import forestry.recipe.ForestryRecipes;
import forestry.util.RecipeSerializers;

public class CarpenterRecipeBuilder {

    private int packagingTime;
    private FluidVariant fluid;
    private long fluidAmount;
    private Ingredient box;
    private FinishedRecipe recipe;
    @Nullable
    private ItemStack result;

    public CarpenterRecipeBuilder setPackagingTime(int packagingTime) {
        this.packagingTime = packagingTime;
        return this;
    }

    public CarpenterRecipeBuilder setFluid(FluidVariant fluid, long fluidAmount) {
        this.fluid = fluid;
        this.fluidAmount = fluidAmount;
        return this;
    }

    public CarpenterRecipeBuilder setBox(Ingredient box) {
        this.box = box;
        return this;
    }

    public CarpenterRecipeBuilder recipe(ShapedRecipeBuilder recipe) {
        Holder<FinishedRecipe> holder = new Holder<>();
        recipe.unlockedBy("impossible", new ImpossibleTrigger.TriggerInstance()).save(holder::set);
        this.recipe = holder.get();
        return this;
    }

    public CarpenterRecipeBuilder recipe(ShapelessRecipeBuilder recipe) {
        Holder<FinishedRecipe> holder = new Holder<>();
        recipe.unlockedBy("impossible", new ImpossibleTrigger.TriggerInstance()).save(holder::set);
        this.recipe = holder.get();
        return this;
    }

    /**
     * In case the recipe should create an item stack, and not a basic item without NBT
     *
     * @param result The result to override {@link #recipe(ShapedRecipeBuilder)} or
     *               {@link #recipe(ShapelessRecipeBuilder)}
     * @return This builder for chaining
     */
    public CarpenterRecipeBuilder override(ItemStack result) {
        this.result = result;
        return this;
    }

    public void build(Consumer<FinishedRecipe> consumer, ResourceLocation id) {
        consumer.accept(new Result(id, packagingTime, fluid, fluidAmount, box, recipe, result));
    }

    private record Result(ResourceLocation id, int packagingTime,
            FluidVariant fluid, long fluidAmount,
            Ingredient box, FinishedRecipe recipe,
            @Nullable ItemStack result) implements FinishedRecipe {

        public Result(ResourceLocation id, int packagingTime, FluidVariant fluid, long fluidAmount, Ingredient box,
                FinishedRecipe recipe, @Nullable ItemStack result) {
            this.id = id;
            this.packagingTime = packagingTime;
            this.fluid = fluid;
            this.fluidAmount = fluidAmount;
            this.box = box;
            this.recipe = recipe;
            this.result = result;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.addProperty("time", packagingTime);

            if (fluid != null) {
                json.add("fluid", RecipeSerializers.serializeFluid(fluid));
                json.addProperty("fluidAmount", fluidAmount);
            }

            json.add("box", box.toJson());
            json.add("recipe", recipe.serializeRecipe());

            if (result != null) {
                json.add("result", RecipeSerializers.item(result));
            }
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return ForestryRecipes.CARPENTER.serializer();
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }
}
