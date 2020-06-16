package com.peatral.embersconstruct.common;

import com.peatral.embersconstruct.client.gui.GuiHandler;
import com.peatral.embersconstruct.common.network.StampTableSelectionPacket;
import com.peatral.embersconstruct.common.tileentity.TileEntityKiln;
import com.peatral.embersconstruct.common.tileentity.TileEntityStampTable;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ColorHandlerEvent;
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
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimeknights.tconstruct.common.TinkerNetwork;

@Mod(modid = EmbersConstruct.MODID, name = EmbersConstruct.NAME, version = EmbersConstruct.VERSION, dependencies = EmbersConstruct.DEPENDENCIES, updateJSON = EmbersConstruct.UPDATE_CHECKER_URL)
@Mod.EventBusSubscriber
public class EmbersConstruct {
    public static final String MODID = "embersconstruct";
    public static final String MOD_NAME = "Embers' Construct";
    public static final String NAME = "Embers Construct";
    public static final String VERSION = "1.2.2";
    public static final String DEPENDENCIES =
            "required-after:tconstruct;" +
            "required-after:embers;" +
            "after:conarm;" +
            "after:pewter;" +
            "after:soot;" +
            "after:taiga;" +
            "after:plustic;" +
            "after:tinkerscompendium";

    public static final String UPDATE_CHECKER_URL = "https://peatral.github.io/MinecraftMods/embersconstruct/update.json";

    @SidedProxy(clientSide = "com.peatral.embersconstruct.client.ClientProxy", serverSide = "com.peatral.embersconstruct.common.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance(MODID)
    public static EmbersConstruct instance;

    public static Logger logger = LogManager.getLogger(MOD_NAME);

    public static CreativeTabEmbersConstruct tabEmbersConstruct = new CreativeTabEmbersConstruct();

    public static boolean isConarmLoaded = false;

    public EmbersConstruct() {}

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        isConarmLoaded = Loader.isModLoaded("conarm");
        logger.info(isConarmLoaded ? "Construct's Armory is loaded, adding recipes!" : "It seems Construct's Armory is not loaded, skipping recipes...");
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
        registerTileEntities();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        EmbersConstructBlocks.registerBlocks(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        EmbersConstructItems.main(event.getRegistry());
        EmbersConstructBlocks.registerItemBlocks(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        proxy.registerItemRenders();
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerItemColors(ColorHandlerEvent.Item event) {
        event.getItemColors().registerItemColorHandler((stack, tintIndex) -> 14865332, EmbersConstructItems.Stamp);
    }

    public void registerTileEntities() {
        registerTileEntity(TileEntityKiln.class, "kiln");
        registerTileEntity(TileEntityStampTable.class, "stamptable");
    }

    private static void registerTileEntity(Class<? extends TileEntity> clazz, String name) {
        GameRegistry.registerTileEntity(clazz, new ResourceLocation(MODID, name));
    }
}

