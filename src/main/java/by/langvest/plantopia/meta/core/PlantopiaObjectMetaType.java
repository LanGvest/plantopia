package by.langvest.plantopia.meta.core;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public abstract class PlantopiaObjectMetaType<T, P extends PlantopiaObjectMetaProperties<T>> {
	private final String category;
	private final String name;
	protected final P properties;
	protected T type = null;

	public PlantopiaObjectMetaType(String category, String name, @NotNull P properties) {
		this.category = category;
		this.name = name;
		this.properties = properties;
	}

	public String getName() {
		return name;
	}

	public String getCategory() {
		return category;
	}

	public String getId() {
		return category + ":" + name;
	}

	@Override
	public String toString() {
		return String.format("%s{%s}", this.getClass().getSimpleName(), getId());
	}
}