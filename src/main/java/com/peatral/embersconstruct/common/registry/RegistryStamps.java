package com.peatral.embersconstruct.common.registry;

import com.peatral.embersconstruct.common.EmbersConstruct;
import com.peatral.embersconstruct.common.util.Stamp;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.*;

@Mod.EventBusSubscriber
public class RegistryStamps {

    public static IForgeRegistry<Stamp> registry;

    @SubscribeEvent
    public static void registerRegistry(RegistryEvent.NewRegistry event) {
        RegistryBuilder regBuilder = new RegistryBuilder();
        regBuilder.setType(Stamp.class);
        regBuilder.setName(new ResourceLocation(EmbersConstruct.MODID, "stamps"));
        regBuilder.setIDRange(0, 1000);
        registry = regBuilder.create();
    }

    public static List<Stamp> values() {
        return orderCollection(registry.getValuesCollection());
    }

    public static List<Stamp> orderCollection(Collection<Stamp> stamps) {
        List<Stamp> out = new ArrayList<>();

        out.addAll(stamps);

        Collections.sort(out, (s1, s2) -> s1.getName().compareToIgnoreCase(s2.getName()));

        return out;
    }
}
