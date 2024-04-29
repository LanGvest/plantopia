package by.langvest.plantopia.util;

import by.langvest.plantopia.item.PlantopiaTripleHighBlockItem;
import by.langvest.plantopia.item.PlantopiaWideTripleHighBlockItem;
import by.langvest.plantopia.meta.object.PlantopiaBlockMeta;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.Item.Properties;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class PlantopiaBlockItemHelper {
	public static @NotNull Supplier<BlockItem> getBlockItemSupplier(@NotNull PlantopiaBlockMeta blockMeta, Properties properties) {
		int height = blockMeta.getBlockHeightType().getBaseHeight();
		int width = blockMeta.getBlockWidthType().getBaseWidth();

		if(height == 1) return () -> new BlockItem(blockMeta.getBlock(), properties);
		if(height == 2) return () -> new DoubleHighBlockItem(blockMeta.getBlock(), properties);
		if(height == 3 && width == 1) return () -> new PlantopiaTripleHighBlockItem(blockMeta.getBlock(), properties);
		if(height == 3 && width == 2) return () -> new PlantopiaWideTripleHighBlockItem(blockMeta.getBlock(), properties);
		return () -> new BlockItem(blockMeta.getBlock(), properties);
	}
}