package by.langvest.plantopia.datagen.lang;

import by.langvest.plantopia.Plantopia;
import by.langvest.plantopia.adv.PlantopiaAdvancement;
import by.langvest.plantopia.meta.PlantopiaAdvancementMeta;
import by.langvest.plantopia.meta.PlantopiaMetaStore;
import by.langvest.plantopia.tab.PlantopiaCreativeModeTabs;
import by.langvest.plantopia.util.PlantopiaStringHelper;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class PlantopiaLanguageProvider extends LanguageProvider {
	public PlantopiaLanguageProvider(DataGenerator generator) {
		super(generator, Plantopia.MOD_ID, "en_us");
	}

	@Override
	protected void addTranslations() {
		generateAll();

		add(PlantopiaCreativeModeTabs.TAB_PLANTOPIA, "Plantopia");
	}

	private void add(@NotNull CreativeModeTab tab, String name) {
		add(tab.getDisplayName().getString(), name);
	}

	private void add(@NotNull PlantopiaAdvancement advancement, String title, String description) {
		PlantopiaAdvancementMeta advancementMeta = Objects.requireNonNull(PlantopiaMetaStore.getAdvancement(advancement));

		add(advancementMeta.getTitle().getKey(), title);
		add(advancementMeta.getDescription().getKey(), description);
	}

	private void generateAll() {
		PlantopiaMetaStore.getBlocks().forEach(blockMeta -> {
			if(!blockMeta.shouldGenerateTranslation()) return;

			Block block = blockMeta.getBlock();

			add(block, getDisplayNameById(blockMeta.getName()));
		});
	}

	private String getDisplayNameById(@NotNull String id) {
		return Arrays.stream(id.split("_"))
			.map(PlantopiaStringHelper::capitalize)
			.collect(Collectors.joining(" "));
	}
}