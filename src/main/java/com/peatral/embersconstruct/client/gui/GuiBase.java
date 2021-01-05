package com.peatral.embersconstruct.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public abstract class GuiBase extends GuiContainer {
    /** The player inventory bound to this GUI. */
    protected final InventoryPlayer playerInventory;
    protected final IInventory tile;

    public GuiBase(InventoryPlayer playerInv, IInventory inv, Container container) {
        super(container);
        this.playerInventory = playerInv;
        this.tile = inv;
    }

    /**
     * Draws the screen and all the components in it.
     */
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s = this.tile.getDisplayName().getUnformattedText();
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
        this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Draws the background layer of this inventory (behind the items).
     */
    protected void drawBackground(ResourceLocation textures) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(textures);
        int i = getHCenter();
        int j = getVCenter();
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
    }

    protected int getHCenter() {
        return (this.width - this.xSize) / 2;
    }

    protected int getVCenter() {
        return (this.height - this.ySize) / 2;
    }
}
