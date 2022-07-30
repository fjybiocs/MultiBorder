package com.mcfuntime.MultiBorder;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Border {
    // automatically generated getters by idea
    public double getxMin() {
        return xMin;
    }

    public double getxMax() {
        return xMax;
    }

    public double getzMax() {
        return zMax;
    }

    public double getzMin() {
        return zMin;
    }

    private final double xMin;
    private final double xMax;
    private final double zMax;
    private final double zMin;
    private final double spawnX;
    private final double spawnZ;

    public Border(double xMax, double xMin, double zMax, double zMin, double spawnX, double spawnZ){
        this.xMin = xMin;
        this.xMax = xMax;
        this.zMax = zMax;
        this.zMin = zMin;
        this.spawnX = spawnX;
        this.spawnZ = spawnZ;
    }
    public boolean isInsideBorder(double x, double z){
        return x < this.xMax && x > this.xMin && z < this.zMax && z > this.zMin;
    }

    // knock back the player from the edge
    public void knockBack(Player player, double distance){
        Location curLoc = player.getLocation();
        double desX = curLoc.getX();
        double desZ = curLoc.getZ();

        if(curLoc.getX() > xMax){
            desX = this.xMax - distance;
        }
        else if(curLoc.getX() < xMin){
            desX = this.xMin + distance;
        }

        if(curLoc.getZ() > zMax){
            desZ = this.zMax - distance;
        }
        else if(curLoc.getZ() < zMin) {
            desZ = this.zMin + distance;
        }

        Location desLoc = new Location(player.getWorld(), desX, curLoc.getY(), desZ, curLoc.getYaw(), curLoc.getPitch());
        SafeTeleport.safeTeleport(player, desLoc);
    }

    // return the corresponding border for the player
    public static Border getCorrBorder(Location loc, Player p){
        assert loc.getWorld() != null;
        // if the player is going to end
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
