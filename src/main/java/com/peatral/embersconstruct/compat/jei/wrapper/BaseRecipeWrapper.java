package com.peatral.embersconstruct.compat.jei.wrapper;

import mezz.jei.api.recipe.IRecipeWrapper;

public abstract class BaseRecipeWrapper<T> implements IRecipeWrapper {
    T recipe;

    public BaseRecipeWrapper() {
    }
}
