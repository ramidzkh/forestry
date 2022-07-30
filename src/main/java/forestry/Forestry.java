package forestry;

import net.minecraft.resources.ResourceLocation;

public interface Forestry {

    String MOD_ID = "forestry";

    static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    static void initialize() {
    }
}
