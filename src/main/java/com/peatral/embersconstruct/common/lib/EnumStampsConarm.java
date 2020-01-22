package com.peatral.embersconstruct.common.lib;

import c4.conarm.common.ConstructsRegistry;
import net.minecraft.item.Item;

public enum EnumStampsConarm {
    ARMOR_PLATE("armor_plate", ConstructsRegistry.armorPlate),
    ARMOR_TRIM("armor_trim", ConstructsRegistry.armorTrim),
    BOOTS_CORE("boots_core", ConstructsRegistry.bootsCore),
    CHEST_CORE("chest_core", ConstructsRegistry.chestCore),
    HELMET_CORE("helmet_core", ConstructsRegistry.helmetCore),
    LEGGINGS_CORE("leggings_core", ConstructsRegistry.leggingsCore);


    private String name;
    private Item part;

    EnumStampsConarm(String name, Item part) {
        this.name = name;
        this.part = part;
    }

    public String getName() {
        return name;
    }

    public Item getPart() {
        return part;
    }
}
