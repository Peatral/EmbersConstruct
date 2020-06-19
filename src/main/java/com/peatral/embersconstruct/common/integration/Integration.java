package com.peatral.embersconstruct.common.integration;

import com.peatral.embersconstruct.common.util.OreDictValues;
import com.peatral.embersconstruct.common.util.Stamp;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import slimeknights.tconstruct.library.tinkering.MaterialItem;

import java.util.List;

public class Integration {

    public static Stamp initStamp(String name, MaterialItem part) {
        return new Stamp(part, name).setRegistryName(name);
    }

    public static Stamp initStamp(String name, Item part, int cost, Fluid fluid) {
        return new Stamp(part, name, cost, fluid).setRegistryName(name);
    }

    public static Stamp initStamp(String name, OreDictValues mv) {
        return initStamp(name, mv.getName(), mv.getValue());
    }

    public static Stamp initStamp(String name, String oreDict, int cost) {
        return new Stamp(oreDict, name, cost).setRegistryName(name);
    }

    public static void registerAll(RegistryEvent.Register<Stamp> event, List<Stamp> stamps) {
        for (Stamp stamp : stamps) register(event, stamp);
    }

    public static void register(RegistryEvent.Register<Stamp> event, Stamp stamp) {
        event.getRegistry().register(stamp);
    }
}
