package forestry.recipe;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import forestry.Forestry;

public interface ForestryRecipe extends Recipe<Inventory> {

    // <editor-fold desc="Ignore these methods, we just piggyback off Minecraft's system for recipe sync">
    @Deprecated
    @Override
    default boolean matches(Inventory container, Level level) {
        return false;
    }

    @Deprecated
    @Override
    default ItemStack assemble(Inventory container) {
        return ItemStack.EMPTY;
    }

    @Deprecated
    @Override
    default boolean canCraftInDimensions(int i, int j) {
        return false;
    }

    @Deprecated
    @Override
    default ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    @Deprecated
    @Override
    default NonNullList<ItemStack> getRemainingItems(Inventory container) {
        return NonNullList.create();
    }

    @Deprecated
    @Override
    default NonNullList<Ingredient> getIngredients() {
        return NonNullList.create();
    }

    @Deprecated
    @Override
    default boolean isSpecial() {
        return true;
    }

    @Deprecated
    @Override
    default String getGroup() {
        return Forestry.MOD_ID;
    }

    @Deprecated
    @Override
    default ItemStack getToastSymbol() {
        return ItemStack.EMPTY;
    }

    @Deprecated
    @Override
    default boolean isIncomplete() {
        return true;
    }
    // </editor-fold>

    @Override
    ResourceLocation getId();

    ForestryRecipes.Type<?> getRecipeType();

    @Override
    default RecipeSerializer<?> getSerializer() {
        return getRecipeType().serializer();
    }

    @Override
    default RecipeType<?> getType() {
        return getRecipeType().type();
    }
}
