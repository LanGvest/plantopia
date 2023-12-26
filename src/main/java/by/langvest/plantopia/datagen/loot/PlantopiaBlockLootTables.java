package by.langvest.plantopia.datagen.loot;

import by.langvest.plantopia.meta.PlantopiaBlockMeta;
import by.langvest.plantopia.meta.PlantopiaMetaStore;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class PlantopiaBlockLootTables extends BlockLoot {
	@Override
	protected void addTables() {
		generateAll();
	}

	private void generateAll() {}

	@Override
	protected @NotNull Iterable<Block> getKnownBlocks() {
		return PlantopiaMetaStore.getBlocks().stream().map(PlantopiaBlockMeta::getBlock)::iterator;
	}
}