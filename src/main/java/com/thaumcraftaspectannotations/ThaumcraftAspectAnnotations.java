package com.thaumcraftaspectannotations;

import java.io.File;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.thaumcraftaspectannotations.config.ThaumcraftAspectAnnotationsConfig;
import com.thaumcraftaspectannotations.proxy.CommonProxy;


@Mod(
    modid = Tags.MODID,
    name = Tags.MODNAME,
    version = Tags.VERSION,
    acceptedMinecraftVersions = "[1.12.2]",
    dependencies = "required-after:thaumcraft;",
    guiFactory = "com.thaumcraftaspectannotations.config.ThaumcraftAspectAnnotationsGuiFactory"
)
public class ThaumcraftAspectAnnotations {

    @Mod.Instance(Tags.MODID)
    public static ThaumcraftAspectAnnotations instance;

    @SidedProxy(
        clientSide = "com.thaumcraftaspectannotations.proxy.ClientProxy",
        serverSide = "com.thaumcraftaspectannotations.proxy.ServerProxy"
    )
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        File configFile = new File(event.getModConfigurationDirectory(), Tags.MODID + ".cfg");
        ThaumcraftAspectAnnotationsConfig.init(configFile);

        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}