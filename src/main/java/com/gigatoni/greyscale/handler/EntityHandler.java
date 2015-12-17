package com.gigatoni.greyscale.handler;

import com.gigatoni.greyscale.client.ExtendedProperties;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;

public class EntityHandler {

    @SubscribeEvent(priority= EventPriority.NORMAL)
    public void onEntityConstructing(EntityConstructing e)
    {
        if (e.entity instanceof EntityPlayer)
            e.entity.registerExtendedProperties(ExtendedProperties.identifier, new ExtendedProperties());
    }
}
