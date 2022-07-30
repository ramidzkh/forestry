package forestry.core;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.Registry;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;

import forestry.Forestry;

public class ForestryBlocks {
    public static final DropExperienceBlock APATITE_ORE = register("apatite_ore",
            new DropExperienceBlock(FabricBlockSettings.copy(Blocks.COAL_ORE), UniformInt.of(0, 4)));
    public static final DropExperienceBlock DEEPSLATE_APATITE_ORE = register("deepslate_apatite_ore",
            new DropExperienceBlock(FabricBlockSettings.copy(APATITE_ORE), UniformInt.of(0, 4)));
    public static final DropExperienceBlock TIN_ORE = register("tin_ore",
            new DropExperienceBlock(FabricBlockSettings.copy(Blocks.COPPER_ORE)));
    public static final DropExperienceBlock DEEPSLATE_TIN_ORE = register("deepslate_tin_ore",
            new DropExperienceBlock(FabricBlockSettings.copy(TIN_ORE)));
    public static final DropExperienceBlock RAW_TIN_BLOCK = register("raw_tin_block",
            new DropExperienceBlock(FabricBlockSettings.copy(Blocks.RAW_COPPER_BLOCK)));

    public static <T extends Block> T register(String id, T block) {
        return Registry.register(Registry.BLOCK, Forestry.id(id), block);
    }

    public static void init() {
    }
}
