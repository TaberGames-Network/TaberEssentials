package com.thetabergroup.tessentials.commands;

import com.thetabergroup.tessentials.handlers.PlayerHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


import java.io.File;
import java.util.ArrayList;

public class Notifications implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        Player player = (Player) sender;

        Inventory notificationInventory = Bukkit.createInventory(null, 9, ChatColor.GOLD  + "" + ChatColor.BOLD + "TABER Notifications");

        if(label.equalsIgnoreCase("notifications")){

            File f = new File("plugins/taberessentials/playerdata" + player.getName() + ".yml");
            YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);

            //Items
            ItemStack notificationImportant = new ItemStack(Material.TRIPWIRE_HOOK);
            ItemMeta notificationImportantMeta = notificationImportant.getItemMeta();
            ArrayList<String> notificationImportantLore = new ArrayList<>();
            if(yml.getInt("notifAmount") > 0){

                if(yml.getInt("importantNotif") > 0){
                    notificationImportant.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
                    notificationImportantMeta.setDisplayName(ChatColor.RED + "Important Notification: " + yml.getString("Notif1:"));
                    notificationImportantLore.add(ChatColor.RED + "Important Notification!");
                    notificationImportantMeta.setLore(notificationImportantLore);
                    notificationInventory.addItem(notificationImportant);
                }

            }
        }

        return false;
    }

}
