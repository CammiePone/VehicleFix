package dev.cammiescorner.vehiclefix;

import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockTags {
	public static final TagKey<Block> PASSABLE = TagKey.of(Registry.BLOCK_KEY, new Identifier("vehiclefix", "passable"));
}
