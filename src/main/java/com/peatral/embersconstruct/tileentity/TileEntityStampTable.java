package com.peatral.embersconstruct.tileentity;

import com.peatral.embersconstruct.client.gui.GuiStampTable;
import com.peatral.embersconstruct.inventory.ContainerStampTable;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.tools.common.tileentity.TileToolStation;

public class TileEntityStampTable extends TileToolStation {
    public TileEntityStampTable() {
        this.inventoryTitle = "gui.embersconstruct.stamptable.name";
    }

    @SideOnly(Side.CLIENT)
    public GuiContainer createGui(InventoryPlayer inventoryPlayer, World world, BlockPos pos) {
        return new GuiStampTable(inventoryPlayer, world, pos, this);
    }

    public Container createContainer(InventoryPlayer inventoryPlayer, World world, BlockPos pos) {
        return new ContainerStampTable(inventoryPlayer, this);
    }
}
