package com.peatral.embersconstruct;

import com.peatral.embersconstruct.client.gui.GuiHandler;
import com.peatral.embersconstruct.integration.conarm.IntegrationConarm;
import com.peatral.embersconstruct.integration.plustic.IntegrationPlusTiC;
import com.peatral.embersconstruct.integration.soot.IntegrationSoot;
import com.peatral.embersconstruct.integration.tconevo.IntegrationTinkersEvolution;
import com.peatral.embersconstruct.integration.tinkerscompendium.IntegrationTinkersCompendium;
import com.peatral.embersconstruct.integration.tinkerscomplement.IntegrationTinkersComplement;
import com.peatral.embersconstruct.integration.tinkersconstruct.IntegrationTinkersConstruct;
import com.peatral.embersconstruct.integration.totaltinkers.IntegrationTotalTinkers;
import com.peatral.embersconstruct.network.StampTableSelectionPacket;
import com.peatral.embersconstruct.proxy.IProxy;
import com.peatral.embersconstruct.registry.*;
import com.peatral.embersconstruct.tileentity.TileEntityBloomery;
import com.peatral.embersconstruct.tileentity.TileEntityBloomeryTop;
import com.peatral.embersconstruct.tileentity.TileEntityKiln;
import com.peatral.embersconstruct.tileentity.TileEntityStampTable;
import com.peatral.embersconstruct.util.RecipeRemover;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidRegistry;
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
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimeknights.mantle.pulsar.config.ForgeCFG;
import slimeknights.mantle.pulsar.control.PulseManager;
import slimeknights.tconstruct.common.TinkerNetwork;

@Mod(modid = EmbersConstruct.MODID, name = EmbersConstruct.NAME, version = EmbersConstruct.VERSION, dependencies = EmbersConstruct.DEPENDENCIES, updateJSON = EmbersConstruct.UPDATE_CHECKER_URL)
@Mod.EventBusSubscriber
public class EmbersConstruct {
    public static final String MODID = "embersconstruct";
    public static final String MOD_NAME = "Embers' Construct";
    public static final String NAME = "Embers Construct";
    public static final String VERSION = "1.3.3";
    public static final String DEPENDENCIES =
            "required-after:mantle;" +
            "required-after:tconstruct;" +
            "required-after:embers;" +
            "after:conarm;" +
            "after:pewter;" +
            "after:soot;" +
            "after:taiga;" +
            "after:plustic;" +
            "after:tinkerscompendium;" +
            "after:tconevo;" +
            //"after:tinkersurvival;" +
            "after:totaltinkers;" +
            "after:tcomplement";

    public static final String UPDATE_CHECKER_URL = "https://www.peatral.xyz/MinecraftMods/embersconstruct/update.json";

    @SidedProxy(clientSide = "com.peatral.embersconstruct.proxy.ClientProxy", serverSide = "com.peatral.embersconstruct.proxy.ServerProxy")
    public static IProxy proxy;

    @Mod.Instance(MODID)
    public static EmbersConstruct instance;

    public static Logger logger = LogManager.getLogger(MOD_NAME);

    public static CreativeTabEmbersConstruct tabEmbersConstruct = new CreativeTabEmbersConstruct();

    public static ForgeCFG pulseConfig = new ForgeCFG("EmbersConstructModules", "Modules");
    public static PulseManager pulseManager = new PulseManager(pulseConfig);

    static {
        FluidRegistry.enableUniversalBucket();

        // Integration Modules
        pulseManager.registerPulse(new IntegrationTinkersConstruct());
        pulseManager.registerPulse(new IntegrationTinkersComplement.Chisel());
        pulseManager.registerPulse(new IntegrationTinkersComplement.ExNihilo());
        pulseManager.registerPulse(new IntegrationTinkersEvolution());
        pulseManager.registerPulse(new IntegrationTinkersCompendium());
        pulseManager.registerPulse(new IntegrationTotalTinkers());
        pulseManager.registerPulse(new IntegrationConarm());
        pulseManager.registerPulse(new IntegrationPlusTiC());
        pulseManager.registerPulse(new IntegrationSoot());
    }



    public EmbersConstruct() {
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);

        TinkerNetwork.instance.registerPacket(StampTableSelectionPacket.class);
        EmbersConstructMaterials.main();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);

        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
        registerTileEntities();

        //Embers Construct Recipes
        if (EmbersConstructConfig.general.kiln) KilnRecipes.main();
        if (EmbersConstructConfig.general.bloomery) BloomeryRecipes.main();
        if (EmbersConstructConfig.general.stamptable) StampTableRecipes.main();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);

        //Interaction with other mods
        if (EmbersConstructConfig.embersConstructSettings.melter) RegistryMelting.main();
        if (EmbersConstructConfig.embersConstructSettings.mixer) RegistryAlloying.main();
        if (EmbersConstructConfig.embersConstructSettings.stamper) RegistryStamping.main();


        OreDictionary.registerOre("blockWroughtIron", EmbersConstructBlocks.WroughtIronBlock);
        OreDictionary.registerOre("ingotWroughtIron", EmbersConstructItems.WroughtIronIngot);
        OreDictionary.registerOre("nuggetWroughtIron", EmbersConstructItems.WroughtIronNugget);

        RegistryResearch.main();

        RecipeRemover.main();
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
        registerTileEntity(TileEntityBloomery.class, "bloomery");
        registerTileEntity(TileEntityBloomeryTop.class, "bloomerytop");
    }

    private static void registerTileEntity(Class<? extends TileEntity> clazz, String name) {
        GameRegistry.registerTileEntity(clazz, new ResourceLocation(MODID, name));
    }
}

