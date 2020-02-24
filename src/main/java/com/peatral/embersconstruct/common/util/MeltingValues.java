package com.peatral.embersconstruct.common.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import slimeknights.tconstruct.library.materials.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum MeltingValues {
    INGOT("ingot", Material.VALUE_Ingot),
    ORE("ore", Material.VALUE_Ore()),
    BLOCK("block", Material.VALUE_Block),
    NUGGET("nugget", Material.VALUE_Nugget),
    DUST("dust", Material.VALUE_Ingot),
    SHARD("shard", Material.VALUE_Shard),
    PLATE("plate", Material.VALUE_Ingot);

    private String name;
    private int value;

    MeltingValues(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public static List<String> names() {
        List<String> names = new ArrayList<>();
        for (MeltingValues m : values()) names.add(m.getName());
        return names;
    }

    public static MeltingValues getByName(String name) {
        for (MeltingValues m : values()) if (m.name.equals(name)) return m;
        return null;
    }

    public static Map<String, Integer> getValuesFromDict(ItemStack stack) {
        Map<String, Integer> map = new HashMap<>();

        //OreDictionary Magic
        int[] ids = OreDictionary.getOreIDs(stack);
        for (int id : ids) {
            String name = OreDictionary.getOreName(id);
            //Unify name to *_*
            for (String type : MeltingValues.names()) name = name.replace(type, "*_");

            if (name.startsWith("*_")) {
                //Add all "variants"
                for (MeltingValues mv : MeltingValues.values()) {
                    map.put(name.replace("*_", mv.getName()), mv.getValue());
                }
            }
        }

        return map;
    }
}
