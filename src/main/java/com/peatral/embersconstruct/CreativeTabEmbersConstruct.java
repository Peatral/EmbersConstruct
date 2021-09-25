package com.peatral.embersconstruct;

import com.peatral.embersconstruct.item.ItemStamp;
import com.peatral.embersconstruct.registry.RegistryStamps;
import com.peatral.embersconstruct.util.Stamp;
import com.peatral.embersconstruct.util.TimeTracker;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import teamroots.embers.RegistryManager;

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
        if (RegistryStamps.registry.getValuesCollection().size() == 0) {
            iconIndex = -1;
            return;
        }
        if (EmbersConstruct.proxy.isClient() && iconTracker.hasDelayPassed(world, 80)) {
            iconIndex = rand.nextInt(RegistryStamps.registry.getValuesCollection().size());
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
        return iconIndex == -1 ? new ItemStack(RegistryManager.stamp_gear) :
                ((ItemStamp) EmbersConstructItems.Stamp).fromStamp(RegistryStamps.registry.getValuesCollection().toArray(new Stamp[]{})[iconIndex]);
    }
}
