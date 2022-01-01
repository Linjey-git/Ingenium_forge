package com.linjey.ingenium.block.entity;

import com.linjey.ingenium.recipe.LightningInfusionerRecipe;
import com.linjey.ingenium.recipe.ModRecipes;
import com.linjey.ingenium.screen.LightningInfusionerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class LightningInfusionerBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemHandler = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    };
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();


    public LightningInfusionerBlockEntity(BlockPos pWorldPosiotion, BlockState pBlockState) {
        super(ModBlockEntities.LIGHTNING_INFUSIONER.get(), pWorldPosiotion, pBlockState);
    }

    @Override
    public Component getDisplayName() {
        return new TextComponent("Lightning Infusioner");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new LightningInfusionerMenu(containerId, inventory, this);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }


    private void strikeLightning() {
        if (!this.level.isClientSide()) {
            EntityType.LIGHTNING_BOLT.spawn((ServerLevel) level, null, null, worldPosition,
                    MobSpawnType.TRIGGERED, true, true);
        }
    }

    public void craft(Level level, BlockPos pPos, BlockState pState, LightningInfusionerBlockEntity entity) {
        SimpleContainer inv = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inv.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<LightningInfusionerRecipe> recipe = entity.level.getRecipeManager()
                .getRecipeFor(LightningInfusionerRecipe.Type.INSTANCE, inv, level);

        recipe.ifPresent(iRecipe -> {
            ItemStack output = iRecipe.getResultItem();
            if (iRecipe.getWeather().equals(LightningInfusionerRecipe.Weather.CLEAR) &&
                    !level.isRaining()) {
                craftTheItem(output, entity);
            }
            if (iRecipe.getWeather().equals(LightningInfusionerRecipe.Weather.RAIN) &&
                    level.isRaining()) {
                craftTheItem(output, entity);
            }
            if (iRecipe.getWeather().equals(LightningInfusionerRecipe.Weather.THUNDERING) &&
                    level.isThundering()) {
                strikeLightning();
                craftTheItem(output, entity);
            }

        });
    }

    private void craftTheItem(ItemStack output, LightningInfusionerBlockEntity entity) {
        entity.itemHandler.extractItem(0, 1, false);
        entity.itemHandler.extractItem(1, 1, false);
        entity.itemHandler.extractItem(2, 1, false);
        entity.itemHandler.insertItem(3, output, false);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, LightningInfusionerBlockEntity pBlockEntity) {
        pBlockEntity.craft(pLevel, pPos,pState,pBlockEntity);
    }

}
