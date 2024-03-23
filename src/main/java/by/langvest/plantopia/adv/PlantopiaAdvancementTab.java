package by.langvest.plantopia.adv;

import by.langvest.plantopia.Plantopia;

public enum PlantopiaAdvancementTab {
	TAB_PLANTOPIA(Plantopia.MOD_ID);

	private final String name;

	PlantopiaAdvancementTab(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return getName();
	}
}