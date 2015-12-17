package com.gigatoni.greyscale.proxy;

import com.gigatoni.greyscale.client.gui.Mod_GuiHud;
import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

public class ClientProxy extends CommonProxy {
    public static KeyBinding[] keyBindings;

    @Override
    public void init()
    {
        keyBindings = new KeyBinding[1];
        keyBindings[0] = new KeyBinding("key.structure.desc", Keyboard.KEY_P, "key.magicbeans.category");
        for (int i = 0; i < keyBindings.length; ++i)
            ClientRegistry.registerKeyBinding(keyBindings[i]);

        MinecraftForge.EVENT_BUS.register(new Mod_GuiHud(Minecraft.getMinecraft()));
    }
}