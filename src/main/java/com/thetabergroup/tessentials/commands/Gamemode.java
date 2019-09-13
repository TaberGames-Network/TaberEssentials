package com.thetabergroup.tessentials.commands;

import com.thetabergroup.tessentials.guis.GameModeGUI;
import com.thetabergroup.tessentials.taberessentials;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class Gamemode implements CommandExecutor {
    private Plugin plugin = taberessentials.getPlugin(taberessentials.class);
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player player = (Player) sender;
        if (label.equalsIgnoreCase("gamemode")) {
            if (sender instanceof Player || player.hasPermission("tess.gamemode")) {
                if (args.length == 0) {
                    Inventory i = plugin.getServer().createInventory(null, 9, ChatColor.ITALIC + "$gamemode_beta");

                    ItemStack empty = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
                    ItemMeta emptyMeta = empty.getItemMeta();
                    emptyMeta.setDisplayName(" ");
                    empty.setItemMeta(emptyMeta);
                    //MailBox
                    ItemStack creative = new ItemStack(Material.FEATHER, 1);
                    ItemMeta creativeMeta = creative.getItemMeta();
                    creativeMeta.setDisplayName(ChatColor.RED + "Creative");
                    creative.setItemMeta(creativeMeta);

                    //Party
                    ItemStack survival = new ItemStack(Material.COOKED_BEEF, 1);
                    ItemMeta survivalMeta = survival.getItemMeta();
                    survivalMeta.setDisplayName(ChatColor.RED + "Survival");
                    survival.setItemMeta(survivalMeta);

                    //Friends
                    ItemStack adventure = new ItemStack(Material.DIAMOND_SWORD, 1);
                    ItemMeta adventureMeta = adventure.getItemMeta();
                    adventureMeta.setDisplayName(ChatColor.RED + "Adventure");
                    adventure.setItemMeta(adventureMeta);

                    //Inventory Item Layout
                    i.setItem(0, creative);
                    i.setItem(1, survival);
                    i.setItem(2, adventure);
                    i.setItem(3, empty);
                    i.setItem(4, empty);
                    i.setItem(5, empty);
                    i.setItem(6, empty);
                    i.setItem(7, empty);
                    i.setItem(8, empty);

                    player.openInventory(i);

                }else if(args.length > 0){
                    if(args.toString().equalsIgnoreCase("survival") || args.toString().equalsIgnoreCase("s") || args.toString().equalsIgnoreCase("0") ){
                        player.setGameMode(GameMode.SURVIVAL);
                        player.sendMessage(taberessentials.chatFormat + " Gamemode has been set to " + ChatColor.GOLD + "survival.");

                    }
                    if(args.toString().equalsIgnoreCase("creative") || args.toString().equalsIgnoreCase("c") || args.toString().equalsIgnoreCase("1") ){
                        player.setGameMode(GameMode.CREATIVE);
                        player.sendMessage(taberessentials.chatFormat + " Gamemode has been set to " + ChatColor.GOLD + "creative.");

                    }
                    if(args.toString().equalsIgnoreCase("adventure") || args.toString().equalsIgnoreCase("a") || args.toString().equalsIgnoreCase("2") ){
                        player.setGameMode(GameMode.ADVENTURE);
                        player.sendMessage(taberessentials.chatFormat + " Gamemode has been set to " + ChatColor.GOLD + "adventure.");

                    }


                }


            }




        }
        return false;

     }
}
