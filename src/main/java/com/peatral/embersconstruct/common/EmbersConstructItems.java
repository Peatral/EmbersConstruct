package com.peatral.embersconstruct.common;

import com.peatral.embersconstruct.common.item.ItemStamp;
import com.peatral.embersconstruct.common.item.ItemStampConarm;
import com.peatral.embersconstruct.common.item.ItemStampRaw;
import com.peatral.embersconstruct.common.item.ItemStampRawConarm;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@GameRegistry.ObjectHolder(EmbersConstruct.MODID)
public class EmbersConstructItems {

    public static final Item StampRaw = new ItemStampRaw();
    public static final Item Stamp = new ItemStamp();
    public static final Item StampRawConarm = new ItemStampRawConarm();
    public static final Item StampConarm = new ItemStampConarm();

    public static void main(IForgeRegistry<Item> registry) {
        registerItems(registry);
        if (EmbersConstruct.isConarmLoaded) registerConarmItems(registry);
    }

    public static void registerItems(IForgeRegistry<Item> registry) {
        registry.register(init(StampRaw, "stamp_raw"));
        registry.register(init(Stamp, "stamp"));
    }

    @Optional.Method(modid="conarm")
    public static void registerConarmItems(IForgeRegistry<Item> registry) {
        registry.register(init(StampRawConarm, "stamp_raw_conarm"));
        registry.register(init(StampConarm, "stamp_conarm"));
    }

    public static Item init(Item item, String name) {
        return item.setUnlocalizedName(name).setRegistryName(new ResourceLocation(EmbersConstruct.MODID, name));
    }
}
