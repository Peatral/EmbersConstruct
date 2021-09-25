package com.peatral.embersconstruct.client.gui;

import com.peatral.embersconstruct.EmbersConstruct;
import com.peatral.embersconstruct.network.StampTableSelectionPacket;
import com.peatral.embersconstruct.tileentity.TileEntityStampTable;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.tools.common.client.GuiTinkerStation;
import slimeknights.tconstruct.tools.common.inventory.ContainerTinkerStation;

import java.util.Arrays;

@SideOnly(Side.CLIENT)
public class GuiStampTable extends GuiTinkerStation {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(EmbersConstruct.MODID, "textures/gui/container/stamptable.png");

    public static final int Column_Count = 4;
    public static final int Row_Count = 6;

    protected GuiButtonsStampTable buttons;

    public GuiStampTable(InventoryPlayer playerInv, World world, BlockPos pos, TileEntityStampTable tile) {
        super(world, pos, (ContainerTinkerStation) tile.createContainer(playerInv, world, pos));

        buttons = new GuiButtonsStampTable(this, inventorySlots, false);
        this.addModule(buttons);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

        drawBackground(BACKGROUND);

        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        for(GuiButton o : buttons.getButtons()) {
            if (o.isMouseOver()) {
                super.drawHoveringText(Arrays.asList(o.displayString), mouseX, mouseY, fontRenderer);
            }
        }
    }

    public void onSelectionPacket(StampTableSelectionPacket packet) {
        buttons.setSelectedButtonByStamp(packet.output);
    }
}
