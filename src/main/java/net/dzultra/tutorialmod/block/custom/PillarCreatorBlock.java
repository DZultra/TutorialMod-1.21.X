package net.dzultra.tutorialmod.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PillarCreatorBlock extends Block {
    public PillarCreatorBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onSteppedOn(World world, BlockPos blockPos, BlockState blockState, Entity entity) {
        if (!(world instanceof ServerWorld serverWorld)) return;
        if (entity instanceof ItemEntity) return;

        Block PillarBlock = Blocks.RAW_GOLD_BLOCK;

        entity.addVelocity(0, 1.05233, 0);
        entity.velocityModified = true;

        createPillar(serverWorld, world, blockPos, PillarBlock.getDefaultState(), PillarBlock);
    }

    @Override
    protected ActionResult onUse(BlockState blockState, World world, BlockPos blockPos, PlayerEntity player, BlockHitResult hit) {
        if (!(world instanceof ServerWorld serverWorld)) return ActionResult.FAIL;

        Block PillarBlock = Blocks.RAW_GOLD_BLOCK;

        createPillar(serverWorld, world, blockPos, PillarBlock.getDefaultState(), PillarBlock);

        return ActionResult.SUCCESS;
    }

    public void createPillar(ServerWorld serverWorld, World world, BlockPos blockPos, BlockState blockState, Block PillarBlock) {
        BlockPos abovePos1 = blockPos.up(1);
        BlockPos abovePos2 = blockPos.up(2);
        BlockPos abovePos3 = blockPos.up(3);
        BlockPos abovePos4 = blockPos.up(4);
        BlockPos abovePos5 = blockPos.up(5);

        if (!world.getBlockState(abovePos1).isAir()) {return;}
        Executors.newSingleThreadScheduledExecutor().schedule(() -> {
            serverWorld.getServer().execute(() -> {
                if (world.getBlockState(abovePos1).isAir()) {return;}
                // -- Above Block 1 changed to Gold Block
                serverWorld.setBlockState(abovePos1, blockState);
                // --
                Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                    serverWorld.getServer().execute(() -> {
                        if (!world.getBlockState(abovePos2).isAir()) {return;}
                        // -- Above Block 2 changed to Gold Block
                        serverWorld.setBlockState(abovePos2, blockState);
                        // --
                        Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                            serverWorld.getServer().execute(() -> {
                                if (!world.getBlockState(abovePos3).isAir()) {return;}
                                // -- Above Block 3 changed to Gold Block
                                serverWorld.setBlockState(abovePos3, blockState);
                                // --
                                Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                                    serverWorld.getServer().execute(() -> {
                                        if (!world.getBlockState(abovePos4).isAir()) {return;}
                                        // -- Above Block 4 changed to Gold Block
                                        serverWorld.setBlockState(abovePos4, blockState);
                                        // --
                                        Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                                            serverWorld.getServer().execute(() -> {
                                                if (!world.getBlockState(abovePos5).isAir()) {return;}
                                                // -- Above Block 5 changed to Gold Block
                                                serverWorld.setBlockState(abovePos5, blockState);
                                                // --
                                                Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                                                    serverWorld.getServer().execute(() -> {
                                                        if (!world.getBlockState(abovePos5).isOf(PillarBlock)) {return;}
                                                        // -- Above Block 5 getting cleared
                                                        serverWorld.setBlockState(abovePos5, Blocks.AIR.getDefaultState());
                                                        // --
                                                        Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                                                            serverWorld.getServer().execute(() -> {
                                                                if (!world.getBlockState(abovePos4).isOf(PillarBlock)) {return;}
                                                                // -- Above Block 4 getting cleared
                                                                serverWorld.setBlockState(abovePos4, Blocks.AIR.getDefaultState());
                                                                // --
                                                                Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                                                                    serverWorld.getServer().execute(() -> {
                                                                        if (!world.getBlockState(abovePos3).isOf(PillarBlock)) {return;}
                                                                        // -- Above Block 3 getting cleared
                                                                        serverWorld.setBlockState(abovePos3, Blocks.AIR.getDefaultState());
                                                                        // --
                                                                        Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                                                                            serverWorld.getServer().execute(() -> {
                                                                                if (!world.getBlockState(abovePos2).isOf(PillarBlock)) {return;}
                                                                                // -- Above Block 2 getting cleared
                                                                                serverWorld.setBlockState(abovePos2, Blocks.AIR.getDefaultState());
                                                                                // --
                                                                                Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                                                                                    serverWorld.getServer().execute(() -> {
                                                                                        if (!world.getBlockState(abovePos1).isOf(PillarBlock)) {return;}
                                                                                        // -- Above Block 1 getting cleared
                                                                                        serverWorld.setBlockState(abovePos1, Blocks.AIR.getDefaultState());
                                                                                        // --
                                                                                    });
                                                                                }, 100, TimeUnit.MILLISECONDS);
                                                                            });
                                                                        }, 100, TimeUnit.MILLISECONDS);
                                                                    });
                                                                }, 100, TimeUnit.MILLISECONDS);
                                                            });
                                                        }, 100, TimeUnit.MILLISECONDS);
                                                    });
                                                }, 300, TimeUnit.MILLISECONDS);
                                            });
                                        }, 60, TimeUnit.MILLISECONDS);
                                    });
                                }, 70, TimeUnit.MILLISECONDS);
                            });
                        }, 80, TimeUnit.MILLISECONDS);
                    });
                }, 90, TimeUnit.MILLISECONDS);
            });
        }, 100, TimeUnit.MILLISECONDS);
    }
}
