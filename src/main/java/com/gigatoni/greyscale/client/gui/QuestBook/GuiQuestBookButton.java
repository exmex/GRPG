package com.gigatoni.greyscale.client.gui.QuestBook;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class GuiQuestBookButton extends GuiButton {
    public ResourceLocation texture;
    private int textureX;
    private int textureY;
    private int textureHoverX;
    private int textureHoverY;


    public GuiQuestBookButton(int id, int x, int y, int textureX, int textureY, int textureHoverX, int textureHoverY, int width, int height, ResourceLocation texture)
    {
        super(id, x, y, width, height, "");
        this.texture = texture;
        this.displayString = null;
        this.width = width;
        this.height = height;
        this.textureX = textureX;
        this.textureY = textureY;
        this.textureHoverX = textureHoverX;
        this.textureHoverY = textureHoverY;
    }

    public GuiQuestBookButton(int id, int x, int y, int width, int height, ResourceLocation texture, String text)
    {
        super(id, x, y, 20, 20, "");
        this.texture = texture;
        this.displayString = text;
        this.width = width;
        this.height = height;
    }

    public GuiQuestBookButton(int id, int x, int y, int width, int height, ResourceLocation texture)
    {
        super(id, x, y, 20, 20, "");
        this.texture = texture;
        this.displayString = null;
        this.width = width;
        this.height = height;
    }

    public void drawButton(Minecraft mc, int x, int y)
    {
        if (this.visible)
        {
            FontRenderer fontrenderer = mc.fontRenderer;
            mc.getTextureManager().bindTexture(texture);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_146123_n = x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height;
            int k = this.getHoverState(this.field_146123_n);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            //this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, k * 20, this.width + 1, this.height);
            if(textureX != 0){
                if(k == 2)
                    this.drawTexturedModalRect(this.xPosition, this.yPosition, this.textureHoverX, this.textureHoverY, this.width, this.height);
                else
                    this.drawTexturedModalRect(this.xPosition, this.yPosition, this.textureX, this.textureY, this.width, this.height);
            }else {
                this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + k * 20, this.width / 2, this.height);
                this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + k * 20, this.width / 2, this.height);
            }
            this.mouseDragged(mc, x, y);
            int l = 14737632;

            if (packedFGColour != 0)
            {
                l = packedFGColour;
            }
            else if (!this.enabled)
            {
                l = 10526880;
            }
            else if (this.field_146123_n)
            {
                l = 16777120;
            }
            if (this.displayString != null)
                this.drawCenteredString(mc.fontRenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, l);
        }
    }
}
