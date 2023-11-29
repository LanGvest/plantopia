package by.langvest.plantopia.datagen.loot;

import by.langvest.plantopia.util.semantics.PlantopiaBlockSemantics;
import by.langvest.plantopia.util.semantics.PlantopiaSemanticRegistry;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class PlantopiaBlockLootTables extends BlockLoot {
	@Override
	protected void addTables() {}

	@Override
	protected @NotNull Iterable<Block> getKnownBlocks() {
		return PlantopiaSemanticRegistry.getBlocks().stream().map(PlantopiaBlockSemantics::getBlock)::iterator;
	}
}