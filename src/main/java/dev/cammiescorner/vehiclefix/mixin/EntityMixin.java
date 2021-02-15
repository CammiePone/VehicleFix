package dev.cammiescorner.vehiclefix.mixin;

import dev.cammiescorner.vehiclefix.VehicleFix;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Entity.class)
public abstract class EntityMixin
{
	@Shadow	private Box entityBounds;
	@Shadow @Final private EntityType<?> type;

	@Shadow	public abstract boolean hasPassengers();
	@Shadow	public abstract List<Entity> getPassengerList();

	private static final Tag<EntityType<?>> AFFECTS = TagRegistry.entityType(new Identifier(VehicleFix.MOD_ID, "fixed_collision"));

	@Inject(method = "adjustMovementForCollisions(Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/entity/Entity;getBoundingBox()Lnet/minecraft/util/math/Box;"), locals = LocalCapture.CAPTURE_FAILSOFT)
	public void adjustMovementForCollisions(Vec3d movement, CallbackInfoReturnable<Vec3d> info, Box box)
	{
		if(hasPassengers() && (AFFECTS.values().isEmpty() || AFFECTS.contains(type)))
		{
			for(Entity passenger : getPassengerList())
			{
				box = new Box(Math.min(box.minX, passenger.getBoundingBox().minX), box.minY, Math.min(box.minZ, passenger.getBoundingBox().minZ), Math.max(box.maxX, passenger.getBoundingBox().maxX), Math.max(box.maxY, passenger.getBoundingBox().maxY), Math.max(box.maxZ, passenger.getBoundingBox().maxZ));
			}
		}

	}
}
