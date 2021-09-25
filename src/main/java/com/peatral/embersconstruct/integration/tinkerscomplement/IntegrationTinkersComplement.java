package com.peatral.embersconstruct.integration.tinkerscomplement;

import com.peatral.embersconstruct.modules.EmbersConstructModule;
import com.peatral.embersconstruct.util.Stamp;
import knightminer.tcomplement.plugin.chisel.ChiselPlugin;
import knightminer.tcomplement.plugin.exnihilo.ExNihiloPlugin;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.mantle.pulsar.pulse.Pulse;

public class IntegrationTinkersComplement {

    public static final String modid = "tcomplement";
    public static final String PulseId = modid + "Integration";

    @Pulse(id = IntegrationTinkersComplement.Chisel.PulseId,
            modsRequired = modid,
            pulsesRequired = modid + ":ChiselPlugin",
            defaultEnable = true)
    public static class Chisel extends EmbersConstructModule {

        public static final String modid = "chisel";
        public static final String PulseId = IntegrationTinkersComplement.modid + modid + "Integration";

        @SubscribeEvent
        public void registerStamps(RegistryEvent.Register<Stamp> event) {
            registerAll(event, initStamp("chisel_head", ChiselPlugin.chiselHead));
        }
    }

    @Pulse(id = IntegrationTinkersComplement.ExNihilo.PulseId,
            modsRequired = modid,
            pulsesRequired = modid + ":ExNihiloPlugin",
            defaultEnable = true)
    public static class ExNihilo extends EmbersConstructModule {

        public static final String modid = "exnihilo";
        public static final String PulseId = IntegrationTinkersComplement.modid + modid + "Integration";

        @SubscribeEvent
        public void registerStamps(RegistryEvent.Register<Stamp> event) {
            registerAll(event, initStamp("sledge_head", ExNihiloPlugin.sledgeHead));
        }
    }
}
