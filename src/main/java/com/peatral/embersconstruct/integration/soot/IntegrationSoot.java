package com.peatral.embersconstruct.integration.soot;

import com.google.common.eventbus.Subscribe;
import com.peatral.embersconstruct.modules.EmbersConstructModule;
import com.peatral.embersconstruct.registry.KilnRecipes;
import com.peatral.embersconstruct.registry.RegistryStamping;
import com.peatral.embersconstruct.util.OreDictValues;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import slimeknights.mantle.pulsar.pulse.Pulse;
import soot.Registry;

@Pulse(id = IntegrationSoot.PulseId,
        modsRequired = IntegrationSoot.modid,
        defaultEnable = true)
public class IntegrationSoot extends EmbersConstructModule {

    public static final String modid = "soot";
    public static final String PulseId = modid + "Integration";

    @Subscribe
    public void init(FMLInitializationEvent event) {
        registerKilnRecipes();
    }

    @Subscribe
    public void postInit(FMLPostInitializationEvent event) {
        registerStampingRecipes();
    }

    public void registerKilnRecipes() {
        KilnRecipes.instance().addRecipe(Registry.STAMP_NUGGET_RAW, new ItemStack((Registry.STAMP_NUGGET)));
        KilnRecipes.instance().addRecipe(Registry.STAMP_TEXT_RAW, new ItemStack((Registry.STAMP_TEXT)));
    }

    public void registerStampingRecipes() {
        RegistryStamping.registerFromOreDict(OreDictValues.NUGGET.getName(), new ItemStack(Registry.STAMP_NUGGET), OreDictValues.NUGGET.getValue());
    }
}
