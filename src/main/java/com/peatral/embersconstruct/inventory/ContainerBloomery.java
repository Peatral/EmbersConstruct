package com.peatral.embersconstruct.inventory;

import com.peatral.embersconstruct.inventory.slots.SlotBloomeryFuel;
import com.peatral.embersconstruct.inventory.slots.SlotOutput;
import com.peatral.embersconstruct.registry.BloomeryRecipes;
import com.peatral.embersconstruct.tileentity.TileEntityBloomery;
import com.peatral.embersconstruct.tileentity.TileEntityKiln;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerBloomery extends ContainerBase {

    private int timeProgress;
    private int totalTimeProgress;
    private int burnTime;
    private int itemBurnTime;

    public ContainerBloomery(InventoryPlayer playerInventory, IInventory inventory) {
        super(playerInventory, inventory);
        this.addSlotToContainer(new Slot(inventory, 0, 79, 17));
        this.addSlotToContainer(new Slot(inventory, 1, 101, 17));
        this.addSlotToContainer(new SlotBloomeryFuel(inventory, 2, 51, 43));
        this.addSlotToContainer(new SlotOutput(playerInventory.player, inventory, 3, 90, 49));
        addPlayerInventory(playerInventory);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (int i = 0; i < this.listeners.size(); ++i) {
            IContainerListener icontainerlistener = this.listeners.get(i);

            if (this.timeProgress != this.tile.getField(2)) {
                icontainerlistener.sendWindowProperty(this, 2, this.tile.getField(2));
            }

            if (this.burnTime != this.tile.getField(0)) {
                icontainerlistener.sendWindowProperty(this, 0, this.tile.getField(0));
            }

            if (this.itemBurnTime != this.tile.getField(1)) {
                icontainerlistener.sendWindowProperty(this, 1, this.tile.getField(1));
            }

            if (this.totalTimeProgress != this.tile.getField(3)) {
                icontainerlistener.sendWindowProperty(this, 3, this.tile.getField(3));
            }
        }

        this.timeProgress = this.tile.getField(2);
        this.burnTime = this.tile.getField(0);
        this.itemBurnTime = this.tile.getField(1);
        this.totalTimeProgress = this.tile.getField(3);
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

            if (index == TileEntityBloomery.SLOT_OUTPUT) {
                if (!this.mergeItemStack(itemstack1, TileEntityBloomery.SLOT_OUTPUT+1, TileEntityBloomery.SLOT_OUTPUT+37, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (index != TileEntityBloomery.SLOT_INPUT_A && index != TileEntityBloomery.SLOT_INPUT_B && index != TileEntityBloomery.SLOT_FUEL) {
                // Is Ingredient
                if (BloomeryRecipes.instance().getResults(itemstack1).size() > 0) {
                    if (!this.mergeItemStack(itemstack1, TileEntityBloomery.SLOT_INPUT_A, TileEntityBloomery.SLOT_INPUT_B+1, false)) {
                        return ItemStack.EMPTY;
                    }
                // Is Fuel
                } else if (TileEntityKiln.isItemFuel(itemstack1)) {
                    if (!this.mergeItemStack(itemstack1, TileEntityBloomery.SLOT_FUEL, TileEntityBloomery.SLOT_FUEL+1, false)) {
                        return ItemStack.EMPTY;
                    }
                // In Inventory
                } else if (index < TileEntityBloomery.SLOT_OUTPUT+28) {
                    if (!this.mergeItemStack(itemstack1, TileEntityBloomery.SLOT_OUTPUT+28, TileEntityBloomery.SLOT_OUTPUT+37, false)) {
                        return ItemStack.EMPTY;
                    }
                // In Hotbar
                } else if (index < TileEntityBloomery.SLOT_OUTPUT+37 && !this.mergeItemStack(itemstack1, TileEntityBloomery.SLOT_OUTPUT, TileEntityBloomery.SLOT_OUTPUT+27, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, TileEntityBloomery.SLOT_OUTPUT+1, TileEntityBloomery.SLOT_OUTPUT+37, false)) {
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
