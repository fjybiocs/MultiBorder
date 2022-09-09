package com.mcfuntime.MultiBorder;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class ChooseAreaGUI implements Listener {
    private final Inventory inv;
    private static final String title = "§c请谨慎选择，若不了解规则请查看群公告";

    public ChooseAreaGUI() {
        // Create a new inventory, with no owner (as this isn't a real inventory), a size of nine, called example
        inv = Bukkit.createInventory(null, 9, title);

        // Put the items into the inventory
        initializeItems();
    }

    // You can call this whenever you want to put the items in
    public void initializeItems() {
        inv.addItem(createGuiItem(Material.DIAMOND_SWORD, "§6我想要去新区", "§a主世界、地狱内，新区老区在同一个世界，但并无重叠", "§a新区老区末地完全独立（新区有一个全新的末地）"));
        inv.addItem(createGuiItem(Material.IRON_HELMET, "§6我想呆在老区", "§a2023年2月，新区老区会合并"));
    }

    // Nice little method to create a gui item with a custom name, and description
    protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    // You can open the inventory with this
    public void openInventory(final HumanEntity ent) {
        ent.openInventory(inv);
    }

    // Check for clicks on items
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (!e.getView().getTitle().equals(title)) return;

        e.setCancelled(true);

        Player p = (Player) e.getWhoClicked();

        switch (e.getRawSlot()) {
            // chose the new area
            case 0 -> {
                // if this choice is already 2
                if(Config.getPlayerStatus(p) == 2){
                    e.getView().close();
                    p.sendMessage("§c[交大助手] 你已经在新区了");
                    return;
                }
                // clear
                if(!p.isOp()){
                    p.getInventory().clear();
                    p.setExp(0);
                    p.getEnderChest().clear();
                }
                //change status
                Config.putAndSavePlayerStatusRecord(p, 2);
                // random teleport to old zone
                Border border = Config.getWorldBorder("world").getBorder("new");
                border.randomTeleportWithinThisArea(p);
                // close the menu and remind the player
                e.getView().close();
                p.sendMessage("§6[交大助手] 欢迎来到新区");
            }
            // chose the old area
            case 1 -> {
                if(Config.getPlayerStatus(p) == 1){
                    e.getView().close();
                    p.sendMessage("§c[交大助手] 你已经在老区了");
                    return;
                }
                // clear
                if(!p.isOp()){
                    p.getInventory().clear();
                    p.setExp(0);
                    p.getEnderChest().clear();
                }
                // change status
                Config.putAndSavePlayerStatusRecord(p, 1);
                // random teleport to old zone
                Border border = Config.getWorldBorder("world").getBorder("old");
                border.randomTeleportWithinThisArea(p);
                // close the menu and remind the player
                e.getView().close();
                p.sendMessage("§6[交大助手] 欢迎回到老区");
            }
        }
    }
}

