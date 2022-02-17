package dev.cammiescorner.vehiclefix;

import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.block.Block;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class BlockTags {
	public static final Tag<Block> PASSABLE = TagFactory.BLOCK.create(new Identifier("vehiclefix", "passable"));
}
