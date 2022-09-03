package com.mcfuntime.MultiBorder;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AreaCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = ((Player) sender).getPlayer();
            ChooseAreaGUI gui = new ChooseAreaGUI();
            gui.openInventory(player);
        }
        return true;
    }
}
