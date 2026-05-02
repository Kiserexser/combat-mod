package com.example.combatmod.mixin;

import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerInteractionManager.class)
public class ServerReachMixin {

    private static final double REACH_BLOCKS = 3.0;

    @Shadow protected ServerPlayerEntity player;

    @Inject(method = "processEntityAction", at = @At("HEAD"), cancellable = true)
    private void enforceReach(Entity target, CallbackInfo ci) {
        Vec3d eyes = player.getEyePos();
        Box targetBox = target.getBoundingBox();
        double closestX = clamp(eyes.x, targetBox.minX, targetBox.maxX);
        double closestY = clamp(eyes.y, targetBox.minY, targetBox.maxY);
        double closestZ = clamp(eyes.z, targetBox.minZ, targetBox.maxZ);
        double distSq = eyes.squaredDistanceTo(closestX, closestY, closestZ);
        if (distSq > REACH_BLOCKS * REACH_BLOCKS) {
            ci.cancel();
        }
    }

    private static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }
}