package net.dzultra.tutorialmod.block.entity.custom;

import net.dzultra.tutorialmod.TutorialMod;
import net.dzultra.tutorialmod.block.entity.ImplementedInventory;
import net.dzultra.tutorialmod.block.entity.ModBlockEntities;
import net.dzultra.tutorialmod.screen.custom.PedestalScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public class PedestalBlockEntity extends BlockEntity implements ImplementedInventory, ExtendedScreenHandlerFactory<BlockPos> {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    private float rotation = 0;
    private int tickCounter = 0;

    public PedestalBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PEDESTAL_BE, pos, state);
    }

    public float getRenderingRotation() {
        rotation += 0.5f;
        if(rotation >= 360) {
            rotation = 0;
        }
        return rotation;
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (!world.isClient) return;

        if(world.getBlockEntity(pos) instanceof PedestalBlockEntity pedestalBlockEntity) {
            if (!pedestalBlockEntity.isEmpty()) {
                tickCounter++;
                if (tickCounter >= 7) {
                    double velocityX = ThreadLocalRandom.current().nextDouble(-0.02, 0.02);
                    double velocityY = ThreadLocalRandom.current().nextDouble(0, 0.02);
                    double velocityZ = ThreadLocalRandom.current().nextDouble(-0.02, 0.02);

                    world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, pos.getX() + 0.5, pos.getY() + 1.15, pos.getZ() + 0.5, velocityX, velocityY, velocityZ);
                    tickCounter = 0;
                }
            }
        }
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        this.markDirty();
        return this.inventory;
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, this.inventory, registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, this.inventory, registryLookup);
    }

    // GUI

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return this.pos;
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Pedestal");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new PedestalScreenHandler(syncId, playerInventory, this.pos);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side) {
        return false;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side) {
        return false;
    }

    // Client Server Sync
    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }


    public void syncedInventoryModification(Consumer<DefaultedList<ItemStack>> inventoryConsumer) {
        inventoryConsumer.accept(this.getItems());
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            serverWorld.getChunkManager().markForUpdate(this.getPos());
            this.markDirty();
        }
    }

    @Override
    public void markDirty() {
        super.markDirty();
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            TutorialMod.LOGGER.info("markDirty after ServerWorld check");
            var packet = this.toUpdatePacket();
            serverWorld.getChunkManager()
                    .chunkLoadingManager
                    .getPlayersWatchingChunk(
                            new ChunkPos(this.pos)
                    )
                    .forEach(
                            player -> player.networkHandler.sendPacket(packet)
                    );
            serverWorld.getChunkManager().markForUpdate(this.getPos());
        }
    }
}
