package com.example.combatmod.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientReachMixin {

    private static final double REACH_BLOCKS = 3.0;

    @Shadow @Final private MinecraftClient client;

    @Inject(method = "attackEntity", at = @At("HEAD"), cancellable = true)
    private void enforceReach(PlayerEntity player, Entity target, CallbackInfo ci) {
        if (client.player == null) return;
        Vec3d eyes = client.player.getEyePos();
        Box targetBox = target.getBoundingBox();
        double closestX = clamp(eyes.x, targetBox.minX, targetBox.maxX);
        double closestY = clamp(eyes.y, targetBox.minY, targetBox.maxY);
        double closestZ = clamp(eyes.z, targetBox.minZ, targetBox.maxZ);
        double distSq = eyes.squaredDistanceTo(closestX, closestY, closestZ);
        if (distSq > REACH_BLOCKS * REACH_