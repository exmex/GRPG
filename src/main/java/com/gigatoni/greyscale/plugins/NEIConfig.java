package com.gigatoni.greyscale.plugins;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import com.gigatoni.greyscale.handler.ConfigHandler;
import com.gigatoni.greyscale.items.Items;
import com.gigatoni.greyscale.reference.Reference;
import net.minecraft.item.ItemStack;

public class NEIConfig implements IConfigureNEI {
    @Override
    public void loadConfig() {
        if(!ConfigHandler.debugMode)
            API.hideItem(new ItemStack(Items.itemDebug));
    }

    @Override
    public String getName() {
        return Reference.MOD_NAME;
    }

    @Override
    public String getVersion() {
        return Reference.VERSION;
    }
}