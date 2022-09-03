package com.mcfuntime.MultiBorder;

import org.bukkit.Bukkit;
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
    private static final String title = "§c选择后不可修改，请谨慎选择！";

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

        //ItemStack clickedItem = e.getCurrentItem();
        // verify current item is not null
        // if (clickedItem == null || clickedItem.getType().isAir()) return;

        Player p = (Player) e.getWhoClicked();

        switch (e.getRawSlot()) {
            case 0 -> {
                if(Config.getPlayerStatus(p) == 1) {
                    // clear
                    p.getInventory().clear();
                    p.setExp(0);
                    p.getEnderChest().clear();
                }
                Config.addPlayerStatusRecord(p, 2);
                e.getView().close();
                p.sendMessage("§6欢迎来到新区");
            }
            case 1 -> {
                if(Config.getPlayerStatus(p) == 2) {
                    p.getInventory().clear();
                    p.setExp(0);
                    p.getEnderChest().clear();
                }
                Config.addPlayerStatusRecord(p, 1);
                e.getView().close();
                p.sendMessage("§6欢迎回到老区");
            }
        }
    }
}

