package forestry.recipe;

import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import forestry.Forestry;

public interface ForestryRecipes {

    Type<CarpenterRecipe> CARPENTER = create("carpenter", new CarpenterRecipe.Serializer());

    static void initialize() {
        // Done in class initialization
    }

    record Type<T extends Recipe<?>> (RecipeType<T> type, RecipeSerializer<T> serializer) {
    }

    private static <T extends Recipe<?>> Type<T> create(String id, RecipeSerializer<T> serializer) {
        RecipeType<T> type = new RecipeType<>() {
            @Override
            public String toString() {
                return id;
            }
        };

        var identifier = Forestry.id(id);
        Registry.register(Registry.RECIPE_TYPE, identifier, type);
        Registry.register(Registry.RECIPE_SERIALIZER, identifier, serializer);

        return new Type<>(type, serializer);
    }
}
