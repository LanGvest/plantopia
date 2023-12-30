package by.langvest.plantopia.datagen.tag;

import by.langvest.plantopia.Plantopia;
import by.langvest.plantopia.meta.PlantopiaBlockMeta.MetaType;
import by.langvest.plantopia.meta.PlantopiaMetaStore;
import by.langvest.plantopia.tag.PlantopiaBlockTags;
import com.google.common.collect.Sets;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public class PlantopiaBlockTagProvider extends BlockTagsProvider {
	private final Set<Block> REPLACEABLE_PLANTS = Sets.newHashSet();
	private final Set<Block> TALL_FLOWERS = Sets.newHashSet();
	private final Set<Block> MINEABLE_WITH_AXE = Sets.newHashSet();
	private final Set<Block> IGNORED_BY_BEES = Sets.newHashSet();
	private final Set<Block> PREFERRED_BY_BEES = Sets.newHashSet();

	public PlantopiaBlockTagProvider(DataGenerator pGenerator, ExistingFileHelper existingFileHelper) {
		super(pGenerator, Plantopia.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags() {
		generateAll();

		add(IGNORED_BY_BEES, Blocks.WITHER_ROSE);

		saveAll();
	}

	private void add(@NotNull Set<Block> tag, Block... blocks) {
		tag.addAll(List.of(blocks));
	}

	private void generateAll() {
		PlantopiaMetaStore.getBlocks().forEach(blockMeta -> {
			Block block = blockMeta.getBlock();
			MetaType type = blockMeta.getType();

			if(block instanceof DoublePlantBlock) {
				REPLACEABLE_PLANTS.add(block);
				if(type == MetaType.FLOWER) TALL_FLOWERS.add(block);
				else if(type == MetaType.PLANT) MINEABLE_WITH_AXE.add(block);
			}

			if(blockMeta.isIgnoredByBees()) IGNORED_BY_BEES.add(block);
			if(blockMeta.isPreferredByBees()) PREFERRED_BY_BEES.add(block);
		});
	}

	private void saveAll() {
		if(!REPLACEABLE_PLANTS.isEmpty()) tag(BlockTags.REPLACEABLE_PLANTS).add(REPLACEABLE_PLANTS.toArray(Block[]::new));
		if(!TALL_FLOWERS.isEmpty()) tag(BlockTags.TALL_FLOWERS).add(TALL_FLOWERS.toArray(Block[]::new));
		if(!MINEABLE_WITH_AXE.isEmpty()) tag(BlockTags.MINEABLE_WITH_AXE).add(MINEABLE_WITH_AXE.toArray(Block[]::new));
		if(!IGNORED_BY_BEES.isEmpty()) tag(PlantopiaBlockTags.IGNORED_BY_BEES).add(IGNORED_BY_BEES.toArray(Block[]::new));
		if(!PREFERRED_BY_BEES.isEmpty()) tag(PlantopiaBlockTags.PREFERRED_BY_BEES).add(PREFERRED_BY_BEES.toArray(Block[]::new));
	}
}