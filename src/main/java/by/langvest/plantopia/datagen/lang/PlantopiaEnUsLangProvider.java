package by.langvest.plantopia.datagen.lang;

import by.langvest.plantopia.Plantopia;
import by.langvest.plantopia.meta.PlantopiaMetaStore;
import by.langvest.plantopia.tab.PlantopiaCreativeModeTabs;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.stream.Collectors;

public class PlantopiaEnUsLangProvider extends LanguageProvider {
	public PlantopiaEnUsLangProvider(DataGenerator generator) {
		super(generator, Plantopia.MOD_ID, "en_us");
	}

	@Override
	protected void addTranslations() {
		generateAll();

		add(PlantopiaCreativeModeTabs.TAB_PLANTOPIA, "Plantopia");
	}

	private void add(@NotNull CreativeModeTab tab, String name) {
		TranslatableComponent component = (TranslatableComponent)tab.getDisplayName();
		add(component.getKey(), name);
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
			.map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1))
			.collect(Collectors.joining(" "));
	}
}