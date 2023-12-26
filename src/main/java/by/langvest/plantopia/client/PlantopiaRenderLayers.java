package by.langvest.plantopia.client;

import by.langvest.plantopia.meta.PlantopiaBlockMeta;
import by.langvest.plantopia.meta.PlantopiaMetaStore;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class PlantopiaRenderLayers {
	public static void setup() {
		applyForAll();
	}

	private static void applyForAll() {
		PlantopiaMetaStore.getBlocks().forEach(blockMeta -> {
			RenderType renderType = getRenderType(blockMeta);
			if(renderType == null) return;
			ItemBlockRenderTypes.setRenderLayer(blockMeta.getBlock(), renderType);
		});
	}

	@Nullable
	private static RenderType getRenderType(@NotNull PlantopiaBlockMeta blockMeta) {
		return switch(blockMeta.getRenderType()) {
			case CUTOUT -> RenderType.cutout();
			case CUTOUT_MIPPED -> RenderType.cutoutMipped();
			case TRANSLUCENT -> RenderType.translucent();
			default -> null;
		};
	}
}