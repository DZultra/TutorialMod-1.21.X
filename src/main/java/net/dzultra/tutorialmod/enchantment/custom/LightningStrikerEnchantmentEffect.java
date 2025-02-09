package net.dzultra.tutorialmod.enchantment.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public record LightningStrikerEnchantmentEffect() implements EnchantmentEntityEffect {
    public static final MapCodec<LightningStrikerEnchantmentEffect> CODEC = MapCodec.unit(LightningStrikerEnchantmentEffect::new);

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos) {
        BlockPos enemyPos = new BlockPos((int) pos.x, (int) pos.y, (int) pos.z);
        if(level == 1) {
            EntityType.LIGHTNING_BOLT.spawn(world, enemyPos, SpawnReason.TRIGGERED);
        }

        if(level == 2) {
            EntityType.LIGHTNING_BOLT.spawn(world, enemyPos, SpawnReason.TRIGGERED);
            EntityType.LIGHTNING_BOLT.spawn(world, enemyPos, SpawnReason.TRIGGERED);
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}