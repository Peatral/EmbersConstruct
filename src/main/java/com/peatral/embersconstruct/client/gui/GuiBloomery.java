package com.peatral.embersconstruct.client.gui;

import com.peatral.embersconstruct.EmbersConstruct;
import com.peatral.embersconstruct.inventory.ContainerBloomery;
import com.peatral.embersconstruct.tileentity.TileEntityBloomery;
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

        int i = getHCenter();
        int j = getVCenter();

        if (TileEntityBloomery.isBurning(this.tile)) {
            int k = this.getBurnLeftScaled(13);
            this.drawTexturedModalRect(i + 52, j + 26 + 12 - k, 176, 12 - k, 14, k + 1);
        }

        int l = this.getTimeProgressScaled(22);
        this.drawTexturedModalRect(i + 113, j + 38, 176, 14, 8, l);
    }

    private int getTimeProgressScaled(int pixels) {
        int i = this.tile.getField(2);
        int j = this.tile.getField(3);
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }

    private int getBurnLeftScaled(int pixels)
    {
        int i = this.tile.getField(1);

        if (i == 0)
        {
            i = 200;
        }

        return this.tile.getField(0) * pixels / i;
    }
}