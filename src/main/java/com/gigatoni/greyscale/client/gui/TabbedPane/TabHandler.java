package com.gigatoni.greyscale.client.gui.TabbedPane;

import net.minecraft.inventory.IInventory;

import java.util.ArrayList;
import java.util.HashMap;

public class TabHandler 
{
	/** the storage of all handlers t be retrieved after storage in a NBT through their mapping*/
	static HashMap<String, TabHandler> handlers = new HashMap();
	
	String invName, texturePath;
	
	/** the storage of all the Tab classes to be displayed int the actual GUI*/
	ArrayList<Class<? extends GuiTab>> tabs;
	
	public TabHandler(String name, String path)
	{
		TabHandler.handlers.put(name, this);
		tabs = new ArrayList();
		invName = name;
		texturePath = path;
	}
	

	/**
	 * 
	 * @param tab adding a tab to the TabHandler to be added int the actual GUI
	 */
	public void addTab(Class<? extends GuiTab> tab) 
	{
		this.tabs.add(tab);
	}

	
	/**
	 * 
	 * @return the name of the inventory
	 */
	public String getInvName() 
	{
		return invName;
	}
	
	/**
	 * 
	 * @return the path to the texture this API's GUI is based on
	 */
	public String getTexturePath()
	{
		return texturePath;
	}

	/**
	 * @return the mapping of the handler so it can be stored in the NBT
	 */
	public String getMapping() 
	{
		return this.invName;
	}


	/**
	 * initializing each tab
	 * 
	 * @param inv
	 * @return a Array with initialized tabs
	 */
	public GuiTab[] initTabs(IInventory inv)
	{
    	GuiTab[] tabs = new GuiTab[this.tabs.size()];
    	int i = 0;
    	GuiTab tab;
    	
    	for(Class<? extends GuiTab> raw : this.tabs)
    	{
    		try 
    		{
				tab = raw.newInstance();
				tab.createTab(i, inv);
				tabs[i] = tab;
				i++;
			} 
    		catch (InstantiationException e) 
			{
				e.printStackTrace();
			} 
    		catch (IllegalAccessException e) 
			{
				e.printStackTrace();
			}
    		
    	}
    	
    	return tabs;
	}
}
