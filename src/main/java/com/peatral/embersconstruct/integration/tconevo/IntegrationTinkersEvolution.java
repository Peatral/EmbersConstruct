package com.peatral.embersconstruct.integration.tconevo;

import com.peatral.embersconstruct.modules.EmbersConstructModule;
import com.peatral.embersconstruct.util.Stamp;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.mantle.pulsar.pulse.Pulse;
import xyz.phanta.tconevo.init.TconEvoItems;

@Pulse(id = IntegrationTinkersEvolution.PulseId,
        modsRequired = IntegrationTinkersEvolution.modid,
        defaultEnable = true)
public class IntegrationTinkersEvolution extends EmbersConstructModule {

    public static final String modid = "tconevo";
    public static final String PulseId = modid + "Integration";

    @SubscribeEvent
    public void registerStamps(RegistryEvent.Register<Stamp> event) {
        registerAll(event,
                initStamp("part_arcane_focus", TconEvoItems.PART_ARCANE_FOCUS));
    }
}
