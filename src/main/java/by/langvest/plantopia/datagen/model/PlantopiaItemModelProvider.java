package by.langvest.plantopia.datagen.model;

import by.langvest.plantopia.Plantopia;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class PlantopiaItemModelProvider extends ItemModelProvider {
	public PlantopiaItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Plantopia.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {}
}