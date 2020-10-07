package com.peatral.embersconstruct.inventory.slots;

import com.peatral.embersconstruct.registry.StampTableRecipes;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotStamp extends Slot {

    public SlotStamp(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        for (StampTableRecipes.Recipe recipe : StampTableRecipes.instance().getRecipeList()) {
            if (recipe.matches(stack)) return true;
        }
        return false;
    }
}
