package com.mcfuntime.MultiBorder;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Border {
    // automatically generated getters by idea
    public double getXMin() {
        return xMin;
    }
    public double getXMax() {
        return xMax;
    }
    public double getZMax() {
        return zMax;
    }
    public double getZMin() {
        return zMin;
    }
    public double getSpawnX(){
        return this.spawnX;
    }
    public double getSpawnZ(){
        return this.spawnZ;
    }

    private final double xMin;
    private final double xMax;
    private final double zMax;
    private final double zMin;
    private final double spawnX;
    private final double spawnZ;
    private final String worldName;

    public Border(double xMax, double xMin, double zMax, double zMin, double spawnX, double spawnZ, String woldName){
        this.xMin = xMin;
        this.xMax = xMax;
        this.zMax = zMax;
        this.zMin = zMin;
        this.spawnX = spawnX;
        this.spawnZ = spawnZ;
        this.worldName = woldName;
    }
    public boolean isInsideBorder(double x, double z){
        return !(x < this.xMax) || !(x > this.xMin) || !(z < this.zMax) || !(z > this.zMin);
    }

    // knock back the player from the edge
    public void knockBack(Player player, double distance){
        Location curLoc = player.getLocation();

        // where to teleport the player to
        double desX = curLoc.getX();
        double desZ = curLoc.getZ();

        // determine which border the player is beyond
        if(curLoc.getX() > xMax){           // beyond the right
            desX = this.xMax - distance;
        }
        else if(curLoc.getX() < xMin){      // beyond the left
            desX = this.xMin + distance;
        }

        if(curLoc.getZ() > zMax){           // beyond the up
            desZ = this.zMax - distance;
        }
        else if(curLoc.getZ() < zMin) {     // beyond the down
            desZ = this.zMin + distance;
        }

        // create a destination object
        Location desLoc = new Location(player.getWorld(), desX, curLoc.getY(), desZ, curLoc.getYaw(), curLoc.getPitch());

        // safely teleport the player to the destination
        SafeTeleport.safeTeleport(player, desLoc);
    }

    public void randomTeleportWithinThisArea(Player p){
        double x = xMin + Math.random() * (xMax - xMin);
        double z = zMin + Math.random() * (zMax - zMin);
        Location desLoc = new Location(Config.server.getWorld(worldName), x, 64, z);
        SafeTeleport.safeTeleport(p, desLoc);
    }

    // return the corresponding border for the player
    public static Border getCorrBorder(Location loc, Player p){
        assert loc.getWorld() != null;
        // if the loc is in the end, then the border is determined by what they have chosen
        if(loc.getWorld().getName().equals("world_the_end") || loc.getWorld().getName().equals("world_the_end_new")) {
            if(Config.getPlayerStatus(p) == 2){
                return Config.getWorldBorder("world_the_end_new").getBorder("new");
            }
            return Config.getWorldBorder("world_the_end").getBorder("old");
        }

        // other world
        WorldBorders wb = Config.getWorldBorder(loc.getWorld().getName());
        if(Config.getPlayerStatus(p) == 2){
            return wb.getBorder("new");
        }
        return wb.getBorder("old");
    }
}
