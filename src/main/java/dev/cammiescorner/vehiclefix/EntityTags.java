package dev.cammiescorner.vehiclefix;

import net.minecraft.entity.EntityType;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntryList;

import java.util.Optional;

public class EntityTags {
	public static final TagKey<EntityType<?>> AFFECTS = TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier("vehiclefix", "affects"));

	public static boolean affectsIsEmpty() {
		Optional<RegistryEntryList.Named<EntityType<?>>> tag = Registry.ENTITY_TYPE.getEntryList(EntityTags.AFFECTS);

		return tag.isEmpty() || tag.get().size() == 0;
	}
}
