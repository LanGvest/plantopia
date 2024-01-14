package by.langvest.plantopia.datagen.sound;

import by.langvest.plantopia.Plantopia;
import by.langvest.plantopia.util.PlantopiaIdentifier;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition.Sound;
import net.minecraftforge.common.data.SoundDefinition.SoundType;
import net.minecraftforge.common.data.SoundDefinitionsProvider;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class PlantopiaSoundProvider extends SoundDefinitionsProvider {
	public PlantopiaSoundProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Plantopia.MOD_ID, existingFileHelper);
	}

	@Override
	public void registerSounds() {
		registerAll();
	}

	private void registerAll() {}

	@Contract("_ -> new")
	protected static @NotNull Sound sound(String name) {
		return Sound.sound(new PlantopiaIdentifier(name), SoundType.SOUND);
	}

	@Contract("_ -> new")
	protected static @NotNull Sound event(String name) {
		return Sound.sound(new PlantopiaIdentifier(name), SoundType.EVENT);
	}
}