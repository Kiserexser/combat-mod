package com.example.combatmod;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CombatMod implements ModInitializer {

    public static final String MOD_ID = "combatmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("[CombatMod] Initialized.");
    }
}