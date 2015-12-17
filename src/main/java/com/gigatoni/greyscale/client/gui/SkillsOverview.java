package com.gigatoni.greyscale.client.gui;

import com.gigatoni.greyscale.client.ExtendedProperties;
import com.gigatoni.greyscale.reference.Reference;
import com.gigatoni.greyscale.reference.SkillsReference;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class SkillsOverview extends GuiScreen {

    private static final ResourceLocation skillsOverviewBG = new ResourceLocation(Reference.LOWERCASE_MOD_ID, "textures/gui/skillsUI/GuiSkills.png");
    //private static final ResourceLocation skillBar = new ResourceLocation(Reference.LOWERCASE_MOD_ID, "textures/gui/skillsUI/skillBar.png");

    private static int xSize = 253;
    private static int ySize = 166;

    private static CustomTab[] guiTabs = { // 9 is max
            new CustomTab(new ItemStack(Items.stone_pickaxe), "Mining"),
            new CustomTab(new ItemStack(Items.stone_axe), "Woodcutting"),
            new CustomTab(new ItemStack(Items.stone_sword), "Melee"),
            new CustomTab(new ItemStack(Items.bow), "Ranged"),
            new CustomTab(new ItemStack(Items.stone_shovel), "Defense"),
            new CustomTab(new ItemStack(Blocks.furnace), "Cooking"),
            new CustomTab(new ItemStack(Blocks.farmland), "Farming"),
            new CustomTab(new ItemStack(Blocks.crafting_table), "Crafting"),
            new CustomTab(new ItemStack(Items.apple), "Speech"), // Change later.
            new CustomTab(new ItemStack(Items.blaze_powder), "Techromancy"), // Change later
    };

    /*private static ItemStack[] guiTabs = { // 9 is max
        new ItemStack(Items.stone_pickaxe),
        new ItemStack(Items.stone_axe),
        new ItemStack(Items.stone_sword),
        new ItemStack(Items.bow),
        new ItemStack(Items.stone_shovel),
        new ItemStack(Blocks.furnace),
        new ItemStack(Blocks.farmland),
        new ItemStack(Blocks.crafting_table),
        new ItemStack(Items.apple), // Change later.
        new ItemStack(Items.nether_star), // Change later
    };*/

    ArrayList<SkillTree> skillTrees;
    int activeTab;

    public SkillsOverview()
    {
        activeTab = 0;
        skillTrees = SkillsReference.skillTrees;
        /*skillTrees = new ArrayList();
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

        skillTrees.add(skillTree);*/
    }

    public void initGui()
    {
        int x = (width - xSize) / 2 + 3;
        int y = (height - ySize) / 2 + 7;
        /*for(int i = 0; i < guiLabels.length; i++) {
            this.buttonList.add(new GuiButton(i, x, y, 75, 20, guiLabels[i]));
            y += 22;
        }

        ((GuiButton)buttonList.get(7)).enabled = false;
        ((GuiButton)buttonList.get(8)).enabled = false;
        ((GuiButton)buttonList.get(9)).enabled = false;*/

        super.initGui();
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseZ)
    {
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;

        for(int i = 0; i < guiTabs.length; i++) {
            //if(isInPoint(Mouse.getX(), Mouse.getY(), j + offset, k + 164, 28, 30))
            int offset = (27 * i);
            int offset2 = -28;
            if(i >= 9) {
                offset = (27 * (i - 9));
                offset2 = 164;
            }

            if(isInPoint(mouseX, mouseY, j+offset, k+offset2, 28, 30)) {
                activeTab = i;
            }
        }

        j = (width - xSize) / 2 + 7;
        k = (height - ySize) / 2 + 27;
        if((skillTrees.size()-1) >= activeTab) {
            ArrayList<Skill> skillArr = skillTrees.get(activeTab).skills;
            for (int i = 0; i < skillArr.size(); i++) {
                Skill skill = skillArr.get(i);
                if (skill.dependencies.size() > 0)
                    if (isInPoint(mouseX, mouseY, j + 52 * skill.dependencies.get(0) + 5, k + 9, j + (52 * i) + 5, k + 12))
                        return;
            }
        }
    }

    @Override
    protected void keyTyped(char par1, int par2)
    {
        if (par2 == 1 || par2 == this.mc.gameSettings.keyBindInventory.getKeyCode())
        {
            this.mc.thePlayer.closeScreen();
        }
    }

    @Override
    public void drawScreen(int x, int y, float z) {
        this.drawDefaultBackground();

        this.drawBackgroundImage();
        this.drawOverview();

        this.drawSkills(x, y);

        super.drawScreen(x, y, z);

        drawTabTooltips(x, y);
        drawSkillTooltips(x, y);
    }

    public void drawSkills(int mouseX, int mouseY)
    {
        int x = (width - xSize) / 2 + 7;
        int y = (height - ySize) / 2 + 27;

        if((skillTrees.size()-1) >= activeTab) {
            ArrayList<Skill> skillArr = skillTrees.get(activeTab).skills;
            for (int i = 0; i < skillArr.size(); i++) {
                Skill skill = skillArr.get(i);
                if (skill.dependencies.size() == 0)
                    continue;
                this.mc.getTextureManager().bindTexture(skillsOverviewBG);
                drawRect(x + 52 * skill.dependencies.get(0) + 5, y + 9, x + (52 * i) + 5, y + 12, 0xFF7A7A7A);
                drawHorizontalLine(x + 52 * skill.dependencies.get(0) + 5, x + (52 * i) + 5, y + 10, 0xFF666666);
                //drawRect(x + 52 * skill.dependencies.get(0) + 5, y + 13, x + (52 * i) + 5, y + 13, 0xFF000000);
                GL11.glColor4f(1F, 1F, 1F, 1F);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_BLEND);
            }

            for (int i = 0; i < skillArr.size(); i++) {
                Skill skill = skillArr.get(i);

                this.mc.getTextureManager().bindTexture(skillsOverviewBG);
                if (skill.dependencies.size() == 0) {
                    itemRender.renderWithColor = true;
                    drawTexturedModalRect(x + (52 * i), y, 0, 166, 22, 22);
                } else {
                    drawTexturedModalRect(x + (52 * i), y, 0, 188, 22, 22);
                }

                GL11.glTranslatef(0.0F, 0.0F, 0.0F);
                GL11.glColor4f(0.1F, 0.1F, 0.1F, 1.0F);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_CULL_FACE);
                itemRender.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.getTextureManager(), skill.itemStack, x + 3 + (52 * i), y + 3);
                //GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_BLEND);
            }
        }else{
            // Draw no skills available yet
        }
    }

    public void drawSkillTooltips(int mouseX, int mouseY)
    {
        int x = (width - xSize) / 2 + 7;
        int y = (height - ySize) / 2 + 27;

        ArrayList<Skill> skillArr = skillTrees.get(activeTab).skills;
        for(int i = 0; i < skillArr.size(); i++) {
            Skill skill = skillArr.get(i);
            if (isInPoint(mouseX, mouseY, x + (52 * i), y, 22, 22)) {
                List list = new ArrayList();
                list.add(skill.name);
                for (int j = 0; j < skill.description.length; j++)
                    list.add(skill.description[j]);
                drawHoveringText(list, mouseX, mouseY, fontRendererObj);
            }
        }
    }

    public void actionPerformed(GuiButton button)
    {
        switch(button.id)
        {
            case 10: this.mc.thePlayer.closeScreen(); break;
            default:
        }
    }

    protected void drawOverview()
    {
        int x = (width - xSize) / 2 + 10;
        int y = (height - ySize) / 2 + 7;

        int skL = 0;
        int skP = 0;
        if(activeTab == 0)
        {
            //skL = ((ExtendedProperties)mc.thePlayer.getExtendedProperties(ExtendedProperties.identifier)).miningSkillLevel;
            skL = mc.thePlayer.experienceLevel;
            skP = ((ExtendedProperties)mc.thePlayer.getExtendedProperties(ExtendedProperties.identifier)).miningSkillPoints;
        }

        fontRendererObj.drawString("Skill Level: "+skL, x, y, 0x0);
        fontRendererObj.drawString("Skill Points: " + skP, x + 160, y, 0x0);

        GL11.glTranslatef(0.0F,0.0F, 0.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        x -= 5;

        ScaledResolution scaledresolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        int scaledWidth = scaledresolution.getScaledWidth();
        int scaledHeight = scaledresolution.getScaledHeight();

        int fullXPWidth = 182;
        int calcXPWidth = (int)(this.mc.thePlayer.experience * (float)(182 + 1));

        this.mc.getTextureManager().bindTexture(Gui.icons);

        y += 10;

        drawTexturedModalRect(x + 30, y, 0, 64, fullXPWidth, 5);

        if(calcXPWidth > 0)
            drawTexturedModalRect(x + 30, y, 0, 69, calcXPWidth, 5);

        x +=2;
        y += 10;
        /*for(int i = 0; i < 5; i++)
        {
            this.mc.getTextureManager().bindTexture(skillsOverviewBG);
            //drawTexturedModalRect(x + (52*i), y, 0, 188, 22, 22);
            drawTexturedModalRect(x + (52*i), y, 0, 166, 22, 22);

            GL11.glColor4f(0.1F, 0.1F, 0.1F, 1.0F);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_CULL_FACE);
            itemRender.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.getTextureManager(), new ItemStack(Items.stone_pickaxe), x + 2 + (52*i), y + 2);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_BLEND);
        }*/
    }

    protected void drawBackgroundImage()
    {
        /*
        int displayX = (width - xSize) / 2;
        ScaledResolution var8 = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        int var4 = (this.width - xSize) / 2;
        int var5 = (this.height - ySize) / 2;
        this.mc.getTextureManager().bindTexture(skillsOverviewBG);
        this.drawTexturedModalRect(var4, var5, 0, 0, xSize, ySize);*/

        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        //GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;

        // Tabs
        for(int i = 0; i < guiTabs.length; i++) {
            RenderHelper.enableGUIStandardItemLighting();
            itemRender.renderWithColor = false;

            if(activeTab == i)
                continue;

            if(i >= 9)
            {
                this.mc.getTextureManager().bindTexture(skillsOverviewBG);
                int offset = 0;
                offset += (27 * (i-9));
                int sizeY = 30;
                drawTexturedModalRect(j + offset, k + 162, 78, 166, 28, 30);

                GL11.glColor4f(0.1F, 0.1F, 0.1F, 1.0F);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_CULL_FACE);
                itemRender.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.getTextureManager(), guiTabs[i].itemStack, j + offset + 6, k + 168);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glColor4f(1F, 1F, 1F, 1.0F);
            } else {
                this.mc.getTextureManager().bindTexture(skillsOverviewBG);
                int offset = 0;
                offset += (27 * i);
                int sizeY = 30;
                drawTexturedModalRect(j + offset, k - 24, 48, 166, 28, 30);

                GL11.glColor4f(0.1F, 0.1F, 0.1F, 1.0F);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_CULL_FACE);
                itemRender.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.getTextureManager(), guiTabs[i].itemStack, j + offset + 6, k - 18);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glColor4f(1F, 1F, 1F, 1.0F);
            }
            itemRender.renderWithColor = true;
        }

        //drawTexturedModalRect(j + 30, k - 30, 48, 166, 28, 30);
        //drawTexturedModalRect(j + 57, k - 30, 48, 166, 28, 30);
        this.mc.getTextureManager().bindTexture(skillsOverviewBG);
        drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        itemRender.renderWithColor = true;
        //activeTab
        {
            int offset = (27 * activeTab);
            if(activeTab >= 9)
                offset = (27 * (activeTab-9));
            if (activeTab == 9)
                drawTexturedModalRect(j + offset, k + 162, 134, 200, 28, 32);
            else if(activeTab > 9)
                drawTexturedModalRect(j + offset, k + 162, 162, 200, 28, 32);
            else if(activeTab == 0)
                drawTexturedModalRect(j + offset, k - 28, 48, 200, 28, 32);
            else
                drawTexturedModalRect(j + offset, k - 28, 76, 200, 28, 32);

            GL11.glColor4f(0.1F, 0.1F, 0.1F, 1.0F);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_CULL_FACE);
            if(activeTab >= 9)
                itemRender.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.getTextureManager(), guiTabs[activeTab].itemStack, j + offset + 6, k + 170);
            else
                itemRender.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.getTextureManager(), guiTabs[activeTab].itemStack, j + offset + 6, k - 20);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glColor4f(1F, 1F, 1F, 1.0F);
        }
    }

    protected void drawTabTooltips(int mouseX, int mouseY)
    {
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;

        for(int i = 0; i < guiTabs.length; i++) {
            //if(isInPoint(Mouse.getX(), Mouse.getY(), j + offset, k + 164, 28, 30))
            int offset = (27 * i);
            int offset2 = -28;
            if(i >= 9) {
                offset = (27 * (i - 9));
                offset2 = 164;
            }

            if(isInPoint(mouseX, mouseY, j+offset, k+offset2, 28, 30)) {
                List list = new ArrayList();
                list.add(guiTabs[i].name);
                drawHoveringText(list, mouseX, mouseY, fontRendererObj);
            }
        }
    }

    protected boolean isInPoint(int mouseX, int mouseY, int boxX, int boxY, int sizeX, int sizeY)
    {
        return mouseX > boxX && mouseX < boxX + sizeX && mouseY > boxY && mouseY < boxY + sizeY;
    }
}