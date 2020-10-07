package com.peatral.embersconstruct.inventory;

import com.peatral.embersconstruct.inventory.slots.SlotKilnFuel;
import com.peatral.embersconstruct.inventory.slots.SlotOutput;
import com.peatral.embersconstruct.registry.KilnRecipes;
import com.peatral.embersconstruct.tileentity.TileEntityKiln;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerKiln extends ContainerBase {
    private int cookTime;
    private int totalCookTime;
    private int kilnBurnTime;
    private int currentItemBurnTime;

    public ContainerKiln(InventoryPlayer playerInventory, IInventory kilnInventory) {
        super(playerInventory, kilnInventory);
        this.addSlotToContainer(new Slot(kilnInventory, 0, 56, 17));
        this.addSlotToContainer(new SlotKilnFuel(kilnInventory, 1, 56, 53));
        this.addSlotToContainer(new SlotOutput(playerInventory.player, kilnInventory, 2, 116, 35));

        addPlayerInventory(playerInventory);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (int i = 0; i < this.listeners.size(); ++i) {
            IContainerListener icontainerlistener = this.listeners.get(i);

            if (this.cookTime != this.tile.getField(2)) {
                icontainerlistener.sendWindowProperty(this, 2, this.tile.getField(2));
            }

            if (this.kilnBurnTime != this.tile.getField(0)) {
                icontainerlistener.sendWindowProperty(this, 0, this.tile.getField(0));
            }

            if (this.currentItemBurnTime != this.tile.getField(1)) {
                icontainerlistener.sendWindowProperty(this, 1, this.tile.getField(1));
            }

            if (this.totalCookTime != this.tile.getField(3)) {
                icontainerlistener.sendWindowProperty(this, 3, this.tile.getField(3));
            }
        }

        this.cookTime = this.tile.getField(2);
        this.kilnBurnTime = this.tile.getField(0);
        this.currentItemBurnTime = this.tile.getField(1);
        this.totalCookTime = this.tile.getField(3);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int data) {
        this.tile.setField(id, data);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index == 2) {
                if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (index != 1 && index != 0) {
                if (!KilnRecipes.instance().getResult(itemstack1).isEmpty()) {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (TileEntityKiln.isItemFuel(itemstack1)) {
                    if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 3 && index < 30) {
                    if (!this.mergeItemStack(itemstack1, 30, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 30 && index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 3, 39, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }
}