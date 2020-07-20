package com.peatral.embersconstruct.integration.plustic;

import com.peatral.embersconstruct.integration.Integration;
import com.peatral.embersconstruct.util.Stamp;
import landmaster.plustic.modules.ModuleTools;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class IntegrationPlusTiC extends Integration {
    public static List<Stamp> stamps = new ArrayList<>();

    @Optional.Method(modid = "plustic")
    @SubscribeEvent
    public static void registerStamps(RegistryEvent.Register<Stamp> event) {
        stamps.add(initStamp("battery_cell", ModuleTools.battery_cell));
        stamps.add(initStamp("laser_medium", ModuleTools.laser_medium));
        stamps.add(initStamp("pipe_piece", ModuleTools.pipe_piece));

        registerAll(event, stamps);
    }

}
