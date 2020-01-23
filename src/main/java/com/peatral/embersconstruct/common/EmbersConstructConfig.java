package com.peatral.embersconstruct.common;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = EmbersConstruct.MODID)
public class EmbersConstructConfig {

    @Config.LangKey("embersconstruct.config.section.general")
    public static final General general = new General();

    public static class General {

        @Config.LangKey("embersconstruct.config.showNewVersionMessage")
        @Config.Comment("Show the Update-Checker message if new version is available.")
        public boolean showNewVersionMessage = true;
    }

    @Mod.EventBusSubscriber(modid = EmbersConstruct.MODID)
    private static class EventHandler {
        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(EmbersConstruct.MODID)) {
                ConfigManager.sync(EmbersConstruct.MODID, Config.Type.INSTANCE);
            }
        }
    }
}
