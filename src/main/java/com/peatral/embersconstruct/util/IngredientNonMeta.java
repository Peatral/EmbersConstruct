package com.peatral.embersconstruct.util;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import javax.annotation.Nullable;

public class IngredientNonMeta extends Ingredient {

    public IngredientNonMeta(ItemStack stack) {
        super(stack);
    }

    @Override
    public boolean apply(@Nullable ItemStack stack) {
        for (ItemStack stack1 : getMatchingStacks()) {
            if (stack1.getItem() == stack.getItem())
                return true;
        }
        return false;
    }
}
