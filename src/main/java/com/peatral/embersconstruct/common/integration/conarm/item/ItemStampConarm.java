package com.peatral.embersconstruct.common.integration.conarm.item;

import com.peatral.embersconstruct.common.item.IMetaItem;
import com.peatral.embersconstruct.common.item.ItemEmbersConstruct;
import com.peatral.embersconstruct.common.integration.conarm.lib.EnumStampsConarm;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;
import java.util.Locale;

public class ItemStampConarm extends ItemEmbersConstruct implements IMetaItem {

    public ItemStampConarm() {
        super();
        setHasSubtypes(true);
    }

    @Override
    public String getTexture(int meta) {
        return "stamp_" + EnumStampsConarm.values()[meta].getName();
    }

    @Override
    public int getVariants() {
        return EnumStampsConarm.values().length;
    }

    @Override
    public void getSubItems(@Nonnull CreativeTabs tabs, @Nonnull NonNullList<ItemStack> itemList) {
        if (isInCreativeTab(tabs)) {
            for (int counter = 0; counter < EnumStampsConarm.values().length; counter++) {
                itemList.add(new ItemStack(this, 1, counter));
            }
        }
    }

    @Nonnull
    @Override
    public String getUnlocalizedName(ItemStack item) {
        if (item.getItemDamage() <= EnumStampsConarm.values().length - 1) {
            return "item.stamp_" + EnumStampsConarm.values()[item.getItemDamage()].getName().toLowerCase(Locale.ROOT);
        }
        return "Invalid";
    }
}