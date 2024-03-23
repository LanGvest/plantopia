package by.langvest.plantopia.item.special;

import by.langvest.plantopia.client.render.PlantopiaItemRenderProperties;
import net.minecraftforge.client.IItemRenderProperties;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class PlantopiaRenderedIconItem extends PlantopiaIconItem {
	public PlantopiaRenderedIconItem(Properties properties) {
		super(properties);
	}

	@Override
	public void initializeClient(@NotNull Consumer<IItemRenderProperties> consumer) {
		consumer.accept(PlantopiaItemRenderProperties.getInstance());
	}
}