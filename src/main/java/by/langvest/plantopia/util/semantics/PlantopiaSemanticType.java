package by.langvest.plantopia.util.semantics;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public abstract class PlantopiaSemanticType<T, P extends PlantopiaSemanticProperties<T>> {
	private final String category;
	private final String name;
	protected final P properties;
	protected final T type;

	public PlantopiaSemanticType(String category, String name, @NotNull P properties) {
		this.category = category;
		this.name = name;
		this.properties = properties;
		this.type = properties.type;
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