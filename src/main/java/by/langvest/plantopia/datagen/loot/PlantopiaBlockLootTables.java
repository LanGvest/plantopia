package by.langvest.plantopia.datagen.loot;

import by.langvest.plantopia.meta.PlantopiaBlockMeta;
import by.langvest.plantopia.meta.PlantopiaMetaStore;
import by.langvest.plantopia.meta.property.PlantopiaBlockDropType;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.predicates.*;
import org.jetbrains.annotations.NotNull;

public class PlantopiaBlockLootTables extends BlockLoot {
	private static final LootItemCondition.Builder HAS_SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS));

	@Override
	protected void addTables() {
		generateAll();
	}

	private void generateAll() {
		PlantopiaMetaStore.getBlocks().forEach(blockMeta -> {
			Block block = blockMeta.getBlock();
			PlantopiaBlockDropType dropType = blockMeta.getDropType();

			if(dropType == PlantopiaBlockDropType.SELF_BY_SHEARS) {
				if(block instanceof DoublePlantBlock) add(block, createDoublePlantWithSelfByShearsDrops(block));
			}
		});
	}

	@Override
	protected @NotNull Iterable<Block> getKnownBlocks() {
		return PlantopiaMetaStore.getBlocks().stream().map(PlantopiaBlockMeta::getBlock)::iterator;
	}

	private static LootTable.@NotNull Builder createDoublePlantWithSelfByShearsDrops(Block block) {
		LootPoolEntryContainer.Builder<?> lootEntry = LootItem.lootTableItem(block).when(HAS_SHEARS);
		return LootTable.lootTable()
			.withPool(
				LootPool.lootPool()
					.add(lootEntry)
					.when(
						LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(
							StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)
						)
					)
					.when(
						LocationCheck.checkLocation(
							LocationPredicate.Builder.location().setBlock(
								BlockPredicate.Builder.block().of(block).setProperties(
									StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER).build()
								).build()
							), new BlockPos(0, 1, 0)
						)
					)
			)
			.withPool(
				LootPool.lootPool()
					.add(lootEntry)
					.when(
						LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(
							StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER)
						)
					)
					.when(
						LocationCheck.checkLocation(
							LocationPredicate.Builder.location().setBlock(
								BlockPredicate.Builder.block().of(block).setProperties(
									StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER).build()
								).build()
							), new BlockPos(0, -1, 0)
						)
					)
			);
	}
}