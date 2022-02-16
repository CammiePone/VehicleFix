package dev.cammiescorner.vehiclefix.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(Entity.class)
public abstract class EntityMixin {
	@Shadow public abstract List<Entity> getPassengerList();
	@Shadow @Nullable public abstract Entity getVehicle();

	@ModifyVariable(method = "adjustMovementForCollisions(Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;", at = @At(value = "INVOKE_ASSIGN",
			target = "Lnet/minecraft/entity/Entity;getBoundingBox()Lnet/minecraft/util/math/Box;"
	))
	private Box getBoundingBox(Box box) {
		if(getVehicle() != null)
			return new Box(box.minX, getVehicle().getBoundingBox().minY + 0.1, box.minZ, box.maxX, box.maxY, box.maxZ);

		return box;
	}

	@Inject(method = "adjustMovementForCollisions(Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;", at = @At(value = "RETURN",
			ordinal = 1
	), cancellable = true, locals = LocalCapture.CAPTURE_FAILSOFT)
	public void vehiclefix$handleRiderCollisions(Vec3d movement, CallbackInfoReturnable<Vec3d> info, Box box, List<VoxelShape> list, Vec3d vec3d) {
		for(Entity passenger : getPassengerList()) {
			Vec3d passengerCollision = passenger.adjustMovementForCollisions(movement);

			info.setReturnValue(new Vec3d(
					vec3d.getX() > 0 ? Math.min(passengerCollision.getX(), vec3d.getX()) : vec3d.getX() < 0 ? Math.max(passengerCollision.getX(), vec3d.getX()) : vec3d.getX(),
					vec3d.getY() > 0 ? Math.min(passengerCollision.getY(), vec3d.getY()) : Math.max(passengerCollision.getY(), vec3d.getY()),
					vec3d.getZ() > 0 ? Math.min(passengerCollision.getZ(), vec3d.getZ()) : vec3d.getZ() < 0 ? Math.max(passengerCollision.getZ(), vec3d.getZ()) : vec3d.getZ()
			));
		}
	}
}
