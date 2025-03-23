package net.dzultra.tutorialmod.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class OnStepSpawnerBlock extends Block {
    public static final BooleanProperty PLAYER_ON_BLOCK = BooleanProperty.of("player_on_block");

    public OnStepSpawnerBlock(Settings settings) {
        super(settings);
        setDefaultState(this.getDefaultState()
                .with(PLAYER_ON_BLOCK, false)
        );
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PLAYER_ON_BLOCK);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!(world instanceof ServerWorld serverWorld)) return;
        if (!(entity instanceof PlayerEntity)) return;

        serverWorld.setBlockState(pos, state.with(PLAYER_ON_BLOCK, true));
        serverWorld.scheduleBlockTick(pos, this, 10);
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        boolean playerStillOnBlock = !world.getEntitiesByClass(PlayerEntity.class, new Box(pos.up()), entity -> true).isEmpty();
        if (playerStillOnBlock) return;
        world.setBlockState(pos, state.with(PLAYER_ON_BLOCK, false));
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        super.onStateReplaced(state, world, pos, newState, moved);
        if (!(world instanceof ServerWorld serverWorld)) return;

        if (!state.isOf(newState.getBlock())) return;

        if (!state.get(PLAYER_ON_BLOCK) && newState.get(PLAYER_ON_BLOCK)) {
            spawnCreeper(serverWorld, pos);
        }
    }

    private void spawnCreeper(ServerWorld world, BlockPos pos) {
        net.minecraft.util.math.random.Random random = world.getRandom();

        int xOffset = random.nextInt(11) - 5; // Bereich: -5 bis 5
        int zOffset = random.nextInt(11) - 5;
        BlockPos spawnPos = pos.add(xOffset, 0, zOffset);

        CreeperEntity creeper = new CreeperEntity(EntityType.CREEPER, world);
        creeper.refreshPositionAndAngles(Vec3d.ofCenter(spawnPos), random.nextFloat() * 360F, 0);
        world.spawnEntity(creeper);
    }
}

