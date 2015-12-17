package com.gigatoni.greyscale.client.gui;

import net.minecraft.item.ItemStack;

public class CustomTab {
    ItemStack itemStack;
    String name;

    public CustomTab(ItemStack is, String n){
        this.itemStack = is;
        this.name = n;
    }
}