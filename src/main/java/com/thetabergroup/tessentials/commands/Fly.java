package com.thetabergroup.tessentials.commands;

import com.thetabergroup.tessentials.taberessentials;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class Fly implements CommandExecutor {

    private Plugin plugin = taberessentials.getPlugin(taberessentials.class);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;

        if (label.equalsIgnoreCase("fly")) {
            if (player.hasPermission("tess.fly")) {
                if(args.length == 0) {
                    if (player.getAllowFlight() == true) {
                        player.sendMessage(taberessentials.chatFormat + " Flight has been " + ChatColor.RED + "disabled.");
                        player.setAllowFlight(false);
                        player.setFlying(false);
                    } else if (player.getAllowFlight() == false) {
                        player.sendMessage(taberessentials.chatFormat + " Flight has been " + ChatColor.GREEN + "enabled.");
                        player.setAllowFlight(true);
                        player.setFlying(true);
                    }
                }else if(args.length == 1){

                    Inventory i = plugin.getServer().createInventory(null, 9, ChatColor.ITALIC + "$fly_beta");

                    ItemStack empty = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
                    ItemMeta emptyMeta = empty.getItemMeta();
                    emptyMeta.setDisplayName(" ");
                    empty.setItemMeta(emptyMeta);
                    //On
                    ItemStack flyOn = new ItemStack(Material.EMERALD_BLOCK, 1);
                    ItemMeta flyOnMeta = flyOn.getItemMeta();
                    flyOnMeta.setDisplayName(ChatColor.GREEN + "Enable Fly");
                    flyOn.setItemMeta(flyOnMeta);

                    //Off
                    ItemStack flyOff = new ItemStack(Material.REDSTONE_BLOCK, 1);
                    ItemMeta flyOffMeta = flyOff.getItemMeta();
                    flyOffMeta.setDisplayName(ChatColor.RED + "Disable Fly");
                    flyOff.setItemMeta(flyOffMeta);

                    //Inventory Item Layout
                    i.setItem(0, flyOn);
                    i.setItem(1, flyOff);
                    i.setItem(2, empty);
                    i.setItem(3, empty);
                    i.setItem(4, empty);
                    i.setItem(5, empty);
                    i.setItem(6, empty);
                    i.setItem(7, empty);
                    i.setItem(8, empty);

                    player.openInventory(i);


                }
            }
        }
        return false;
    }
}
