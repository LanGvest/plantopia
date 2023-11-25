package by.langvest.plantopia.util;

import by.langvest.plantopia.Plantopia;
import net.minecraft.resources.ResourceLocation;

public class PlantopiaIdentifier extends ResourceLocation {
	public PlantopiaIdentifier(String name) {
		super(Plantopia.MOD_ID, name);
	}
}