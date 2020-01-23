package com.peatral.embersconstruct.common.integration.conarm;

import com.peatral.embersconstruct.common.EmbersConstructItems;
import com.peatral.embersconstruct.common.registry.RegistryMelting;
import com.peatral.embersconstruct.common.registry.RegistryStamping;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ConarmIntegration {

    @Optional.Method(modid="conarm")
    public static void postInit(FMLPostInitializationEvent event) {
        RegistryMelting.registerConarmRecipes();
        RegistryStamping.registerConarmRecipes();
    }

    @Optional.Method(modid="conarm")
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        EmbersConstructItems.registerConarmItems(event.getRegistry());
    }
}
