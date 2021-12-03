package com.peatral.embersconstruct.block;

import com.peatral.embersconstruct.EmbersConstruct;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockWroughtIron extends Block {
    public BlockWroughtIron() {
        super(Material.IRON);

        setHardness(5f);
        setHarvestLevel("pickaxe", -1); // we're generous. no harvest level required
        setCreativeTab(EmbersConstruct.tabEmbersConstruct);
    }
}
