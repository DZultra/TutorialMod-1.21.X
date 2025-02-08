package net.dzultra.tutorialmod;

import net.dzultra.tutorialmod.block.ModBlocks;
import net.dzultra.tutorialmod.component.ModDataComponentTypes;
import net.dzultra.tutorialmod.item.ModItemGroups;
import net.dzultra.tutorialmod.item.ModItems;
import net.dzultra.tutorialmod.sound.ModSounds;
import net.dzultra.tutorialmod.util.HammerUsageEvent;
import net.dzultra.tutorialmod.util.SheepHitEvent;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.registry.FuelRegistry;
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

		FuelRegistry.INSTANCE.add(ModItems.STARLIGHT_ASHES, 600);

		PlayerBlockBreakEvents.BEFORE.register(new HammerUsageEvent());

		AttackEntityCallback.EVENT.register(new SheepHitEvent());
	}
}