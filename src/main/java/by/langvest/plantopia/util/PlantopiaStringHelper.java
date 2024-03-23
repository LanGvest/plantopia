package by.langvest.plantopia.util;

import org.jetbrains.annotations.NotNull;

public class PlantopiaStringHelper {
	public static @NotNull String capitalize(@NotNull String text) {
		return Character.toUpperCase(text.charAt(0)) + text.substring(1);
	}
}