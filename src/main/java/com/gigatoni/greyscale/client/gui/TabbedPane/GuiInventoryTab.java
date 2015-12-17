package com.gigatoni.greyscale.client.gui.TabbedPane;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

public abstract class GuiInventoryTab extends GuiTab
{
	public ItemStack[] itemList;
	
	/**
	 * called when a tab is switched
	 */
	public void changeTab(List slots, IInventory inv)
	{
		this.controlList = new ArrayList();
		this.inventorySlots = slots;
		this.initTab(inv);
	}
	
	public void createTab(int i, IInventory inv)
	{
		super.createTab(i, inv);
		itemList = new ItemStack[getInvSize()];
	}
	
	/**
	 * adds the given Slot to the Container to be displayed
	 * @param par1Slot
	 * @return
	 */
    protected Slot addSlotToContainer(Slot par1Slot)
    {
        par1Slot.slotNumber = this.inventorySlots.size();
        this.inventorySlots.add(par1Slot);
        return par1Slot;
    }
    
    public ItemStack getStackInSlot(int i)
    {
    	return itemList[i];
    }
    
    public abstract int getInvSize();

	public void setInventorySlotContents(int var1, ItemStack var2) 
	{
		this.itemList[var1] = var2;
	}

	public ItemStack getStackInSlotOnClosing(int var1)
	{
        if (itemList[var1] != null)
        {
            ItemStack itemstack = itemList[var1];
            itemList[var1] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
	}

	public ItemStack decrStackSize(int var1, int var2) 
	{
		if (itemList[var1] != null)
        {
            if (itemList[var1].stackSize <= var2)
            {
                ItemStack itemstack = itemList[var1];
                itemList[var1] = null;
                return itemstack;
            }
 
            ItemStack itemstack1 = itemList[var1].splitStack(var2);
 
            if (itemList[var1].stackSize == 0)
            {
                itemList[var1] = null;
            }
 
            return itemstack1;
        }
        else
        {
            return null;
        }
	}
	
	@SuppressWarnings(value = "unused")
	public void onInventoryChanged() {}

	public void writeToNBT(NBTTagCompound nbt)
	{
		
	}
	
    public void readFromNBT(NBTTagCompound nbt)
    {
        
    }
}
