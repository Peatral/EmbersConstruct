package com.peatral.embersconstruct.client.gui;

import com.peatral.embersconstruct.common.inventory.ContainerKiln;
import com.peatral.embersconstruct.common.tileentity.TileEntityKiln;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    public static final int GUI_KILN = 0;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        switch (ID) {
            case GuiHandler.GUI_KILN:
                return new ContainerKiln(player.inventory, (TileEntityKiln) tileEntity);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        switch (ID) {
            case GuiHandler.GUI_KILN:
                return new GuiKiln(player.inventory, (TileEntityKiln) tileEntity);
        }
        return null;
    }
}