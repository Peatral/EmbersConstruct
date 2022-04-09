package com.peatral.embersconstruct.util;

import com.peatral.embersconstruct.registry.RegistryStamps;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.registries.IForgeRegistryEntry;
import slimeknights.tconstruct.library.tinkering.MaterialItem;
import slimeknights.tconstruct.library.tools.IToolPart;

import java.util.function.Supplier;

public class Stamp extends IForgeRegistryEntry.Impl<Stamp> {

    public static final String STAMP_PATH = "stamp";

    private Item item;
    private String name;
    private Supplier<Integer> cost;
    private String oreDictKey;

    public Stamp(String oreDict, String name, int cost) {
        this(null, name, cost, oreDict);
    }

    public Stamp(MaterialItem item, String name) {
        this(item, name, item instanceof IToolPart ? ((IToolPart) item)::getCost : OreDictValues.INGOT::getValue, null);
    }

    public Stamp(Item item, String name, int cost) {
        this(item, name, cost, null);
    }

    public Stamp(Item item, String name, int cost, String oreDict) {
        this(item, name, () -> cost, oreDict);
    }

    public Stamp(Item item, String name, Supplier<Integer> cost, String oreDict) {
        this.item = item;
        this.name = name;
        this.cost = cost;
        this.oreDictKey = oreDict;
    }

    public Item getItem() {
        return item;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost.get();
    }

    public String getOreDictKey() {
        return oreDictKey;
    }

    public boolean usesOreDictKey() {
        return oreDictKey != null;
    }

    public static Stamp getStampFromStack(ItemStack stack) {
        if (stack.getTagCompound() != null && stack.getTagCompound().hasKey(STAMP_PATH)) {
            return RegistryStamps.registry.getValue(new ResourceLocation(stack.getTagCompound().getString(STAMP_PATH)));
        }

        return null;
    }

    public static ItemStack putStamp(ItemStack stack, Stamp stamp) {
        NBTTagCompound compound = stack.getTagCompound() == null ? new NBTTagCompound() : stack.getTagCompound();
        compound.setString(STAMP_PATH, stamp.getRegistryName().toString());
        stack.setTagCompound(compound);
        return stack;
    }
}
