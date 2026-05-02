package com.example.combatmod.render;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

import java.util.List;

public class EnemyCircleRenderer {

    private static final int SEGMENTS = 32;
    private static final float RADIUS = 0.6f;
    private static final float Y_OFFSET = 0.02f;

    public static void onWorldRenderEnd(WorldRenderContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null || client.player == null) return;

        List<HostileEntity> hostiles = client.world.getEntitiesByClass(
            HostileEntity.class,
            client.player.getBoundingBox().expand(64),
            e -> true
        );

        MatrixStack matrices = context.matrixStack();
        VertexConsumerProvider consumers = context.consumers();
        if (matrices == null || consumers == null) return;

        Vec3d cam = context.camera().getPos();

        for (HostileEntity entity : hostiles) {
            drawCircle(matrices, consumers, entity, cam);
        }
    }

    private static void drawCircle(MatrixStack matrices, VertexConsumerProvider consumers,
                                   HostileEntity entity, Vec3d cam) {
        double x = entity.getX() - cam.x;
        double y = entity.getY() - cam.y + Y_OFFSET;
        double z = entity.getZ() - cam.z;

        matrices.push();
        matrices.translate(x, y, z);

        VertexConsumer consumer = consumers.getBuffer(RenderLayer.getLines());
        Matrix4f matrix = matrices.peek().getPositionMatrix();

        for (int i = 0; i < SEGMENTS; i++) {
            float angle1 = (float) (2 * Math.PI * i / SEGMENTS);
            float angle2 = (float) (2 * Math.PI * (i + 1) / SEGMENTS);

            float x1 = RADIUS * (float) Math.cos(angle1);
            float z1 = RADIUS * (float) Math.sin(angle1);
            float x2 = RADIUS * (float) Math.cos(angle2);
            float z2 = RADIUS * (float) Math.sin(angle2);

            consumer.vertex(matrix, x1, 0, z1).color(255, 0, 0, 200).normal(0, 1, 0).next();
            consumer.vertex(matrix, x2, 0, z2).color(255, 0, 0, 200).normal(0, 1, 0).next();
        }

        matrices.pop();
    }
}