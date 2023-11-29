package by.langvest.plantopia.datagen.loot;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class PlantopiaLootTableProvider extends LootTableProvider {
	private static final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> LOOT_TABLES = ImmutableList.of(
		Pair.of(PlantopiaBlockLootTables::new, LootContextParamSets.BLOCK)
	);

	public PlantopiaLootTableProvider(DataGenerator generator) {
		super(generator);
	}

	@Override
	protected @NotNull List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
		return LOOT_TABLES;
	}

	@Override
	protected void validate(@NotNull Map<ResourceLocation, LootTable> map, @NotNull ValidationContext context) {
		map.forEach((id, table) -> LootTables.validate(context, id, table));
	}
}