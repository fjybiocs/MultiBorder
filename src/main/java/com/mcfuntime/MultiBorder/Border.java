package com.mcfuntime.MultiBorder;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Border {
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
            desX = curLoc.getX() - distance;
        }
        else if(curLoc.getX() < xMin){
            desX = curLoc.getX() + distance;
        }

        if(curLoc.getZ() > zMax){
            desZ = curLoc.getZ() - distance;
        }
        else if(curLoc.getZ() < zMin) {
            desZ = curLoc.getZ() + distance;
        }

        if(!this.isInsideBorder(desX, desZ)){
            desX = this.spawnX;
            desZ = this.spawnZ;
        }

        Location desLoc = new Location(player.getWorld(), desX, curLoc.getY(), desZ, curLoc.getYaw(), curLoc.getPitch());
        player.teleport(desLoc);
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
