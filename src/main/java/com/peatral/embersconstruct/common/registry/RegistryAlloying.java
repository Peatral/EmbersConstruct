package com.peatral.embersconstruct.common.registry;

import com.peatral.embersconstruct.common.EmbersConstruct;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.smeltery.AlloyRecipe;
import teamroots.embers.recipe.FluidMixingRecipe;
import teamroots.embers.recipe.RecipeRegistry;

import java.util.List;

public class RegistryAlloying {
    public static void registerTinkerRecipes() {
        List<AlloyRecipe> alloyRecipes = TinkerRegistry.getAlloys();
        int c = 0;
        for (AlloyRecipe rec : alloyRecipes) {
            boolean flag = true;
            for (FluidStack s : rec.getFluids()) {
                if (s.amount > 16) flag = false;
            }
            if (flag) {
                RecipeRegistry.mixingRecipes.add(new FluidMixingRecipe(rec.getFluids().toArray(new FluidStack[]{}), rec.getResult()));
                c++;
            }
        }
        // EmbersConstruct.logger.info("Registered " + c + " alloying recipes from Tinkers'.");
    }
}
