package com.peatral.embersconstruct.client.gui;

import com.peatral.embersconstruct.EmbersConstruct;
import com.peatral.embersconstruct.inventory.ContainerBloomery;
import com.peatral.embersconstruct.inventory.ContainerKiln;
import com.peatral.embersconstruct.tileentity.TileEntityKiln;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiBloomery extends GuiBase {
    public static final ResourceLocation BLOOMERY_GUI_TEXTURES = new ResourceLocation(EmbersConstruct.MODID,"textures/gui/container/bloomery.png");

    public GuiBloomery(InventoryPlayer playerInv, IInventory kilnInv) {
        super(playerInv, kilnInv, new ContainerBloomery(playerInv, kilnInv));
    }

    /**
     * Draws the background layer of this inventory (behind the items).
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        drawBackground(BLOOMERY_GUI_TEXTURES);
    }
}