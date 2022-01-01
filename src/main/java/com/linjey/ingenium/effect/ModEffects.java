package com.linjey.ingenium.effect;

import com.linjey.ingenium.ModIngenium;
import com.linjey.ingenium.effect.custom.ExpEffect;
import com.linjey.ingenium.effect.custom.FlightEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {

    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, ModIngenium.MOD_ID);

    public static final RegistryObject<MobEffect> FLIGHT = register("flight", new FlightEffect(MobEffectCategory.BENEFICIAL, 745784));
    public static final RegistryObject<MobEffect> EXP = register("exp", new ExpEffect(MobEffectCategory.BENEFICIAL, 169255132));

    private static <T extends MobEffect> RegistryObject<T> register(String name, T effect) {
        return EFFECTS.register(name, () -> effect);
    }

}
