package com.gigatoni.greyscale.client.gui.QuestBook;

import com.gigatoni.greyscale.reference.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class GuiQuestBook extends GuiScreen {

    private static int xSize = 253;
    private static int ySize = 166;

    private ResourceLocation textureBook = new ResourceLocation(Reference.MOD_ID + ":textures/gui/questBook/background.png");

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

        this.buttonList.add(new GuiQuestBookButton(0, 55, 185, 75, 20, new ResourceLocation(Reference.MOD_ID +":textures/gui/widgets.png"), "Close"));
        this.buttonList.add(new GuiQuestBookButton(1, 40, 158, 3, 207, 26, 207, 18, 10, textureBook));
        this.buttonList.add(new GuiQuestBookButton(2, 120, 158, 3, 194, 26, 194, 18, 10, textureBook));

        super.initGui();
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    public void actionPerformed(GuiButton button)
    {
        switch(button.id)
        {
            case 0: this.mc.thePlayer.closeScreen(); break;
            default:
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseZ) {

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
        drawBackground();
        for (Object aButtonList : buttonList)
            ((GuiButton) aButtonList).drawButton(Minecraft.getMinecraft(), x, y);

        mc.getTextureManager().bindTexture(textureBook);
        //drawTexturedModalRect(40, 158, 3, 207, 18, 10); // 26, 207 -- selected
        //drawTexturedModalRect(120, 158, 3, 194, 18, 10);

        mc.fontRenderer.drawString("Quest# 1", 40, 20, Color.BLACK.getRGB(), false);
        mc.fontRenderer.drawString("Quest# 2", 40, 30, Color.BLACK.getRGB(), false);
        mc.fontRenderer.drawString("Quest# 3", 40, 40, Color.BLACK.getRGB(), false);
        mc.fontRenderer.drawString("Quest# 4", 40, 50, Color.BLACK.getRGB(), false);
        String superLongTitle = "Superlongtitlethatshouldbecroppedbutitisntcurrentlysowehavetoimplementit";
        if(superLongTitle.length() <= 20) // cheap.
            mc.fontRenderer.drawString(superLongTitle, 40, 60, Color.BLACK.getRGB(), false);
        else
            mc.fontRenderer.drawString(superLongTitle.substring(0, 20) + "...", 40, 60, Color.BLACK.getRGB(), false);
    }

    public void drawBackground(){
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(textureBook);
        drawTexturedModalRect(0, 0, 0, 0, 186, 182);
    }
}
