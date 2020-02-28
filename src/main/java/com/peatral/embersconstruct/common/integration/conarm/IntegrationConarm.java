package com.peatral.embersconstruct.common.integration.conarm;

import c4.conarm.common.ConstructsRegistry;
import com.peatral.embersconstruct.common.integration.Integration;
import com.peatral.embersconstruct.common.util.Stamp;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class IntegrationConarm extends Integration {

    public static List<Stamp> stamps = new ArrayList<>();

    static {
        stamps.add(initStamp("armor_plates", ConstructsRegistry.armorPlate));
        stamps.add(initStamp("armor_trim", ConstructsRegistry.armorTrim));
        stamps.add(initStamp("boots_core", ConstructsRegistry.bootsCore));
        stamps.add(initStamp("chest_core", ConstructsRegistry.chestCore));
        stamps.add(initStamp("helmet_core", ConstructsRegistry.helmetCore));
        stamps.add(initStamp("leggings_core", ConstructsRegistry.leggingsCore));
    }

    @Optional.Method(modid = "conarm")
    @SubscribeEvent
    public static void registerStamps(RegistryEvent.Register<Stamp> event) {
        registerAll(event, stamps);
    }
}
