package com.gigatoni.greyscale.reference;

import com.gigatoni.greyscale.client.gui.Skill;
import com.gigatoni.greyscale.client.gui.SkillTree;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class SkillsReference {

    public static ArrayList<SkillTree> skillTrees;

    public static void initialize(){
        skillTrees = new ArrayList();
        SkillTree skillTree = new SkillTree();
        skillTree.icon = new ItemStack(Reference.debugItem);
        skillTree.name = "Mining";

        Skill skill = new Skill();
        skill.itemStack = new ItemStack(Items.wooden_pickaxe);
        skill.name = "Hardened wood";
        skill.description = new String[]{"Increases durability"};
        skillTree.skills.add(skill);

        skill = new Skill();
        skill.itemStack = new ItemStack(Items.iron_ingot);
        skill.dependencies.add(0);
        skill.name = "Luck I";
        skill.description = new String[]{"Increases drop chances"};
        skillTree.skills.add(skill);

        skill = new Skill();
        skill.itemStack = new ItemStack(Items.stone_pickaxe);
        skill.dependencies.add(1);
        skill.name = "Stone upgrade";
        skill.description = new String[]{"Allows the crafting of a stone pickaxe"};
        skillTree.skills.add(skill);

        skillTrees.add(skillTree);

        skillTree = new SkillTree();
        skillTree.icon = new ItemStack(Reference.debugItem);
        skillTree.name = "Woodcutting";
        skillTrees.add(skillTree);

        skillTree = new SkillTree();
        skillTree.icon = new ItemStack(Reference.debugItem);
        skillTree.name = "Melee";
        skillTrees.add(skillTree);

        skillTree = new SkillTree();
        skillTree.icon = new ItemStack(Reference.debugItem);
        skillTree.name = "Ranged";
        skillTrees.add(skillTree);

        skillTree = new SkillTree();
        skillTree.icon = new ItemStack(Reference.debugItem);
        skillTree.name = "Defense";
        skillTrees.add(skillTree);

        skillTree = new SkillTree();
        skillTree.icon = new ItemStack(Reference.debugItem);
        skillTree.name = "Cooking";
        skillTrees.add(skillTree);

        skillTree = new SkillTree();
        skillTree.icon = new ItemStack(Reference.debugItem);
        skillTree.name = "Farming";
        skillTrees.add(skillTree);

        skillTree = new SkillTree();
        skillTree.icon = new ItemStack(Reference.debugItem);
        skillTree.name = "Crafting";
        skillTrees.add(skillTree);

        skillTree = new SkillTree();
        skillTree.icon = new ItemStack(Reference.debugItem);
        skillTree.name = "Speech";
        skillTrees.add(skillTree);

        skillTree = new SkillTree();
        skillTree.icon = new ItemStack(Reference.debugItem);
        skillTree.name = "Techromancy";
        skillTrees.add(skillTree);
    }
}
