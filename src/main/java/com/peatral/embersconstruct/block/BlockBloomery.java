package com.peatral.embersconstruct.block;

import com.google.common.collect.Lists;
import com.peatral.embersconstruct.EmbersConstruct;
import com.peatral.embersconstruct.EmbersConstructBlocks;
import com.peatral.embersconstruct.client.gui.GuiHandler;
import com.peatral.embersconstruct.tileentity.TileEntityBase;
import com.peatral.embersconstruct.tileentity.TileEntityBloomery;
import com.peatral.embersconstruct.tileentity.TileEntityBloomeryTop;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BlockBloomery extends BlockEmbersConstruct implements ITileEntityProvider {

    public static final PropertyBool BURNING = PropertyBool.create("burning");
    public static final PropertyBool TOP = PropertyBool.create("top");

    public BlockBloomery() {
        super(Material.ROCK);
        this.setSoundType(SoundType.STONE);
        this.setHardness(1.2f);
        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(BURNING, false)
                .withProperty(TOP, false));
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public int getLightValue(IBlockState state) {
        return state.getValue(BURNING) ? 13 : super.getLightValue(state);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, BURNING, TOP);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TOP, meta == 1);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TOP) ? 1 : 0;
    }

    public static void setState(boolean active, World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        TileEntity tileentity = world.getTileEntity(pos);

        world.setBlockState(pos, EmbersConstructBlocks.Bloomery.getDefaultState().withProperty(BURNING, active), 3);

        if (tileentity != null) {
            tileentity.validate();
            world.setTileEntity(pos, tileentity);
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return openGui(playerIn, worldIn, state.getValue(TOP) ? pos.down() : pos);
    }

    public boolean openGui(EntityPlayer player, World world, BlockPos pos) {
        if (!world.isRemote) {
            player.openGui(EmbersConstruct.instance, GuiHandler.GUI_BLOOMERY, world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos){
        if (world.getBlockState(pos.up()) == Blocks.AIR.getDefaultState()){
            return true;
        }
        return false;
    }

    @Override
    public void onBlockExploded(World world, BlockPos pos, Explosion explosion){
        if (!world.isRemote){
            world.spawnEntity(new EntityItem(world,pos.getX()+0.5,pos.getY()+0.5,pos.getZ()+0.5,new ItemStack(this,1,0)));
        }
        IBlockState state = world.getBlockState(pos);
        if (this.getMetaFromState(state) == 0)
            world.setBlockToAir(pos.up());
        else
            world.setBlockToAir(pos.down());
        if (world.getTileEntity(pos) instanceof TileEntityBase)
            ((TileEntityBase)world.getTileEntity(pos)).breakBlock(world,pos,state,null);
        world.setBlockToAir(pos);
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state){
        if (this.getMetaFromState(state) == 0)
            world.setBlockState(pos.up(), this.getDefaultState().withProperty(TOP, true));
        else
            world.setBlockState(pos.down(), this.getStateFromMeta(0));
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune){
        return new ArrayList<ItemStack>();
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player){
        if (!world.isRemote && !player.capabilities.isCreativeMode){
            world.spawnEntity(new EntityItem(world,pos.getX()+0.5,pos.getY()+0.5,pos.getZ()+0.5,new ItemStack(this,1,0)));
        }
        if (this.getMetaFromState(state) == 0){
            if (world.getTileEntity(pos.up()) instanceof TileEntityBase)
                ((TileEntityBase)world.getTileEntity(pos.up())).breakBlock(world, pos.up(), state, player);
            world.setBlockToAir(pos.up());
        }
        else {
            if (world.getTileEntity(pos.down()) instanceof TileEntityBase)
                ((TileEntityBase)world.getTileEntity(pos.down())).breakBlock(world, pos.down(), state, player);
            world.setBlockToAir(pos.down());
        }
        if (world.getTileEntity(pos) instanceof TileEntityBase)
            ((TileEntityBase)world.getTileEntity(pos)).breakBlock(world,pos,state,player);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return meta == 1 ? new TileEntityBloomeryTop() : new TileEntityBloomery();
    }

    /**
     * ----- Collision Boxes -----
     */

    private static final AxisAlignedBB BASE = new AxisAlignedBB(0, 0, 0, 1, 0.5, 1);
    private static final AxisAlignedBB CUBE1 = new AxisAlignedBB(0.062, 0.5, 0.062, 0.938, 1, 0.938);
    private static final AxisAlignedBB CUBE2 = new AxisAlignedBB(0.125, 0, 0.125, 0.875, 0.375, 0.875);
    private static final AxisAlignedBB CUBE3 = new AxisAlignedBB(0.25, 0.375, 0.25, 0.75, 0.688, 0.75);
    private static final AxisAlignedBB CUBE4 = new AxisAlignedBB(0.375, 0.688, 0.375, 0.625, 1, 0.625);
    private static final List<AxisAlignedBB> COLLISION_BOXES_BOTTOM = Lists.newArrayList(BASE, CUBE1);
    private static final List<AxisAlignedBB> COLLISION_BOXES_TOP = Lists.newArrayList(CUBE2, CUBE3, CUBE4);
    private static final AxisAlignedBB BOUNDING_BOX_BOTTOM = new AxisAlignedBB(0, 0, 0, 1, 1, 1);
    private static final AxisAlignedBB BOUNDING_BOX_TOP = new AxisAlignedBB(0.125, 0, 0.125, 0.875, 1, 0.875);

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return state.getValue(TOP) ? BOUNDING_BOX_TOP : BOUNDING_BOX_BOTTOM;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entity, boolean isActualState)
    {
        entityBox = entityBox.offset(-pos.getX(), -pos.getY(), -pos.getZ());
        for (AxisAlignedBB box : state.getValue(TOP) ? COLLISION_BOXES_TOP : COLLISION_BOXES_BOTTOM) {
            if (entityBox.intersects(box))
                collidingBoxes.add(box.offset(pos));
        }
    }

    @Override
    @Nullable
    public RayTraceResult collisionRayTrace(IBlockState state, World world, BlockPos pos, Vec3d start, Vec3d end) {
        double distanceSq;
        double distanceSqShortest = Double.POSITIVE_INFINITY;
        RayTraceResult resultClosest = null;
        RayTraceResult result;
        start = start.subtract(pos.getX(), pos.getY(), pos.getZ());
        end = end.subtract(pos.getX(), pos.getY(), pos.getZ());
        for (AxisAlignedBB box : state.getValue(TOP) ? COLLISION_BOXES_TOP : COLLISION_BOXES_BOTTOM)
        {
            result = box.calculateIntercept(start, end);
            if (result == null)
                continue;

            distanceSq = result.hitVec.squareDistanceTo(start);
            if (distanceSq < distanceSqShortest)
            {
                distanceSqShortest = distanceSq;
                resultClosest = result;
            }
        }
        return resultClosest == null ? null : new RayTraceResult(RayTraceResult.Type.BLOCK, resultClosest.hitVec.addVector(pos.getX(), pos.getY(), pos.getZ()), resultClosest.sideHit, pos);
    }
}
