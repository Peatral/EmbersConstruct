package com.peatral.embersconstruct.integration.conarm;

import c4.conarm.common.ConstructsRegistry;
import com.peatral.embersconstruct.integration.Integration;
import com.peatral.embersconstruct.util.Stamp;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class IntegrationConarm extends Integration {

    public static List<Stamp> stamps = new ArrayList<>();

    @Optional.Method(modid = "conarm")
    @SubscribeEvent
    public static void registerStamps(RegistryEvent.Register<Stamp> event) {
        stamps.add(initStamp("armor_plates", ConstructsRegistry.armorPlate));
        stamps.add(initStamp("armor_trim", ConstructsRegistry.armorTrim));
        stamps.add(initStamp("boots_core", ConstructsRegistry.bootsCore));
        stamps.add(initStamp("chest_core", ConstructsRegistry.chestCore));
        stamps.add(initStamp("helmet_core", ConstructsRegistry.helmetCore));
        stamps.add(initStamp("leggings_core", ConstructsRegistry.leggingsCore));

        registerAll(event, stamps);
    }
}
