package com.gigatoni.greyscale.client.renderer;

import com.gigatoni.greyscale.items.ItemDebug;
import com.gigatoni.greyscale.reference.Reference;
import com.gigatoni.greyscale.utility.LogHelper;
import com.gigatoni.greyscale.utility.SchematicUtil;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

public class WorldRenderEvent extends Event {
    @SubscribeEvent
    public void onRender(RenderWorldLastEvent e)
    {
        if (Reference.loadedSchematic != null && Reference.schematicPosition != null) {
            /*int x = (int) Math.floor(Minecraft.getMinecraft().thePlayer.posX);
            int y = (int) Math.floor(Minecraft.getMinecraft().thePlayer.posY);
            int z = (int) Math.floor(Minecraft.getMinecraft().thePlayer.posZ);*/
            int x = (int)Math.floor(Reference.schematicPosition.xCoord);
            int y = (int)Math.floor(Reference.schematicPosition.yCoord);
            int z = (int)Math.floor(Reference.schematicPosition.zCoord);

            GL11.glPushMatrix();
            GL11.glTranslated(x, y, z);
            renderShape(x, y, z, Minecraft.getMinecraft().thePlayer.rotationYaw, 0x00FFFFFF, 1.0f);
            GL11.glPopMatrix();
        }
    }

    private final DisplayListWrapper wrapper = new DisplayListWrapper() {
        @Override
        public void compile() {
            Tessellator t = Tessellator.instance;
            RenderBlocks renderBlocks = new RenderBlocks();
            renderBlocks.setRenderBounds(0.05D, 0.05D, 0.05D, 0.95D, 0.95D, 0.95D);
            t.startDrawingQuads();
            t.setBrightness(200);
            renderBlocks.renderFaceXNeg(Blocks.cobblestone, -0.5D, 0.0D, -0.5D, Blocks.cobblestone.getIcon(0, 0));
            renderBlocks.renderFaceXPos(Blocks.cobblestone, -0.5D, 0.0D, -0.5D, Blocks.cobblestone.getIcon(0, 0));
            renderBlocks.renderFaceYNeg(Blocks.cobblestone, -0.5D, 0.0D, -0.5D, Blocks.cobblestone.getIcon(0, 0));
            renderBlocks.renderFaceYPos(Blocks.cobblestone, -0.5D, 0.0D, -0.5D, Blocks.cobblestone.getIcon(0, 0));
            renderBlocks.renderFaceZNeg(Blocks.cobblestone, -0.5D, 0.0D, -0.5D, Blocks.cobblestone.getIcon(0, 0));
            renderBlocks.renderFaceZPos(Blocks.cobblestone, -0.5D, 0.0D, -0.5D, Blocks.cobblestone.getIcon(0, 0));
            t.draw();
        }
    };

    private void renderShape(int x, int y, int z, float rotationYaw, int color, float scale) {
        if (Reference.loadedSchematic == null) return;

        final Minecraft mc = Minecraft.getMinecraft();
        if (mc != null) {
            mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        } else {
            LogHelper.warn("Binding texture to null client.");
            return;
        }

        GL11.glColor4f(1F, 1F, 1F, 1F);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GL11.glDisable(GL11.GL_LIGHTING);

        int rotation = MathHelper.floor_double((double) (rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        SchematicUtil schematicUtil = new SchematicUtil();
        int i = 0;
        for (int sy = 0; sy < Reference.loadedSchematic.height; sy++)
            for (int sz = 0; sz < Reference.loadedSchematic.length; sz++)
                for (int sx = 0; sx < Reference.loadedSchematic.width; sx++) {
                    Block b = Block.getBlockById(Reference.loadedSchematic.blocks[i]);
                    if (b != Blocks.air) {
                        int rx = schematicUtil.blockCoordsRotation(sx - getXShift(), sz, rotation)[0];
                        int rz = schematicUtil.blockCoordsRotation(sx - getXShift(), sz, rotation)[1];
                        renderAt(x + rx, y + getYOffset() + sy, z + rz, 1.0f);
                    }
                    i++;
                }

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
    }

    private int getXShift() { return -25; }
    private int getYOffset() { return -60; }

    private void renderAt(double x, double y, double z, float scale) {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5F, y, z + 0.5F);
        GL11.glScalef(scale, scale, scale);
        wrapper.render();
        GL11.glPopMatrix();
    }
}