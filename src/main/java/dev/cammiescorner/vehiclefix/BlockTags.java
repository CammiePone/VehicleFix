package dev.cammiescorner.vehiclefix;

import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class BlockTags {
	public static final TagKey<Block> PASSABLE = TagKey.of(Registries.BLOCK.getKey(), new Identifier("vehiclefix", "passable"));
}
