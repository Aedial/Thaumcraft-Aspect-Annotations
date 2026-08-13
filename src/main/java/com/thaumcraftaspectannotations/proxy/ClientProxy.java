package com.thaumcraftaspectannotations.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import com.thaumcraftaspectannotations.client.ContainerAspectOverlayHandler;


public class ClientProxy extends CommonProxy {

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);

        MinecraftForge.EVENT_BUS.register(new ContainerAspectOverlayHandler());
    }
}