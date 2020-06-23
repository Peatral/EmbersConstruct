package com.peatral.embersconstruct.compat.jei.wrapper;

import com.google.common.collect.Lists;
import com.peatral.embersconstruct.registry.KilnRecipes;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;

public class KilnRecipeWrapper extends BaseRecipeWrapper<KilnRecipes.Recipe> {
    public KilnRecipeWrapper(KilnRecipes.Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(ItemStack.class, Lists.newArrayList(recipe.getInput().getMatchingStacks()));
        ingredients.setOutput(ItemStack.class, recipe.getOutput());
    }
}
