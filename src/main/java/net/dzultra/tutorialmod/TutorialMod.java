package net.dzultra.tutorialmod;

import net.dzultra.tutorialmod.block.ModBlocks;
import net.dzultra.tutorialmod.component.ModDataComponentTypes;
import net.dzultra.tutorialmod.effect.ModEffects;
import net.dzultra.tutorialmod.enchantment.ModEnchantmentEffects;
import net.dzultra.tutorialmod.entity.ModEntities;
import net.dzultra.tutorialmod.entity.custom.MantisEntity;
import net.dzultra.tutorialmod.item.ModItemGroups;
import net.dzultra.tutorialmod.item.ModItems;
import net.dzultra.tutorialmod.potion.ModPotions;
import net.dzultra.tutorialmod.sound.ModSounds;
import net.dzultra.tutorialmod.util.*;
import net.dzultra.tutorialmod.villager.ModVillagerTrades;
import net.dzultra.tutorialmod.villager.ModVillagers;
import net.dzultra.tutorialmod.world.gen.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Very important comment
public class TutorialMod implements ModInitializer {
	public static final String MOD_ID = "tutorialmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModItemGroups.registerItemGroups();
		ModDataComponentTypes.registerDataComponentTypes();
		ModSounds.registerSounds();
		ModEffects.registerEffects();
		ModPotions.registerPotions();
		ModEvents.registerEvents();
		ModEnchantmentEffects.registerEnchantmentEffects();
		ModCrops.registerCrops();
		ModWorldGeneration.generateModWorldGen();
		ModFlammableBlocks.registerFlammableBlocks();
		ModStrippableBlocks.registerStrippableBlocks();
		ModEntities.registerModEntities();
		MantisEntity.registerMantisAttributes();
		ModVillagers.registerVillagers();
		ModVillagerTrades.registerVillagerTrades();
	}
}