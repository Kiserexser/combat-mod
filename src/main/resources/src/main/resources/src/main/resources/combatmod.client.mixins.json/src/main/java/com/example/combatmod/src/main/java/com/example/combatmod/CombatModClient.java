package com.example.combatmod;

import com.example.combatmod.render.EnemyCircleRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;

public class CombatModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        WorldRenderEvents.END.register(EnemyCircleRenderer::onWorldRenderEnd);
        CombatMod.LOGGER.info("[CombatMod] Client initialized.");
    }
}