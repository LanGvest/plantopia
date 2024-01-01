package by.langvest.plantopia.meta.core;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class PlantopiaMetaAccessor {
	@Contract(pure = true)
	public static <T extends PlantopiaObjectMetaType<T, P>, P extends PlantopiaObjectMetaProperties<T>> T getMetaType(@NotNull P metaProperties) {
		return metaProperties.type;
	}

	public static <T extends PlantopiaObjectMetaType<T, P>, P extends PlantopiaObjectMetaProperties<T>> P getMetaProperties(@NotNull T metaType) {
		return metaType.properties;
	}

	public static <T extends PlantopiaObjectMetaType<T, P>, P extends PlantopiaObjectMetaProperties<T>> void setRecursiveMetaType(@NotNull T metaType) {
		metaType.type = metaType;
	}
}