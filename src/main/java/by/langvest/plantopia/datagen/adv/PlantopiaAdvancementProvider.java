package by.langvest.plantopia.datagen.adv;

import by.langvest.plantopia.adv.PlantopiaAdvancement;
import by.langvest.plantopia.meta.PlantopiaMetaStore;
import by.langvest.plantopia.util.PlantopiaIdentifier;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.advancements.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class PlantopiaAdvancementProvider extends AdvancementProvider {
	private Consumer<Advancement> consumer;

	public PlantopiaAdvancementProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, existingFileHelper);
	}

	@Override
	protected void registerAdvancements(@NotNull Consumer<Advancement> consumer, @NotNull ExistingFileHelper existingFileHelper) {
		setConsumer(consumer);

		saveAll();
	}

	private void setConsumer(@NotNull Consumer<Advancement> consumer) {
		this.consumer = consumer;
	}

	private void saveAll() {
		PlantopiaMetaStore.getAdvancements().forEach(advancementMeta -> {
			PlantopiaAdvancement advancement = advancementMeta.getAdvancement();
			PlantopiaAdvancement parent = advancementMeta.getParent();
			Advancement.Builder builder = advancement.getBuilder();
			String group = advancementMeta.getGroup().getName();
			String name = advancementMeta.getName();

			builder.display(
				advancementMeta.getIcon(),
				advancementMeta.getTitle(),
				advancementMeta.getDescription(),
				advancementMeta.getBackground(),
				advancementMeta.getFrameType(),
				advancementMeta.shouldShowToast(),
				advancementMeta.shouldAnnounceToChat(),
				advancementMeta.isHidden()
			);

			if(parent != null && parent.getInstance() != null) builder.parent(parent.getInstance());

			advancement.save(consumer, new PlantopiaIdentifier(group + "/" + name));
		});
	}
}