package com.peatral.embersconstruct.integration.tconevo;

import com.peatral.embersconstruct.integration.Integration;
import com.peatral.embersconstruct.util.Stamp;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.phanta.tconevo.init.TconEvoItems;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class IntegrationTinkersEvolution extends Integration {

    public static List<Stamp> stamps = new ArrayList<>();

    @Optional.Method(modid = "tconevo")
    @SubscribeEvent
    public static void registerStamps(RegistryEvent.Register<Stamp> event) {
        stamps.add(initStamp("part_arcane_focus", TconEvoItems.PART_ARCANE_FOCUS));

        registerAll(event, stamps);
    }
}
