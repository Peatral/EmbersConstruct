package com.peatral.embersconstruct.integration.tinkers_reforged;

import com.peatral.embersconstruct.modules.EmbersConstructModule;
import com.peatral.embersconstruct.util.Stamp;
import mrthomas20121.tinkers_reforged.tools.Tools;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.mantle.pulsar.pulse.Pulse;

@Pulse(id = IntegrationTinkersReforged.PulseId,
        modsRequired = IntegrationTinkersReforged.modid,
        defaultEnable = true)
public class IntegrationTinkersReforged extends EmbersConstructModule {

    public static final String modid = "tinkers_reforged";
    public static final String PulseId = modid + "Integration";

    @SubscribeEvent
    public void registerStamps(RegistryEvent.Register<Stamp> event) {
        // I have no choice but to copy the ugly structure

        //if(TinkersReforgedConfig.SettingTools.enableTools) {

            //if(TinkersReforgedConfig.SettingTools.enableLightsword) {
                register(event, initStamp("light_blade", Tools.lightblade));
            //}

            //if(Loader.isModLoaded("atum") && TinkersReforgedConfig.SettingMaterials.modules.atum) {
                //if(TinkersReforgedConfig.SettingTools.enableClub) {
                    register(event, initStamp("club_head", Tools.clubHead));
                //}

                //if(TinkersReforgedConfig.SettingTools.enableGreatsword) {
                    register(event, initStamp("great_blade", Tools.greatBlade));
                //}

                //if(TinkersReforgedConfig.SettingTools.enableKhopesh) {
                    register(event, initStamp("curved_blade", Tools.curvedBlade));
                //}
            //}

            //if(Loader.isModLoaded("geolosys")) {
                register(event, initStamp("propick_head", Tools.propickHead));
            //}
        //}
    }
}
