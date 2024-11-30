package net.dzultra.tutorialmod.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class KaboomBlock extends Block {
    public KaboomBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!(world instanceof ServerWorld serverWorld)) return;
        if (entity.isSneaking()) {
            serverWorld.createExplosion(null, pos.getX(), pos.getY()+1, pos.getZ(), 5f, World.ExplosionSourceType.BLOCK);
        }
    }
}
