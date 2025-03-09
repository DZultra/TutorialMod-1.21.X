package net.dzultra.tutorialmod.block.entity.custom;

import net.dzultra.tutorialmod.block.entity.ImplementedInventory;
import net.dzultra.tutorialmod.block.entity.ModBlockEntities;
import net.dzultra.tutorialmod.particle.ModParticles;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ThreadLocalRandom;

public class PedestalBlockEntity extends BlockEntity implements ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    private float rotation = 0;
    private float velocityX;
    private float velocityY;
    private float velocityZ;
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
        return inventory;
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, inventory, registryLookup);
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
}
