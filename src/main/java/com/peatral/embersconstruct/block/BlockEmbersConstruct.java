package com.peatral.embersconstruct.block;

import com.peatral.embersconstruct.EmbersConstruct;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockEmbersConstruct extends Block {
    public BlockEmbersConstruct(Material materialIn) {
        super(materialIn);
        setCreativeTab(EmbersConstruct.tabEmbersConstruct);
    }
}
