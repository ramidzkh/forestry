package forestry;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.world.item.CreativeModeTab;

import forestry.core.ForestryItems;

public class Constants {
    public static final String MOD_ID = "forestry";

    // Item Groups
    public static final CreativeModeTab FORESTRY_TAB = FabricItemGroupBuilder.build(Forestry.id("forestry"),
            () -> ForestryItems.FERTILIZER.getDefaultInstance());
}
