package com.gigatoni.greyscale.client.gui.TabbedPane;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiTabbedPane extends GuiContainer
{
	/** the TabHandler  for the Tabs */
    public TabHandler handler;
    
    /** the tabs to display in this TabbedPane*/
    GuiTab[] tabs;
    
    /** if the duplicated items of the cntrolList were removed*/
    private boolean debugged;
    
    /** Currently selected creative inventory tab index. */
    int selectedTabIndex;

    /**
     * the old size of the screen to check if the components of the controlList have to be updated
     */
    int oldX, oldY;
    
    /**the TileEntity to which this GUI belongs*/
	private TileEntityTabbedPane tileEntity;
	
	/**the player that opened the GUI*/
	public EntityPlayer player;

    public GuiTabbedPane(EntityPlayer player, TabHandler handler, TileEntityTabbedPane tile)
    {
        super(new ContainerTabbedPane(player, handler, tile));
        this.handler = handler;
        player.openContainer = this.inventorySlots;
        selectedTabIndex = 0;
        
        debugged = false;
        
        this.player = player;
        tileEntity = tile;
        
        tabs = ((ContainerTabbedPane)this.inventorySlots).tabs;
        
        this.allowUserInput = true;
        this.ySize = getYSize();
        this.xSize = getXSize();
        
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
    }
    
    public void initGui()
    {
    	super.initGui();
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
    	super.updateScreen();
    	
    	//retrieving the selected Tabindex from the serverside
    	this.selectedTabIndex = ((TileEntityTabbedPane)Minecraft.getMinecraft().getIntegratedServer().worldServers[player.dimension].getTileEntity(this.tileEntity.xCoord, this.tileEntity.yCoord, this.tileEntity.zCoord)).getSelectedTabIndex();
    	
    	
    	//checking if the components of the controlList have to be adjusted
    	
    	boolean sizeChanged;
    	
    	this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
    	
    	sizeChanged = ( ! (oldX == width) || ! (oldY == height));
    	
    	oldX = width;
    	oldY = height;
    	
    	//debug controlList once to get rid of duplicated items
    	if(! debugged)
    	{
    		debugged = true;
    		((ContainerTabbedPane)this.inventorySlots).updateTabSlots(selectedTabIndex, true);
    	}
    	
    	((ContainerTabbedPane)this.inventorySlots).updateTabSlots(selectedTabIndex, sizeChanged);

    	this.buttonList = tabs[selectedTabIndex].controlList;
        
    	
    	//updating the Coordinates for a Tab to work with
    	for(GuiTab tab : tabs)
    	{
    		tab.updateConstants(guiLeft, guiTop);
    	}
    }

    /**
     * @return the x size of the GuiTexture
     */
    private int getXSize()
    {
    	return 184;
    }
    
    /**
     * @return the y size of the GuiTexture
     */
    private int getYSize()
    {
    	if(this.tabs.length > 7)
    	{
    		return 238;
    	}
    	else
    	{
    		return 209;
    	}
    }
    
    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        super.onGuiClosed();

        if (this.mc.thePlayer != null && this.mc.thePlayer.inventory != null)
        {
            //this.mc.thePlayer.inventoryContainer.removeCraftingFromCrafters(this.field_82324_x);
        }
        
        Keyboard.enableRepeatEvents(false);
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        GuiTab var3 = tabs[selectedTabIndex];

        if (var3.drawInForegroundOfTab())
        {
            this.fontRendererObj.drawString(var3.getTabLabel(), 4, 32, 0x000000F);
        }
    }

    /**
     * Called when the mouse is clicked.
     * 
     * when a Tab is clicked, the selectedtabIndex is adjusted
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        if (par3 == 0)
        {
            int x = par1 - this.guiLeft;
            int y = par2 - this.guiTop;

            int index = x / 28;
            
            if(y >= 0 && y < 30 && index < tabs.length)
            {
            	setCurrentCreativeTab(tabs[index]);
            }
            else if(y >= 206 && y <= 234 && index < tabs.length)
            {
            	setCurrentCreativeTab(tabs[7+index]);
            }
        }

        super.mouseClicked(par1, par2, par3);
    }

    
    /**
     * setting the Tab to be displayed
     * @param tab
     */
    private void setCurrentCreativeTab(GuiTab tab)
    {
        this.tileEntity.setSelectedTabIndex(tab.getTabIndex());
        this.selectedTabIndex = tab.getTabIndex();
        ((TileEntityTabbedPane)this.mc.getIntegratedServer().worldServers[this.player.dimension].getTileEntity(this.tileEntity.xCoord, this.tileEntity.yCoord, this.tileEntity.zCoord)).setSelectedTabIndex(tab.getTabIndex());
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        boolean var4 = Mouse.isButtonDown(0);
        int var5 = this.guiLeft;
        int var6 = this.guiTop;
        int var7 = var5 + 175;
        int var8 = var6 + 18;
        int var9 = var7 + 14;
        int var10 = var8 + 112;

        super.drawScreen(par1, par2, par3);
        GuiTab[] var11 = tabs;
        int var12 = var11.length;

        for (int var13 = 0; var13 < var12; ++var13)
        {
            GuiTab var14 = var11[var13];

            if (this.renderInventoryHoveringText(var14, par1, par2))
            {
                break;
            }
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_LIGHTING);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GuiTab var5 = tabs[selectedTabIndex];
        
        this.mc.renderEngine.bindTexture(new ResourceLocation(handler.getTexturePath()));
        
        drawTexturedModalRect(this.guiLeft+ 0, this.guiTop + 28, 0, 0, 184, 180);
        
        
        for(int i = 0; i < tabs.length; i++)
        {
        	renderTab(tabs[i]);
        }
        
        tabs[selectedTabIndex].drawTab(this.guiLeft + 5, this.guiTop + 36, mc);
    }

    /**
     * Renders the creative inventory hovering text if mouse is over it. Returns true if did render or false otherwise.
     * Params: current creative tab to be checked, current mouse x position, current mouse y position.
     */
    protected boolean renderInventoryHoveringText(GuiTab tab, int par2, int par3)
    {
        int xTab;
        int yTab;
        
        yTab = tab.getTabColumn();
        
        xTab = tab.getTabIndex() * 28;


        if (this.func_146978_c(xTab + 3, yTab + 3, 23, 27, par2, par3))
        {
            this.drawCreativeTabHoveringText(tab.getTabLabel(), par2, par3);
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Renders each tab icon or top texture in
     * 
     * forcing the current tab to display itself
     */
    protected void renderTab(GuiTab tab)
    {
        boolean sel = tab.getTabIndex() == selectedTabIndex;
        boolean firstRow = tab.isTabInFirstRow();
        boolean first = tab.getTabIndex() == 0 || tab.getTabIndex() == 9;
        boolean end = tab.getTabIndex() == 8 || tab.getTabIndex() == 16;

        //GL11.glDisable(GL11.GL_LIGHTING);


        int fromX = 0;
        int fromY = 0;
        int toX = 0;
        int toY = 0;
        int drawX = 0;
        int drawY = 0;
        
        if(sel && firstRow)
        {
        	fromY = 224;
        	toY = 0;
        	
        	fromX = first ? 0 : end ? 56 : 28;
        	toX = tab.getTabIndex() * 28;
        	
        	drawX = 28;
        	drawY = 32;
        }
        
        if(sel && !firstRow)
        {
        	fromY = 224;
        	toY = 206;
        	
        	fromX = first ? 86 : end ? 114 : 142;
        	toX = tab.getTabIndex() * 28;
        	
        	drawX = 28;
        	drawY = 32;
        }
        
        if(!sel && firstRow)
        {
        	fromY = 190;
        	toY = 0;
        	
        	fromX = 0;
        	toX = tab.getTabIndex() * 28;
        	
        	drawX = 28;
        	drawY = 30;
        }
        
        if(!sel && !firstRow)
        {
        	fromY = 190;
        	toY = 208;
        	
        	fromX = 30;
        	toX = tab.getTabIndex() * 28;
        	
        	drawX = 28;
        	drawY = 28;
        }
        
        drawTexturedModalRect(this.guiLeft + toX, this.guiTop + toY, fromX, fromY, drawX, drawY);
        
        this.zLevel = 100.0F;
        itemRender.zLevel = 100.0F;
        
        
        switch(tab.getLabelType())
        { 
		case ICON:
			break;
		case NONE:
			break;
		case TEXTURE:
			GL11.glDisable(GL11.GL_LIGHTING);
        	itemRender.zLevel = 0.0F;
        	this.zLevel = 0.0F;
        	
        	this.mc.renderEngine.bindTexture(new ResourceLocation(tab.getPath()));
        	drawTexturedModalRect(this.guiLeft + toX + 6, this.guiTop + toY + 6, 0, 0, 16, 16);
			break;
        }
        
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
    	this.tabs[selectedTabIndex].actionPerformed(par1GuiButton);
    }
}