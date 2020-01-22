package com.peatral.embersconstruct.common.lib;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import slimeknights.tconstruct.tools.TinkerTools;

public enum EnumStamps {
    ARROW_HEAD("arrow_head", TinkerTools.arrowHead),
    ARROW_SHAFT("arrow_shaft", TinkerTools.arrowShaft),
    AXE_HEAD("axe_head", TinkerTools.axeHead),
    BINDING("binding", TinkerTools.binding),
    BOW_LIMB("bow_limb", TinkerTools.bowLimb),
    BOW_STRING("bow_string", TinkerTools.bowString),
    BROAD_AXE_HEAD("broad_axe_head", TinkerTools.broadAxeHead),
    CROSS_GUARD("cross_guard", TinkerTools.crossGuard),
    EMERALD("emerald", Items.EMERALD), //GEM!!!!
    EXCAVATOR_HEAD("excavator_head", TinkerTools.excavatorHead),
    FLETCHING("fletching", TinkerTools.fletching),
    HAMMER_HEAD("hammer_head", TinkerTools.hammerHead),
    HAND_GUARD("hand_guard", TinkerTools.handGuard),
    KAMA_HEAD("kama_head", TinkerTools.kamaHead),
    KNIFE_BLADE("knife_blade", TinkerTools.knifeBlade),
    LARGE_PLATE("large_plate", TinkerTools.largePlate),
    LARGE_SWORD_BLADE("large_sword_blade", TinkerTools.largeSwordBlade),
    PAN_HEAD("pan_head", TinkerTools.panHead),
    PICK_HEAD("pick_head", TinkerTools.pickHead),
    //POLISHING_KIT("polishing_kit", TinkerIt),
    SCYTHE_HEAD("scythe_head", TinkerTools.scytheHead),
    SHARD("shard", TinkerTools.shard),
    SHARPENING_KIT("sharpening_kit", TinkerTools.sharpeningKit),
    SHOVEL_HEAD("shovel_head", TinkerTools.shovelHead),
    SIGN_HEAD("sign_head", TinkerTools.signHead),
    SWORD_BLADE("sword_blade", TinkerTools.swordBlade),
    TOOL_ROD("tool_rod", TinkerTools.toolRod),
    TOUGH_BINDING("tough_binding", TinkerTools.toughBinding),
    TOUGH_TOOL_ROD("tough_tool_rod", TinkerTools.toughToolRod),
    WIDEGUARD("wide_guard", TinkerTools.wideGuard);


    private String name;
    private Item part;

    EnumStamps(String name, Item part) {
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
