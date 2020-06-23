package com.peatral.embersconstruct.registry;

import com.peatral.embersconstruct.EmbersConstruct;
import com.peatral.embersconstruct.EmbersConstructItems;
import com.peatral.embersconstruct.item.ItemStamp;
import com.peatral.embersconstruct.util.Stamp;
import net.minecraft.item.ItemStack;
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

    public static List<ItemStack> getStampTableCrafting() {
        List<ItemStack> stacks = new ArrayList<>();
        for (Stamp stamp : values()) {
            stacks.add(((ItemStamp) EmbersConstructItems.StampRaw).fromStamp(stamp));
        }
        return stacks;
    }
}
