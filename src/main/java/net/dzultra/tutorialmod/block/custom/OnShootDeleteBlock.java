package net.dzultra.tutorialmod.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class OnShootDeleteBlock extends Block {
    public OnShootDeleteBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (world.isClient) return;

        BlockPos blockPos = hit.getBlockPos();

        Block.dropStack(world, blockPos, this.asItem().getDefaultStack());
        world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
    }
}
