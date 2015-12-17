package com.gigatoni.greyscale.client.gui.TabbedPane;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

public abstract class GuiTab
{
    private int tabIndex;
    private String tabLabel;
	private boolean drawTitle;
	
	/** The way in which the Tab-Label has to be drawn */
	LabelType labelType;
	
	private Item tabIcon;
	
	boolean synced;
	/**
	 * Whether to update the Tab every tick, even if it is not in the foreground
	 */
	private boolean ticklyUpdate;
	
	/** path to the texture */
	private String path;
	
	public static int guiX;
	public static int guiY;
	
	/**
	 * the List of all the components to be displayed on the screen
	 */
	public List controlList;
	
	/**
	 * the List of all Slots beeing in this Tab
	 */
	public List inventorySlots;

    public int getTabIndex()
    {
        return this.tabIndex;
    }
    
    /**
     * in which way the Tab-Label has to be drawn
     * @return
     */
    public LabelType getLabelType()
    {
    	return labelType;
    }
    
    public void setLabelType(LabelType type)
    {
    	this.labelType = type;
    }
    
    public void setTabIcon(int i)
    {
    	this.tabIcon = Item.getItemById(i);
    }
    
    public Item getTabIcon()
    {
    	return tabIcon;
    }
    
    public void setTabLabel(String lab)
    {
    	this.tabLabel = lab;
    }

    public String getTabLabel()
    {
        return this.tabLabel;
    }

    /**
     * The Texture for the Label
     * @return
     */
    public String getPath()
    {
    	return path;
    }
    
    public void setPath(String p)
    {
    	this.path = p;
    }

    /**
     * @return if the TabLabel has to be rendered in the GUI
     */
    public boolean drawInForegroundOfTab()
    {
        return this.drawTitle;
    }

    /**
     * Whether to draw the TabLabel or not
     * @param draw
     */
    public void setDrawTitle(boolean draw)
    {
        this.drawTitle = draw;
    }

    /**
     * returns index % 8
     */
    public int getTabColumn()
    {
        return this.tabIndex % 8;
    }

    /**
     * returns tabIndex < 8
     */
    public boolean isTabInFirstRow()
    {
        return this.tabIndex < 8;
    }
    
    /**
     * updates the constatnts the Tab is given to draw it's textures with
     * @param guiLeft
     * @param guiTop
     */
    public void updateConstants(int guiLeft, int guiTop)
    {
    	this.guiX = guiLeft;
    	this.guiY = guiTop;
    }
    
    /**
     * Draws a textured rectangle at the stored z-value. Args: x, y, u, v, width, height
     * 
     * @NOTE: copied from vanilla
     */
    public void drawTexturedModalRect(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        float var7 = 0.00390625F;
        float var8 = 0.00390625F;
        Tessellator var9 = Tessellator.instance;
        var9.startDrawingQuads();
        var9.addVertexWithUV((double)(par1 + 0), (double)(par2 + par6), 0.0, (double)((float)(par3 + 0) * var7), (double)((float)(par4 + par6) * var8));
        var9.addVertexWithUV((double)(par1 + par5), (double)(par2 + par6), 0.0, (double)((float)(par3 + par5) * var7), (double)((float)(par4 + par6) * var8));
        var9.addVertexWithUV((double)(par1 + par5), (double)(par2 + 0), 0.0, (double)((float)(par3 + par5) * var7), (double)((float)(par4 + 0) * var8));
        var9.addVertexWithUV((double)(par1 + 0), (double)(par2 + 0), 0.0, (double)((float)(par3 + 0) * var7), (double)((float)(par4 + 0) * var8));
        var9.draw();
    }

    /**
     * The maximum size of you Texture is 176 x 86 pixel
     * 
     * @param x = starting x-Coord
     * @param y = starting y-Coord
     * @param mc = the Minecraft instance
     */
	public abstract void drawTab(int x, int y, Minecraft mc);
	
	public void changeTab(List slots, IInventory inv) 
	{ 
		controlList = new ArrayList();
		initTab(inv); 
	}
	/**
	 * used to force the TileEntity to sync with the tab
	 */
	public void forceTabSync()
	{
		this.synced = false;
	}
	
	/**
	 * @return if the tab's variables have changed
	 */
	public boolean needsSync()
	{
		return !this.synced;
	}
	
	/**
	 * Everytime the Tab is put in the foreground this is called to add the Slots to the container
	 * @param inv
	 */
	public abstract void initTab(IInventory inv);
	
	/**
	 * this is called to create the Tab at the beginning of each game session (loading of the world)
	 * @param i
	 * @param inv
	 */
	public void createTab(int i, IInventory inv)
	{
        controlList = new ArrayList();
        inventorySlots = new ArrayList();
        this.tabIndex = i;
        this.initTab(inv);
        this.synced = true;
	}
	
	public int getInvSize()
	{
		return 0;
	}

	/**
	 * fired when a Button is pressen
	 * @param par1GuiButton pressed button
	 */
	public void actionPerformed(GuiButton par1GuiButton) {}

	public void setTicklyUpdate(boolean tick)
	{
		this.ticklyUpdate = tick;
	}
	
	/**
	 * @return if the tab needs to be scheduled every tick
	 */
	public boolean ticklyUpdate() 
	{
		return ticklyUpdate;
	}

	/**
	 * called every Tick if the Tab is selected or "ticklyUpdate" is true
	 */
	public abstract void updateTab();

	/**
	 * used to write the constants of the Tab to an NBT
	 * @param nbt
	 */
	public void writeToNBT(NBTTagCompound nbt) {}
	/**
	 * used to read the constants of the Tab from an NBT
	 * @param nbt
	 */
	public void readFromNBT(NBTTagCompound nbt) {}
}
