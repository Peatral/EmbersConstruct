package com.peatral.embersconstruct.common;

import com.peatral.embersconstruct.common.block.BlockKiln;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@GameRegistry.ObjectHolder(EmbersConstruct.MODID)
public class EmbersConstructBlocks {
    public static final Block Kiln = new BlockKiln();

    public static void registerBlocks(IForgeRegistry<Block> registry) {
        registry.register(init(Kiln, "kiln"));
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry) {
        registry.register(EmbersConstructItems.init(new ItemBlock(Kiln), "kiln"));
    }

    public static Block init(Block block, String name) {
        return block.setUnlocalizedName(name).setRegistryName(new ResourceLocation(EmbersConstruct.MODID, name));
    }
}
