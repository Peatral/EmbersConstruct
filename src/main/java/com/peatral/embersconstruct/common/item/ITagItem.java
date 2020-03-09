package com.peatral.embersconstruct.common.item;

import net.minecraft.item.ItemStack;

import java.util.List;

public interface ITagItem {
    String getTexture(ItemStack stack);
    List<ItemStack> getVariants();
}