package com.linjey.ingenium.item.custom;

import com.linjey.ingenium.effect.ModEffects;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BibliolithOfExperienceItem extends Item {
    public BibliolithOfExperienceItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        stack.shrink(1);
        player.awardStat(Stats.ITEM_USED.get(this));
        player.giveExperienceLevels(10);
       // player.addEffect(new MobEffectInstance(ModEffects.EXP.get(), 200));

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flagIn) {
        if (Screen.hasShiftDown()) {
            tooltip.add(new TranslatableComponent("tooltip.ingenium.bibliolith_of_experience_shift"));
        } else {
            tooltip.add(new TranslatableComponent("tooltip.ingenium.bibliolith_of_experience"));
        }
    }

}
