package com.gigatoni.greyscale.client.gui.TabbedPane;

import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.List;

public class TileEntityTabbedPane extends TileEntity implements IInventory
{
    /**
     * the curent container
     * @NOTE maybe deprecated
     */
    private ContainerTabbedPane container;

    /**
     * the TabHandler of the TabbedPane
     */
    private TabHandler handler;

    /**
     * the Tabs to be displayed in the actual GUI
     */
    public GuiTab[] tabs;

    /**
     * if the client-side has already been synced with the server-side
     */
    private boolean synced;

    /**
     * the amount of tabs
     */
    private int tabAmount;

    /**
     * the ItemStack[] for each Tab are stored in here
     */
    private ArrayList<ItemStack[]> inventory;

    /**
     * each Tab has an own NBTTagCompound they are stored in here
     */
    private ArrayList<NBTTagCompound> NBTS;

    /**
     * the index of the current displayed Tab
     */
    private int selectedTabIndex;

    /**
     * The constructor called by the server to initialize the TileEntity and read it from the NBT
     */
    public TileEntityTabbedPane()
    {
        inventory = new ArrayList();
        NBTS = new ArrayList();
        synced = false;
    }

    /**
     * the constructor called by the client each start
     * @param handler the TabHandler of the TabbedPane
     */
    public TileEntityTabbedPane(TabHandler handler)
    {
        this.handler = handler;
        inventory = new ArrayList();
        NBTS = new ArrayList();
        this.tabAmount = handler.tabs.size();

        selectedTabIndex = 0;

        tabs = handler.initTabs(this);
        synced = false;
        for(GuiTab tab : tabs)
        {
            this.NBTS.add(new NBTTagCompound());
            this.inventory.add(new ItemStack[tab.getInvSize()]);
        }
    }

    /**
     * setting the current container
     * @NOTE might not be needed anymore
     * @param pane
     */
    public void setContainer(ContainerTabbedPane pane)
    {
        this.container = pane;
    }

    /**
     *
     * @return the current displayed Tab
     */
    GuiTab getTab()
    {
        return tabs[selectedTabIndex];
    }

    /**
     * updating the TileEntitys status
     *
     * 1. syncing the server-side with the client-side once
     *
     * 2. updating the Tabs if needed
     *
     * 3. checking if the Tab's variables have changed so they have to be rewritten in the NBT
     */
    public void updateEntity()
    {
        super.updateEntity();

        if(!synced && this.container != null &&  (this.container.getPlayer() instanceof EntityPlayerMP))
        {
            if(!this.worldObj.isRemote)
            {
                System.out.println("synced");
                int n = 0;
                ((TileEntityTabbedPane)Minecraft.getMinecraft().theWorld.getTileEntity(this.xCoord, this.yCoord, this.zCoord)).selectedTabIndex = selectedTabIndex;
                for(ItemStack[] items : ((TileEntityTabbedPane)Minecraft.getMinecraft().theWorld.getTileEntity(this.xCoord, this.yCoord, this.zCoord)).inventory)
                {
                    for(int i = 0; i < items.length; i++)
                    {
                        if(this.inventory.get(n)[i] != null)
                        {
                            items[i] = new ItemStack(this.inventory.get(n)[i].getItem(), this.inventory.get(n)[i].stackSize, this.inventory.get(n)[i].getItemDamage());
                        }
                        else
                        {
                            items[i] = null;
                        }
                    }
                    n++;
                }
                synced = true;
            }
        }

        for(int i = 0; i < tabs[selectedTabIndex].getInvSize(); i++)
        {
            if(((GuiInventoryTab)tabs[1]).itemList != null)
            {
                //System.out.println("item in slot "+ i + " = "+((this.inventory.get(selectedTabIndex)[i]+ "at client: "+this.worldObj.isRemote)));
            }
        }

        for(GuiTab tab : tabs)
        {
            if(tab.getTabIndex() == selectedTabIndex || tab.ticklyUpdate())
            {
                tab.updateTab();
            }

            if(tab.needsSync())
            {
                tab.writeToNBT(NBTS.get(tab.getTabIndex()));
            }
        }

        //System.out.println("tab index : "+ selectedTabIndex + " at client: "+ this.worldObj.isRemote);

    }

    /**
     * reading the TileEntity from NBTTagCompound
     *
     *
     */
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        handler = TabHandler.handlers.get(nbt.getString("handler"));

        tabs = handler.initTabs(this);

        for(GuiTab tab : tabs)
        {
            this.NBTS.add(new NBTTagCompound());
            this.inventory.add(new ItemStack[tab.getInvSize()]);
        }

        //System.out.println("reading");

        handler = TabHandler.handlers.get(nbt.getString("handler"));

        tabAmount = handler.tabs.size();

        selectedTabIndex = nbt.getInteger("selectedIndex");

        NBTTagList tabList = (NBTTagList)nbt.getTag("Tabs");

        List<NBTBase> tabListList = ReflectionHelper.getPrivateValue(NBTTagList.class, tabList, 0);
        for (int i = 0; i < tabList.tagCount(); i++)
        {

            NBTTagCompound tabTag = (NBTTagCompound)tabListList.get(i);

            if((GuiInventoryTab.class).isAssignableFrom(handler.tabs.get(i)))
            {
                NBTTagList itemList = (NBTTagList)tabTag.getTag("Items");

                ItemStack[] items = new ItemStack[tabTag.getInteger("size")];

                //System.out.println("items length: "+items.length);

                List<NBTBase> list = ReflectionHelper.getPrivateValue(NBTTagList.class, itemList, 0);

                for (int j = 0; j < itemList.tagCount(); j++)
                {
                    NBTTagCompound itemCompound = (NBTTagCompound)list.get(j);
                    byte byte0 = itemCompound.getByte("Slot");

                    if (byte0 >= 0 && byte0 < items.length)
                    {
                        items[byte0] = ItemStack.loadItemStackFromNBT(itemCompound);
                        System.out.println("loading: "+ items[byte0]+ " from nbt");
                        //((GuiInventoryTab)tabs[i]).itemList[byte0] = ItemStack.loadItemStackFromNBT(itemCompound);
                    }
                }

                ((GuiInventoryTab)tabs[i]).itemList = items;

                if(this.inventory.size() == handler.tabs.size())
                {
                    this.inventory.set(i, items);
                }
                else
                {
                    this.inventory.add(items);
                }
            }
            else
            {
                if(this.inventory.size() == handler.tabs.size())
                {
                    this.inventory.set(i, null);
                }
                else
                {
                    this.inventory.add(null);
                }
            }

            if(NBTS.size() == handler.tabs.size())
            {
                NBTS.set(i, tabTag);
            }
            else
            {
                NBTS.add(tabTag);
            }
        }
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        nbt.setString("handler", handler.getMapping());

            NBTTagList tabList = new NBTTagList();

            ItemStack[] items;

            int length;

            for(int i = 0; i < handler.tabs.size(); i++)
            {
                NBTTagCompound tabTag = NBTS.get(i);


                if((GuiInventoryTab.class).isAssignableFrom(handler.tabs.get(i)))
                {

                    NBTTagList itemlist = new NBTTagList();

                    items = ((GuiInventoryTab)tabs[i]).itemList;

                    for (int j = 0; j < tabs.length; j++)
                    {
                        if (items[j] != null)
                        {
                            System.out.println("writing: "+ items[j]+ " to nbt");
                            NBTTagCompound nbttagcompound = new NBTTagCompound();
                            nbttagcompound.setByte("Slot", (byte)j);
                            items[j].writeToNBT(nbttagcompound);
                            itemlist.appendTag(nbttagcompound);
                        }
                    }

                    tabTag.setInteger("size", items.length);

                    tabTag.setTag("Items", itemlist);
                }

                tabList.appendTag(tabTag);

            }

            nbt.setTag("Tabs", tabList);

            nbt.setInteger("selectedIndex", selectedTabIndex);

    }

    public void setSelectedTabIndex(int sel)
    {
        this.selectedTabIndex = sel;
    }

    @Override
    public int getSizeInventory()
    {
        return getTab().getInvSize();
    }

    @Override
    public ItemStack getStackInSlot(int var1)
    {
        if(getTab() instanceof GuiInventoryTab)
        {
            return this.inventory.get(getTab().getTabIndex())[var1];
        }
        return null;
    }

    @Override
    public ItemStack decrStackSize(int var1, int var2)
    {

        if(getTab() instanceof GuiInventoryTab)
        {
            //((GuiInventoryTab)getTab()).decrStackSize(var1, var2);
            ItemStack[] itemList = inventory.get(getTab().getTabIndex());

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
        else
        {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int var1)
    {
        if( getTab() instanceof GuiInventoryTab )
        {
                ((GuiInventoryTab)getTab()).getStackInSlotOnClosing(var1);

                ItemStack[] itemList = inventory.get(getTab().getTabIndex());

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
        else
        {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int var1, ItemStack var2)
    {
        if(getTab() instanceof GuiInventoryTab )
        {
            ((GuiInventoryTab)getTab()).setInventorySlotContents(var1, var2);

            inventory.get(getTab().getTabIndex())[var1] = var2;
        }
    }

    @Override
    public String getInventoryName()
    {
        return container.handler.getInvName();
    }
    public boolean hasCustomInventoryName() { return false; }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public void markDirty()
    {
        if(getTab() instanceof GuiInventoryTab)
        {
            ((GuiInventoryTab)getTab()).onInventoryChanged();
        }
        super.markDirty();
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer var1)
    {
        return true;
    }

    @Override
    public void openInventory(){ }

    @Override
    public void closeInventory() { }

    public int getSelectedTabIndex()
    {
        return this.selectedTabIndex;
    }

    public boolean isItemValidForSlot(int slot, ItemStack item) { return true; }
}