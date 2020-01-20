package com.peatral.embersconstruct.common.item;

import com.peatral.embersconstruct.common.EnumStamps;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;
import java.util.Locale;

public class ItemStamp extends ItemEmbersConstruct implements IMetaItem {

    public ItemStamp() {
        super();
        setHasSubtypes(true);
    }

    @Override
    public String getTexture(int meta) {
        return "stamp_" + EnumStamps.values()[meta].getName();
    }

    @Override
    public int getVariants() {
        return EnumStamps.values().length;
    }

    @Override
    public void getSubItems(@Nonnull CreativeTabs tabs, @Nonnull NonNullList<ItemStack> itemList) {
        if (isInCreativeTab(tabs)) {
            for (int counter = 0; counter < EnumStamps.values().length; counter++) {
                itemList.add(new ItemStack(this, 1, counter));
            }
        }
    }

    @Nonnull
    @Override
    public String getUnlocalizedName(ItemStack item) {
        if (item.getItemDamage() <= EnumStamps.values().length - 1) {
            return "item.stamp_" + EnumStamps.values()[item.getItemDamage()].getName().toLowerCase(Locale.ROOT);
        }
        return "Invalid";
    }
}