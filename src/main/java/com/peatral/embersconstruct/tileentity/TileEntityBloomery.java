package com.peatral.embersconstruct.tileentity;

import com.peatral.embersconstruct.block.BlockBloomery;
import com.peatral.embersconstruct.inventory.ContainerBloomery;
import com.peatral.embersconstruct.registry.BloomeryRecipes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityBloomery extends TileEntityBase implements ITickable {
    public static final int SLOT_INPUT_A = 0;
    public static final int SLOT_INPUT_B = 1;
    public static final int SLOT_FUEL = 2;
    public static final int SLOT_OUTPUT = 3;

    // Side config is ignored for now
    // Top Block
    private static final int[] SLOTS_TOP = new int[] {SLOT_INPUT_A, SLOT_INPUT_B};
    // Bottom Block
    private static final int[] SLOTS_BOTTOM = new int[] {SLOT_OUTPUT, SLOT_FUEL};
    private static final int[] SLOTS_SIDES = new int[] {SLOT_FUEL};

    /** The ItemStacks that hold the items currently being used in the bloomery */
    private NonNullList<ItemStack> itemStacks = NonNullList.withSize(4, ItemStack.EMPTY);

    /** The number of ticks that the bloomery will keep burning */
    private int burnTime;
    /** The number of ticks that a fresh copy of the currently-burning item would keep the bloomery burning for */
    private int itemBurnTime;

    private int timeProgress;
    private int totalTimeProgress;

    @Override
    public String getNameKey() {
        return "container.embersconstruct.bloomery";
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        return this.isItemValidForSlot(index, itemStackIn);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        if (direction == EnumFacing.DOWN && index == SLOT_FUEL) {
            Item item = stack.getItem();

            if (item != Items.WATER_BUCKET && item != Items.BUCKET) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int getSizeInventory() {
        return this.itemStacks.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : this.itemStacks) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return this.itemStacks.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.itemStacks, index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.itemStacks, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        ItemStack itemstack = this.itemStacks.get(index);
        boolean sameStack = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
        this.itemStacks.set(index, stack);

        if (stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }

        if ((index == SLOT_INPUT_A || index == SLOT_INPUT_B) && !sameStack) {
            this.totalTimeProgress = this.getCookTime(stack);
            this.timeProgress = 0;
            this.markDirty();
        }

    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (index == SLOT_OUTPUT) {
            return false;
        } else if (index != SLOT_FUEL) {
            return true;
        } else {
            ItemStack itemstack = this.itemStacks.get(SLOT_FUEL);
            return isItemFuel(stack) || SlotFurnaceFuel.isBucket(stack) && itemstack.getItem() != Items.BUCKET;
        }
    }

    @Override
    public int getField(int id) {
        switch (id) {
            case 0:
                return this.burnTime;
            case 1:
                return this.itemBurnTime;
            case 2:
                return this.timeProgress;
            case 3:
                return this.totalTimeProgress;
            default:
                return 0;
        }
    }

    @Override
    public void setField(int id, int value) {
        switch (id) {
            case 0:
                this.burnTime = value;
                break;
            case 1:
                this.itemBurnTime = value;
                break;
            case 2:
                this.timeProgress = value;
                break;
            case 3:
                this.totalTimeProgress = value;
                break;
        }
    }

    @Override
    public int getFieldCount() {
        return 5;
    }

    @Override
    public void clear() {
        this.itemStacks.clear();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.itemStacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.itemStacks);
        this.burnTime = compound.getInteger("BurnTime");
        this.timeProgress = compound.getInteger("TimeProgress");
        this.totalTimeProgress = compound.getInteger("TotalTimeProgress");
        this.itemBurnTime = compound.getInteger("BurnTimeTotal");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setInteger("BurnTime", (short)this.burnTime);
        compound.setInteger("TimeProgress", (short)this.timeProgress);
        compound.setInteger("TotalTimeProgress", (short)this.totalTimeProgress);
        compound.setInteger("BurnTimeTotal", (short)this.itemBurnTime);
        ItemStackHelper.saveAllItems(compound, this.itemStacks);

        return compound;
    }

    public static int getItemBurnTime(ItemStack stack) {
        return TileEntityFurnace.getItemBurnTime(stack);
    }

    public static boolean isItemFuel(ItemStack stack) {
        return getItemBurnTime(stack) > 0;
    }

    @Override
    public String getGuiID() {
        return "minecraft:bloomery";
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerBloomery(playerInventory, this);
    }

    public boolean isBurning() {
        return this.burnTime > 0;
    }

    @SideOnly(Side.CLIENT)
    public static boolean isBurning(IInventory inventory) {
        return inventory.getField(0) > 0;
    }

    @Override
    public void update() {
        boolean flag = this.isBurning();
        boolean flag1 = false;


        if (this.isBurning()) {
            this.burnTime--;
            BlockBloomery.setState(true, this.world, this.pos);
        }

        if (!this.world.isRemote) {
            ItemStack itemstack = this.itemStacks.get(SLOT_FUEL);

            if (this.isBurning() || !itemstack.isEmpty() && !this.itemStacks.get(SLOT_INPUT_A).isEmpty()) {
                if (!this.isBurning() && this.canSmelt()) {
                    this.burnTime = getItemBurnTime(itemstack);
                    this.itemBurnTime = this.burnTime;

                    if (this.isBurning()) {
                        flag1 = true;

                        if (!itemstack.isEmpty()) {
                            Item item = itemstack.getItem();
                            itemstack.shrink(1);

                            if (itemstack.isEmpty()) {
                                ItemStack item1 = item.getContainerItem(itemstack);
                                this.itemStacks.set(SLOT_FUEL, item1);
                            }
                        }
                    }
                }

                if (this.isBurning() && this.canSmelt()) {
                    ++this.timeProgress;

                    if (this.timeProgress == this.totalTimeProgress) {
                        this.timeProgress = 0;
                        this.totalTimeProgress = this.getCookTime(this.itemStacks.get(0));
                        this.smeltItem();
                        flag1 = true;
                    }
                } else {
                    this.timeProgress = 0;
                }
            } else if (!this.isBurning() && this.timeProgress > 0) {
                this.timeProgress = MathHelper.clamp(this.timeProgress - 2, 0, this.totalTimeProgress);
            }

            if (flag != this.isBurning()) {
                flag1 = true;
                BlockBloomery.setState(this.isBurning(), this.world, this.pos);
            }
        }

        if (flag1) {
            this.markDirty();
        }
    }

    public int getCookTime(ItemStack stack) {
        return 200;
    }

    private boolean canSmelt() {
        ItemStack itemstack = BloomeryRecipes.instance().getResult(this.itemStacks.get(SLOT_INPUT_A), this.itemStacks.get(SLOT_INPUT_B));

        if (itemstack.isEmpty()) {
            return false;
        }
        else {
            ItemStack itemstack1 = this.itemStacks.get(SLOT_OUTPUT);

            if (itemstack1.isEmpty()) {
                return true;
            } else if (!itemstack1.isItemEqual(itemstack)) {
                return false;
            } else if (itemstack1.getCount() + itemstack.getCount() <= this.getInventoryStackLimit() && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize()) {
                return true;
            } else {
                return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize(); // Forge fix: make bloomery respect stack sizes in bloomery recipes
            }
        }
    }

    public void smeltItem() {
        if (this.canSmelt()) {
            ItemStack stackA = this.itemStacks.get(SLOT_INPUT_A);
            ItemStack stackB = this.itemStacks.get(SLOT_INPUT_B);
            ItemStack stackResult = BloomeryRecipes.instance().getResult(stackA, stackB);
            ItemStack stackOutput = this.itemStacks.get(SLOT_OUTPUT);

            if (stackOutput.isEmpty()) {
                this.itemStacks.set(SLOT_OUTPUT, stackResult.copy());
            } else if (stackOutput.getItem() == stackResult.getItem()) {
                stackOutput.grow(stackResult.getCount());
            }

            stackA.shrink(1);
            stackB.shrink(1);
        }
    }
}
