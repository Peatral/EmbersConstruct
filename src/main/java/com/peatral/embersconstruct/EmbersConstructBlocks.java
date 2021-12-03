package com.peatral.embersconstruct;

import com.google.common.collect.Lists;
import com.peatral.embersconstruct.block.BlockBloomery;
import com.peatral.embersconstruct.block.BlockKiln;
import com.peatral.embersconstruct.block.BlockStampTable;
import com.peatral.embersconstruct.block.BlockWroughtIron;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.List;

import static net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(EmbersConstruct.MODID)
public class EmbersConstructBlocks {
    public static List<Block> blocks = Lists.newLinkedList();

    @ObjectHolder("kiln")
    public static final Block Kiln = null;
    @ObjectHolder("stamptable")
    public static final Block StampTable = null;
    @ObjectHolder("bloomery")
    public static final Block Bloomery = null;
    @ObjectHolder("wrought_iron_block")
    public static final Block WroughtIronBlock = null;

    public static void registerBlocks(IForgeRegistry<Block> registry) {
        registerBlock(registry, new BlockKiln(), "kiln");
        registerBlock(registry, new BlockStampTable(), "stamptable");
        registerBlock(registry, new BlockBloomery(), "bloomery");
        registerBlock(registry, new BlockWroughtIron(), "wrought_iron_block");
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
