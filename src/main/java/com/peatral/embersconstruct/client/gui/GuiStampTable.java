package com.peatral.embersconstruct.client.gui;

import com.peatral.embersconstruct.EmbersConstruct;
import com.peatral.embersconstruct.network.StampTableSelectionPacket;
import com.peatral.embersconstruct.tileentity.TileEntityStampTable;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.tools.common.client.GuiTinkerStation;
import slimeknights.tconstruct.tools.common.inventory.ContainerTinkerStation;

@SideOnly(Side.CLIENT)
public class GuiStampTable extends GuiTinkerStation {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(EmbersConstruct.MODID, "textures/gui/container/stamptable.png");

    public static final int Column_Count = 4;
    public static final int Row_Count = 6;

    protected GuiButtonsStampTable buttons;

    public GuiStampTable(InventoryPlayer playerInv, World world, BlockPos pos, TileEntityStampTable tile) {
        super(world, pos, (ContainerTinkerStation) tile.createContainer(playerInv, world, pos));

        buttons = new GuiButtonsStampTable(this, inventorySlots, false);
        this.addModule(buttons);
    }

    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

        drawBackground(BACKGROUND);

        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
    }

    public void onSelectionPacket(StampTableSelectionPacket packet) {
        buttons.setSelectedButtonByStamp(packet.output);
    }
}
