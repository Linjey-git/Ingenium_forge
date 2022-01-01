package com.linjey.ingenium.item;

import com.linjey.ingenium.ModIngenium;
import com.linjey.ingenium.ModItemGroup;
import com.linjey.ingenium.item.custom.BibliolithOfExperienceItem;
import com.linjey.ingenium.item.custom.OrbOfTemporaryFlightItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModIngenium.MOD_ID);

    public static final RegistryObject<Item> ORB_OF_TEMPORARY_FLIGHT = ITEMS.register("orb_of_temporary_flight",
            () -> new OrbOfTemporaryFlightItem(new Item.Properties().tab(ModItemGroup.INGENIUM_TAB).stacksTo(8)));

    public static final RegistryObject<Item> BIBLIOLITH_OF_EXPERIENCE = ITEMS.register("bibliolith_of_experience",
            () -> new BibliolithOfExperienceItem(new Item.Properties().tab(ModItemGroup.INGENIUM_TAB)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
