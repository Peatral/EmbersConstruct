package com.peatral.embersconstruct.common.registry;

import com.peatral.embersconstruct.common.EmbersConstructItems;
import com.peatral.embersconstruct.common.EnumStamps;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.Fluid;
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
import java.util.HashMap;
import java.util.Map;

public class RegistryMelting {
    public static void registerRecipes() {
        for (int i = 0; i < EnumStamps.values().length; i++) {
            GameRegistry.addSmelting(new ItemStack(EmbersConstructItems.StampRaw,1, i), new ItemStack(EmbersConstructItems.Stamp, 1, i), 1.0f);
        }
    }

    public static void registerTinkerRecipes() {
        Collection<Material> materials = TinkerRegistry.getAllMaterials();
        for (Material material : materials) {
            Fluid fluid = material.getFluid();
            if (fluid != null) {
                ItemStack repItem = material.getRepresentativeItem();

                //OreDictionary Magic
                int[] ids = OreDictionary.getOreIDs(repItem);
                for (int id : ids) {
                    String name = OreDictionary.getOreName(id);
                    //Unify name to ingot*
                    name = name.replace("ore", "ingot").replace("block", "ingot").replace("nugget", "ingot");
                    if (name.startsWith("ingot")) {
                        //Add all "variants"
                        Map<String, Integer> names = new HashMap<>();
                        names.put(name, Material.VALUE_Ingot);
                        names.put(name.replace("ingot", "ore"), Material.VALUE_Ore());
                        names.put(name.replace("ingot", "block"), Material.VALUE_Block);
                        names.put(name.replace("ingot", "nugget"), Material.VALUE_Nugget);
                        //Register recipes for "variants"
                        for (String alternateName : names.keySet()) {
                            for (ItemStack stack : OreDictionary.getOres(alternateName)) {
                                RecipeRegistry.meltingRecipes.add(new ItemMeltingRecipe(Ingredient.fromStacks(stack), new FluidStack(fluid, names.get(alternateName))));
                            }
                        }
                    } else {
                        //If that thing isn't an ingot??
                        for (ItemStack stack : OreDictionary.getOres(name)) {
                            RecipeRegistry.meltingRecipes.add(new ItemMeltingRecipe(Ingredient.fromStacks(stack), new FluidStack(fluid, Material.VALUE_Ingot)));
                        }
                    }
                }
                //If that thing isn't even in the OreDictionary??
                if (ids.length == 0) RecipeRegistry.meltingRecipes.add(new ItemMeltingRecipe(Ingredient.fromStacks(repItem), new FluidStack(fluid, Material.VALUE_Ingot)));



                //Toolpart smelting
                for (IToolPart toolPart : TinkerRegistry.getToolParts()) {
                    if (toolPart instanceof MaterialItem) {
                        ItemStack stack = toolPart.getItemstackWithMaterial(material);
                        Ingredient ingredient = Ingredient.fromStacks(stack);
                        RecipeRegistry.meltingRecipes.add(new ItemMeltingRecipe(ingredient, new FluidStack(fluid, toolPart.getCost())));
                    }
                }
            }
        }
    }
}
