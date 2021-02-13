package com.peatral.embersconstruct.block;

import com.peatral.embersconstruct.EmbersConstruct;
import com.peatral.embersconstruct.EmbersConstructConfig;
import com.peatral.embersconstruct.tileentity.TileEntityStampTable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.mantle.inventory.BaseContainer;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.shared.block.BlockTable;
import slimeknights.tconstruct.tools.common.block.ITinkerStationBlock;

import javax.annotation.Nonnull;

public class BlockStampTable extends BlockTable implements ITinkerStationBlock {
    public BlockStampTable() {
        super(Material.WOOD);

        if (EmbersConstructConfig.general.stamptable)
            setCreativeTab(EmbersConstruct.tabEmbersConstruct);

        setSoundType(SoundType.WOOD);
        setResistance(5f);
        setHardness(1f);

        setHarvestLevel("axe", 0);
    }

    @Nonnull
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TileEntityStampTable();
    }

    public boolean openGui(EntityPlayer player, World world, BlockPos pos) {
        if (!world.isRemote) {
            player.openGui(TConstruct.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
            if (player.openContainer instanceof BaseContainer) {
                ((BaseContainer)player.openContainer).syncOnOpen((EntityPlayerMP)player);
            }
        }

        return true;
    }

    @Override
    public int getGuiNumber(IBlockState state) {
        return 5;
    }

}
