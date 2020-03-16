package com.peatral.embersconstruct.common.registry;

import com.peatral.embersconstruct.common.EmbersConstruct;
import com.peatral.embersconstruct.common.EmbersConstructBlocks;
import com.peatral.embersconstruct.common.EmbersConstructItems;
import com.peatral.embersconstruct.common.item.ItemStamp;
import com.peatral.embersconstruct.common.util.Stamp;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import teamroots.embers.RegistryManager;

import java.util.List;

@Mod.EventBusSubscriber
public class RegistryCrafting {
    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        event.getRegistry().register(init( "stamptable", EmbersConstructBlocks.StampTable, " X ", "XOX", " X ", 'X', RegistryManager.plate_caminite, 'O', "workbench"));
        event.getRegistry().register(init( "kiln", EmbersConstructBlocks.Kiln, "XXX", "X X", "XXX", 'X', RegistryManager.block_caminite_brick));
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
}
