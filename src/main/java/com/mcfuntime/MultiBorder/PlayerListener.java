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

public class PlayerListener implements Listener{

    @EventHandler(ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent event){
        Player p = event.getPlayer();
        Location loc = p.getLocation();

        Border border = Border.getCorrBorder(loc, p.getPlayer());
        if(border.isInsideBorder(loc.getX(), loc.getZ())){
            border.knockBack(p, Config.getKnockBackDistance());
            event.getPlayer().sendMessage(Config.getKnockBackMsg());
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent event){
        Location loc = event.getTo();
        Player p = event.getPlayer();

        assert loc != null;

        // teleport the player to the new end when '2' players jump into end.
        if(Config.getPlayerStatus(p) == 2){

            assert loc.getWorld() != null;
            if(loc.getWorld().getName().equals("world_the_end")) {
                event.setCancelled(true);
                p.teleport(Bukkit.getServer().getWorld("world_the_end_new").getSpawnLocation());
                return;
            }
        }

        Border border = Border.getCorrBorder(loc, event.getPlayer());
        if(border.isInsideBorder(loc.getX(), loc.getZ())){
            event.setCancelled(true);
            event.getPlayer().sendMessage(Config.getTpOutMsg());
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        // judge whether the player has been set status
        if(Config.getPlayerStatus(event.getPlayer()) == 0) {
            // determine whether the player is first join the game
            if(event.getPlayer().hasPlayedBefore()){
                Config.putAndSavePlayerStatusRecord(event.getPlayer(), 1);  // old area
                Config.getWorldBorder("world").getBorder("old").randomTeleportWithinThisArea(event.getPlayer());
            } else {
                Config.putAndSavePlayerStatusRecord(event.getPlayer(), 2);  // new area
                Config.getWorldBorder("world").getBorder("new").randomTeleportWithinThisArea(event.getPlayer());
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Config.removePlayerStatusRecordFromMemory(event.getPlayer());
    }

}
