package net.dzultra.tutorialmod.util;

import net.dzultra.tutorialmod.block.ModBlocks;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;

public class ModStrippableBlocks {
    public static void registerStrippableBlocks() {
        StrippableBlockRegistry.register(ModBlocks.DRIFTWOOD_LOG, ModBlocks.STRIPPED_DRIFTWOOD_LOG);
        StrippableBlockRegistry.register(ModBlocks.DRIFTWOOD_WOOD, ModBlocks.STRIPPED_DRIFTWOOD_WOOD);
    }
}
