package com.peatral.embersconstruct.client.gui;

import com.peatral.embersconstruct.inventory.ContainerStampTable;
import com.peatral.embersconstruct.network.StampTableSelectionPacket;
import com.peatral.embersconstruct.registry.RegistryStamps;
import com.peatral.embersconstruct.util.Stamp;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.common.TinkerNetwork;
import slimeknights.tconstruct.library.client.CustomTextureCreator;
import slimeknights.tconstruct.library.client.Icons;
import slimeknights.tconstruct.library.tinkering.MaterialItem;
import slimeknights.tconstruct.tools.common.client.GuiButtonItem;

import java.io.IOException;
import java.util.List;

public class GuiButtonsStampTable extends GuiSideButtonsPaged {

    public int selected = -1;

    public GuiButtonsStampTable(GuiStampTable parent, Container container, boolean right) {
        super(parent, container, GuiStampTable.Column_Count, GuiStampTable.Row_Count, right);
    }

    @Override
    public void updatePosition(int parentX, int parentY, int parentSizeX, int parentSizeY) {
        super.updatePosition(parentX, parentY, parentSizeX, parentSizeY);

        int index = 0;

        buttonList.clear();
        buttonCount = 0;
        for(ItemStack stack : RegistryStamps.getStampTableCrafting()) {
            Stamp stamp = Stamp.getStampFromStack(stack);
            if(stamp == null) {
                continue;
            }

            Item i = stamp.getItem();
            ItemStack icon = stack;
            if (i instanceof MaterialItem) icon = ((MaterialItem) i).getItemstackWithMaterial(CustomTextureCreator.guiMaterial);
            GuiButtonItem<Stamp> button = new GuiButtonItem<>(index++, -1, -1, icon, stamp);
            button.displayString = stack.getDisplayName();
            shiftButton(button, 0, 18);
            addSideButton(button);

            if(index - 1 == selected) {
                button.pressed = true;
            }
        }

        super.updatePosition(parentX, parentY, parentSizeX, parentSizeY);
    }

    public void setSelectedButtonByStamp(Stamp stamp) {
        for(Object o : buttonList) {
            if(o instanceof GuiButtonItem) {
                GuiButtonItem<Stamp> button = (GuiButtonItem<Stamp>) o;
                button.pressed = button.data == stamp;
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void actionPerformed(GuiButton button) throws IOException {

        for(GuiButton o : buttonList) {
            if(o instanceof GuiButtonItem) {
                ((GuiButtonItem<Stamp>) o).pressed = false;
            }
        }

        if(button instanceof GuiButtonItem) {
            GuiButtonItem<Stamp> buttonItem = (GuiButtonItem<Stamp>) button;
            buttonItem.pressed = true;
            selected = button.id;

            ContainerStampTable container = ((ContainerStampTable) parent.inventorySlots);
            Stamp output = buttonItem.data;
            container.setOutput(output);

            TinkerNetwork.sendToServer(new StampTableSelectionPacket(output));
        }
    }

    protected void shiftButton(GuiButtonItem<Stamp> button, int xd, int yd) {
        button.setGraphics(Icons.ICON_Button.shift(xd, yd),
                Icons.ICON_ButtonHover.shift(xd, yd),
                Icons.ICON_ButtonPressed.shift(xd, yd),
                Icons.ICON);
    }

    public List<GuiButton> getButtons() {
        return buttonList;
    }
}
