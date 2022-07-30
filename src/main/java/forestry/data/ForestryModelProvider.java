package forestry.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.core.Registry;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import forestry.core.ForestryBlocks;
import forestry.core.ForestryItems;

public class ForestryModelProvider extends FabricModelProvider {
    public ForestryModelProvider(FabricDataGenerator generator) {
        super(generator);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators stateGenerator) {
        createOre(stateGenerator, ForestryBlocks.APATITE_ORE);
        createOre(stateGenerator, ForestryBlocks.DEEPSLATE_APATITE_ORE);
        createOre(stateGenerator, ForestryBlocks.TIN_ORE);
        createOre(stateGenerator, ForestryBlocks.DEEPSLATE_TIN_ORE);
        createOre(stateGenerator, ForestryBlocks.RAW_TIN_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        itemModelGenerator.generateFlatItem(ForestryItems.FERTILIZER, ModelTemplates.FLAT_ITEM);
    }

    public void createOre(BlockModelGenerators stateGenerator, Block block) {
        ResourceLocation resourceLocation = Registry.BLOCK.getKey(block);
        ResourceLocation modelLocation = new ResourceLocation(resourceLocation.getNamespace(),
                "block/ores/" + resourceLocation.getPath().replaceAll("_ore", ""));
        TextureMapping mapping = (new TextureMapping()).put(TextureSlot.PARTICLE, modelLocation)
                .put(TextureSlot.ALL, modelLocation).put(TextureSlot.NORTH, modelLocation)
                .put(TextureSlot.SOUTH, modelLocation).put(TextureSlot.EAST, modelLocation)
                .put(TextureSlot.WEST, modelLocation).put(TextureSlot.UP, modelLocation)
                .put(TextureSlot.DOWN, modelLocation);
        stateGenerator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block,
                ModelTemplates.CUBE.create(block, mapping, stateGenerator.modelOutput)));
    }
}
