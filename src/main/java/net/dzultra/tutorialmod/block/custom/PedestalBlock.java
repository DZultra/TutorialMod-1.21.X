package net.dzultra.tutorialmod.block.custom;

import com.mojang.serialization.MapCodec;
import net.dzultra.tutorialmod.TutorialMod;
import net.dzultra.tutorialmod.block.entity.ModBlockEntities;
import net.dzultra.tutorialmod.block.entity.custom.PedestalBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerSyncHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PedestalBlock extends BlockWithEntity implements BlockEntityProvider {
    private static final VoxelShape SHAPE =
            Block.createCuboidShape(2, 0, 2, 14, 13, 14);
    public static final MapCodec<PedestalBlock> CODEC = PedestalBlock.createCodec(PedestalBlock::new);

    public PedestalBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PedestalBlockEntity(pos, state);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if(state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if(blockEntity instanceof PedestalBlockEntity) {
                ItemScatterer.spawn(world, pos, ((PedestalBlockEntity) blockEntity));
                world.updateComparators(pos, this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntities.PEDESTAL_BE,
                (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }



    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos,
                                             PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(world.getBlockEntity(pos) instanceof PedestalBlockEntity pedestalBlockEntity) {

            if (player.isSneaking() && !world.isClient()) {
                player.openHandledScreen(pedestalBlockEntity);
                return ItemActionResult.SUCCESS;
            }

            if(pedestalBlockEntity.isEmpty() && !stack.isEmpty()) {
                // Block Empty & Hand has Item -> Item from Hand into Block
                pedestalBlockEntity.syncedInventoryModification(itemStacks -> {
                    itemStacks.set(0, stack.copyWithCount(1));
                });
                world.playSound(player, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1f, 2f);
                stack.decrement(1);

                pedestalBlockEntity.markDirty();
                world.updateListeners(pos, state, state, 0);
            }

            else if(!pedestalBlockEntity.isEmpty() && stack.isEmpty() && !player.isSneaking()) {
                // Block has Item & Hand Empty -> Item from Block into Hand
                ItemStack stackOnPedestal = pedestalBlockEntity.getStack(0);
                player.setStackInHand(Hand.MAIN_HAND, stackOnPedestal);
                world.playSound(player, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1f, 1f);
                pedestalBlockEntity.syncedInventoryModification(itemStacks -> {
                    itemStacks.clear();
                });

                pedestalBlockEntity.markDirty();
                world.updateListeners(pos, state, state, 0);
            }

            else if(pedestalBlockEntity.isEmpty() && stack.isEmpty()) {
                // Block Empty & Hand Empty -> Do nothing
                pedestalBlockEntity.markDirty();
                return ItemActionResult.CONSUME;
            }

            else if(!pedestalBlockEntity.isEmpty() && !stack.isEmpty()) {
                // Block has Item & Hand has Item -> If same ItemStack increment Stack, if not same ItemStack do nth
                if (stack.isOf(pedestalBlockEntity.getStack(0).getItem()) && (stack.getCount() < stack.getMaxCount())) {
                    world.playSound(player, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1f, 2f);
                    pedestalBlockEntity.syncedInventoryModification(itemStacks -> {
                        itemStacks.clear();
                    });

                    stack.increment(1);

                    pedestalBlockEntity.markDirty();
                    world.updateListeners(pos, state, state, 0);
                } else {
                    pedestalBlockEntity.markDirty();
                    return ItemActionResult.CONSUME; // Do nth but don't place block
                }
            }
            return ItemActionResult.SUCCESS;
        }
        return ItemActionResult.FAIL;
    }
}
