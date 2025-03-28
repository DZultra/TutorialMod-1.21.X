package net.dzultra.tutorialmod.item.custom;

import net.dzultra.tutorialmod.block.ModBlocks;
import net.dzultra.tutorialmod.component.ModDataComponentTypes;
import net.dzultra.tutorialmod.particle.ModParticles;
import net.dzultra.tutorialmod.sound.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;

public class ChiselItem extends Item {
    private static final Map<Block, Block> CHISEL_MAP =
            Map.of(
                    Blocks.STONE, Blocks.STONE_BRICKS,
                    Blocks.END_STONE, Blocks.END_STONE_BRICKS,
                    Blocks.AMETHYST_BLOCK, ModBlocks.PINK_GARNET_BLOCK,
                    ModBlocks.PINK_GARNET_BLOCK, Blocks.AMETHYST_BLOCK,
                    Blocks.GOLD_BLOCK, Blocks.NETHERITE_BLOCK
            );

    public ChiselItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        Block clickedBlock = world.getBlockState(context.getBlockPos()).getBlock();

        if(CHISEL_MAP.containsKey(clickedBlock)) {
            if(!world.isClient()) {
                world.setBlockState(context.getBlockPos(), CHISEL_MAP.get(clickedBlock).getDefaultState());

                //var slot = context.getHand() == Hand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
                //context.getStack().damage(1, context.getPlayer(),slot);

                context.getStack().damage(1, ((ServerWorld) world), ((ServerPlayerEntity) context.getPlayer()),
                        item -> context.getPlayer().sendEquipmentBreakStatus(item, EquipmentSlot.MAINHAND));

                world.playSound(null, context.getBlockPos(), ModSounds.CHISEL_USE, SoundCategory.BLOCKS);

                ((ServerWorld) world).spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, clickedBlock.getDefaultState()),
                        context.getBlockPos().getX() + 0.5, context.getBlockPos().getY() + 1.0,
                        context.getBlockPos().getZ() + 0.5, 5, 0, 0, 0, 1);

                ((ServerWorld) world).spawnParticles(ParticleTypes.FLAME,
                        context.getBlockPos().getX() + 0.5, context.getBlockPos().getY() + 0.5,
                        context.getBlockPos().getZ() + 0.5, 10, 0, 0, 0, 0.1);

                ((ServerWorld) world).spawnParticles(ModParticles.PINK_GARNET_PARTICLE,
                        context.getBlockPos().getX() + 0.5, context.getBlockPos().getY() + 0.5,
                        context.getBlockPos().getZ() + 0.5, 8, 0, 0, 0, 1);

                context.getStack().set(ModDataComponentTypes.COORDINATES, context.getBlockPos());
            }
        } else {
            context.getStack().set(ModDataComponentTypes.COORDINATES, null);
            return ActionResult.FAIL;
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if(Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("tooltip.tutorialmod.chisel.shift_down"));
        } else {
            tooltip.add(Text.translatable("tooltip.tutorialmod.chisel"));
        }

        BlockPos coordinates = stack.get(ModDataComponentTypes.COORDINATES);

        if(stack.get(ModDataComponentTypes.COORDINATES) != null) {
            tooltip.add(Text.translatable("tooltip.tutorialmod.chisel.component.coordinates")
                    .append(Text.literal(" X: "))
                    .append(Text.literal(String.valueOf(coordinates.getX())))
                    .append(Text.literal(" Y: "))
                    .append(Text.literal(String.valueOf(coordinates.getY())))
                    .append(Text.literal(" Z: "))
                    .append(Text.literal(String.valueOf(coordinates.getZ())))
            );
        }

        super.appendTooltip(stack, context, tooltip, type);
    }
}