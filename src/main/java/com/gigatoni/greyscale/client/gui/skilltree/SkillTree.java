package com.gigatoni.greyscale.client.gui.skilltree;

import com.gigatoni.greyscale.reference.Reference;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class SkillTree {
    public ItemStack icon;
    public String name;

    public ArrayList<Skill> skills;

    public SkillTree(){
        name = "Unknown SkillTree";
        icon = new ItemStack(Reference.debugItem);
        skills = new ArrayList();
    }
}
