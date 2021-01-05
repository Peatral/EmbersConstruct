package com.peatral.embersconstruct;

import com.peatral.embersconstruct.block.BlockBloomery;
import com.peatral.embersconstruct.block.BlockKiln;
import com.peatral.embersconstruct.block.BlockStampTable;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

@GameRegistry.ObjectHolder(EmbersConstruct.MODID)
public class EmbersConstructBlocks {
    public static List<Block> blocks = new ArrayList<>();

    public static final Block Kiln = new BlockKiln();
    public static final Block StampTable = new BlockStampTable();
    public static final Block Bloomery = new BlockBloomery();

    public static void registerBlocks(IForgeRegistry<Block> registry) {
        registerBlock(registry, Kiln, "kiln");
        registerBlock(registry, StampTable, "stamptable");
        registerBlock(registry, Bloomery, "bloomery");
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry) {
        for (Block block : blocks) {
            registry.register(EmbersConstructItems.init(new ItemBlock(block), block.getRegistryName().getResourcePath()));
        }
    }

    public static Block init(Block block, String name) {
        block = block.setUnlocalizedName(EmbersConstruct.MODID + "." + name).setRegistryName(new ResourceLocation(EmbersConstruct.MODID, name));
        blocks.add(block);
        return block;
    }

    public static Block registerBlock(IForgeRegistry<Block> registry, Block block, String name) {
        block = init(block, name);
        registry.register(block);
        return block;
    }
}
