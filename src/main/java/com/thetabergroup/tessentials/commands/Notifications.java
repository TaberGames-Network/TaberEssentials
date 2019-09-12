package com.thetabergroup.tessentials.commands;

import com.thetabergroup.tessentials.handlers.PlayerHandler;
import com.thetabergroup.tessentials.taberessentials;
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
import org.bukkit.plugin.Plugin;


import java.io.File;
import java.util.ArrayList;

public class Notifications implements CommandExecutor {

    private Plugin plugin = taberessentials.getPlugin(taberessentials.class);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player player = (Player) sender;
        if (label.equalsIgnoreCase("notifications")) {
            if (player.hasPermission("tess.notifications")) {
                Inventory i = plugin.getServer().createInventory(null, 9, ChatColor.ITALIC + "$notification_beta");

                ItemStack empty = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
                ItemMeta emptyMeta = empty.getItemMeta();
                emptyMeta.setDisplayName(" ");
                empty.setItemMeta(emptyMeta);
                //MailBox
                ItemStack mailBox = new ItemStack(Material.BOOK, 1);
                ItemMeta mailBoxMeta = mailBox.getItemMeta();
                mailBoxMeta.setDisplayName(ChatColor.RED + "Mailbox");
                mailBox.setItemMeta(mailBoxMeta);

                //Party
                ItemStack party = new ItemStack(Material.BOOK, 1);
                ItemMeta partyMeta = party.getItemMeta();
                partyMeta.setDisplayName(ChatColor.RED + "Parties");
                party.setItemMeta(partyMeta);

                //Friends
                ItemStack friends = new ItemStack(Material.BOOK, 1);
                ItemMeta friendsMeta = party.getItemMeta();
                friendsMeta.setDisplayName(ChatColor.RED + "Parties");
                friends.setItemMeta(friendsMeta);

                //Inventory Item Layout
                i.setItem(0, mailBox);
                i.setItem(1, party);
                i.setItem(2, friends);
                i.setItem(3, empty);
                i.setItem(4, empty);
                i.setItem(5, empty);
                i.setItem(6, empty);
                i.setItem(7, empty);
                i.setItem(8, empty);

                player.openInventory(i);

            }


        }
        return false;
    }
}
