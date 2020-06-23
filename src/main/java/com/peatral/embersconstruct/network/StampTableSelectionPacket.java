package com.peatral.embersconstruct.network;

import com.peatral.embersconstruct.client.gui.GuiStampTable;
import com.peatral.embersconstruct.EmbersConstructItems;
import com.peatral.embersconstruct.inventory.ContainerStampTable;
import com.peatral.embersconstruct.util.Stamp;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import slimeknights.mantle.inventory.BaseContainer;
import slimeknights.mantle.network.AbstractPacketThreadsafe;
import slimeknights.tconstruct.common.TinkerNetwork;

public class StampTableSelectionPacket extends AbstractPacketThreadsafe {

    public Stamp output;

    public StampTableSelectionPacket() {
    }

    public StampTableSelectionPacket(Stamp output) {
        this.output = output;
    }

    @Override
    public void handleClientSafe(NetHandlerPlayClient netHandler) {
        Container container = Minecraft.getMinecraft().player.openContainer;
        if(container instanceof ContainerStampTable) {
            ((ContainerStampTable) container).setOutput(output);
            if(Minecraft.getMinecraft().currentScreen instanceof GuiStampTable) {
                ((GuiStampTable) Minecraft.getMinecraft().currentScreen).onSelectionPacket(this);
            }
        }
    }

    @Override
    public void handleServerSafe(NetHandlerPlayServer netHandler) {
        Container container = netHandler.player.openContainer;
        if(container instanceof ContainerStampTable) {
            ((ContainerStampTable) container).setOutput(output);

            // find all people who also have the same gui open and update them too
            WorldServer server = netHandler.player.getServerWorld();
            for(EntityPlayer player : server.playerEntities) {
                if(player == netHandler.player) {
                    continue;
                }
                if(player.openContainer instanceof ContainerStampTable) {
                    if(((BaseContainer) container).sameGui((BaseContainer) player.openContainer)) {
                        ((ContainerStampTable) player.openContainer).setOutput(output);
                        // same gui, send him an update
                        TinkerNetwork.sendTo(this, (EntityPlayerMP) player);
                    }
                }
            }
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        output = Stamp.getStampFromStack(ByteBufUtils.readItemStack(buf));
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeItemStack(buf, Stamp.putStamp(new ItemStack(EmbersConstructItems.Stamp), output));
    }
}