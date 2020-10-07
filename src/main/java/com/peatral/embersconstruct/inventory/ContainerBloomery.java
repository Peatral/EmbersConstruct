package com.peatral.embersconstruct.inventory;

import com.peatral.embersconstruct.inventory.slots.SlotBloomeryFuel;
import com.peatral.embersconstruct.inventory.slots.SlotOutput;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBloomery extends ContainerBase {
    public ContainerBloomery(InventoryPlayer playerInventory, IInventory inventory) {
        super(playerInventory, inventory);
        this.addSlotToContainer(new Slot(inventory, 0, 79, 17));
        this.addSlotToContainer(new Slot(inventory, 1, 101, 17));
        this.addSlotToContainer(new SlotBloomeryFuel(inventory, 2, 51, 43));
        this.addSlotToContainer(new SlotOutput(playerInventory.player, inventory, 3, 90, 49));
        addPlayerInventory(playerInventory);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        return super.transferStackInSlot(playerIn, index);
    }
}
