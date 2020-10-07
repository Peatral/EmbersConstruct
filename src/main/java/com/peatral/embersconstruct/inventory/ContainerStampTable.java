package com.peatral.embersconstruct.inventory;

import com.peatral.embersconstruct.inventory.slots.SlotStamp;
import com.peatral.embersconstruct.network.StampTableSelectionPacket;
import com.peatral.embersconstruct.registry.RegistryStamps;
import com.peatral.embersconstruct.registry.StampTableRecipes;
import com.peatral.embersconstruct.tileentity.TileEntityStampTable;
import com.peatral.embersconstruct.util.Stamp;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import slimeknights.mantle.inventory.BaseContainer;
import slimeknights.mantle.inventory.IContainerCraftingCustom;
import slimeknights.mantle.inventory.SlotCraftingCustom;
import slimeknights.tconstruct.common.TinkerNetwork;
import slimeknights.tconstruct.shared.inventory.InventoryCraftingPersistent;
import slimeknights.tconstruct.tools.common.inventory.ContainerTinkerStation;

public class ContainerStampTable extends ContainerTinkerStation<TileEntityStampTable> implements IContainerCraftingCustom {
    public InventoryCraftingPersistent craftMatrix;
    public IInventory craftResult;

    private Stamp output;

    public ContainerStampTable(InventoryPlayer playerInventory, TileEntityStampTable tile) {
        super(tile);

        this.output = null;
        this.craftMatrix = new InventoryCraftingPersistent(this, tile, 1, 1);
        this.craftResult = new InventoryCraftResult();

        this.addSlotToContainer(new SlotStamp(this.craftMatrix, 0, 48, 35));
        this.addSlotToContainer(new SlotCraftingCustom(this, playerInventory.player, craftMatrix, craftResult, 1, 106, 35));

        this.addPlayerInventory(playerInventory, 8, 84);
        onCraftMatrixChanged(null);
    }

    @Override
    public void syncWithOtherContainer(BaseContainer<TileEntityStampTable> otherContainer, EntityPlayerMP player) {
        syncWithOtherContainer((ContainerStampTable) otherContainer, player);
    }

    protected void syncWithOtherContainer(ContainerStampTable otherContainer, EntityPlayerMP player) {
        this.setOutput(otherContainer.output);
        if(output != null) {
            TinkerNetwork.sendTo(new StampTableSelectionPacket(output), player);
        }
    }

    public void setOutput(Stamp stamp) {

        for(Stamp candidate : RegistryStamps.values()) {
            if(stamp.equals(candidate)) {
                output = stamp;
                updateResult();
                return;
            }
        }
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventoryIn) {
        updateResult();
    }

    public void updateResult() {
        if (craftMatrix.getStackInSlot(0).isEmpty() || output == null) {
            craftResult.setInventorySlotContents(0, ItemStack.EMPTY);
        } else {
            craftResult.setInventorySlotContents(0, StampTableRecipes.instance().getResultWithStamp(craftMatrix.getStackInSlot(0), output));
        }

    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        if(index != 1) {
            return super.transferStackInSlot(playerIn, index);
        }

        Slot slot = this.inventorySlots.get(index);
        if(slot == null || !slot.getHasStack()) {
            return ItemStack.EMPTY;
        }

        return super.transferStackInSlot(playerIn, index);
    }

    @Override
    public void onCrafting(EntityPlayer player, ItemStack output, IInventory craftMatrix) {
        ItemStack itemstack1 = craftMatrix.getStackInSlot(0);

        if(!itemstack1.isEmpty()) {
            craftMatrix.decrStackSize(0, 1);
        }

        updateResult();
    }

    @Override
    public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
        // prevents that doubleclicking on a stencil pulls them out of the crafting slot
        return slotIn.inventory != this.craftResult && super.canMergeSlot(stack, slotIn);
    }
}
