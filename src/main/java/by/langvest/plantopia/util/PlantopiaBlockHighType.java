package by.langvest.plantopia.util;

public enum PlantopiaBlockHighType {
	NORMAL(1),
	DOUBLE(2),
	TRIPLE(3),
	COLUMN(1);

	private final int baseHigh;

	PlantopiaBlockHighType(int baseHigh) {
		this.baseHigh = baseHigh;
	}

	public int getBaseHigh() {
		return baseHigh;
	}
}