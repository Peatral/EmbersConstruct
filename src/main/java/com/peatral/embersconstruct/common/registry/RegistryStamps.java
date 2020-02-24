package com.peatral.embersconstruct.common.registry;

import com.peatral.embersconstruct.common.EmbersConstruct;
import com.peatral.embersconstruct.common.util.Stamp;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class RegistryStamps {

    public static IForgeRegistry<Stamp> registry;
    private static List<Stamp> stamps = new ArrayList<>();

    @SubscribeEvent
    public static void registerRegistry(RegistryEvent.NewRegistry event) {
        RegistryBuilder regBuilder = new RegistryBuilder();
        regBuilder.setType(Stamp.class);
        regBuilder.setName(new ResourceLocation(EmbersConstruct.MODID, "stamps"));
        regBuilder.setIDRange(0, 1000);
        registry = regBuilder.create();
    }

    public static void registerAll(RegistryEvent<Stamp> event, Stamp... stamps) {
        for (Stamp stamp : stamps) register(event, stamp);
    }

    public static void register(RegistryEvent<Stamp> event, Stamp stamp) {
        registry.register(stamp);
        stamps.add(stamp);
    }

    public static List<Stamp> values() {
        return stamps; //registry is unordered or something, it's a mess when I use registry.getValues()
    }
}
