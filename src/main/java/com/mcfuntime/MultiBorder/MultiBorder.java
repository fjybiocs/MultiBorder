package com.mcfuntime.MultiBorder;

import org.bukkit.plugin.java.JavaPlugin;
import org.dynmap.DynmapAPI;

public class MultiBorder extends JavaPlugin {

    public void onEnable(){
        saveDefaultConfig();

        // read config
        Config.initializeConfig(this);

        // register listener
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new ChooseAreaGUI(), this);

        // initialize server
        Config.server = getServer();

        // register command
        this.getCommand("area").setExecutor(new AreaCommandExecutor());

        // initialize dynmap support
        DynmapDisplay.initializeDynmap(this);

        getLogger().info("MultiBorder now enabled.");
    }

    public void onDisable(){
        getLogger().info("MultiBorder now disabled.");
        saveConfig();
    }
}
