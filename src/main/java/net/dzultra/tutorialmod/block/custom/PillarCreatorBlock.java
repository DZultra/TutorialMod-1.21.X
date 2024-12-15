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
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

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

        Block PillarBlock = Blocks.GOLD_BLOCK;

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

    public void createPillar(ServerWorld serverWorld, World world, BlockPos blockPos, BlockState blockState, Block CreatedPillarBlock) {
        BlockPos abovePos1 = blockPos.up(1);
        BlockPos abovePos2 = blockPos.up(2);
        BlockPos abovePos3 = blockPos.up(3);
        BlockPos abovePos4 = blockPos.up(4);
        BlockPos abovePos5 = blockPos.up(5);

        if (!world.getBlockState(abovePos1).isAir() || !world.getBlockState(abovePos2).isAir() || !world.getBlockState(abovePos3).isAir() || !world.getBlockState(abovePos4).isAir() || !world.getBlockState(abovePos5).isAir()) {return;}

        // Placing
        Executors.newSingleThreadScheduledExecutor().schedule(() -> {
            serverWorld.getServer().execute(() -> {
                placePillarBlock(serverWorld, abovePos1, blockState);

                Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                    serverWorld.getServer().execute(() -> {
                        placePillarBlock(serverWorld, abovePos2, blockState);

                        Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                            serverWorld.getServer().execute(() -> {
                                placePillarBlock(serverWorld, abovePos3, blockState);

                                Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                                    serverWorld.getServer().execute(() -> {
                                        placePillarBlock(serverWorld, abovePos4, blockState);

                                        Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                                            serverWorld.getServer().execute(() -> {
                                                placePillarBlock(serverWorld, abovePos5, blockState);

                                                 // Clearing
                                                Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                                                    serverWorld.getServer().execute(() -> {
                                                        clearPillarBlock(serverWorld, world, abovePos5, CreatedPillarBlock);

                                                        Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                                                            serverWorld.getServer().execute(() -> {
                                                                clearPillarBlock(serverWorld, world, abovePos4, CreatedPillarBlock);

                                                                Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                                                                    serverWorld.getServer().execute(() -> {
                                                                        clearPillarBlock(serverWorld, world, abovePos3, CreatedPillarBlock);

                                                                        Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                                                                            serverWorld.getServer().execute(() -> {
                                                                                clearPillarBlock(serverWorld, world, abovePos2, CreatedPillarBlock);

                                                                                Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                                                                                    serverWorld.getServer().execute(() -> {
                                                                                        clearPillarBlock(serverWorld, world, abovePos1, CreatedPillarBlock);

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

    @Override
    public void onBroken(WorldAccess world, BlockPos blockPos, BlockState state) {
        if (!(world instanceof ServerWorld serverWorld)) return;

        for (int i = 1; i <= 5; i++) {
            serverWorld.setBlockState(blockPos.up(i), Blocks.AIR.getDefaultState());
        }
    }

    public void placePillarBlock(ServerWorld serverWorld, BlockPos abovePosX, BlockState blockState) {
        serverWorld.setBlockState(abovePosX, blockState);
    }

    public void clearPillarBlock(ServerWorld serverWorld, World world, BlockPos abovePosX, Block CreatedPillarBlock) {
        if (!world.getBlockState(abovePosX).isOf(CreatedPillarBlock)) {return;}
        serverWorld.setBlockState(abovePosX, Blocks.AIR.getDefaultState());
    }
}
