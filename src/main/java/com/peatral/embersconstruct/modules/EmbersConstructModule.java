package com.peatral.embersconstruct.modules;

import com.peatral.embersconstruct.EmbersConstruct;
import com.peatral.embersconstruct.EmbersConstructItems;
import com.peatral.embersconstruct.util.OreDictValues;
import com.peatral.embersconstruct.util.Stamp;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.registries.IForgeRegistry;
import slimeknights.tconstruct.library.tinkering.MaterialItem;

import java.util.List;

public class EmbersConstructModule {


    public static void registerBlockItem(IForgeRegistry<Item> registry, Block block) {
        registry.register(EmbersConstructItems.init(new ItemBlock(block), block.getRegistryName().getResourcePath()));
    }

    public static Block initItem(Block block, String name) {
        block = block.setUnlocalizedName(EmbersConstruct.MODID + "." + name).setRegistryName(new ResourceLocation(EmbersConstruct.MODID, name));
        return block;
    }

    public static Block registerBlock(IForgeRegistry<Block> registry, Block block, String name) {
        block = initItem(block, name);
        registry.register(block);
        return block;
    }

    public static Item initItem(Item item, String name) {
        item = item.setUnlocalizedName(EmbersConstruct.MODID + "." + name).setRegistryName(new ResourceLocation(EmbersConstruct.MODID, name));
        return item;
    }

    public static Item registerItem(IForgeRegistry<Item> registry, Item item, String name) {
        item = initItem(item, name);
        registry.register(item);
        return item;
    }

    public static Stamp initStamp(String name, MaterialItem part) {
        return new Stamp(part, name).setRegistryName(name);
    }

    public static Stamp initStamp(String name, Item part, int cost, Fluid fluid) {
        return new Stamp(part, name, cost, fluid).setRegistryName(name);
    }

    public static Stamp initStamp(String name, OreDictValues mv) {
        return initStamp(name, mv.getName(), mv.getValue());
    }

    public static Stamp initStamp(String name, String oreDict, int cost) {
        return new Stamp(oreDict, name, cost).setRegistryName(name);
    }

    public static void registerAll(RegistryEvent.Register<Stamp> event, List<Stamp> stamps) {
        for (Stamp stamp : stamps) register(event, stamp);
    }

    public static void registerAll(RegistryEvent.Register<Stamp> event, Stamp... stamps) {
        for (Stamp stamp : stamps) register(event, stamp);
    }

    public static void register(RegistryEvent.Register<Stamp> event, Stamp stamp) {
        event.getRegistry().register(stamp);
    }

    public static void registerTileEntity(Class<? extends TileEntity> clazz, String name) {
        GameRegistry.registerTileEntity(clazz, new ResourceLocation(EmbersConstruct.MODID, name));
    }

    public static IRecipe initRecipe(String name, Block result, Object... recipe) {
        return initRecipe(name, Item.getItemFromBlock(result), recipe);
    }

    public static IRecipe initRecipe(String name, Item result, Object... recipe) {
        return initRecipe(name, new ItemStack(result), recipe);
    }

    public static IRecipe initRecipe(String name, ItemStack result, Object... recipe) {
        ShapedOreRecipe r = new ShapedOreRecipe(new ResourceLocation(EmbersConstruct.MODID, name), result, recipe);
        r.setRegistryName(new ResourceLocation(EmbersConstruct.MODID, name));
        return r;
    }
}
