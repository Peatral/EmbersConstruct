package com.peatral.embersconstruct.integration.conarm;

import c4.conarm.common.ConstructsRegistry;
import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;
import com.peatral.embersconstruct.EmbersConstructMaterials;
import com.peatral.embersconstruct.modules.EmbersConstructModule;
import com.peatral.embersconstruct.util.Stamp;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.mantle.pulsar.pulse.Pulse;
import slimeknights.tconstruct.library.TinkerRegistry;

@Pulse(id = IntegrationConarm.PulseId,
        modsRequired = IntegrationConarm.modid,
        defaultEnable = true)
public class IntegrationConarm extends EmbersConstructModule {

    public static final String modid = "conarm";
    public static final String PulseId = modid + "Integration";

    @SubscribeEvent
    public void registerStamps(RegistryEvent.Register<Stamp> event) {
        registerAll(event,
                initStamp("armor_plates", ConstructsRegistry.armorPlate),
                initStamp("armor_trim", ConstructsRegistry.armorTrim),
                initStamp("boots_core", ConstructsRegistry.bootsCore),
                initStamp("chest_core", ConstructsRegistry.chestCore),
                initStamp("helmet_core", ConstructsRegistry.helmetCore),
                initStamp("leggings_core", ConstructsRegistry.leggingsCore));
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {
        TinkerRegistry.addMaterialStats(EmbersConstructMaterials.wroughtiron,
                new CoreMaterialStats(9, 15),
                new PlatesMaterialStats(0.85F, 3, 0),
                new TrimMaterialStats(2.5F));
    }
}
