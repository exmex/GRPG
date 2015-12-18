package com.gigatoni.greyscale;

import com.gigatoni.greyscale.client.renderer.WorldRenderEvent;
import com.gigatoni.greyscale.handler.ConfigHandler;
import com.gigatoni.greyscale.handler.EntityHandler;
import com.gigatoni.greyscale.handler.GuiHandler;
import com.gigatoni.greyscale.items.Items;
import com.gigatoni.greyscale.proxy.IProxy;
import com.gigatoni.greyscale.reference.Reference;
import com.gigatoni.greyscale.reference.SkillsReference;
import com.gigatoni.greyscale.utility.LogHelper;
import com.gigatoni.greyscale.utility.SchematicUtil;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, guiFactory = Reference.GUI_FACTORY_CLASS, acceptedMinecraftVersions = "[1.7.10,)")
public class Greyscale
{
    @Mod.Instance(value = Reference.MOD_ID)
    public static Greyscale INSTANCE;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static IProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        ConfigHandler.init(event.getSuggestedConfigurationFile(), event.getModConfigurationDirectory());

        // Register instance.
        INSTANCE = this;

        SkillsReference.initialize();

        Reference.loadedSchematic = new SchematicUtil().get(Reference.schematics[0]);
        if (Reference.loadedSchematic == null) {
            //Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Schematic is ded!"));
            LogHelper.warn(Reference.schematics[0] + " Schematic NOT loaded!");
        }

        //Loader.isModLoaded("");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();

        NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new GuiHandler());
        MinecraftForge.EVENT_BUS.register(new EntityHandler());
        //MinecraftForge.EVENT_BUS.register(new WorldRenderEvent());

        Items.Initialize();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {

    }

    @Mod.EventHandler
    public void onServerStarted(FMLServerStartedEvent event) {

    }

    @Mod.EventHandler
    public void onServerStopping(FMLServerStoppingEvent event) {

    }
}
