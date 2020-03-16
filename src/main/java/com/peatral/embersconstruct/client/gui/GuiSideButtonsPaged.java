package com.peatral.embersconstruct.client.gui;

import com.peatral.embersconstruct.common.EmbersConstruct;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.inventory.Container;
import slimeknights.mantle.client.gui.GuiModule;
import slimeknights.mantle.client.gui.GuiMultiModule;

import java.io.IOException;

public class GuiSideButtonsPaged extends GuiModule {

    private final int columns;
    private final int rows;
    private GuiButton clickedButton;
    protected int buttonCount = 0;

    public int spacing = 4;

    public int page = 0;
    public int pageCount = 1;

    private GuiButton buttonPrev;
    private GuiButton buttonNext;

    public GuiSideButtonsPaged(GuiMultiModule parent, Container container, int columns, int rows) {
        this(parent, container, columns, rows, false);
    }

    public GuiSideButtonsPaged(GuiMultiModule parent, Container container, int columns, int rows, boolean right) {
        super(parent, container, right, false);
        this.columns = columns;
        this.rows = rows;
    }

    public void addSideButton(GuiButton button) {
        if (buttonCount >= this.rows*columns) button.visible = false;
        int rows = ((buttonCount - 1) / columns + 1) % (this.rows*columns);

        this.xSize = button.width * columns + spacing * (columns - 1);
        this.ySize = (button.height * rows + spacing * (rows - 1));

        int offset = buttonCount;
        int x = (offset % columns) * (button.width + spacing);
        int y = ((offset / columns) * (button.height + spacing)) % (this.rows * (button.height + spacing)) + 22;

        button.x = guiLeft + x;
        button.y = guiTop + y;

        if(this.right) {
            button.x += parent.getXSize();
        }

        this.buttonList.add(button);
        this.buttonCount++;
        this.pageCount = (int) Math.ceil((float) buttonCount/(this.rows*columns));
    }

    @Override
    public boolean handleMouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if(mouseButton == 0) {
            if (buttonPrev.mousePressed(mc, mouseX, mouseY)) return clickTabButton(buttonPrev);
            if (buttonNext.mousePressed(mc, mouseX, mouseY)) return clickTabButton(buttonNext);
            for(Object o : this.buttonList) {
                GuiButton guibutton = (GuiButton) o;

                if(guibutton.mousePressed(this.mc, mouseX, mouseY)) {
                    this.clickedButton = guibutton;
                    guibutton.playPressSound(this.mc.getSoundHandler());
                    this.actionPerformed(guibutton);
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean handleMouseReleased(int mouseX, int mouseY, int state) {
        if(clickedButton != null) {
            clickedButton.mouseReleased(mouseX, mouseY);
            clickedButton = null;
            return true;
        }
        return false;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        buttonPrev.drawButton(this.mc, mouseX, mouseY, partialTicks);
        String s = (page+1)+"/" + pageCount;
        int x = (this.columns*(18+spacing)-spacing - mc.fontRenderer.getStringWidth(s))/2;
        drawString(mc.fontRenderer, s, guiLeft + x, guiTop+6, 16777215);
        buttonNext.drawButton(this.mc, mouseX, mouseY, partialTicks);

        for(GuiButton button : buttonList) {
            button.drawButton(this.mc, mouseX, mouseY, partialTicks);
        }
    }

    @Override
    public void updatePosition(int parentX, int parentY, int parentSizeX, int parentSizeY) {
        super.updatePosition(parentX, parentY, parentSizeX, parentSizeY);
        buttonPrev = new GuiButton(-1, guiLeft-1, guiTop, 20, 20, "<");
        buttonPrev.enabled = true;
        buttonNext = new GuiButton(-2, guiLeft-1+(this.columns-1)*(20+spacing-2), guiTop, 20, 20, ">");
        buttonNext.enabled = true;
    }

    public boolean clickTabButton(GuiButton button) {
        button.playPressSound(this.mc.getSoundHandler());
        if (button.equals(buttonPrev)) {
            if (--page < 0) page += pageCount;
        } else if (button.equals(buttonNext)) {
            page = ++page%pageCount;
        }
        updateButtons();

        return true;
    }

    public void updateButtons() {
        for (int i = 0; i < buttonList.size(); i++) {
            if (i >= page*rows*columns && i < (page+1)*rows*columns) buttonList.get(i).visible = true;
            else buttonList.get(i).visible = false;
        }
    }
}
