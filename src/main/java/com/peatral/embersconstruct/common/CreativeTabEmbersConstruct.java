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

    // Currently not in use
    public void updateIcon() {
        World world = EmbersConstruct.proxy.getClientWorld();
        if (EmbersConstruct.proxy.isClient() && iconTracker.hasDelayPassed(world, 80)) {
            int next = rand.nextInt(RegistryStamps.values().size() - 1);
            iconIndex = next >= iconIndex ? next + 1 : next;
            iconTracker.markTime(world);
        }
    }

    public CreativeTabEmbersConstruct() {
        super("tabEmbersConstruct");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getTabIconItem() {
        // updateIcon();
        return new ItemStack(EmbersConstructItems.Stamp, 1, iconIndex);
    }
}
