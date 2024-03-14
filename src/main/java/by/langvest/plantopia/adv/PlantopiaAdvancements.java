package by.langvest.plantopia.adv;

import by.langvest.plantopia.meta.PlantopiaAdvancementMeta.MetaProperties;
import by.langvest.plantopia.meta.PlantopiaMetaStore;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class PlantopiaAdvancements {
	private static final PlantopiaAdvancementTab GROUP = PlantopiaAdvancementTab.TAB_PLANTOPIA;

	public static @NotNull PlantopiaAdvancement registerAdvancement(String name, @NotNull Supplier<PlantopiaAdvancement> supplier, @NotNull MetaProperties metaProperties) {
		PlantopiaAdvancement advancement = supplier.get();
		PlantopiaMetaStore.add(name, advancement, metaProperties.group(GROUP));
		return advancement;
	}
}