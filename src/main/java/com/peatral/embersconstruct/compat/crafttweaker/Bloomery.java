package com.peatral.embersconstruct.compat.crafttweaker;

import com.peatral.embersconstruct.registry.BloomeryRecipes;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.CraftTweaker;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ZenRegister
@ZenClass(Bloomery.CLASS)
public class Bloomery {
    public static final String NAME = "Bloomery";
    public static final String CLASS = "mods.embersconstruct.Bloomery";

    @ZenMethod
    public static void add(IIngredient inputA, IIngredient inputB, IItemStack output) {
        BloomeryRecipes.Recipe recipe = new BloomeryRecipes.Recipe(CraftTweakerMC.getIngredient(inputA), CraftTweakerMC.getIngredient(inputB), CraftTweakerMC.getItemStack(output));
        CraftTweaker.LATE_ACTIONS.add(new Add(recipe));
    }

    @ZenMethod
    public static void removeByInput(IItemStack input) {
        CraftTweaker.LATE_ACTIONS.add(new RemoveByInput(CraftTweakerMC.getItemStack(input)));
    }

    @ZenMethod
    public static void removeByInput(IItemStack inputA, IItemStack inputB) {
        CraftTweaker.LATE_ACTIONS.add(new RemoveByInputs(CraftTweakerMC.getItemStack(inputA), CraftTweakerMC.getItemStack(inputB)));
    }

    @ZenMethod
    public static void removeByOutput(IItemStack output) {
        CraftTweaker.LATE_ACTIONS.add(new RemoveByOutput(CraftTweakerMC.getItemStack(output)));
    }

    private static List<BloomeryRecipes.Recipe> getRecipesByInputs(ItemStack itemstackA, ItemStack itemstackB) {
        return BloomeryRecipes.instance().getRecipeList().stream().filter(recipe -> recipe.getInputA().apply(itemstackA) && recipe.getInputB().apply(itemstackB) || recipe.getInputA().apply(itemstackB) && recipe.getInputB().apply(itemstackA)).collect(Collectors.toCollection(ArrayList::new));
    }

    private static List<BloomeryRecipes.Recipe> getRecipesByInput(ItemStack stack) {
        return BloomeryRecipes.instance().getRecipeList().stream().filter(recipe -> recipe.getInputA().apply(stack) || recipe.getInputB().apply(stack)).collect(Collectors.toCollection(ArrayList::new));
    }

    private static List<BloomeryRecipes.Recipe> getRecipesByOutput(ItemStack stack) {
        return BloomeryRecipes.instance().getRecipeList().stream().filter(recipe -> stack.isItemEqual(recipe.getOutput())).collect(Collectors.toCollection(ArrayList::new));
    }

    public static class Add implements IAction {
        BloomeryRecipes.Recipe recipe;

        public Add(BloomeryRecipes.Recipe recipe) {
            this.recipe = recipe;
        }

        @Override
        public void apply() {
            BloomeryRecipes.instance().addRecipe(recipe);
        }

        @Override
        public String describe() {
            return String.format("Adding %s recipe: %s",NAME,recipe.toString());
        }
    }

    public static class RemoveByInput implements IAction {
        ItemStack input;

        protected RemoveByInput(ItemStack input) {
            this.input = input;
        }

        @Override
        public void apply() {
            BloomeryRecipes.instance().removeAll(getRecipesByInput(input));
        }

        @Override
        public String describe() {
            return String.format("Removing %s recipes with input: %s", NAME, input.toString());
        }
    }

    public static class RemoveByInputs implements IAction {
        ItemStack inputA;
        ItemStack inputB;

        protected RemoveByInputs(ItemStack inputA, ItemStack inputB) {
            this.inputA = inputA;
            this.inputB = inputB;
        }

        @Override
        public void apply() {
            BloomeryRecipes.instance().removeAll(getRecipesByInputs(inputA, inputB));
        }

        @Override
        public String describe() {
            return String.format("Removing %s recipes with inputs: %s, %s", NAME, inputA.toString(), inputB.toString());
        }
    }

    public static class RemoveByOutput implements IAction {
        ItemStack output;

        protected RemoveByOutput(ItemStack output) {
            this.output = output;
        }

        @Override
        public void apply() {
            BloomeryRecipes.instance().removeAll(getRecipesByOutput(output));
        }

        @Override
        public String describe() {
            return String.format("Removing %s recipes with output: %s",NAME,output.toString());
        }
    }

}
