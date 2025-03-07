package net.dzultra.tutorialmod.villager;

import net.dzultra.tutorialmod.item.ModItems;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;

public class ModVillagerTrades {

    public static void registerVillagerTrades() {
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 1, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 3),
                    new ItemStack(ModItems.CAULIFLOWER, 8), 7, 2, 0.04f));

            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.DIAMOND, 9),
                    new ItemStack(ModItems.CAULIFLOWER_SEEDS, 2), 3, 4, 0.04f));
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 2, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 12),
                    new ItemStack(ModItems.HONEY_BERRIES, 5), 4, 7, 0.04f));
        });

        TradeOfferHelper.registerVillagerOffers(ModVillagers.KAUPENGER, 1, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 10),
                    new ItemStack(ModItems.CHISEL, 1), 4, 7, 0.04f));

            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 16),
                    new ItemStack(ModItems.RAW_PINK_GARNET, 1), 4, 7, 0.04f));
        });

        TradeOfferHelper.registerVillagerOffers(ModVillagers.KAUPENGER, 2, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 10),
                    new ItemStack(ModItems.CHISEL, 1), 4, 7, 0.04f));

            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(ModItems.PINK_GARNET, 16),
                    new ItemStack(ModItems.TOMAHAWK, 1), 3, 12, 0.09f));
        });

        TradeOfferHelper.registerWanderingTraderOffers(1, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 10),
                    new ItemStack(ModItems.CHISEL, 1), 4, 7, 0.04f));

            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(ModItems.PINK_GARNET, 16),
                    new ItemStack(ModItems.TOMAHAWK, 1), 3, 12, 0.09f));
        });
    }
}
