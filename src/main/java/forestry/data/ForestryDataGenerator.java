package forestry.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public interface ForestryDataGenerator {

    static void initialize(FabricDataGenerator generator) {
        generator.addProvider(ForestryModelProvider::new);
    }
}
