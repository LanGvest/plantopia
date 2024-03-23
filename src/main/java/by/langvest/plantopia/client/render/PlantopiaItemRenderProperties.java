package by.langvest.plantopia.client.render;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class PlantopiaItemRenderProperties implements IItemRenderProperties {
	private static PlantopiaItemRenderProperties instance = null;

	private PlantopiaItemRenderProperties() {}

	public static PlantopiaItemRenderProperties getInstance() {
		if(instance == null) instance = new PlantopiaItemRenderProperties();
		return instance;
	}

	@Contract(" -> new")
	public @NotNull BlockEntityWithoutLevelRenderer getItemStackRenderer() {
		return PlantopiaItemStuckRenderer.getInstance();
	}
}