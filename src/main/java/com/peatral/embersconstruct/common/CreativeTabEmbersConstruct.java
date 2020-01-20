package com.peatral.embersconstruct.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import slimeknights.mantle.client.CreativeTab;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;

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
