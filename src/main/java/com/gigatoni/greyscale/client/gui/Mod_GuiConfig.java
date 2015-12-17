package com.gigatoni.greyscale.client.gui;

import com.gigatoni.greyscale.handler.ConfigHandler;
import com.gigatoni.greyscale.reference.Reference;
import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

public class Mod_GuiConfig extends GuiConfig {
    public Mod_GuiConfig(GuiScreen guiScreen){
        super(guiScreen,
                new ConfigElement(ConfigHandler.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
                Reference.MOD_ID,
                true,
                false,
                GuiConfig.getAbridgedConfigPath(ConfigHandler.config.toString()));
    }
}
