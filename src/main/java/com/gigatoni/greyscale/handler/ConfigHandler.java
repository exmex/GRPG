package com.gigatoni.greyscale.handler;

import com.gigatoni.greyscale.reference.Reference;
import com.gigatoni.greyscale.utility.LogHelper;
import com.gigatoni.greyscale.utility.ResourceUtil;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

public class ConfigHandler {
    public static Configuration config;
    public static ArrayList<String> schematics;
    public static String schematicDir;

    // turns on extra logging printouts
    public static boolean debugMode = true;

    public static void init(File configFile, File configDir) {
        if (config == null)
        {
            config = new Configuration(configFile);
            loadConfig();
        }

        schematics = new ArrayList<String>();
        schematicDir = configDir + "/" + Reference.MOD_ID + "/schematics";
        File schematicFolder = new File(schematicDir);
        if(!schematicFolder.mkdirs()){
            //schematics
            for (final File fileEntry : schematicFolder.listFiles()) {
                if (fileEntry.isFile()) {
                    schematics.add(fileEntry.getName());
                }
            }
            LogHelper.info("Loaded " + schematics.size() + " Schematics!");
        }else{
            LogHelper.info("No schematics loaded!");
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
