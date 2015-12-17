package com.gigatoni.greyscale.client.gui;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

/**
 * Created by GigaToni on 6/16/2015.
 */
public class Mod_GuiHud extends Gui {
    private Minecraft mc;

    public Mod_GuiHud(Minecraft mc) {
        super();
        this.mc = mc;
    }

    @SubscribeEvent
    public void RenderGameOverlay(RenderGameOverlayEvent event) {
        if((event.type.equals(RenderGameOverlayEvent.ElementType.HEALTH) ||
                event.type.equals(RenderGameOverlayEvent.ElementType.FOOD) ||
                event.type.equals(RenderGameOverlayEvent.ElementType.ARMOR) ||
                event.type.equals(RenderGameOverlayEvent.ElementType.EXPERIENCE)) && event.isCancelable())
            event.setCanceled(true);
    }
}
