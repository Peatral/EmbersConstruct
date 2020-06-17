package com.peatral.embersconstruct.common;

import com.google.common.collect.Lists;
import com.peatral.embersconstruct.common.network.StampTableSelectionPacket;
import com.peatral.embersconstruct.common.registry.*;
import com.peatral.embersconstruct.common.util.RecipeRemover;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import slimeknights.tconstruct.common.TinkerNetwork;
import slimeknights.tconstruct.library.TinkerRegistry;

import java.lang.reflect.Field;

public class CommonProxy {
    public void registerItemRenders() {
    }
    public void registerItemRender(Item item) {
    }

    public void preInit(FMLPreInitializationEvent event) {
        TinkerNetwork.instance.registerPacket(StampTableSelectionPacket.class);
    }

    public void init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {
        RegistryMelting.main();
        RegistryAlloying.main();
        RegistryStamping.main();
        KilnRecipes.main();
        StampTableRecipes.main();

        RecipeRemover.main();
    }

    public boolean isClient() {
        return false;
    }

    public World getClientWorld() {
        return null;
    }

}
