package com.peatral.embersconstruct.registry;

import com.peatral.embersconstruct.EmbersConstructBlocks;
import com.peatral.embersconstruct.EmbersConstructItems;
import net.minecraft.item.ItemStack;
import teamroots.embers.RegistryManager;
import teamroots.embers.research.ResearchBase;
import teamroots.embers.research.ResearchCategory;
import teamroots.embers.research.ResearchManager;
import teamroots.embers.research.subtypes.ResearchShowItem;
import teamroots.embers.research.subtypes.ResearchSwitchCategory;

import static teamroots.embers.research.ResearchManager.PAGE_ICONS;

public class RegistryResearch {

    public static ResearchBase stamps;
    public static ResearchBase bloomery;

    public static void main() {
        ResearchCategory categoryWorld = ResearchManager.categoryWorld;
        ResearchCategory categoryMechanisms = ResearchManager.categoryMechanisms;

        bloomery = new ResearchShowItem("bloomery", new ItemStack(EmbersConstructBlocks.Bloomery), 6, 0).addItem(new ResearchShowItem.DisplayItem(new ItemStack(EmbersConstructBlocks.Bloomery))).addAncestor(ResearchManager.caminite);
        bloomery.addPage(new ResearchShowItem("wrought_iron", ItemStack.EMPTY, 0, 0).addItem(new ResearchShowItem.DisplayItem(new ItemStack(EmbersConstructItems.WroughtIronIngot))));

        stamps = new ResearchShowItem("stamps", new ItemStack(RegistryManager.stamp_gear), 2, 7).addItem(new ResearchShowItem.DisplayItem(new ItemStack(RegistryManager.stamp_flat_raw))).addAncestor(ResearchManager.stamper);
        stamps.addPage(new ResearchShowItem("stamping_table", ItemStack.EMPTY, 0, 0).addItem(new ResearchShowItem.DisplayItem(new ItemStack(EmbersConstructBlocks.StampTable))));
        stamps.addPage(new ResearchShowItem("kiln", ItemStack.EMPTY, 0, 0).addItem(new ResearchShowItem.DisplayItem(new ItemStack(EmbersConstructBlocks.Kiln))));

        categoryWorld.addResearch(bloomery);
        categoryMechanisms.addResearch(stamps);
    }

    private static ResearchSwitchCategory makeCategorySwitch(ResearchCategory targetCategory, int x, int y, ItemStack icon, int u, int v) {
        return (ResearchSwitchCategory)(new ResearchSwitchCategory(targetCategory.name + "_category", icon, (double)x, (double)y)).setTargetCategory(targetCategory).setIconBackground(PAGE_ICONS, 0.09375D * (double)u, 0.09375D * (double)v);
    }
}
