package com.peatral.embersconstruct.integration.plustic;

import com.peatral.embersconstruct.modules.EmbersConstructModule;
import com.peatral.embersconstruct.util.Stamp;
import landmaster.plustic.modules.ModuleTools;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.mantle.pulsar.pulse.Pulse;

@Pulse(id = IntegrationPlusTiC.PulseId,
        modsRequired = IntegrationPlusTiC.modid,
        defaultEnable = true)
public class IntegrationPlusTiC extends EmbersConstructModule {

    public static final String modid = "plustic";
    public static final String PulseId = modid + "Integration";

    @SubscribeEvent
    public void registerStamps(RegistryEvent.Register<Stamp> event) {
        registerAll(event,
                initStamp("battery_cell", ModuleTools.battery_cell),
                initStamp("laser_medium", ModuleTools.laser_medium),
                initStamp("pipe_piece", ModuleTools.pipe_piece));
    }

}
