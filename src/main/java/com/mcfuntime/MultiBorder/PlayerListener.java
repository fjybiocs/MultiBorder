package com.mcfuntime.MultiBorder;

import org.bukkit.Location;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.checkerframework.checker.units.qual.C;

public class PlayerListener implements Listener{

    @EventHandler(ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent event){
        Player p = event.getPlayer();
        Location loc = p.getLocation();

        if(Config.getPlayerStatus(p) == 0){
            event.setCancelled(true);
            p.sendMessage("请先选择区域");
        }

        Border border = Border.getCorrBorder(loc, p.getPlayer());
        if(!border.isInsideBorder(loc.getX(), loc.getZ())){
            border.knockBack(p, Config.getKnockBackDistance());
            event.getPlayer().sendMessage(Config.getKnockBackMsg());
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent event){
        Location loc = event.getTo();
        Player p = event.getPlayer();

        if(Config.getPlayerStatus(p) == 0){
            event.setCancelled(true);
            p.sendMessage("请先选择区域");
        }

        // if a player go to the wrong end world
        if(Config.getPlayerStatus(p) == 2){
            assert loc != null;
            assert loc.getWorld() != null;
            if(loc.getWorld().getName().equals("world_the_end")) {
                event.setCancelled(true);
                p.teleport(Bukkit.getServer().getWorld("world_the_end_new").getSpawnLocation());
                return;
            }
        }

        assert loc != null;
        Border border = Border.getCorrBorder(loc, event.getPlayer());
        assert border != null;
        if(!border.isInsideBorder(loc.getX(), loc.getZ())){
            event.setCancelled(true);
            event.getPlayer().sendMessage(Config.getTpOutMsg());
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Config.getPlayerStatus(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Config.removePlayerStatusRecord(event.getPlayer());
    }

}
