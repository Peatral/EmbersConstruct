package com.peatral.embersconstruct.common;

import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = EmbersConstruct.MODID, name = EmbersConstruct.NAME, version = EmbersConstruct.VERSION, dependencies = EmbersConstruct.DEPENDENCIES)
@Mod.EventBusSubscriber
public class EmbersConstruct {
    public static final String MODID = "embersconstruct";
    public static final String MOD_NAME = "Embers' Construct";
    public static final String NAME = "Embers Construct";
    public static final String VERSION = "1.0";
    public static final String DEPENDENCIES =
            "required-after:tconstruct;" +
            "required-after:embers;" +
            "after:conarm;" +
            "after:pewter";

    @SidedProxy(clientSide = "com.peatral.embersconstruct.client.ClientProxy", serverSide = "com.peatral.embersconstruct.common.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance(MODID)
    public static EmbersConstruct instance;

    public static Logger logger = LogManager.getLogger(MOD_NAME);

    public static CreativeTabEmbersConstruct tabEmbersConstruct = new CreativeTabEmbersConstruct();

    public static boolean isConarmLoaded = false;


    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        isConarmLoaded = Loader.isModLoaded("conarm");
        logger.info(isConarmLoaded ? "Construct's Armory is loaded, adding recipes!" : "It seems Construct's Armory is not loaded, skipping recipes...");
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

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        EmbersConstructItems.main(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        // Register models
        //proxy.registerBlockRenders();
        proxy.registerItemRenders();
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
    }


}

