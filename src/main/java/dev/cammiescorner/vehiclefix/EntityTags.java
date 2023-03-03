package dev.cammiescorner.vehiclefix;

import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class EntityTags {
	public static final TagKey<EntityType<?>> AFFECTS = TagKey.of(Registries.ENTITY_TYPE.getKey(), new Identifier("vehiclefix", "affects"));

	public static boolean affectsIsEmpty() {
		Optional<RegistryEntryList.Named<EntityType<?>>> tag = Registries.ENTITY_TYPE.getEntryList(EntityTags.AFFECTS);

		return tag.isEmpty() || tag.get().size() == 0;
	}
}
