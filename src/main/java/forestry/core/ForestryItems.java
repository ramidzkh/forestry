package forestry.core;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import forestry.Constants;
import forestry.Forestry;

public class ForestryItems {
    public static final Item FERTILIZER = register("fertilizer_compound", new Item(core()));
    public static final BlockItem APATITE_ORE = blockItem(ForestryBlocks.APATITE_ORE, core());
    public static final BlockItem DEEPSLATE_APATITE_ORE = blockItem(ForestryBlocks.DEEPSLATE_APATITE_ORE, core());
    public static final BlockItem TIN_ORE = blockItem(ForestryBlocks.TIN_ORE, core());
    public static final BlockItem DEEPSLATE_TIN_ORE = blockItem(ForestryBlocks.DEEPSLATE_TIN_ORE, core());
    public static final BlockItem RAW_TIN_BLOCK = blockItem(ForestryBlocks.RAW_TIN_BLOCK, core());

    public static <T extends Item> T register(String id, T item) {
        return Registry.register(Registry.ITEM, Forestry.id(id), item);
    }

    public static FabricItemSettings core() {
        return new FabricItemSettings().group(Constants.FORESTRY_TAB);
    }

    public static BlockItem blockItem(Block block, FabricItemSettings settings) {
        return register(Registry.BLOCK.getKey(block).getPath(), new BlockItem(block, settings));
    }

    public static void init() {
    }
}
