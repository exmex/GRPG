package com.gigatoni.greyscale.reference;

import com.gigatoni.greyscale.utility.SchematicUtil;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.Vec3;

public class Reference {
    public static final String MOD_ID = "greyscale";
    public static final String LOWERCASE_MOD_ID = "greyscale";
    public static final String MOD_NAME = "Greyscale Dev";
    public static final String VERSION = "1.7.10-1.0";

    public static final String CLIENT_PROXY_CLASS = "com.gigatoni.greyscale.proxy.ClientProxy";
    public static final String SERVER_PROXY_CLASS = "com.gigatoni.greyscale.proxy.ServerProxy";
    public static final String GUI_FACTORY_CLASS = "com.gigatoni.greyscale.client.gui.GuiFactory";


    public static final int SKILL_UI_ID = 20;

    public static CreativeTabs genericRPGItems = new CreativeTabs("GRPG Items") {
        @Override
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem() {
            return Items.chainmail_helmet;
        }
    };

    public static final Item debugItem = GameRegistry.findItem(Reference.MOD_ID, "debug");

    public static SchematicUtil.Schematic loadedSchematic;
    public static String[] schematics = {
            "Blacksmith",
            "Bakery",
            "Box-T1",
            "Box-T2",
            "Carpenter",
            "Butcher",
    };

    public static Vec3[] schematicOffsets = {
            Vec3.createVectorHelper(0, -4, 0),
            Vec3.createVectorHelper(0, 0, 0),
            Vec3.createVectorHelper(0, 0, 0),
            Vec3.createVectorHelper(0, 0, 0),
            Vec3.createVectorHelper(0, -4, 0),
            Vec3.createVectorHelper(0, -1, 0),
    };

    public static Vec3 schematicPosition;
    public static int schematicRotation;
}
