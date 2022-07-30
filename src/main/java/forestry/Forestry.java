package forestry;

import net.minecraft.resources.ResourceLocation;

import forestry.core.ForestryBlocks;
import forestry.core.ForestryItems;
import forestry.world.ForestryOres;

public interface Forestry {

    static ResourceLocation id(String path) {
        return new ResourceLocation(Constants.MOD_ID, path);
    }

    static void initialize() {
        ForestryBlocks.init();
        ForestryItems.init();
        ForestryOres.init();
    }
}
