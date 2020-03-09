package com.peatral.embersconstruct.client.gui;

import com.peatral.embersconstruct.common.registry.KilnRecipes;
import com.peatral.embersconstruct.common.tileentity.TileEntityKiln;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerKiln extends Container {
    private final IInventory tileKiln;
    private int cookTime;
    private int totalCookTime;
    private int kilnBurnTime;
    private int currentItemBurnTime;

    public ContainerKiln(InventoryPlayer playerInventory, IInventory kilnInventory) {
        this.tileKiln = kilnInventory;
        this.addSlotToContainer(new Slot(kilnInventory, 0, 56, 17));
        this.addSlotToContainer(new SlotKilnFuel(kilnInventory, 1, 56, 53));
        this.addSlotToContainer(new SlotKilnOutput(playerInventory.player, kilnInventory, 2, 116, 35));

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }

    public void addListener(IContainerListener listener) {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.tileKiln);
    }

    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (int i = 0; i < this.listeners.size(); ++i) {
            IContainerListener icontainerlistener = this.listeners.get(i);

            if (this.cookTime != this.tileKiln.getField(2)) {
                icontainerlistener.sendWindowProperty(this, 2, this.tileKiln.getField(2));
            }

            if (this.kilnBurnTime != this.tileKiln.getField(0)) {
                icontainerlistener.sendWindowProperty(this, 0, this.tileKiln.getField(0));
            }

            if (this.currentItemBurnTime != this.tileKiln.getField(1)) {
                icontainerlistener.sendWindowProperty(this, 1, this.tileKiln.getField(1));
            }

            if (this.totalCookTime != this.tileKiln.getField(3)) {
                icontainerlistener.sendWindowProperty(this, 3, this.tileKiln.getField(3));
            }
        }

        this.cookTime = this.tileKiln.getField(2);
        this.kilnBurnTime = this.tileKiln.getField(0);
        this.currentItemBurnTime = this.tileKiln.getField(1);
        this.totalCookTime = this.tileKiln.getField(3);
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data) {
        this.tileKiln.setField(id, data);
    }

    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.tileKiln.isUsableByPlayer(playerIn);
    }

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