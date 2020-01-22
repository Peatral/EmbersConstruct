package com.peatral.embersconstruct.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class CreativeTabEmbersConstruct extends CreativeTabs {
    public CreativeTabEmbersConstruct() {
        super("tabEmbersConstruct");
    }

    @Nonnull
    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(EmbersConstructItems.Stamp, 1, 3);
    }
}
