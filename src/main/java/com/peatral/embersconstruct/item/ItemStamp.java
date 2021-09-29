package com.peatral.embersconstruct.item;

import com.peatral.embersconstruct.EmbersConstruct;
import com.peatral.embersconstruct.registry.RegistryStamps;
import com.peatral.embersconstruct.util.Stamp;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.materials.Material;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ItemStamp extends Item implements ITagItem {

    private boolean raw;

    public ItemStamp() {
        this(false);
    }

    public ItemStamp(boolean raw) {
        super();
        setHasSubtypes(true);
        setCreativeTab(EmbersConstruct.tabEmbersConstruct);
        this.raw = raw;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        Stamp stamp = Stamp.getStampFromStack(stack);
        if (stamp != null && !raw) {
            float cost = stamp.getCost() / (float) Material.VALUE_Ingot;
            tooltip.add(Util.translateFormatted("tooltip.pattern.cost", Util.df.format(cost)));
        }
    }

    @Override
    public String getTexture(ItemStack stack) {
        Stamp stamp = Stamp.getStampFromStack(stack);
        if (stamp != null) {
            return "stamps/stamp_" + stamp.getName() + "_raw";
        }
        return "stamps/stamp_raw";
    }

    @Override
    public List<ItemStack> getVariants() {
        List<ItemStack> l = new ArrayList<>();
        for (Stamp s : RegistryStamps.registry.getValuesCollection()) l.add(fromStamp(s));
        return l;
    }

    @Override
    public void getSubItems(@Nonnull CreativeTabs tabs, @Nonnull NonNullList<ItemStack> itemList) {
        if (isInCreativeTab(tabs)) {
            itemList.add(new ItemStack(this));
            for (Stamp s : RegistryStamps.registry.getValuesCollection()) itemList.add(fromStamp(s));
        }
    }

    @Nonnull
    @Override
    public String getUnlocalizedName(ItemStack stack) {
        Stamp stamp = Stamp.getStampFromStack(stack);
        if (stamp != null) {
            return "item.embersconstruct.stamp_" + stamp.getName().toLowerCase(Locale.ROOT) + (raw ? "_raw" : "");
        }

        return "Invalid";
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        Stamp stamp = Stamp.getStampFromStack(stack);
        String name = "";
        if (stamp != null) {
            Item item = stamp.getItem();
            name = item != null ? item.getItemStackDisplayName(new ItemStack(item)) : (stamp.usesOreDictKey() ? I18n.translateToLocalFormatted("oredict.name." + stamp.getOreDictKey()) : "");
        }
        return I18n.translateToLocalFormatted("item.embersconstruct.stamp" + (raw ? "_raw" : "") + ".name", name).trim().replace("  ", " ");

    }

    public ItemStack fromStamp(Stamp stamp) {
        ItemStack stack = new ItemStack(this);
        NBTTagCompound tags = new NBTTagCompound();
        tags.setString(Stamp.STAMP_PATH, stamp.getRegistryName().toString());
        stack.setTagCompound(tags);
        return stack;
    }

    public boolean isRaw() {
        return raw;
    }

    public boolean isBlank(ItemStack stack) {
        if(stack.isEmpty() || !(stack.getItem() instanceof ItemStamp)) {
            return false;
        }

        return Stamp.getStampFromStack(stack) == null;
    }


}