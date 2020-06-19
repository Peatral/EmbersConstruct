package com.peatral.embersconstruct.common.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import slimeknights.tconstruct.library.materials.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum OreDictValues {
    INGOT("ingot", Material.VALUE_Ingot),
    ORE("ore", Material.VALUE_Ore()),
    BLOCK("block", Material.VALUE_Block),
    NUGGET("nugget", Material.VALUE_Nugget),
    DUST("dust", Material.VALUE_Ingot),
    SHARD("shard", Material.VALUE_Shard),
    PLATE("plate", Material.VALUE_Ingot),
    GEM("gem", Material.VALUE_Gem),
    GEAR("gear", Material.VALUE_Ingot * 4);

    private String name;
    private int meltingvalue;

    OreDictValues(String name, int meltingvalue) {
        this.name = name;
        this.meltingvalue = meltingvalue;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return meltingvalue;
    }

    public static List<String> names() {
        List<String> names = new ArrayList<>();
        for (OreDictValues m : values()) names.add(m.getName());
        return names;
    }

    public static OreDictValues getByName(String name) {
        for (OreDictValues m : values()) if (m.name.equals(name)) return m;
        return null;
    }

    public static Map<String, Integer> getValuesFromDict(ItemStack stack) {
        Map<String, Integer> map = new HashMap<>();

        //OreDictionary Magic
        int[] ids = OreDictionary.getOreIDs(stack);
        for (int id : ids) {
            String name = OreDictionary.getOreName(id);

            boolean gem = name.startsWith(GEM.name);

            //Unify name to *_*
            for (String type : OreDictValues.names()) name = name.replace(type, "*_");

            if (name.startsWith("*_")) {
                //Add all "variants"
                for (OreDictValues mv : OreDictValues.values()) {
                    map.put(name.replace("*_", mv.name), gem && mv != GEM ? mv.meltingvalue / INGOT.meltingvalue * GEM.meltingvalue : mv.meltingvalue);
                }
            }
        }

        return map;
    }
}
