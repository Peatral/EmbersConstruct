package com.peatral.embersconstruct.proxy;

import com.peatral.embersconstruct.EmbersConstruct;
import com.peatral.embersconstruct.EmbersConstructItems;
import com.peatral.embersconstruct.client.render.EmbersConstructRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class ClientProxy implements IProxy {
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
