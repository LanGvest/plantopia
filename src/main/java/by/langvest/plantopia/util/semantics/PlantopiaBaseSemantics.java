package by.langvest.plantopia.util.semantics;

public abstract class PlantopiaBaseSemantics<T> {
	private final String name;
	private final T object;

	public PlantopiaBaseSemantics(String name, T object) {
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