package com.peatral.embersconstruct.common.registry;

import com.peatral.embersconstruct.common.EmbersConstruct;
import com.peatral.embersconstruct.common.EmbersConstructItems;
import com.peatral.embersconstruct.common.util.MeltingValues;
import com.peatral.embersconstruct.common.util.Stamp;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tools.IToolPart;
import teamroots.embers.RegistryManager;
import teamroots.embers.recipe.ItemStampingRecipe;
import teamroots.embers.recipe.RecipeRegistry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class RegistryStamping {

    private static int c = 0;

    public static void main() {
        registerOreDictRecipes();
        registerTinkerRecipes();
        EmbersConstruct.logger.info("Registered " + c + " stamping recipes.");
    }

    public static void registerOreDictRecipes() {
        registerFromOreDict(MeltingValues.INGOT.getName(), new ItemStack(RegistryManager.stamp_bar), MeltingValues.INGOT.getValue());
        registerFromOreDict(MeltingValues.GEAR.getName(), new ItemStack(RegistryManager.stamp_gear), MeltingValues.GEAR.getValue());
        registerFromOreDict(MeltingValues.PLATE.getName(), new ItemStack(RegistryManager.stamp_plate), MeltingValues.PLATE.getValue());
    }

    public static void registerTinkerRecipes() {
        Collection<Material> materials = TinkerRegistry.getAllMaterials();
        for (int i = 0; i < RegistryStamps.values().size(); i++) {
            Stamp stamp = RegistryStamps.values().get(i);
            if (stamp.usesCustomFluid()) {
                registerMeta(new ItemStack(stamp.getItem()), new FluidStack(stamp.getFluid(), stamp.getCost()), i);
            } else if (stamp.usesOreDictKey()) {
                for (String oreDictName : OreDictionary.getOreNames()) {
                    if (oreDictName.startsWith(stamp.getOreDictKey())) {
                        NonNullList<ItemStack> ores = OreDictionary.getOres(oreDictName);
                        for (ItemStack ore : ores) {
                            List<String> names = new ArrayList<>();
                            for (String fluidName : FluidRegistry.getRegisteredFluids().keySet()) {
                                System.out.println(fluidName);
                                for (int oreId : OreDictionary.getOreIDs(ore)) {

                                    if (OreDictionary.getOreName(oreId).replace(stamp.getOreDictKey(), "").toLowerCase().equals(fluidName)) {
                                        names.add(fluidName);
                                    }
                                }
                            }
                            for (String name : names) registerMeta(ore, FluidRegistry.getFluidStack(name, stamp.getCost()), i);
                        }
                    }
                }

            } else {
                for (Material material : materials) {
                    if (FluidRegistry.isFluidRegistered(material.identifier)) {
                        ItemStack result = null;
                        if (stamp.getItem() instanceof IToolPart) {
                            IToolPart part = (IToolPart) stamp.getItem();
                            if (part.canUseMaterial(material)) result = part.getItemstackWithMaterial(material);
                        } else {
                            result = new ItemStack(stamp.getItem());
                        }
                        if (result != null) {
                            registerMeta(result, FluidRegistry.getFluidStack(material.identifier, stamp.getCost()), i);
                        }
                    }
                }
            }
        }

    }

    public static void registerFromOreDict(String key, ItemStack stamp, int cost) {
        Map<String, Fluid> fluids = FluidRegistry.getRegisteredFluids();
        for (String fluidName : fluids.keySet()) {
            for (ItemStack result : OreDictionary.getOres(key + Character.toString(fluidName.charAt(0)).toUpperCase() + fluidName.substring(1))) {
                register(stamp, result, new FluidStack(fluids.get(fluidName), cost));
            }
        }
    }

    public static void registerMeta(ItemStack result, FluidStack fluid, int meta) {
        register(new ItemStack(EmbersConstructItems.Stamp, 1, meta), result, fluid);
    }

    public static void register(ItemStack stamp, ItemStack result, FluidStack fluid) {
        RecipeRegistry.stampingRecipes.add(new ItemStampingRecipe(Ingredient.EMPTY, fluid, Ingredient.fromStacks(stamp), result));
        c++;
    }
}
