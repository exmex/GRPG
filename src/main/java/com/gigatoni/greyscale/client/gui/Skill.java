package com.gigatoni.greyscale.client.gui;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class Skill {
    public ItemStack itemStack;
    public String name;
    public String[] description;

    public ArrayList<Integer> dependencies;

    public Skill(){
        name = "Unknown Skill";
        itemStack = new ItemStack(Items.wooden_sword);
        dependencies = new ArrayList<Integer>();
        description = new String[]{};
    }
}
