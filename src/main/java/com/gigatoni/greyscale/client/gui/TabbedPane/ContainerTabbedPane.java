package com.gigatoni.greyscale.client.gui.TabbedPane;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

class ContainerTabbedPane extends Container
{
    TabHandler handler;
    int oldIndex;
	private InventoryPlayer playerInv;
	TileEntityTabbedPane tabInv;
	Slot[] cast;
    GuiTab[] tabs;
    
    public ContainerTabbedPane(EntityPlayer player, TabHandler handler, TileEntityTabbedPane tabInv)
    {
    	this.playerInv = player.inventory;
    	this.tabInv = tabInv;
        this.handler = handler;
        this.oldIndex = -1;
        cast = null;
        
        tabInv.setContainer(this);
        
        tabs = tabInv.tabs;
        
        //updateTabSlots(0, false);
    }

    public EntityPlayer getPlayer()
    {
    	return playerInv.player;
    }
    
    public void detectAndSendChanges()
    {
    	super.detectAndSendChanges();
    	
    	if(tabInv.getSelectedTabIndex() != oldIndex)
    	{
    		updateTabSlots(tabInv.getSelectedTabIndex(), false);
    	}
    }
    
    public void onContainerClosed(EntityPlayer player)
    {
    	super.onContainerClosed(player);
    }
    
    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return true;
    }
    
    public void updateTabSlots(int index, boolean sizeChanged)
    {   	
    	if(this.oldIndex != index || sizeChanged)
    	{   		
    		//System.out.println("updating on clientside: "+ this.playerInv.player.worldObj.isRemote + " for index "+ index);
    			this.inventorySlots = new ArrayList();	 
    			
    			for (int i = 0; i < 3; ++i)
    			{
    				int y = 96 + 28 + 18*i;
    				
    				for(int j = 0; j < 9; j++)
    				{
    					this.addSlotToContainer(new Slot(playerInv, i*9 + j + 9, 12+18*j, y));
    				}
            	}
    			
    			for(int i = 0; i< 9; i++)
    			{
    				this.addSlotToContainer(new Slot(playerInv, i, 12+ i*18, 154+28));
    			}
    		
    			tabs[index].changeTab(inventorySlots, tabInv);
    		
    			List shift = new ArrayList();
    		
    			for(int i = 0; i < 36; i++)
    			{
    				shift.add(this.inventoryItemStacks.get(i));
    			}
    		
    			this.inventoryItemStacks = shift;
    		
    			if(tabs[index] instanceof GuiInventoryTab)
    			{
    			
    				for(ItemStack item : ((GuiInventoryTab)tabs[index]).itemList)
    				{	
    					this.inventoryItemStacks.add((Object)item);
    				}
    			}
    			
    		oldIndex = index;
    	}
    }
    
    @Override
    public void putStackInSlot(int slotnumber, ItemStack item)
    {
    	if(slotnumber < 36 + tabs[tabInv.getSelectedTabIndex()].getInvSize())
    	{
    		super.putStackInSlot(slotnumber, item);
    	}
    	/*
    	System.out.println(this.playerInv.player.worldObj.isRemote);
    	super.putStackInSlot(slotnumber, item);*/
    }

    public ItemStack transferStackInSlot(EntityPlayer player, int slotnumber)
    {    	
    	
    	ItemStack itemstack = null;
    	Slot slot = (Slot)inventorySlots.get(slotnumber);
    	
    	if(slot != null && slot.getHasStack())
    	{
    		
    		ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

    			if(slotnumber < 36)
        		{
    				if(!mergeItemStack(itemstack1, 35, 35 + tabs[oldIndex].getInvSize(), true))
    				{
    					return null;
    				}
        		}
    			else if(slotnumber > 35)
    			{
    				if(!mergeItemStack(itemstack1, 0, 35, true))
    				{
    					return null;
    				}
    			}
    			
    			if (itemstack1.stackSize == 0)
                {
                    slot.putStack(null);
                }
                else
                {
                    slot.onSlotChanged();
                }
     
                if (itemstack1.stackSize == itemstack.stackSize)
                {
                    return null;
                }
                
                slot.onPickupFromSlot(player, itemstack);
    	}
    	
    		return itemstack;
    }
}
