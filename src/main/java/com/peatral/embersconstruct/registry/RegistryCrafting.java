package com.peatral.embersconstruct.registry;

import com.peatral.embersconstruct.EmbersConstruct;
import com.peatral.embersconstruct.EmbersConstructBlocks;
import com.peatral.embersconstruct.EmbersConstructConfig;
import com.peatral.embersconstruct.EmbersConstructItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import teamroots.embers.RegistryManager;

@Mod.EventBusSubscriber
public class RegistryCrafting {
    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        if (EmbersConstructConfig.general.stamptable) event.getRegistry().register(init( "stamptable", EmbersConstructBlocks.StampTable, " X ", "XOX", " X ", 'X', RegistryManager.plate_caminite, 'O', "workbench"));
        if (EmbersConstructConfig.general.kiln) event.getRegistry().register(init( "kiln", EmbersConstructBlocks.Kiln, "XXX", "X X", "XXX", 'X', RegistryManager.block_caminite_brick));
        if (EmbersConstructConfig.general.bloomery) event.getRegistry().register(init( "bloomery", EmbersConstructBlocks.Bloomery, "XXX", "XXX", "X X", 'X', RegistryManager.block_caminite_brick));

        event.getRegistry().register(init( "wrought_iron_block", EmbersConstructBlocks.WroughtIronBlock, "XXX", "XXX", "XXX", 'X', EmbersConstructItems.WroughtIronIngot));
        event.getRegistry().register(initShapeless( "wrought_iron_from_block", new ItemStack(EmbersConstructItems.WroughtIronIngot, 9), EmbersConstructBlocks.WroughtIronBlock));
        event.getRegistry().register(init( "wrought_iron_ingot", EmbersConstructItems.WroughtIronIngot, "XXX", "XXX", "XXX", 'X', EmbersConstructItems.WroughtIronNugget));
        event.getRegistry().register(initShapeless( "wrought_iron_nugget", new ItemStack(EmbersConstructItems.WroughtIronNugget, 9), EmbersConstructItems.WroughtIronIngot));
        event.getRegistry().register(initShapeless( "wrought_iron_plate", new ItemStack(EmbersConstructItems.WroughtIronPlate, 1), EmbersConstructItems.WroughtIronIngot, EmbersConstructItems.WroughtIronIngot, RegistryManager.tinker_hammer));
    }

    public static IRecipe init(String name, Block result, Object... recipe) {
        return init(name, Item.getItemFromBlock(result), recipe);
    }

    public static IRecipe init(String name, Item result, Object... recipe) {
        return init(name, new ItemStack(result), recipe);
    }

    public static IRecipe init(String name, ItemStack result, Object... recipe) {
        ShapedOreRecipe r = new ShapedOreRecipe(new ResourceLocation(EmbersConstruct.MODID, name), result, recipe);
        r.setRegistryName(new ResourceLocation(EmbersConstruct.MODID, name));
        return r;
    }

    public static IRecipe initShapeless(String name, Block result, Object... recipe) {
        return initShapeless(name, Item.getItemFromBlock(result), recipe);
    }

    public static IRecipe initShapeless(String name, Item result, Object... recipe) {
        return initShapeless(name, new ItemStack(result), recipe);
    }

    public static IRecipe initShapeless(String name, ItemStack result, Object... recipe) {
        ShapelessOreRecipe r = new ShapelessOreRecipe(new ResourceLocation(EmbersConstruct.MODID, name), result, recipe);
        r.setRegistryName(new ResourceLocation(EmbersConstruct.MODID, name));
        return r;
    }

}
