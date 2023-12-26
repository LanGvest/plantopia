package by.langvest.plantopia.meta.core;

public abstract class PlantopiaObjectMeta<T> {
	private final String name;
	private final T object;

	public PlantopiaObjectMeta(String name, T object) {
		this.name = name;
		this.object = object;
	}

	public String getName() {
		return name;
	}

	public T getObject() {
		return object;
	}
}