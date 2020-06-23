package com.peatral.embersconstruct.compat.jei;

import com.peatral.embersconstruct.util.Stamp;
import mezz.jei.api.ISubtypeRegistry;
import net.minecraft.item.ItemStack;

public class StampSubtypeInterpreter implements ISubtypeRegistry.ISubtypeInterpreter {

    @Override
    public String apply(ItemStack itemStack) {
        String meta = itemStack.getItemDamage() + ":";

        Stamp stamp = Stamp.getStampFromStack(itemStack);
        if(stamp == null) {
            return meta;
        }

        return meta + stamp.getRegistryName();
    }

}
