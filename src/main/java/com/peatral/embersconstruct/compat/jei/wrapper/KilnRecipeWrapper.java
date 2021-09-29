package com.peatral.embersconstruct.compat.jei.wrapper;

import com.google.common.collect.Lists;
import com.peatral.embersconstruct.registry.KilnRecipes;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;

public class KilnRecipeWrapper extends BaseRecipeWrapper<KilnRecipes.Recipe> {
    public KilnRecipeWrapper(KilnRecipes.Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(VanillaTypes.ITEM, Lists.newArrayList(recipe.getInput().getMatchingStacks()));
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
    }
}
