package com.peatral.embersconstruct.integration.conarm;

import c4.conarm.common.ConstructsRegistry;
import c4.conarm.common.armor.traits.ArmorTraits;
import c4.conarm.lib.materials.ArmorMaterialType;
import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;
import com.google.common.eventbus.Subscribe;
import com.peatral.embersconstruct.EmbersConstructMaterials;
import com.peatral.embersconstruct.modules.EmbersConstructModule;
import com.peatral.embersconstruct.util.Stamp;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.mantle.pulsar.pulse.Pulse;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.traits.ITrait;

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

    @Subscribe
    public void preInit(FMLPreInitializationEvent event) {
        TinkerRegistry.addMaterialStats(EmbersConstructMaterials.wroughtiron,
                new CoreMaterialStats(9, 15),
                new PlatesMaterialStats(0.85F, 3, 0),
                new TrimMaterialStats(2.5F));
    }

    @Subscribe
    public void init(FMLInitializationEvent event){
        addArmorTrait(EmbersConstructMaterials.wroughtiron, ArmorTraits.magnetic2, (ITrait)ArmorTraits.magnetic);
    }

    public static void addArmorTrait(Material material, ITrait trait, ITrait secondTrait) {
        material.addTrait(trait, ArmorMaterialType.CORE);
        if (secondTrait != null) {
            material.addTrait(secondTrait, ArmorMaterialType.PLATES);
            material.addTrait(secondTrait, ArmorMaterialType.TRIM);
        } else {
            material.addTrait(trait, ArmorMaterialType.PLATES);
            material.addTrait(trait, ArmorMaterialType.TRIM);
        }

    }

    public static void addArmorTrait(Material material, ITrait trait, String type) {
        material.addTrait(trait, type);
    }
}
