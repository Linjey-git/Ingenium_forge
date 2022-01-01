package com.linjey.ingenium.effect.custom;

import com.linjey.ingenium.effect.ModEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class FlightEffect extends MobEffect {
    public FlightEffect(MobEffectCategory typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        int i = entityLivingBaseIn.getEffect(ModEffects.FLIGHT.get()).getDuration();
        if (entityLivingBaseIn instanceof Player) {
            Player player = (Player) entityLivingBaseIn;
            if (i <= 30) {
                player.getAbilities().mayfly = false;
                player.getAbilities().flying = false;
            } else {
                player.getAbilities().mayfly = true;
            }
        }
    }
}
