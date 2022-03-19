package com.peatral.embersconstruct.compat.crafttweaker;

import com.peatral.embersconstruct.registry.KilnRecipes;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.CraftTweaker;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ZenRegister
@ZenClass(Kiln.CLASS)
public class Kiln {
    public static final String NAME = "Kiln";
    public static final String CLASS = "mods.embersconstruct.Kiln";

    @ZenMethod
    public static void add(IIngredient input, IItemStack output) {
        KilnRecipes.Recipe recipe = new KilnRecipes.Recipe(CraftTweakerMC.getIngredient(input), CraftTweakerMC.getItemStack(output));
        CraftTweaker.LATE_ACTIONS.add(new Add(recipe));
    }

    @ZenMethod
    public static void removeByInput(IItemStack input) {
        CraftTweaker.LATE_ACTIONS.add(new RemoveByInput(CraftTweakerMC.getIngredient(input)));
    }

    @ZenMethod
    public static void removeByOutput(IItemStack output) {
        CraftTweaker.LATE_ACTIONS.add(new RemoveByOutput(CraftTweakerMC.getItemStack(output)));
    }

    private static List<KilnRecipes.Recipe> getRecipesByInput(Ingredient ingredient) {
        return KilnRecipes.instance().getRecipeList().stream().filter(recipe -> recipe.getInput().equals(ingredient)).collect(Collectors.toCollection(ArrayList::new));
    }

    private static List<KilnRecipes.Recipe> getRecipesByOutput(ItemStack stack) {
        return KilnRecipes.instance().getRecipeList().stream().filter(recipe -> stack.isItemEqual(recipe.getOutput())).collect(Collectors.toCollection(ArrayList::new));
    }

    public static class Add implements IAction {
        KilnRecipes.Recipe recipe;

        public Add(KilnRecipes.Recipe recipe) {
            this.recipe = recipe;
        }

        @Override
        public void apply() {
            KilnRecipes.instance().addRecipe(recipe);
        }

        @Override
        public String describe() {
            return String.format("Adding %s recipe: %s",NAME,recipe.toString());
        }
    }

    public static class RemoveByInput implements IAction {
        Ingredient input;

        protected RemoveByInput(Ingredient input) {
            this.input = input;
        }

        @Override
        public void apply() {
            KilnRecipes.instance().removeAll(getRecipesByInput(input));
        }

        @Override
        public String describe() {
            return String.format("Removing %s recipes with input: %s",NAME,input.toString());
        }
    }

    public static class RemoveByOutput implements IAction {
        ItemStack output;

        protected RemoveByOutput(ItemStack output) {
            this.output = output;
        }

        @Override
        public void apply() {
            KilnRecipes.instance().removeAll(getRecipesByOutput(output));
        }

        @Override
        public String describe() {
            return String.format("Removing %s recipes with output: %s",NAME,output.toString());
        }
    }

}
