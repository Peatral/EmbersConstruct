package com.peatral.embersconstruct.common.block;

import com.peatral.embersconstruct.common.EmbersConstruct;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockEmbersConstruct extends Block {
    public BlockEmbersConstruct(Material materialIn) {
        super(materialIn);
        setCreativeTab(EmbersConstruct.tabEmbersConstruct);
    }
}
