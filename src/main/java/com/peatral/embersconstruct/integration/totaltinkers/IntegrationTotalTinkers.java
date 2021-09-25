package com.peatral.embersconstruct.integration.totaltinkers;

import com.peatral.embersconstruct.modules.EmbersConstructModule;
import com.peatral.embersconstruct.util.Stamp;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.mantle.pulsar.pulse.Pulse;
import uvmidnight.totaltinkers.explosives.Explosives;
import uvmidnight.totaltinkers.newweapons.NewWeapons;
import uvmidnight.totaltinkers.oldweapons.OldWeapons;

@Pulse(id = IntegrationTotalTinkers.PulseId,
        modsRequired = IntegrationTotalTinkers.modid,
        defaultEnable = true)
public class IntegrationTotalTinkers extends EmbersConstructModule {

    public static final String modid = "totaltinkers";
    public static final String PulseId = modid + "Integration";

    @SubscribeEvent
    public void registerStamps(RegistryEvent.Register<Stamp> event) {
        registerAll(event,
                initStamp("fullguard", OldWeapons.fullGuard),
                initStamp("greatbladecore", NewWeapons.greatbladeCore));

        if (Explosives.explosiveCore != null) register(event, initStamp("explosivecore", Explosives.explosiveCore));
    }
}
