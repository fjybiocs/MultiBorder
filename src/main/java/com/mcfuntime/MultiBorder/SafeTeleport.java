package com.mcfuntime.MultiBorder;

// modified from https://github.com/Brettflan/WorldBorder/blob/44f388f3ba8d5d84d6e4cf223beedf8bc35c7191/src/main/java/com/wimbli/WorldBorder/BorderData.java

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.EnumSet;

public class SafeTeleport {

    //these material IDs are acceptable for places to teleport player; breathable blocks and water
    public static final EnumSet<Material> safeOpenBlocks = EnumSet.noneOf(Material.class);
    static {
        safeOpenBlocks.add(Material.AIR);
        safeOpenBlocks.add(Material.CAVE_AIR);
        safeOpenBlocks.add(Material.OAK_SAPLING);
        safeOpenBlocks.add(Material.SPRUCE_SAPLING);
        safeOpenBlocks.add(Material.BIRCH_SAPLING);
        safeOpenBlocks.add(Material.JUNGLE_SAPLING);
        safeOpenBlocks.add(Material.ACACIA_SAPLING);
        safeOpenBlocks.add(Material.DARK_OAK_SAPLING);
        safeOpenBlocks.add(Material.WATER);
        safeOpenBlocks.add(Material.RAIL);
        safeOpenBlocks.add(Material.POWERED_RAIL);
        safeOpenBlocks.add(Material.DETECTOR_RAIL);
        safeOpenBlocks.add(Material.ACTIVATOR_RAIL);
        safeOpenBlocks.add(Material.COBWEB);
        safeOpenBlocks.add(Material.GRASS);
        safeOpenBlocks.add(Material.FERN);
        safeOpenBlocks.add(Material.DEAD_BUSH);
        safeOpenBlocks.add(Material.DANDELION);
        safeOpenBlocks.add(Material.POPPY);
        safeOpenBlocks.add(Material.BLUE_ORCHID);
        safeOpenBlocks.add(Material.ALLIUM);
        safeOpenBlocks.add(Material.AZURE_BLUET);
        safeOpenBlocks.add(Material.RED_TULIP);
        safeOpenBlocks.add(Material.ORANGE_TULIP);
        safeOpenBlocks.add(Material.WHITE_TULIP);
        safeOpenBlocks.add(Material.PINK_TULIP);
        safeOpenBlocks.add(Material.OXEYE_DAISY);
        safeOpenBlocks.add(Material.BROWN_MUSHROOM);
        safeOpenBlocks.add(Material.RED_MUSHROOM);
        safeOpenBlocks.add(Material.TORCH);
        safeOpenBlocks.add(Material.WALL_TORCH);
        safeOpenBlocks.add(Material.REDSTONE_WIRE);
        safeOpenBlocks.add(Material.WHEAT);
        safeOpenBlocks.add(Material.LADDER);
        safeOpenBlocks.add(Material.LEVER);
        safeOpenBlocks.add(Material.LIGHT_WEIGHTED_PRESSURE_PLATE);
        safeOpenBlocks.add(Material.HEAVY_WEIGHTED_PRESSURE_PLATE);
        safeOpenBlocks.add(Material.STONE_PRESSURE_PLATE);
        safeOpenBlocks.add(Material.OAK_PRESSURE_PLATE);
        safeOpenBlocks.add(Material.SPRUCE_PRESSURE_PLATE);
        safeOpenBlocks.add(Material.BIRCH_PRESSURE_PLATE);
        safeOpenBlocks.add(Material.JUNGLE_PRESSURE_PLATE);
        safeOpenBlocks.add(Material.ACACIA_PRESSURE_PLATE);
        safeOpenBlocks.add(Material.DARK_OAK_PRESSURE_PLATE);
        safeOpenBlocks.add(Material.REDSTONE_TORCH);
        safeOpenBlocks.add(Material.REDSTONE_WALL_TORCH);
        safeOpenBlocks.add(Material.STONE_BUTTON);
        safeOpenBlocks.add(Material.SNOW);
        safeOpenBlocks.add(Material.SUGAR_CANE);
        safeOpenBlocks.add(Material.REPEATER);
        safeOpenBlocks.add(Material.COMPARATOR);
        safeOpenBlocks.add(Material.OAK_TRAPDOOR);
        safeOpenBlocks.add(Material.SPRUCE_TRAPDOOR);
        safeOpenBlocks.add(Material.BIRCH_TRAPDOOR);
        safeOpenBlocks.add(Material.JUNGLE_TRAPDOOR);
        safeOpenBlocks.add(Material.ACACIA_TRAPDOOR);
        safeOpenBlocks.add(Material.DARK_OAK_TRAPDOOR);
        safeOpenBlocks.add(Material.MELON_STEM);
        safeOpenBlocks.add(Material.ATTACHED_MELON_STEM);
        safeOpenBlocks.add(Material.PUMPKIN_STEM);
        safeOpenBlocks.add(Material.ATTACHED_PUMPKIN_STEM);
        safeOpenBlocks.add(Material.VINE);
        safeOpenBlocks.add(Material.NETHER_WART);
        safeOpenBlocks.add(Material.TRIPWIRE);
        safeOpenBlocks.add(Material.TRIPWIRE_HOOK);
        safeOpenBlocks.add(Material.CARROTS);
        safeOpenBlocks.add(Material.POTATOES);
        safeOpenBlocks.add(Material.OAK_BUTTON);
        safeOpenBlocks.add(Material.SPRUCE_BUTTON);
        safeOpenBlocks.add(Material.BIRCH_BUTTON);
        safeOpenBlocks.add(Material.JUNGLE_BUTTON);
        safeOpenBlocks.add(Material.ACACIA_BUTTON);
        safeOpenBlocks.add(Material.DARK_OAK_BUTTON);
        safeOpenBlocks.add(Material.SUNFLOWER);
        safeOpenBlocks.add(Material.LILAC);
        safeOpenBlocks.add(Material.ROSE_BUSH);
        safeOpenBlocks.add(Material.PEONY);
        safeOpenBlocks.add(Material.TALL_GRASS);
        safeOpenBlocks.add(Material.LARGE_FERN);
        safeOpenBlocks.add(Material.BEETROOTS);
        try {	// signs in 1.14 can be different wood types
            safeOpenBlocks.add(Material.ACACIA_SIGN);
            safeOpenBlocks.add(Material.ACACIA_WALL_SIGN);
            safeOpenBlocks.add(Material.BIRCH_SIGN);
            safeOpenBlocks.add(Material.BIRCH_WALL_SIGN);
            safeOpenBlocks.add(Material.DARK_OAK_SIGN);
            safeOpenBlocks.add(Material.DARK_OAK_WALL_SIGN);
            safeOpenBlocks.add(Material.JUNGLE_SIGN);
            safeOpenBlocks.add(Material.JUNGLE_WALL_SIGN);
            safeOpenBlocks.add(Material.OAK_SIGN);
            safeOpenBlocks.add(Material.OAK_WALL_SIGN);
            safeOpenBlocks.add(Material.SPRUCE_SIGN);
            safeOpenBlocks.add(Material.SPRUCE_WALL_SIGN);
        }
        catch (NoSuchFieldError ex) {}
    }

    //these material IDs are ones we don't want to drop the player onto, like cactus or lava or fire or activated Ender portal
    public static final EnumSet<Material> painfulBlocks = EnumSet.noneOf(Material.class);
    static {
        painfulBlocks.add(Material.LAVA);
        painfulBlocks.add(Material.FIRE);
        painfulBlocks.add(Material.CACTUS);
        painfulBlocks.add(Material.END_PORTAL);
        painfulBlocks.add(Material.MAGMA_BLOCK);
    }

    // check if a particular spot consists of 2 breathable blocks over something relatively solid
    private static boolean isSafeSpot(World world, int X, int Y, int Z, boolean flying) {
        boolean safe = safeOpenBlocks.contains(world.getBlockAt(X, Y, Z).getType())		// target block open and safe
                && safeOpenBlocks.contains(world.getBlockAt(X, Y + 1, Z).getType());	// above target block open and safe
        Material below = world.getBlockAt(X, Y - 1, Z).getType();
        return (safe
                && (!safeOpenBlocks.contains(below) || below== Material.WATER)	// below target block not open/breathable (so presumably solid), or is water
                && !painfulBlocks.contains(below)									// below target block not painful
        );
    }

    // find the closest safe Y position from the starting position
    private static double getSafeY(World world, int X, int Y, int Z, boolean flying) {
        // artificial height limit of 127 added for Nether worlds since CraftBukkit still incorrectly returns 255 for their max height, leading to players sent to the "roof" of the Nether
        final boolean isNether = world.getEnvironment() == World.Environment.NETHER;
        int limTop = isNether ? 125 : world.getMaxHeight() - 2;
        int limBot = 63;
        final int highestBlockBoundary = Math.min(world.getHighestBlockYAt(X, Z) + 1, limTop);

        for(int y1 = Y, y2 = Y; (y1 > limBot) || (y2 < limTop); y1--, y2++){
            // Look above.
            if(y2 < limTop){
                if (isSafeSpot(world, X, y2, Z, flying))
                    return y2;
            }
            // Look below.
            if(y1 > limBot && y2 != y1){
                if (isSafeSpot(world, X, y1, Z, flying))
                    return y1;
            }

        }

        return -100;
    }

    public static void safeTeleport(Player p, Location loc){
        assert loc.getWorld() != null;
        double desY = getSafeY(loc.getWorld(), (int)loc.getX(), (int)loc.getY(), (int)loc.getZ(), p.isFlying());

        if(desY == -100){
            desY = loc.getWorld().getHighestBlockYAt((int)loc.getX(), (int)loc.getZ());
        }

        Location desLoc = new Location(loc.getWorld(), loc.getX(), desY, loc.getZ(), loc.getYaw(), loc.getPitch());
        p.teleport(desLoc);
    }
}
