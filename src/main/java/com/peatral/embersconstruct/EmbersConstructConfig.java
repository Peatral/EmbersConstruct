package com.peatral.embersconstruct;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = EmbersConstruct.MODID)
public class EmbersConstructConfig {

    @Config.LangKey("embersconstruct.config.section.general")
    public static final General general = new General();

    @Config.LangKey("embersconstruct.config.section.embersConstructRecipeSettings")
    @Config.Comment("Changes require a restart to come into effect!")
    public static final ECRecipeSettings embersConstructSettings = new ECRecipeSettings();

    @Config.LangKey("embersconstruct.config.section.tinkersConstructRecipeSettings")
    @Config.Comment("Changes require a restart to come into effect!")
    public static final TCRecipeSettings tinkersConstructSettings = new TCRecipeSettings();

    public static class General {

        @Config.LangKey("embersconstruct.config.showNewVersionMessage")
        @Config.Comment("Show the Update-Checker message if new version is available.")
        public boolean showNewVersionMessage = true;

        @Config.LangKey("embersconstruct.config.stamptable")
        @Config.Comment("Enable stamp table")
        public boolean stamptable = true;

        @Config.LangKey("embersconstruct.config.bloomery")
        @Config.Comment("Enable bloomery")
        public boolean bloomery = true;

        @Config.LangKey("embersconstruct.config.kiln")
        @Config.Comment("Enable kiln")
        public boolean kiln = true;
    }

    public static class ECRecipeSettings {
        @Config.LangKey("embersconstruct.config.stampTableNeedBlank")
        @Config.Comment("Make already made raw stamps non-changeable")
        public boolean stampTableNeedBlank = true;

        @Config.LangKey("embersconstruct.config.dustStamping")
        @Config.Comment("Make dusts from ingots via the Stamper")
        public boolean dustStamping = true;

        @Config.LangKey("embersconstruct.config.melter")
        @Config.Comment("Enable melting recipes")
        public boolean melter = true;

        @Config.LangKey("embersconstruct.config.mixer")
        @Config.Comment("Enable mixing recipes")
        public boolean mixer = true;

        @Config.LangKey("embersconstruct.config.stamper")
        @Config.Comment("Enable stamping recipes")
        public boolean stamper = true;
    }

    public static class TCRecipeSettings {
        @Config.LangKey("embersconstruct.config.removeMelting")
        @Config.Comment("")
        public boolean removeMelting = false;

        @Config.LangKey("embersconstruct.config.removeAlloying")
        @Config.Comment("")
        public boolean removeAlloying = false;

        @Config.LangKey("embersconstruct.config.removeTableCasting")
        @Config.Comment("")
        public boolean removeTableCasting = false;

        @Config.LangKey("embersconstruct.config.removeBasinCasting")
        @Config.Comment("")
        public boolean removeBasinCasting = false;
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
