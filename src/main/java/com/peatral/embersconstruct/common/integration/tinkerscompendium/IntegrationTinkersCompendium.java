package com.peatral.embersconstruct.common.integration.tinkerscompendium;

import com.peatral.embersconstruct.common.integration.Integration;
import com.peatral.embersconstruct.common.util.Stamp;
import lance5057.tDefense.core.parts.TDParts;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class IntegrationTinkersCompendium extends Integration {

    public static List<Stamp> stamps = new ArrayList<>();

    static {
        stamps.add(initStamp("armor_plate", TDParts.armorPlate));
        //stamps.add(initStamp("fabric", TDParts.fabric));
        stamps.add(initStamp("rivets", TDParts.rivets));
        stamps.add(initStamp("clasp", TDParts.clasp));
        stamps.add(initStamp("chainmail", TDParts.chainmail));
        stamps.add(initStamp("filigree", TDParts.filigree));
    }

    @Optional.Method(modid = "tinkerscompendium")
    @SubscribeEvent
    public static void registerStamps(RegistryEvent<Stamp> event) {
        registerAll(event, stamps);
    }
}
