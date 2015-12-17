package com.gigatoni.greyscale.client;

import com.gigatoni.greyscale.reference.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ExtendedProperties implements IExtendedEntityProperties {

    public static final String identifier = Reference.LOWERCASE_MOD_ID + "_properties";

    public int miningSkillLevel;
    public int miningSkillPoints;
    public int[] learnedSkills;

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        compound.setInteger("miningLevel", miningSkillLevel);
        compound.setInteger("miningPoints", miningSkillPoints);
        /*for(int i = 0; i < x; i++) {
            compound.setInteger("skillLearned." + i, learnedSkills[i]);
        }*/
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        miningSkillLevel = compound.getInteger("miningLevel");
        miningSkillPoints = compound.getInteger("miningPoints");
    }

    @Override
    public void init(Entity entity, World world) {
        miningSkillLevel = 0;
        miningSkillPoints = 0;
    }
}