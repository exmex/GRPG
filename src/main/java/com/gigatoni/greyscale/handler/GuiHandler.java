package com.gigatoni.greyscale.handler;

import com.gigatoni.greyscale.client.gui.QuestBook.GuiQuestBook;
import com.gigatoni.greyscale.client.gui.GuiSkillsOverview;
import com.gigatoni.greyscale.reference.Reference;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

    public void registerKeyBindings () {}

    @Override
    public Object getServerGuiElement ( int ID, EntityPlayer player, World world, int x, int y, int z ) {
        return null;
    }

    @Override
    public Object getClientGuiElement ( int ID, EntityPlayer player, World world, int x, int y, int z ) {
        if ( ID == Reference.SKILL_UI_ID )
            return new GuiSkillsOverview();
        if( ID == Reference.QUESTBOOK_UI_ID)
            return new GuiQuestBook();
        return null;
    }
}