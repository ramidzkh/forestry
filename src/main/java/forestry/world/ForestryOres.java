package forestry.world;

import java.util.List;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import forestry.Constants;
import forestry.Forestry;
import forestry.core.ForestryBlocks;

public class ForestryOres {
    public static void init() {
        var apatite = OrePlacements.commonOrePlacement(3,
                HeightRangePlacement.triangle(VerticalAnchor.absolute(48), VerticalAnchor.absolute(112)));
        var tin = OrePlacements.commonOrePlacement(16,
                HeightRangePlacement.triangle(VerticalAnchor.bottom(), VerticalAnchor.absolute(64)));
        var apatiteOre = registerOre("apatite_ore", new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES,
                ForestryBlocks.APATITE_ORE.defaultBlockState(), 3), apatite);
        var deepslateApatiteOre = registerOre("deepslate_apatite_ore",
                new OreConfiguration(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                        ForestryBlocks.DEEPSLATE_APATITE_ORE.defaultBlockState(), 3),
                apatite);
        var tinOre = registerOre("tin_ore",
                new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES, ForestryBlocks.TIN_ORE.defaultBlockState(), 9),
                tin);
        var deepslateTinOre = registerOre("deepslate_tin_ore", new OreConfiguration(
                OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ForestryBlocks.DEEPSLATE_TIN_ORE.defaultBlockState(), 9), tin);

        BiomeModifications.create(Forestry.id("ores")).add(ModificationPhase.ADDITIONS,
                BiomeSelectors.foundInOverworld(), context -> {

                    context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.UNDERGROUND_ORES,
                            apatiteOre.value());
                    context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.UNDERGROUND_ORES,
                            deepslateApatiteOre.value());
                    context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.UNDERGROUND_ORES,
                            tinOre.value());
                    context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.UNDERGROUND_ORES,
                            deepslateTinOre.value());
                });
    }

    private static Holder<PlacedFeature> registerOre(String registryName, OreConfiguration oreConfiguration,
            List<PlacementModifier> placementModifiers) {
        String identifier = new ResourceLocation(Constants.MOD_ID, registryName).toString();
        Holder<ConfiguredFeature<OreConfiguration, ?>> oreFeature = FeatureUtils.register(identifier, Feature.ORE,
                oreConfiguration);
        return PlacementUtils.register(identifier, oreFeature, placementModifiers);
    }
}
