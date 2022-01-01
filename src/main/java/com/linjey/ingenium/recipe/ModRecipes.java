package com.linjey.ingenium.recipe;

import com.linjey.ingenium.ModIngenium;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ModIngenium.MOD_ID);

    public static final RegistryObject<RecipeSerializer<LightningInfusionerRecipe>> LIGHTNING_INFUSION_RECIPE =
            SERIALIZERS.register("lightning_infusion", () -> LightningInfusionerRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        Registry.register(Registry.RECIPE_TYPE, LightningInfusionerRecipe.Type.ID, LightningInfusionerRecipe.Type.INSTANCE);
    }
}
