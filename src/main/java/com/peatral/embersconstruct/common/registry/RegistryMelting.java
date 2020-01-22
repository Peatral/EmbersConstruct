package com.peatral.embersconstruct.common.registry;

import com.peatral.embersconstruct.common.EmbersConstruct;
import com.peatral.embersconstruct.common.EmbersConstructItems;
import com.peatral.embersconstruct.common.lib.EnumStamps;
import com.peatral.embersconstruct.common.lib.EnumStampsConarm;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Optional;
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
    private static Map<String, Integer> MELTING_VALUES = new HashMap<>();

    static {
        MELTING_VALUES.put("ingot", Material.VALUE_Ingot);
        MELTING_VALUES.put("ore", Material.VALUE_Ore());
        MELTING_VALUES.put("block", Material.VALUE_Block);
        MELTING_VALUES.put("nugget", Material.VALUE_Nugget);
        MELTING_VALUES.put("dust", Material.VALUE_Ingot);
        MELTING_VALUES.put("shard", Material.VALUE_Shard);
        MELTING_VALUES.put("plate", Material.VALUE_Ingot);
    }

    public static void main() {
        registerRecipes();
        //Includes Armorparts #Genius
        registerTinkerRecipes();
        if (EmbersConstruct.isConarmLoaded) registerConarmRecipes();
    }

    public static void registerRecipes() {
        for (int i = 0; i < EnumStamps.values().length; i++) {
            GameRegistry.addSmelting(new ItemStack(EmbersConstructItems.StampRaw,1, i), new ItemStack(EmbersConstructItems.Stamp, 1, i), 1.0f);
        }
    }

    @Optional.Method(modid="conarm")
    public static void registerConarmRecipes() {
        for (int i = 0; i < EnumStampsConarm.values().length; i++) {
            GameRegistry.addSmelting(new ItemStack(EmbersConstructItems.StampRawConarm, 1, i), new ItemStack(EmbersConstructItems.StampConarm, 1, i), 1.0f);
        }
    }

    public static void registerTinkerRecipes() {
        int c = 0;
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
                    for (String type : MELTING_VALUES.keySet()) name = name.replace(type, "k_");

                    if (name.startsWith("k_")) {
                        //Add all "variants"
                        for (String type : MELTING_VALUES.keySet()) {
                            int am = MELTING_VALUES.get(type);
                            String k =  name.replace("k_", type);

                            //Register recipes for "variants"
                            for (ItemStack stack : OreDictionary.getOres(k)) {
                                RecipeRegistry.meltingRecipes.add(new ItemMeltingRecipe(Ingredient.fromStacks(stack), new FluidStack(fluid, am)));
                                c++;
                            }
                        }

                    } else {
                        //If that thing isn't an ingot??
                        for (ItemStack stack : OreDictionary.getOres(name)) {
                            RecipeRegistry.meltingRecipes.add(new ItemMeltingRecipe(Ingredient.fromStacks(stack), new FluidStack(fluid, Material.VALUE_Ingot)));
                            c++;
                        }
                    }
                }
                //If that thing isn't even in the OreDictionary??
                if (ids.length == 0) {
                    RecipeRegistry.meltingRecipes.add(new ItemMeltingRecipe(Ingredient.fromStacks(repItem), new FluidStack(fluid, Material.VALUE_Ingot)));
                    c++;
                }



                //Toolpart smelting
                for (IToolPart toolPart : TinkerRegistry.getToolParts()) {
                    if (toolPart instanceof MaterialItem) {
                        ItemStack stack = toolPart.getItemstackWithMaterial(material);
                        Ingredient ingredient = Ingredient.fromStacks(stack);
                        RecipeRegistry.meltingRecipes.add(new ItemMeltingRecipe(ingredient, new FluidStack(fluid, toolPart.getCost())));
                        c++;
                    }
                }
            }
        }

        EmbersConstruct.logger.info("Registered " + c + " melting recipes from Tinkers'.");
    }
}
