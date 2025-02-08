package net.dzultra.tutorialmod.util;

import net.dzultra.tutorialmod.item.ModItems;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SheepHitEvent implements AttackEntityCallback {
    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, Entity entity, @Nullable EntityHitResult entityHitResult) {
        if(!(entity instanceof SheepEntity sheepEntity) || !(world instanceof ServerWorld)) return ActionResult.PASS;

        if(player.getMainHandStack().getItem() == ModItems.PINK_GARNET) {
            player.sendMessage(Text.literal("You clicked a Sheep with a Pink Garnet!"));
            player.getMainHandStack().decrement(1);
            sheepEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 600, 6));
        }

        return ActionResult.PASS;
    }
}
