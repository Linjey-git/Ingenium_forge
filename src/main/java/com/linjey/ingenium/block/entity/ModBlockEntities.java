package com.linjey.ingenium.block.entity;

import com.linjey.ingenium.ModIngenium;
import com.linjey.ingenium.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, ModIngenium.MOD_ID);

    public static final RegistryObject<BlockEntityType<LightningInfusionerBlockEntity>> LIGHTNING_INFUSIONER =
            BLOCK_ENTITIES.register("lightning_infusioner", () ->
                    BlockEntityType.Builder.of(LightningInfusionerBlockEntity::new,
                            ModBlocks.LIGHTNING_INFUSIONER.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

}
