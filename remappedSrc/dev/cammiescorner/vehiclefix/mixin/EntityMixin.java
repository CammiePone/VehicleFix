package dev.cammiescorner.vehiclefix.mixin;

import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(Entity.class)
public abstract class EntityMixin
{
	@Shadow	private Box entityBounds;
	@Shadow @Final private EntityType<?> type;

	@Shadow public abstract boolean hasPassengers();
	@Shadow public abstract List<Entity> getPassengerList();

	private static final Tag<EntityType<?>> AFFECTS = TagRegistry.entityType(new Identifier("vehiclefix", "fixed_collision"));

	@Redirect(method = "adjustMovementForCollisions(Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getBoundingBox()Lnet/minecraft/util/math/Box;"))
	Box getBoundingBox(Entity entity)
	{
		Box box = entityBounds;

		if(hasPassengers() && (AFFECTS.values().isEmpty() || AFFECTS.contains(type)))
		{
			for(Entity passenger : getPassengerList())
			{
				box = new Box(Math.min(box.minX, passenger.getBoundingBox().minX), box.minY, Math.min(box.minZ, passenger.getBoundingBox().minZ), Math.max(box.maxX, passenger.getBoundingBox().maxX), Math.max(box.maxY, passenger.getBoundingBox().maxY), Math.max(box.maxZ, passenger.getBoundingBox().maxZ));
			}
		}

		return box;
	}
}
