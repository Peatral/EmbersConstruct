package com.peatral.embersconstruct.common;

import com.peatral.embersconstruct.common.registry.RegistryStamps;
import com.peatral.embersconstruct.common.util.TimeTracker;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class CreativeTabEmbersConstruct extends CreativeTabs {

    private Random rand = new Random();

    int iconIndex = 0;
    TimeTracker iconTracker = new TimeTracker();

    public CreativeTabEmbersConstruct() {
        super("tabEmbersConstruct");
    }

    public void updateIcon() {
        World world = EmbersConstruct.proxy.getClientWorld();
        if (EmbersConstruct.proxy.isClient() && iconTracker.hasDelayPassed(world, 80)) {
            iconIndex = rand.nextInt(RegistryStamps.values().size());
            iconTracker.markTime(world);
        }
    }

    @Override
    @SideOnly (Side.CLIENT)
    public ItemStack getIconItemStack() {
        return getTabIconItem();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getTabIconItem() {
        updateIcon();
        return new ItemStack(EmbersConstructItems.Stamp, 1, iconIndex);
    }
}
