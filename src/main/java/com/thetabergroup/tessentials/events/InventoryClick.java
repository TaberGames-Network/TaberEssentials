package com.thetabergroup.tessentials.events;

import com.thetabergroup.tessentials.taberessentials;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryClick implements Listener {

    @EventHandler
    public void InvenClick(InventoryClickEvent event){

        Player player = (Player) event.getWhoClicked();

        Inventory open = event.getClickedInventory();
        ItemStack item = event.getCurrentItem();

        if(open == null){
            return;
        }

        if(open.getName().equals(ChatColor.ITALIC + "$notification_beta") || open.getName().equals(ChatColor.ITALIC + "$gamemode_beta")){

            event.setCancelled(true);

            if(item == null || !item.hasItemMeta()){
                return;
            }
            if(item.getItemMeta().getDisplayName().equals(ChatColor.RED + "Mailbox")){
                player.closeInventory();
                player.sendMessage(taberessentials.chatFormat + " Opening MailBox...");
            }
            if(item.getItemMeta().getDisplayName().equals(ChatColor.RED + "Survival")){
                player.closeInventory();
                player.setGameMode(GameMode.SURVIVAL);
                player.sendMessage(taberessentials.chatFormat + " Gamemode set to " + ChatColor.GOLD + "survival.");
            }
            if(item.getItemMeta().getDisplayName().equals(ChatColor.RED + "Creative")){
                player.closeInventory();
                player.setGameMode(GameMode.CREATIVE);
                player.sendMessage(taberessentials.chatFormat + " Gamemode set to " + ChatColor.GOLD + "creative.");
            }
            if(item.getItemMeta().getDisplayName().equals(ChatColor.RED + "Adventure")){
                player.closeInventory();
                player.setGameMode(GameMode.ADVENTURE);
                player.sendMessage(taberessentials.chatFormat + " Gamemode set to " + ChatColor.GOLD + "adventure.");
            }
        }

    }

}
