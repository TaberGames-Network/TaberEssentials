package com.thetabergroup.tessentials.commands;

import com.thetabergroup.tessentials.taberessentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Heal implements CommandExecutor {
    private Plugin plugin = taberessentials.getPlugin(taberessentials.class);
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        Player player = (Player) sender;

        if(label.equalsIgnoreCase("heal")){

            if(sender instanceof Player || player.hasPermission("tess.heal")){

                if(args.length == 0){
                        player.sendMessage(taberessentials.chatFormat + " You have been " + ChatColor.GOLD + "healed.");
                        player.setHealth(20.0);
                        player.setFoodLevel(20);
                        player.setFireTicks(0);
                }else if(args.length == 1){
                    if(player.getServer().getPlayer(args[0]) != null){
                        Player targetPlayer = Bukkit.getServer().getPlayer(args[0]);
                        targetPlayer.sendMessage(taberessentials.chatFormat + " You have been healed by: " + ChatColor.GOLD + player.getDisplayName());
                        targetPlayer.setHealth(20.0);
                        targetPlayer.setFoodLevel(20);
                        targetPlayer.setFireTicks(0);
                    }else{
                        player.sendMessage(taberessentials.chatFormat + ChatColor.GOLD + " " + args[0] + ChatColor.GRAY + " is not online.");
                    }

                }

            }

        }

        return false;
    }

}
