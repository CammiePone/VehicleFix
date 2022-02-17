package dev.cammiescorner.vehiclefix;

import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.entity.EntityType;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class EntityTags {
	public static final Tag<EntityType<?>> AFFECTS = TagFactory.ENTITY_TYPE.create(new Identifier("vehiclefix", "affects"));
}
