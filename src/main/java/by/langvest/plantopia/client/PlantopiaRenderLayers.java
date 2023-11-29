package by.langvest.plantopia.client;

import by.langvest.plantopia.util.semantics.PlantopiaSemanticRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class PlantopiaRenderLayers {
	public static void setup() {
		PlantopiaSemanticRegistry.getBlocks().forEach(blockSemantics -> {
			@Nullable RenderType renderType = switch(blockSemantics.getRenderType()) {
				case CUTOUT -> RenderType.cutout();
				case TRANSLUCENT -> RenderType.translucent();
				default -> null;
			};

			if(renderType == null) return;
			ItemBlockRenderTypes.setRenderLayer(blockSemantics.getBlock(), renderType);
		});
	}
}