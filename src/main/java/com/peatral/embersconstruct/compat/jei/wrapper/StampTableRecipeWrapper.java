package com.peatral.embersconstruct.compat.jei.wrapper;

import com.google.common.collect.Lists;
import com.peatral.embersconstruct.registry.StampTableRecipes;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;

public class StampTableRecipeWrapper extends BaseRecipeWrapper<StampTableRecipes.Recipe> {
    public StampTableRecipeWrapper(StampTableRecipes.Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(VanillaTypes.ITEM, Lists.newArrayList(recipe.getInput().getMatchingStacks()));
        ingredients.setOutputs(VanillaTypes.ITEM, recipe.getOutputWithStamps());
    }


}
