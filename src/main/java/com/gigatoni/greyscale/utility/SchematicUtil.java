package com.gigatoni.greyscale.utility;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockStairs;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.io.InputStream;

/**
 * Created by multimote on 05.07.14.
 */
public class SchematicUtil {
    public Schematic get(String res){
        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("assets/greyscale/mcschematic/"+res+".schematic");
            if(is==null)
            {
                LogHelper.warn("I can't load schematic, because i can't open file.");
                return null;
            }

            NBTTagCompound nbtdata = CompressedStreamTools.readCompressed(is);
            short width = nbtdata.getShort("Width");
            short height = nbtdata.getShort("Height");
            short length = nbtdata.getShort("Length");

            byte[] blocks = nbtdata.getByteArray("Blocks");
            byte[] data = nbtdata.getByteArray("Data");
            LogHelper.info("Loaded schem size: " + width + " x " + height + " x " + length);
            NBTTagList tileentities = nbtdata.getTagList("TileEntities", 10);
            is.close();
            return new Schematic(tileentities, width, height, length, blocks, data);
        } catch (Exception e) {
            LogHelper.warn("I can't load schematic, because " + e.toString());
            return null;
        }
    }

    public class Schematic { // <3
        public  NBTTagList tileentities;
        public  short width;
        public  short height;
        public short length;
        public byte[] blocks;
        public byte[] data;
        public Schematic(NBTTagList tileentities, short width, short height, short length, byte[] blocks, byte[] data){
            this.tileentities = tileentities;
            this.width = width;
            this.height = height;
            this.length = length;
            this.blocks = blocks;
            this.data = data;
        }

    }

    public class rotations{
        public int[] rs;
        public rotations(int... r){
            rs = r;
        }
        public int getSide(int meta){
            for(int i = 0; i<rs.length; i++)
                if(rs[i]==meta)return i;
            return -1;
        }

        public int getMeta(int side){
            return rs[side];
        }
    }

    public int[] blockCoordsRotation(int blockX, int blockZ, int rotation){
        int[] coords={blockX, blockZ};
        switch (rotation){
            case 1:  coords[0]=-blockZ; coords[1]=blockX; break;
            case 2:  coords[0]=-blockX; coords[1]=-blockZ; break;
            case 3:  coords[0]=blockZ; coords[1]=-blockX; break;
        }
        return coords;
    }

    public rotations signRotations = new rotations(2,5,3,4);
    public rotations stairsRotations = new rotations(2,1,3,0);
    public rotations chestRotations = new rotations(4,2,5,3);
    public rotations pumpkinRotations = new rotations(2,3,0,1);
    public rotations torchRotations = new rotations(4,1,3,2);

    public rotations doorRotations = new rotations(1,2,3,0);

    public int rotateMeta(int blockId, int meta, int rotation){ //todo rewrite this shit

        if(rotation>0) {
            if(Block.getIdFromBlock(Blocks.torch)==blockId || Block.getIdFromBlock(Blocks.redstone_torch)==blockId)
                return torchRotations.getMeta( (torchRotations.getSide(meta)+rotation)%4 );

            if(meta<4 && Block.getBlockById(blockId) instanceof BlockDoor)
                return doorRotations.getMeta( (doorRotations.getSide(meta)+rotation)%4 );

            if(Block.getIdFromBlock(Blocks.wall_sign)==blockId)
                return signRotations.getMeta( (signRotations.getSide(meta)+rotation)%4 );

            if(Block.getIdFromBlock(Blocks.ladder)==blockId)
                return signRotations.getMeta( (signRotations.getSide(meta)+rotation)%4 );

            if(Block.getIdFromBlock(Blocks.chest)==blockId || Block.getIdFromBlock(Blocks.ender_chest)==blockId)
                return chestRotations.getMeta( (chestRotations.getSide(meta)+rotation)%4 );

            if(Block.getIdFromBlock(Blocks.furnace)==blockId || Block.getIdFromBlock(Blocks.lit_furnace)==blockId)
                return signRotations.getMeta( (signRotations.getSide(meta)+rotation)%4 );

            if(Block.getIdFromBlock(Blocks.pumpkin)==blockId || Block.getIdFromBlock(Blocks.lit_pumpkin)==blockId)
                return pumpkinRotations.getMeta( (pumpkinRotations.getSide(meta)+rotation)%4 );

            if(Block.getBlockById(blockId) instanceof BlockStairs)
                return stairsRotations.getMeta((stairsRotations.getSide(meta)+rotation)%4);
        }

        return meta;
    }
}