package net.dzultra.tutorialmod.util;

import net.dzultra.tutorialmod.TutorialMod;
import net.dzultra.tutorialmod.item.ModItems;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;

public class ModCrops {
    public static void registerCrops() {
        CompostingChanceRegistry.INSTANCE.add(ModItems.CAULIFLOWER, 0.5f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.CAULIFLOWER_SEEDS, 0.25f);

        TutorialMod.LOGGER.info("Registering Mod Crops for " + TutorialMod.MOD_ID);
    }
}
