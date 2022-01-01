package com.linjey.ingenium;

import com.linjey.ingenium.item.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModItemGroup {

    public static final CreativeModeTab INGENIUM_TAB = new CreativeModeTab("ingenium_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.ORB_OF_TEMPORARY_FLIGHT.get());
        }
    };

}
