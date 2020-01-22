package com.peatral.embersconstruct.common;

import com.peatral.embersconstruct.common.registry.RegistryAlloying;
import com.peatral.embersconstruct.common.registry.RegistryMelting;
import com.peatral.embersconstruct.common.registry.RegistryStamping;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    public void registerItemRenders() {
    }

    public void preInit(FMLPreInitializationEvent event) {

    }

    public void init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {
        RegistryMelting.main();
        RegistryAlloying.main();
        RegistryStamping.main();
    }

}
