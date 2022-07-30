package com.mcfuntime.MultiBorder;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.dynmap.DynmapAPI;
import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.MarkerSet;

public class DynmapDisplay {

    private static DynmapAPI dynmap;
    private static MarkerSet markerset;

    public static void initializeDynmap(Plugin plugin){
        // enable dynmap
        dynmap = (DynmapAPI) Bukkit.getServer().getPluginManager().getPlugin("Dynmap");
        if(dynmap == null){
            Bukkit.getServer().getPluginManager().disablePlugin(plugin);
        }

        Config.getWorldBorder().forEach((worldName, areas) -> {
            areas.getBorder().forEach((areaName, border) -> {
                String areaDisplayName = "老区";
                if(areaName.equals("new")){
                    areaDisplayName = "新区";
                }
                markerset = dynmap.getMarkerAPI().createMarkerSet(
                        "mb." + worldName + "." + areaName, areaDisplayName,
                        dynmap.getMarkerAPI().getMarkerIcons(), false);
                AreaMarker areaMarker = markerset.createAreaMarker(
                "mb." + worldName + "." + areaName, areaDisplayName, true, worldName,
                        new double[] {border.getxMin(), border.getxMax()},
                        new double[] {border.getzMin(), border.getzMax()}, false);
            });
        });
    }
}
