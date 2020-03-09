package com.peatral.embersconstruct.common;

import com.peatral.embersconstruct.common.registry.KilnRecipes;
import com.peatral.embersconstruct.common.registry.RegistryAlloying;
import com.peatral.embersconstruct.common.registry.RegistryMelting;
import com.peatral.embersconstruct.common.registry.RegistryStamping;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    public void registerItemRenders() {
    }
    public void registerItemRender(Item item) {
    }

    public void preInit(FMLPreInitializationEvent event) {

    }

    public void init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {
        RegistryMelting.main();
        RegistryAlloying.main();
        RegistryStamping.main();
        KilnRecipes.main();
    }

    public boolean isClient() {
        return false;
    }

    public World getClientWorld() {
        return null;
    }

}
