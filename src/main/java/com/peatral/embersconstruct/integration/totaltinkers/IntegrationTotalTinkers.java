package com.peatral.embersconstruct.integration.totaltinkers;

import com.peatral.embersconstruct.integration.Integration;
import com.peatral.embersconstruct.util.Stamp;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import uvmidnight.totaltinkers.explosives.Explosives;
import uvmidnight.totaltinkers.newweapons.NewWeapons;
import uvmidnight.totaltinkers.oldweapons.OldWeapons;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class IntegrationTotalTinkers extends Integration {
    public static List<Stamp> stamps = new ArrayList<>();

    @Optional.Method(modid = "totaltinkers")
    @SubscribeEvent
    public static void registerStamps(RegistryEvent.Register<Stamp> event) {
        stamps.add(initStamp("fullguard", OldWeapons.fullGuard));
        stamps.add(initStamp("greatbladecore", NewWeapons.greatbladeCore));
        if (Explosives.explosiveCore != null) stamps.add(initStamp("explosivecore", Explosives.explosiveCore));

        registerAll(event, stamps);
    }
}
