package net.dzultra.tutorialmod.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.server.world.ServerWorld;
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
        entity.addVelocity(0, 1.05233, 0);
        entity.velocityModified = true;

        createPillar(serverWorld, world, blockPos, Blocks.GOLD_BLOCK.getDefaultState(), Blocks.GOLD_BLOCK);
    }

    public void createPillar(ServerWorld serverWorld, World world, BlockPos blockPos, BlockState blockState, Block block) {
        BlockPos abovePos1 = blockPos.up(1);
        BlockPos abovePos2 = blockPos.up(2);
        BlockPos abovePos3 = blockPos.up(3);
        BlockPos abovePos4 = blockPos.up(4);
        BlockPos abovePos5 = blockPos.up(5);

        if (world.getBlockState(abovePos1).isAir()) {
            Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                serverWorld.getServer().execute(() -> {
                    if (world.getBlockState(abovePos1).isAir()) {
                        // --
                        serverWorld.setBlockState(abovePos1, blockState);
                        // --
                        Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                            serverWorld.getServer().execute(() -> {
                                if (world.getBlockState(abovePos2).isAir()) {
                                    // --
                                    serverWorld.setBlockState(abovePos2, blockState);
                                    // --
                                    Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                                        serverWorld.getServer().execute(() -> {
                                            if (world.getBlockState(abovePos3).isAir()) {
                                                // --
                                                serverWorld.setBlockState(abovePos3, blockState);
                                                // --
                                                Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                                                    serverWorld.getServer().execute(() -> {
                                                        if (world.getBlockState(abovePos4).isAir()) {
                                                            // --
                                                            serverWorld.setBlockState(abovePos4, blockState);
                                                            // --
                                                            Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                                                                serverWorld.getServer().execute(() -> {
                                                                    if (world.getBlockState(abovePos5).isAir()) {
                                                                        // --
                                                                        serverWorld.setBlockState(abovePos5, blockState);
                                                                        // --
                                                                        // --
                                                                        Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                                                                            serverWorld.getServer().execute(() -> {
                                                                                if (world.getBlockState(abovePos5).isOf(block)) {
                                                                                    // --
                                                                                    serverWorld.setBlockState(abovePos5, Blocks.AIR.getDefaultState());
                                                                                    // --
                                                                                    Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                                                                                        serverWorld.getServer().execute(() -> {
                                                                                            if (world.getBlockState(abovePos4).isOf(block)) {
                                                                                                // --
                                                                                                serverWorld.setBlockState(abovePos4, Blocks.AIR.getDefaultState());
                                                                                                // --
                                                                                                Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                                                                                                    serverWorld.getServer().execute(() -> {
                                                                                                        if (world.getBlockState(abovePos3).isOf(block)) {
                                                                                                            // --
                                                                                                            serverWorld.setBlockState(abovePos3, Blocks.AIR.getDefaultState());
                                                                                                            // --
                                                                                                            Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                                                                                                                serverWorld.getServer().execute(() -> {
                                                                                                                    if (world.getBlockState(abovePos2).isOf(block)) {
                                                                                                                        // --
                                                                                                                        serverWorld.setBlockState(abovePos2, Blocks.AIR.getDefaultState());
                                                                                                                        // --
                                                                                                                        Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                                                                                                                            serverWorld.getServer().execute(() -> {
                                                                                                                                if (world.getBlockState(abovePos1).isOf(block)) {
                                                                                                                                    // --
                                                                                                                                    serverWorld.setBlockState(abovePos1, Blocks.AIR.getDefaultState());
                                                                                                                                    // --
                                                                                                                                }
                                                                                                                            });
                                                                                                                        }, 100, TimeUnit.MILLISECONDS);
                                                                                                                    }
                                                                                                                });
                                                                                                            }, 100, TimeUnit.MILLISECONDS);
                                                                                                        }
                                                                                                    });
                                                                                                }, 100, TimeUnit.MILLISECONDS);
                                                                                            }
                                                                                        });
                                                                                    }, 100, TimeUnit.MILLISECONDS);
                                                                                }
                                                                            });
                                                                        }, 300, TimeUnit.MILLISECONDS);
                                                                        // --
                                                                    }
                                                                });
                                                            }, 60, TimeUnit.MILLISECONDS);
                                                        }
                                                    });
                                                }, 70, TimeUnit.MILLISECONDS);
                                            }
                                        });
                                    }, 80, TimeUnit.MILLISECONDS);
                                }
                            });
                        }, 90, TimeUnit.MILLISECONDS);
                    }
                });
            }, 100, TimeUnit.MILLISECONDS);
        }
    }
}
