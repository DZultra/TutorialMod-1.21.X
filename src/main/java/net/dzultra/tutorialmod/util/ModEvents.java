package net.dzultra.tutorialmod.util;

import net.dzultra.tutorialmod.TutorialMod;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;

public class ModEvents {
    public static void registerEvents() {
        PlayerBlockBreakEvents.BEFORE.register(new HammerUsageEvent());
        AttackEntityCallback.EVENT.register(new SheepHitEvent());



        TutorialMod.LOGGER.info("Registering Mod Events for " + TutorialMod.MOD_ID);
    }
}
