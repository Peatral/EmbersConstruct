package com.peatral.embersconstruct.proxy;

import com.peatral.embersconstruct.EmbersConstruct;
import com.peatral.embersconstruct.EmbersConstructItems;
import com.peatral.embersconstruct.client.render.EmbersConstructRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy implements IProxy {
    @Override
    public void registerItemRenders() {
        for (Item item : EmbersConstructItems.items) registerItemRender(item);
    }

    public void registerItemRender(Item item) {
        EmbersConstructRenderer.registerItemRender(EmbersConstruct.MODID, item);
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
    }

    @Override
    public void init(FMLInitializationEvent event) {
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
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
