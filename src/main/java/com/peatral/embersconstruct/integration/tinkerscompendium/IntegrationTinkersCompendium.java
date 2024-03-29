package com.peatral.embersconstruct.integration.tinkerscompendium;

import com.peatral.embersconstruct.modules.EmbersConstructModule;
import com.peatral.embersconstruct.util.Stamp;
import lance5057.tDefense.core.parts.TDParts;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.mantle.pulsar.pulse.Pulse;

@Pulse(id = IntegrationTinkersCompendium.PulseId,
        modsRequired = IntegrationTinkersCompendium.modid,
        defaultEnable = true)
public class IntegrationTinkersCompendium extends EmbersConstructModule {

    public static final String modid = "tinkerscompendium";
    public static final String PulseId = modid + "Integration";

    @SubscribeEvent
    public void registerStamps(RegistryEvent.Register<Stamp> event) {
        registerAll(event,
                //initStamp("wire", TDParts.wire),
                //initStamp("fabric", TDParts.fabric),
                initStamp("armor_plate", TDParts.armorPlate),
                initStamp("rivets", TDParts.rivets),
                initStamp("clasp", TDParts.clasp),
                initStamp("chainmail", TDParts.chainmail),
                initStamp("ring_shank", TDParts.ringShank),
                initStamp("setting", TDParts.setting),
                initStamp("filigree", TDParts.filigree));
    }
}
