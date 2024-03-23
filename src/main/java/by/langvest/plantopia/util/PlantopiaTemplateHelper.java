package by.langvest.plantopia.util;

import by.langvest.plantopia.adv.PlantopiaAdvancementTab;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class PlantopiaTemplateHelper {
	@Contract(pure = true)
	public static @NotNull String advancementTitle(PlantopiaAdvancementTab group, String name) {
		return "advancements." + group + "." + name + ".title";
	}

	@Contract(pure = true)
	public static @NotNull String advancementDescription(PlantopiaAdvancementTab group, String name) {
		return "advancements." + group + "." + name + ".description";
	}

	@Contract(pure = true)
	public static @NotNull String advancementBackground(String name) {
		return "textures/gui/advancements/backgrounds/" + name + ".png";
	}
}