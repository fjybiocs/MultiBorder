package com.mcfuntime.MultiBorder;

import org.bukkit.plugin.java.JavaPlugin;

public class MultiBorder extends JavaPlugin {

    public void onEnable(){
        saveDefaultConfig();
        Config.initializeConfig(this);

        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new ChooseAreaGUI(), this);

        getLogger().info("MultiBorder now enabled.");
    }

    public void onDisable(){
        getLogger().info("MultiBorder now disabled.");
        saveConfig();
    }
}
