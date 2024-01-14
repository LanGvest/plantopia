package by.langvest.plantopia.handler;

import by.langvest.plantopia.Plantopia;
import by.langvest.plantopia.datagen.lang.PlantopiaEnUsLangProvider;
import by.langvest.plantopia.datagen.loot.PlantopiaLootTableProvider;
import by.langvest.plantopia.datagen.model.PlantopiaBlockStateProvider;
import by.langvest.plantopia.datagen.model.PlantopiaItemModelProvider;
import by.langvest.plantopia.datagen.recipe.PlantopiaRecipeProvider;
import by.langvest.plantopia.datagen.tag.PlantopiaBlockTagProvider;
import by.langvest.plantopia.datagen.tag.PlantopiaItemTagProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = Plantopia.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PlantopiaDataGenerationHandler {
	@SubscribeEvent
	public static void gatherData(@NotNull GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

		generator.addProvider(new PlantopiaRecipeProvider(generator));
		generator.addProvider(new PlantopiaEnUsLangProvider(generator));
		generator.addProvider(new PlantopiaLootTableProvider(generator));
		generator.addProvider(new PlantopiaBlockStateProvider(generator, existingFileHelper));
		generator.addProvider(new PlantopiaItemModelProvider(generator, existingFileHelper));
		generator.addProvider(new PlantopiaBlockTagProvider(generator, existingFileHelper));
		generator.addProvider(new PlantopiaItemTagProvider(generator, existingFileHelper));
	}
}