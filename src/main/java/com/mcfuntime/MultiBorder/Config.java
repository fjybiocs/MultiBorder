package com.mcfuntime.MultiBorder;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.Server;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Config {
    // msg config related
    private static String knockBackMsg;
    private static String tpOutMsg;
    private static int knockBackDistance;
    public static Server server;

    // player status storage related
    final private static Map<String, WorldBorders> borders = new HashMap<String, WorldBorders>();
    final private static Map<String, Integer> playerStatus = new HashMap<String, Integer>();

    // player status files related
    private static File file;
    private static FileConfiguration status;
    private static final String fileName = "player_status.yml";
    private static Plugin plugin;

    // config
    public static FileConfiguration config;

    private static void addArea(double xMax, double xMin,
                                double zMax, double zMin,
                                double spawnX, double spawnZ,
                                String worldName, String areaName){
        Border border = new Border(xMax, xMin, zMax, zMin, spawnX, spawnZ, worldName);
        if(borders.get(worldName) == null){
            WorldBorders wb = new WorldBorders();
            wb.addBorder(areaName, border);
            borders.put(worldName, wb);
        }else{
            borders.get(worldName).addBorder(areaName, border);
        }
    }

    public static void initializeConfig(Plugin plugin){
        // read the messages;
        Config.plugin = plugin;
        config = plugin.getConfig();
        knockBackMsg = config.getString("knock-back-msg");
        tpOutMsg = config.getString("tp-out-msg");
        knockBackDistance = config.getInt("knock-back-distance");

       // read worlds in the config
        ConfigurationSection worlds = config.getConfigurationSection("areas");
        if(worlds == null) {
            System.out.println("You haven't set any area for the server!");
        }else {
            Set<String> worldNames = worlds.getKeys(false);
            // read the worlds in each world
            for (String worldName : worldNames) {
                ConfigurationSection areas = worlds.getConfigurationSection(worldName);
                if (areas != null) {
                    Set<String> areaNames = areas.getKeys(false);
                    // read the properties for every area
                    for (String areaName : areaNames) {
                        ConfigurationSection area = areas.getConfigurationSection(areaName);
                        if (area != null) {
                            addArea(area.getDouble("x-max"), area.getDouble("x-min"),
                                    area.getDouble("z-max"), area.getDouble("z-min"),
                                    area.getDouble("spawn-x"), area.getDouble("spawn-z"),
                                    worldName, areaName);
                        }
                    }
                }
            }
        }

        // initialize the player status file
        file = new File(plugin.getDataFolder(), fileName);
        status = YamlConfiguration.loadConfiguration(file);
        status.options().copyDefaults(true);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            saveStatus();
        }
    }

    // getters
    public static String getKnockBackMsg(){
        return knockBackMsg;
    }
    public static String getTpOutMsg(){
        return tpOutMsg;
    }
    public static WorldBorders getWorldBorder(String worldName){
        return borders.get(worldName);
    }
    public static Map<String, WorldBorders> getWorldBorder(){
        return borders;
    }
    public static int getKnockBackDistance(){
        return knockBackDistance;
    }


    public static void putAndSavePlayerStatusRecord(Player p, int status){
        // ATTENTION: put the exist key will not replace the value
        // so, if it exists, then remove first
        if(playerStatus.get(p.getName()) != null) {
            playerStatus.remove(p.getName());
        }

        // put the record in the hashmap
        playerStatus.put(p.getName(), status);

        // save it to the disk
        Config.status.set(p.getName(), status);
        saveStatus();
    }

    public static void removePlayerStatusRecordFromMemory(Player p){
        playerStatus.remove(p.getName());
    }

    // 0 means unknown, 1 means the old area, 2 means the new area.
    public static int getPlayerStatus(Player p){
        // get from memory first
        if(playerStatus.get(p.getName()) == null){
            // null means it doesn't exist in memory, then get it from disk, and write it into memory
            if(Config.status.getString(p.getName()) == null){
                // return unknown
                return 0;
            }
            else{
                // read from disk, write it into memory
                playerStatus.put(p.getName(), Config.status.getInt(p.getName()));
            }
        }

        return playerStatus.get(p.getName());
    }

    private static void saveStatus(){
        try {
            status.save(file);
        } catch (IOException e) {
            plugin.getLogger().warning("Unable to save " + fileName);
        }
    }
}
