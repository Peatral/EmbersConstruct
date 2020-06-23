package com.peatral.embersconstruct.proxy;

import net.minecraft.world.World;

public class ServerProxy implements IProxy {
    @Override
    public boolean isClient() {
        return false;
    }

    @Override
    public World getClientWorld() {
        return null;
    }

}
