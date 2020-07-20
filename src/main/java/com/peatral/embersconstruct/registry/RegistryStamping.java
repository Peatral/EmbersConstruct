package com.peatral.embersconstruct.registry;

import com.peatral.embersconstruct.EmbersConstruct;
import com.peatral.embersconstruct.EmbersConstructConfig;
import com.peatral.embersconstruct.EmbersConstructItems;
import com.peatral.embersconstruct.item.ItemStamp;
import com.peatral.embersconstruct.util.OreDictValues;
import com.peatral.embersconstruct.util.Stamp;
import com.peatral.embersconstruct.util.Util;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.crafting.IngredientNBT;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.oredict.OreDictionary;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tools.IToolPart;
import soot.Registry;
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
        if (EmbersConstruct.isSootLoaded) registerSootRecipes();
        EmbersConstruct.logger.info("Registered " + c + " stamping recipes.");
    }

    public static void registerOreDictRecipes() {
        registerFromOreDict(OreDictValues.INGOT.getName(), new ItemStack(RegistryManager.stamp_bar), OreDictValues.INGOT.getValue());
        registerFromOreDict(OreDictValues.GEAR.getName(), new ItemStack(RegistryManager.stamp_gear), OreDictValues.GEAR.getValue());
        registerFromOreDict(OreDictValues.PLATE.getName(), new ItemStack(RegistryManager.stamp_plate), OreDictValues.PLATE.getValue());
        if (EmbersConstructConfig.embersConstructSettings.dustStamping) registerItemFromOreDict(OreDictValues.DUST.getName(), OreDictValues.INGOT.getName(), new ItemStack(RegistryManager.stamp_flat), 1);
    }

    @Optional.Method(modid = "soot")
    public static void registerSootRecipes() {
        registerFromOreDict(OreDictValues.NUGGET.getName(), new ItemStack(Registry.STAMP_NUGGET), OreDictValues.NUGGET.getValue());
        EmbersConstruct.logger.info("Registered Soot Stamping");
    }

    public static void registerTinkerRecipes() {
        Collection<Material> materials = TinkerRegistry.getAllMaterials();
        for (Stamp stamp : RegistryStamps.registry.getValuesCollection()) {
            if (stamp.usesCustomFluid()) {
                registerMeta(new ItemStack(stamp.getItem()), new FluidStack(stamp.getFluid(), stamp.getCost()), stamp);
            } else if (stamp.usesOreDictKey()) {
                for (String oreDictName : OreDictionary.getOreNames()) {
                    if (oreDictName.startsWith(stamp.getOreDictKey())) {
                        NonNullList<ItemStack> ores = OreDictionary.getOres(oreDictName);
                        for (ItemStack ore : ores) {
                            List<String> names = new ArrayList<>();
                            for (String fluidName : FluidRegistry.getRegisteredFluids().keySet()) {
                                for (int oreId : OreDictionary.getOreIDs(ore)) {

                                    if (OreDictionary.getOreName(oreId).replace(stamp.getOreDictKey(), "").toLowerCase().equals(Util.getOreDictFromFluid(fluidName))) {
                                        names.add(fluidName);
                                    }
                                }
                            }
                            for (String name : names) registerMeta(ore, FluidRegistry.getFluidStack(name, stamp.getCost()), stamp);
                        }
                    }
                }

            } else {
                for (Material material : materials) {
                    Fluid fluid = Util.getFluidFromMaterial(material);
                    if (fluid != null) {
                        ItemStack result = null;
                        if (stamp.getItem() instanceof IToolPart) {
                            IToolPart part = (IToolPart) stamp.getItem();
                            if (part.canUseMaterial(material)) result = part.getItemstackWithMaterial(material);
                        } else {
                            result = new ItemStack(stamp.getItem());
                        }
                        if (result != null) {
                            registerMeta(result, FluidRegistry.getFluidStack(fluid.getName(), stamp.getCost()), stamp);
                        }
                    }
                }
            }
        }

    }

    public static void registerFromOreDict(String key, ItemStack stamp, int cost) {
        Map<String, Fluid> fluids = FluidRegistry.getRegisteredFluids();
        for (String fluidName : fluids.keySet()) {
            String oreName = Util.getOreDictFromFluid(fluidName);
            if (oreName.length() > 0) {
                for (ItemStack result : OreDictionary.getOres(key + Character.toString(oreName.charAt(0)).toUpperCase() + oreName.substring(1))) {
                    register(stamp, result, new FluidStack(fluids.get(fluidName), cost));
                }
            }
        }
    }

    public static void registerItemFromOreDict(String outputkey, String inputkey, ItemStack stamp, int amount) {
        for (String oreName : OreDictionary.getOreNames()) {
            if (oreName.startsWith(outputkey)) {
                String ore = oreName.replace(outputkey, "").toLowerCase();
                if (ore.length() > 0) {
                    for (ItemStack input : OreDictionary.getOres(inputkey + Character.toString(ore.charAt(0)).toUpperCase() + ore.substring(1))) {
                        if (OreDictionary.getOres(oreName).size() > 0) {
                            ItemStack result = OreDictionary.getOres(oreName).get(0);
                            result.setCount(amount);
                            register(Ingredient.fromStacks(input), new IngredientNBT(stamp) {
                            }, result, null);
                        }
                    }
                }
            }
        }
    }

    public static void registerMeta(ItemStack result, FluidStack fluid, Stamp stamp) {
        register(((ItemStamp)EmbersConstructItems.Stamp).fromStamp(stamp), result, fluid);
    }

    public static void register(ItemStack stamp, ItemStack result, FluidStack fluid) {
        register(Ingredient.EMPTY, new IngredientNBT(stamp){}, result, fluid);
    }

    public static void register(Ingredient input, Ingredient stamp, ItemStack result, FluidStack fluid) {
        boolean found = false;
        for (ItemStampingRecipe test : RecipeRegistry.stampingRecipes) {

            if (test.result.isItemEqual(result) && test.result.getCount() == result.getCount()) {
                if (test.input.getMatchingStacks() == input.getMatchingStacks()) {
                    if (test.fluid == null) {
                        if (fluid == null) {
                            found = true;
                            break;
                        }
                    } else if (test.fluid.getFluid().getName() == fluid.getFluid().getName() && test.fluid.amount == fluid.amount) {
                        found = true;
                        break;
                    }
                }
            }
        }
        if (!found) {
            RecipeRegistry.stampingRecipes.add(new ItemStampingRecipe(input, fluid, stamp, result));
            c++;
        }
    }
}
