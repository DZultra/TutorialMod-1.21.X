package net.dzultra.tutorialmod;

import net.dzultra.tutorialmod.block.ModBlocks;
import net.dzultra.tutorialmod.block.entity.ModBlockEntities;
import net.dzultra.tutorialmod.block.entity.custom.PedestalBlockEntity;
import net.dzultra.tutorialmod.block.entity.renderer.PedestalBlockEntityRenderer;
import net.dzultra.tutorialmod.entity.ModEntities;
import net.dzultra.tutorialmod.entity.client.*;
import net.dzultra.tutorialmod.networking.payloads.SyncPedestalBlockEntityS2CPayload;
import net.dzultra.tutorialmod.particle.ModParticles;
import net.dzultra.tutorialmod.particle.PinkGarnetParticle;
import net.dzultra.tutorialmod.screen.ModScreenHandlers;
import net.dzultra.tutorialmod.screen.custom.GrowthChamberScreen;
import net.dzultra.tutorialmod.screen.custom.PedestalScreen;
import net.dzultra.tutorialmod.util.ModKeyBinds;
import net.dzultra.tutorialmod.util.ModModelPredicates;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.world.ClientWorld;

public class TutorialModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PINK_GARNET_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PINK_GARNET_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CAULIFLOWER_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.HONEY_BERRY_BUSH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DRIFTWOOD_SAPLING, RenderLayer.getCutout());

        ModModelPredicates.registerModelPredicates();

        EntityModelLayerRegistry.registerModelLayer(MantisModel.MANTIS, MantisModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.MANTIS, MantisRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(TomahawkProjectileModel.TOMAHAWK, TomahawkProjectileModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.TOMAHAWK, TomahawkProjectileRenderer::new);

        EntityRendererRegistry.register(ModEntities.CHAIR, ChairRenderer::new);

        ParticleFactoryRegistry.getInstance().register(ModParticles.PINK_GARNET_PARTICLE, PinkGarnetParticle.Factory::new);
        BlockEntityRendererFactories.register(ModBlockEntities.PEDESTAL_BE, PedestalBlockEntityRenderer::new);
        HandledScreens.register(ModScreenHandlers.PEDESTAL_SCREEN_HANDLER, PedestalScreen::new);

        HandledScreens.register(ModScreenHandlers.GROWTH_CHAMBER_SCREEN_HANDLER, GrowthChamberScreen::new);

        ModKeyBinds.registerModKeyBinds();

        ClientPlayNetworking.registerGlobalReceiver(SyncPedestalBlockEntityS2CPayload.ID, (payload, context) -> {
            ClientWorld world = context.client().world;

            if (world == null) {
                return; // Ensure the world is not null
            }

            // Retrieve the BlockEntity at the specified BlockPos
            BlockEntity blockEntity = world.getBlockEntity(payload.blockPos());
            if (blockEntity instanceof PedestalBlockEntity pedestalBlockEntity) {
                // Update the BlockEntity's inventory with the payload data
                pedestalBlockEntity.setStack(0, payload.inventory().getFirst());

                // Mark the BlockEntity for rerendering (optional, if needed)
                world.updateListeners(payload.blockPos(), blockEntity.getCachedState(), blockEntity.getCachedState(), Block.NOTIFY_ALL);
            }
        });
    }
}