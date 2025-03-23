package net.dzultra.tutorialmod.networking;

import net.dzultra.tutorialmod.TutorialMod;
import net.dzultra.tutorialmod.networking.payloads.SyncPedestalBlockEntityS2CPayload;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class ModPayloads {
    public static void registerModPayloads() {
        PayloadTypeRegistry.playS2C().register(SyncPedestalBlockEntityS2CPayload.ID, SyncPedestalBlockEntityS2CPayload.CODEC);
        TutorialMod.LOGGER.info("Registering Mod Payloads for " + TutorialMod.MOD_ID);
    }
}
