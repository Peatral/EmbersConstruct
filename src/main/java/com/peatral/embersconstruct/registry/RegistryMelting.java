package com.peatral.embersconstruct.registry;

import com.peatral.embersconstruct.EmbersConstruct;
import com.peatral.embersconstruct.EmbersConstructBlocks;
import com.peatral.embersconstruct.EmbersConstructItems;
import com.peatral.embersconstruct.util.OreDictValues;
import com.peatral.embersconstruct.util.Stamp;
import com.peatral.embersconstruct.util.Util;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.IngredientNBT;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.smeltery.MeltingRecipe;
import slimeknights.tconstruct.library.tools.IToolPart;
import slimeknights.tconstruct.shared.TinkerFluids;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import teamroots.embers.recipe.ItemMeltingRecipe;
import teamroots.embers.recipe.RecipeRegistry;

import java.util.Collection;
import java.util.Map;

public class RegistryMelting {

    private static int c = 0;

    public static void main() {
        registerOreDictRecipes();
        registerTinkerRecipes();

        registerFromOreDict(new ItemStack(EmbersConstructItems.WroughtIronIngot), TinkerFluids.iron, OreDictValues.INGOT.getValue());
        registerFromOreDict(new ItemStack(EmbersConstructItems.WroughtIronNugget), TinkerFluids.iron, OreDictValues.NUGGET.getValue());
        registerFromOreDict(new ItemStack(EmbersConstructBlocks.WroughtIronBlock), TinkerFluids.iron, OreDictValues.BLOCK.getValue());

        EmbersConstruct.logger.info("Registered " + c + " melting recipes.");
    }

    public static void registerOreDictRecipes() {
        for (OreDictValues mv : OreDictValues.values()) {
            registerFromOreDict(mv.getName(), mv.getValue());
        }
    }

    public static void registerTinkerRecipes() {

        Collection<Material> materials = TinkerRegistry.getAllMaterials();
        for (Material material : materials) {
            Fluid fluid = Util.getFluidFromMaterial(material);
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

        for (MeltingRecipe recipe : TinkerRegistry.getAllMeltingRecipies()) {
            for (ItemStack input : recipe.input.getInputs())
                registerBasic(input, recipe.output.getFluid(), recipe.output.amount);
        }

        for (Stamp stamp : RegistryStamps.registry.getValuesCollection()) {
            if (stamp.usesCustomFluid()) {
                registerFromOreDict(new ItemStack(stamp.getItem()), stamp.getFluid(), stamp.getCost());
            }
        }
    }

    public static void registerFromOreDict(ItemStack item, Fluid fluid) {
        registerFromOreDict(item, fluid, OreDictValues.INGOT.getValue());
    }


    public static void registerFromOreDict(ItemStack item, Fluid fluid, int fallbackCost) {
        Map<String, Integer> oreDictVals = OreDictValues.getValuesFromDict(item);
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

    public static void registerFromOreDict(String key, int cost) {
        Map<String, Fluid> fluids = FluidRegistry.getRegisteredFluids();
        for (String fluidName : fluids.keySet()) {
            String oreName = Util.getOreDictFromFluid(fluidName);
            if (fluidName.length() > 0) {
                for (ItemStack result : OreDictionary.getOres(key + Character.toString(oreName.charAt(0)).toUpperCase() + oreName.substring(1))) {
                    registerBasic(result, fluids.get(fluidName), cost);
                }
            }
        }
    }

    public static void registerBasic(ItemStack input, Fluid output, int cost) {
        registerBasic(Ingredient.fromStacks(input), new FluidStack(output, cost));
    }

    public static void registerBasic(Ingredient input, FluidStack output) {
        registerBasic(new ItemMeltingRecipe(input, output));
    }

    public static void registerBasic(ItemMeltingRecipe recipe) {
        if (RecipeRegistry.meltingRecipes.stream().anyMatch(test -> isRecipeTechnicallySame(test, recipe)))
            return;

        if (recipe.getFluid().amount <= 1500) {
            RecipeRegistry.meltingRecipes.add(recipe);
            c++;
        }
    }

    public static boolean isRecipeTechnicallySame(@NotNull ItemMeltingRecipe a, @NotNull ItemMeltingRecipe b) {
        return a.fluid.getFluid().getName().equals(b.getFluid().getFluid().getName()) && a.fluid.amount == b.getFluid().amount &&
                a.input.getValidItemStacksPacked().equals(b.getInput().getValidItemStacksPacked());
    }
}
