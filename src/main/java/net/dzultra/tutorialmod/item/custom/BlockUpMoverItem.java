package net.dzultra.tutorialmod.item.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockUpMoverItem extends Item {
    public BlockUpMoverItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);

        if (!world.isClient && blockState.isOf(Blocks.DIAMOND_BLOCK)) {
            BlockPos newBlockPos;

            if (world.isReceivingRedstonePower(blockPos)) {
                newBlockPos = blockPos.down();
            } else {
                newBlockPos = blockPos.up();
            }

            if (world.getBlockState(newBlockPos).isAir()) {
                world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
                world.setBlockState(newBlockPos, blockState);
                return ActionResult.SUCCESS;
            } else {
                return ActionResult.FAIL;
            }
        }
        return ActionResult.FAIL;
    }
}
