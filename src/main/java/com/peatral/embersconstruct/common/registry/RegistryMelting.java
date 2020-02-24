package com.peatral.embersconstruct.common.registry;

import com.peatral.embersconstruct.common.EmbersConstruct;
import com.peatral.embersconstruct.common.EmbersConstructItems;
import com.peatral.embersconstruct.common.util.MeltingValues;
import com.peatral.embersconstruct.common.util.Stamp;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.IngredientNBT;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tinkering.MaterialItem;
import slimeknights.tconstruct.library.tools.IToolPart;
import teamroots.embers.recipe.ItemMeltingRecipe;
import teamroots.embers.recipe.RecipeRegistry;

import java.util.Collection;
import java.util.Map;

public class RegistryMelting {

    private static int c = 0;

    public static void main() {
        registerRecipes();
        registerTinkerRecipes();
    }

    public static void registerRecipes() {
        for (int i = 0; i < RegistryStamps.values().size(); i++) {
            GameRegistry.addSmelting(new ItemStack(EmbersConstructItems.StampRaw,1, i), new ItemStack(EmbersConstructItems.Stamp, 1, i), 1.0f);
        }
    }

    public static void registerTinkerRecipes() {

        Collection<Material> materials = TinkerRegistry.getAllMaterials();
        for (Material material : materials) {
            Fluid fluid = null;
            if (FluidRegistry.isFluidRegistered(material.identifier)) fluid = FluidRegistry.getFluid(material.identifier);
            if (fluid != null) {
                ItemStack repItem = material.getRepresentativeItem();

                registerFromOreDict(repItem, fluid);

                for (IToolPart toolPart : TinkerRegistry.getToolParts()) {
                    if (toolPart.canUseMaterial(material)) {
                        registerBasic(new IngredientNBT(toolPart.getItemstackWithMaterial(material)) {}, new FluidStack(fluid, toolPart.getCost()));
                    }
                }
            }
        }

        for (Stamp stamp : RegistryStamps.values()) {
            if (stamp.usesCustomFluid()) {
                registerFromOreDict(new ItemStack(stamp.getItem()), stamp.getFluid(), stamp.getCost());
            }
        }

        EmbersConstruct.logger.info("Registered " + c + " melting recipes from Tinkers'.");
    }

    public static void registerBasic(ItemStack input, Fluid output, int cost) {
        registerBasic(Ingredient.fromStacks(input), new FluidStack(output, cost));
    }

    public static void registerBasic(Ingredient input, FluidStack output) {
        RecipeRegistry.meltingRecipes.add(new ItemMeltingRecipe(input, output));
        c++;
    }

    public static void registerFromOreDict(ItemStack item, Fluid fluid) {
        registerFromOreDict(item, fluid, MeltingValues.INGOT.getValue());
    }


    public static void registerFromOreDict(ItemStack item, Fluid fluid, int fallbackCost) {
        Map<String, Integer> oreDictVals = MeltingValues.getValuesFromDict(item);

        for (String k : oreDictVals.keySet()) {
            //Register recipes for "variants"
            for (ItemStack stack : OreDictionary.getOres(k)) {
                registerBasic(Ingredient.fromStacks(stack), new FluidStack(fluid, oreDictVals.get(k)));
            }
        }

        //If that thing isn't even in the OreDictionary??
        if (oreDictVals.size() == 0) {
            registerBasic(item, fluid, fallbackCost);
        }
    }
}
