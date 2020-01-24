package com.peatral.embersconstruct.client;

import com.peatral.embersconstruct.client.render.EmbersConstructRenderer;
import com.peatral.embersconstruct.common.CommonProxy;
import com.peatral.embersconstruct.common.EmbersConstruct;
import com.peatral.embersconstruct.common.EmbersConstructItems;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    @Override
    public void registerItemRenders() {
        for (Item item : EmbersConstructItems.items) registerItemRender(item);
    }

    public void registerItemRender(Item item) {
        EmbersConstructRenderer.registerItemRender(EmbersConstruct.MODID, item);
    }

    @Override
    public boolean isClient() {
        return true;
    }

    @Override
    public World getClientWorld() {
        return Minecraft.getMinecraft().world;
    }
}
