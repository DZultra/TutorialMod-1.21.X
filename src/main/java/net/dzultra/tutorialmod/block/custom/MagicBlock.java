package net.dzultra.tutorialmod.block.custom;

import net.dzultra.tutorialmod.particle.ModParticles;
import net.dzultra.tutorialmod.util.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class MagicBlock extends Block {
    public MagicBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player,
                                 BlockHitResult hit) {
        world.playSound(player, pos, SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.BLOCKS, 1f, 1f);
        world.addParticle(ModParticles.PINK_GARNET_PARTICLE, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 0, 1, 0);
        return ActionResult.SUCCESS;
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if(entity instanceof ItemEntity itemEntity) {
            if(isValidItem(itemEntity.getStack())) {
                itemEntity.setStack(new ItemStack(Items.DIAMOND, itemEntity.getStack().getCount()));
            }
        } else if (entity instanceof LivingEntity livingEntity) {
            if (world.isReceivingRedstonePower(pos)) {
                livingEntity.getAttributeInstance(EntityAttributes.GENERIC_JUMP_STRENGTH).setBaseValue(1);
            } else {
                livingEntity.damage(world.getDamageSources().generic(),1);
                livingEntity.getAttributeInstance(EntityAttributes.GENERIC_JUMP_STRENGTH).setBaseValue(0.41999998688697815);
            }
        }

        super.onSteppedOn(world, pos, state, entity);
    }

    private boolean isValidItem(ItemStack stack) {
        return stack.isIn(ModTags.Items.TRANSFORMABLE_ITEMS);
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        tooltip.add(Text.translatable("tooltip.tutorialmod.magic_block.tooltip"));
        super.appendTooltip(stack, context, tooltip, options);
    }
}