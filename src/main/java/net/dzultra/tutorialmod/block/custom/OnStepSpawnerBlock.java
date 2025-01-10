package net.dzultra.tutorialmod.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class OnStepSpawnerBlock extends Block {
    public OnStepSpawnerBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!(world instanceof ServerWorld serverWorld)) return;
        if (!(entity instanceof PlayerEntity)) return;

        net.minecraft.util.math.random.Random random = serverWorld.getRandom();

        int xOffset = random.nextInt(11) - 5; // Bereich: -5 bis 5
        int zOffset = random.nextInt(11) - 5;
        BlockPos spawnPos = pos.add(xOffset, 0, zOffset);

        BlockPos groundPos = serverWorld.getTopPosition(net.minecraft.world.Heightmap.Type.WORLD_SURFACE, spawnPos);
        if (!serverWorld.getBlockState(groundPos).isAir()) {
            spawnPos = groundPos.up();
        }

        ZombieEntity zombie = new ZombieEntity(serverWorld);
        zombie.refreshPositionAndAngles(Vec3d.ofCenter(spawnPos), random.nextFloat() * 360F, 0);
        serverWorld.spawnEntity(zombie);
    }
}
