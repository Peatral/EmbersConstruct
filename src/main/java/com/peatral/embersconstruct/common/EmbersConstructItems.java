package com.peatral.embersconstruct.common;

import com.peatral.embersconstruct.common.item.ItemStamp;
import com.peatral.embersconstruct.common.item.ItemStampRaw;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@GameRegistry.ObjectHolder(EmbersConstruct.MODID)
public class EmbersConstructItems {

    public static final Item Stamp = new ItemStamp();
    public static final Item StampRaw = new ItemStampRaw();

    public static void registerItems(IForgeRegistry<Item> registry) {
        registry.register(init(StampRaw, "stamp_raw"));
        registry.register(init(Stamp, "stamp"));
    }

    public static Item init(Item item, String name) {
        return item.setUnlocalizedName(name).setRegistryName(new ResourceLocation(EmbersConstruct.MODID, name));
    }
}
