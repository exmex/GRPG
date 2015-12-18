package com.gigatoni.greyscale.client.gui;

import com.gigatoni.greyscale.reference.Reference;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import static org.lwjgl.opengl.GL11.glScalef;

public class Mod_GuiHud extends Gui {
    private Minecraft mc;
    private static final ResourceLocation healthBar = new ResourceLocation(Reference.LOWERCASE_MOD_ID, "textures/gui/healthBar.png");

    public Mod_GuiHud(Minecraft mc) {
        super();
        this.mc = mc;
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void RenderGameOverlay(RenderGameOverlayEvent event) {
        if(event.type.equals(RenderGameOverlayEvent.ElementType.HEALTH)) {
            RenderHealth(event);
            RenderSkull(event);
        }

        if((event.type.equals(RenderGameOverlayEvent.ElementType.HEALTH) ||
                event.type.equals(RenderGameOverlayEvent.ElementType.FOOD) ||
                event.type.equals(RenderGameOverlayEvent.ElementType.ARMOR) ||
                event.type.equals(RenderGameOverlayEvent.ElementType.EXPERIENCE)
                ) && event.isCancelable())
            event.setCanceled(true);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onRenderLiving(RenderLivingEvent.Specials.Pre event) {
        if(event.entity instanceof EntityPlayer && event.isCancelable()) {
            event.setCanceled(true);
        }
    }

    private void RenderQuestList(RenderGameOverlayEvent event){

    }

    private void RenderSkull(RenderGameOverlayEvent event) {
        ModelSkeletonHead modelskeletonhead = new ModelSkeletonHead(0, 0, 64, 32);
        ResourceLocation resourcelocation = AbstractClientPlayer.locationStevePng;
        if (mc.thePlayer.getGameProfile() != null)
        {
            Minecraft minecraft = Minecraft.getMinecraft();
            Map map = minecraft.func_152342_ad().func_152788_a(mc.thePlayer.getGameProfile());

            if (map.containsKey(MinecraftProfileTexture.Type.SKIN))
            {
                resourcelocation = minecraft.func_152342_ad().func_152792_a((MinecraftProfileTexture)map.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN);
            }
        }
        mc.getTextureManager().bindTexture(resourcelocation);

        GL11.glPushMatrix();
        GL11.glColor3f(1F, 1F, 1F);
        GL11.glEnable(32826 /* GL_RESCALE_NORMAL_EXT */);
        GL11.glEnable(2903 /* GL_COLOR_MATERIAL */);
        GL11.glPushMatrix();
        GL11.glTranslatef(27, 32, 50F);
        float f1 = 30F;
        GL11.glScalef(-f1, f1, f1);
        GL11.glRotatef(0F, 0.0F, 0.0F, 1.0F);
        GL11.glRotatef(-80F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GL11.glRotatef(-135F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(0.0F, 1.0F, 0.0F, 0.0F);
        GL11.glTranslatef(0.0F, 0.0F, 0.0F);
        modelskeletonhead.render((Entity) null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(32826 /* GL_RESCALE_NORMAL_EXT */);
        GL11.glTranslatef(0F, 0F, 0.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(32826 /* GL_RESCALE_NORMAL_EXT */);
        int i1 = 240;
        int k1 = 240;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, i1 / 1.0F, k1 / 1.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(32826 /* GL_RESCALE_NORMAL_EXT */);
        RenderHelper.disableStandardItemLighting();
        //GL11.glDisable(2896 /* GL_LIGHTING */);
        //GL11.glDisable(2929 /* GL_DEPTH_TEST */);
        GL11.glPopMatrix();
    }

    ArrayList<UUID> loadedCapes = new ArrayList<UUID>();
    @SubscribeEvent
    public void renderPlayer(RenderLivingEvent.Pre event) {
        if (event.entity instanceof AbstractClientPlayer) {
            AbstractClientPlayer player = (AbstractClientPlayer) event.entity;

            if((player.getGameProfile().getId().toString().equals("0c312a86-5c74-48f1-8f54-f5837d518bec") || (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment")) && !loadedCapes.contains(player.getGameProfile().getId())) {
                player.func_152121_a(MinecraftProfileTexture.Type.CAPE, new ResourceLocation(Reference.MOD_ID + ":textures/misc/cape.png"));
                loadedCapes.add(player.getGameProfile().getId());
            }
        }
    }

    private void RenderHealth(RenderGameOverlayEvent event) {
        if(mc.theWorld == null || mc.thePlayer == null){
            return;
        }

        EntityClientPlayerMP player = mc.thePlayer;

        if(player.capabilities.isCreativeMode){
            return;
        }

        ScaledResolution resolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);

        int height = resolution.getScaledHeight();
        int width = resolution.getScaledWidth();

        GL11.glPushMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.9F);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        //GL11.glDisable(GL11.GL_LIGHTING);

        mc.getTextureManager().bindTexture(healthBar);

        // Background
        mc.ingameGUI.drawTexturedModalRect(2, 2, 0, 0, 162, 60);

        // Health
        int healthBarWidth = MathHelper.ceiling_float_int(((player.getHealth() / player.getMaxHealth()) * 108));
        mc.ingameGUI.drawTexturedModalRect(52, 16, 0, 60, healthBarWidth, 10);

        // Armor
        int armorBarWidth = MathHelper.ceiling_float_int(((mc.thePlayer.getTotalArmorValue() / 20.0f) * 108));
        mc.ingameGUI.drawTexturedModalRect(52, 29, 0, 70, armorBarWidth, 10);

        if(mc.objectMouseOver != null){
            if(mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY && mc.objectMouseOver.entityHit instanceof EntityLivingBase){

                // TODO: Make bg only appear when actually hover over smb
                EntityLivingBase viewEntity = (EntityLivingBase)mc.objectMouseOver.entityHit;
                mc.ingameGUI.drawTexturedModalRect(width - 126, 12, 0, 90, 116, 16);
                if(viewEntity instanceof EntityPlayer) {
                    mc.ingameGUI.drawTexturedModalRect(width - 126, 27, 0, 106, 116, 15);

                    int entityArmorWidth = MathHelper.ceiling_float_int(((viewEntity.getTotalArmorValue() / 20.0f) * 108));
                    mc.ingameGUI.drawTexturedModalRect(width - 122, 28, 0, 70, entityArmorWidth, 10);
                }

                int viewEntityHealthBarWidth = MathHelper.ceiling_float_int((viewEntity.getHealth() / viewEntity.getMaxHealth()) * 108);
                mc.ingameGUI.drawTexturedModalRect(width - 122, 15, 0, 60, viewEntityHealthBarWidth, 10);

                // Texts
                mc.ingameGUI.drawCenteredString(mc.fontRenderer, viewEntity.getCommandSenderName(), width - 70, 2, Color.CYAN.getRGB());
            }
        }

        int experienceBarWidth = MathHelper.ceiling_float_int((mc.thePlayer.experience) * 86);
        mc.ingameGUI.drawTexturedModalRect(66, 53, 0, 80, experienceBarWidth, 6);

        // Draw strings
        glScalef(0.7f, 0.7f, 0.7f);
        mc.fontRenderer.drawStringWithShadow(String.valueOf(MathHelper.ceiling_float_int(mc.thePlayer.experience * 100)) + "%", 152, 76, Color.GREEN.getRGB());
        if(mc.thePlayer.experienceLevel > 999)
            mc.fontRenderer.drawStringWithShadow("999", 47, 60, Color.GREEN.getRGB());
        else
            mc.fontRenderer.drawStringWithShadow(String.valueOf(mc.thePlayer.experienceLevel), 47, 60, Color.GREEN.getRGB());
        GL11.glPopMatrix();
        GL11.glPushMatrix();

        mc.fontRenderer.drawStringWithShadow(String.valueOf((int) player.getHealth()) + "/" + (int) player.getMaxHealth(), 90, 17, Color.WHITE.getRGB());
        mc.fontRenderer.drawStringWithShadow(String.valueOf(MathHelper.ceiling_float_int(mc.thePlayer.getTotalArmorValue() / 20.0f * 100.0f)) + "/100", 90, 30, Color.WHITE.getRGB());

        //GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_BLEND);
    }
}
