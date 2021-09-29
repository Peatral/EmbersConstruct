package com.peatral.embersconstruct.compat.jei.wrapper;

import com.google.common.collect.Lists;
import com.peatral.embersconstruct.registry.BloomeryRecipes;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;

public class BloomeryRecipeWrapper extends BaseRecipeWrapper<BloomeryRecipes.Recipe> {
    public BloomeryRecipeWrapper(BloomeryRecipes.Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, Lists.newArrayList(Lists.newArrayList(recipe.getInputA().getMatchingStacks()), Lists.newArrayList(recipe.getInputB().getMatchingStacks())));
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
    }
}
