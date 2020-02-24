package com.peatral.embersconstruct.common.item;

import com.peatral.embersconstruct.common.registry.RegistryStamps;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.materials.Material;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;

public class ItemStamp extends ItemEmbersConstruct implements IMetaItem {

    private boolean raw;

    public ItemStamp() {
        this(false);
    }

    public ItemStamp(boolean raw) {
        super();
        setHasSubtypes(true);
        this.raw = raw;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (!raw) {
            float cost = RegistryStamps.values().get(stack.getItemDamage()).getCost() / (float) Material.VALUE_Ingot;
            tooltip.add(Util.translateFormatted("tooltip.pattern.cost", Util.df.format(cost)));
        }
    }

    @Override
    public String getTexture(int meta) {
        return "stamps/stamp_" + RegistryStamps.values().get(meta).getName() + "_raw";
    }

    @Override
    public int getVariants() {
        return RegistryStamps.values().size();
    }

    @Override
    public void getSubItems(@Nonnull CreativeTabs tabs, @Nonnull NonNullList<ItemStack> itemList) {
        if (isInCreativeTab(tabs)) {
            for (int counter = 0; counter < RegistryStamps.values().size(); counter++) {
                itemList.add(new ItemStack(this, 1, counter));
            }
        }
    }

    @Nonnull
    @Override
    public String getUnlocalizedName(ItemStack item) {
        if (item.getItemDamage() < RegistryStamps.values().size()) {
            return "item.stamp_" + RegistryStamps.values().get(item.getItemDamage()).getName().toLowerCase(Locale.ROOT) + (raw ? "_raw" : "");
        }
        return "Invalid";
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        Item item = RegistryStamps.values().get(stack.getItemDamage()).getItem();
        String partName = item != null ? item.getItemStackDisplayName(new ItemStack(item)) : "";
        return I18n.translateToLocalFormatted("item.stamp" + (raw ? "_raw" : "") + ".name", partName).trim();
    }
}