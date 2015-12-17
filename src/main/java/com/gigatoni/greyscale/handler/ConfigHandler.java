package com.gigatoni.greyscale.handler;

import com.gigatoni.greyscale.reference.Reference;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigHandler {
    public static Configuration config;

    // turns on extra logging printouts
    public static boolean debugMode = true;

    public static void init(File configFile) {
        if (config == null)
        {
            config = new Configuration(configFile);
            loadConfig();
        }
    }

    private static void loadConfig() {
        Property prop;
        List<String> configList = new ArrayList<String>();

        config.addCustomCategoryComment(Configuration.CATEGORY_GENERAL, StatCollector.translateToLocal("config.general.description"));

        prop = config.get(Configuration.CATEGORY_GENERAL, "debugMode", ConfigHandler.debugMode);
        prop.comment = StatCollector.translateToLocal("config.debugMode");
        prop.setLanguageKey("config.debugMode.tooltip");
        debugMode = prop.getBoolean();
        configList.add(prop.getName());

        if (config.hasChanged())
        {
            config.save();
        }
    }

    @SubscribeEvent
    public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.modID.equalsIgnoreCase(Reference.MOD_ID))
        {
            loadConfig();
        }
    }
}
