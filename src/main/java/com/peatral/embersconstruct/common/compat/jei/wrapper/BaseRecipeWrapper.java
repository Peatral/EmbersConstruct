package com.peatral.embersconstruct.common.compat.jei.wrapper;

import mezz.jei.api.recipe.IRecipeWrapper;

public abstract class BaseRecipeWrapper<T> implements IRecipeWrapper {
    T recipe;

    public BaseRecipeWrapper() {
    }
}
