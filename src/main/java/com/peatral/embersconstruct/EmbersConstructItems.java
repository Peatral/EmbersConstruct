package com.peatral.embersconstruct;

import com.google.common.collect.Lists;
import com.peatral.embersconstruct.item.ItemEmbersConstruct;
import com.peatral.embersconstruct.item.ItemStamp;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.List;

import static net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(EmbersConstruct.MODID)
public class EmbersConstructItems {
    public static List<Item> items = Lists.newLinkedList();

    @ObjectHolder("stamp_raw")
    public static final Item StampRaw = null;
    @ObjectHolder("stamp")
    public static final Item Stamp = null;

    @ObjectHolder("wrought_iron_ingot")
    public static final Item WroughtIronIngot = null;
    @ObjectHolder("wrought_iron_nugget")
    public static final Item WroughtIronNugget = null;
    @ObjectHolder("wrought_iron_plate")
    public static final Item WroughtIronPlate = null;

    public static void main(IForgeRegistry<Item> registry) {
        registerItems(registry);
    }

    public static void registerItems(IForgeRegistry<Item> registry) {
        registerItem(registry, new ItemStamp(true), "stamp_raw");
        registerItem(registry, new ItemStamp(), "stamp");
        registerItem(registry, new ItemEmbersConstruct(), "wrought_iron_ingot");
        registerItem(registry, new ItemEmbersConstruct(), "wrought_iron_nugget");
        registerItem(registry, new ItemEmbersConstruct(), "wrought_iron_plate");
    }

    public static Item init(Item item, String name) {
        item = item.setUnlocalizedName(EmbersConstruct.MODID + "." + name).setRegistryName(new ResourceLocation(EmbersConstruct.MODID, name));
        items.add(item);
        return item;
    }

    public static Item registerItem(IForgeRegistry<Item> registry, Item item, String name) {
        item = init(item, name);
        registry.register(item);
        return item;
    }
}
