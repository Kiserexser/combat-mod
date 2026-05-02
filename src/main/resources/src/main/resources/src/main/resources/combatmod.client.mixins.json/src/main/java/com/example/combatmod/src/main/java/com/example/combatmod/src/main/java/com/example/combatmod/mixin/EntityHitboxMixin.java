package com.example.combatmod.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityHitboxMixin {

    private static final float HITBOX_SCALE = 1.8f;

    @Inject(
        method = "getDimensions",
        at = @At("RETURN"),
        cancellable = true
    )
    private void scaleHitbox(EntityPose pose, CallbackInfoReturnable<EntityDimensions> cir) {
        EntityDimensions original = cir.getReturnValue();
        cir.setReturnValue(original.scaled(HITBOX_SCALE));
    }
}