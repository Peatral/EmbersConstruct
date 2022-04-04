package com.peatral.embersconstruct.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import slimeknights.tconstruct.library.materials.Material;
import teamroots.embers.ConfigManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public enum OreDictValues {
    INGOT("ingot", () -> Material.VALUE_Ingot),
    ORE("ore", () -> ConfigManager.melterOreAmount * 2),
    BLOCK("block", () -> Material.VALUE_Block),
    NUGGET("nugget", () -> Material.VALUE_Nugget),
    DUST("dust", () -> Material.VALUE_Ingot),
    SHARD("shard", () -> Material.VALUE_Shard),
    PLATE("plate", () -> ConfigManager.stampPlateAmount * Material.VALUE_Ingot),
    GEM("gem", () -> Material.VALUE_Gem),
    GEAR("gear", () -> ConfigManager.stampGearAmount * Material.VALUE_Ingot);

    private String name;
    private Supplier<Integer> meltingvalue;

    OreDictValues(String name, Supplier<Integer> meltingvalue) {
        this.name = name;
        this.meltingvalue = meltingvalue;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return meltingvalue.get();
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

        if (stack == null || stack.isEmpty())
            return map;

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
                    map.put(name.replace("*_", mv.name), gem && mv != GEM ? mv.getValue() / INGOT.getValue() * GEM.getValue() : mv.getValue());
                }
            }
        }

        return map;
    }
}
