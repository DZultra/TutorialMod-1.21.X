package net.dzultra.tutorialmod.fluid;

import net.dzultra.tutorialmod.TutorialMod;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModFluids {
    public static FlowableFluid STILL_ACID = Registry.register(
            Registries.FLUID, Identifier.of(TutorialMod.MOD_ID, "acid"), new AcidFluid.Still());

    public static FlowableFluid FLOWING_ACID= Registry.register(
            Registries.FLUID, Identifier.of(TutorialMod.MOD_ID, "flowing_acid"), new AcidFluid.Flowing());

    public static Item ACID_BUCKET = Registry.register(
            Registries.ITEM, Identifier.of(TutorialMod.MOD_ID, "acid_bucket"),
                new BucketItem(STILL_ACID, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1)));

    public static Block ACID = Registry.register(
            Registries.BLOCK, Identifier.of(TutorialMod.MOD_ID, "acid"), new FluidBlock(STILL_ACID, AbstractBlock.Settings.copy(Blocks.WATER)){});


    public static void registerModFluids() {

        TutorialMod.LOGGER.info("Registering Mod Fluids for " + TutorialMod.MOD_ID);
    }
}
