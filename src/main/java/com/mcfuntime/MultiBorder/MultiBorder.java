package com.mcfuntime.MultiBorder;

import org.bukkit.plugin.java.JavaPlugin;
import org.dynmap.DynmapAPI;

public class MultiBorder extends JavaPlugin {

    public void onEnable(){
        saveDefaultConfig();
        Config.initializeConfig(this);

        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new ChooseAreaGUI(), this);

        this.getCommand("area").setExecutor(new AreaCommandExecutor());

        DynmapDisplay.initializeDynmap(this);

        getLogger().info("MultiBorder now enabled.");
    }

    public void onDisable(){
        getLogger().info("MultiBorder now disabled.");
        saveConfig();
    }
}
