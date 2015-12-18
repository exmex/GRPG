package com.gigatoni.greyscale.handler;

import com.gigatoni.greyscale.client.ExtendedProperties;
import com.gigatoni.greyscale.reference.Reference;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import java.util.UUID;

public class EntityHandler {

    @SubscribeEvent(priority= EventPriority.NORMAL)
    public void onEntityConstructing(EntityConstructing e)
    {
        if (e.entity instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer)e.entity;
            player.registerExtendedProperties(ExtendedProperties.identifier, new ExtendedProperties());
        }
    }
}
