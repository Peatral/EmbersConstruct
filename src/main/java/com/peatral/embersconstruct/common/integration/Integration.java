package com.peatral.embersconstruct.common.integration;

import com.peatral.embersconstruct.common.util.Stamp;
import com.peatral.embersconstruct.common.registry.RegistryStamps;
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

    public static void registerAll(RegistryEvent<Stamp> event, List<Stamp> stamps) {
        for (Stamp stamp : stamps) register(event, stamp);
    }

    public static void register(RegistryEvent<Stamp> event, Stamp stamp) {
        RegistryStamps.register(event, stamp);
    }
}
