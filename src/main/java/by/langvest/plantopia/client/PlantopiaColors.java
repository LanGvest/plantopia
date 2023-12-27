package by.langvest.plantopia.client;

import by.langvest.plantopia.block.PlantopiaBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PlantopiaColors {
	public static void setup() {
		BlockColors blockColors = Minecraft.getInstance().getBlockColors();
		ItemColors itemColors = Minecraft.getInstance().getItemColors();

		/* FIREWEED BLOCK GRASS COLOR 0-1 ******************************************/
		blockColors.register(
			(state, level, pos, tintIndex) -> {
				DoubleBlockHalf half = state.getValue(DoublePlantBlock.HALF);
				if(half == DoubleBlockHalf.LOWER && tintIndex == 0) {
					if(level != null && pos != null) return BiomeColors.getAverageGrassColor(level, pos);
					return GrassColor.get(0.5D, 1.0D);
				}
				if(half == DoubleBlockHalf.UPPER && tintIndex == 1) {
					if(level != null && pos != null) return BiomeColors.getAverageGrassColor(level, pos.below());
					return GrassColor.get(0.5D, 1.0D);
				}
				return -1;
			},
			PlantopiaBlocks.FIREWEED.get()
		);

		/* FIREWEED ITEM GRASS COLOR 0 ******************************************/
		itemColors.register(
			(itemStuck, tintIndex) -> tintIndex == 0 ? GrassColor.get(0.5D, 1.0D) : -1,
			PlantopiaBlocks.FIREWEED.get()
		);
	}
}