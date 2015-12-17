package com.gigatoni.greyscale.items;

import cpw.mods.fml.common.registry.GameRegistry;

public class Items {

    public static ItemDebug itemDebug;

    public static void Initialize(){
        itemDebug = new ItemDebug();
        GameRegistry.registerItem(itemDebug, "[RPG] DEBUG");
    }
}
