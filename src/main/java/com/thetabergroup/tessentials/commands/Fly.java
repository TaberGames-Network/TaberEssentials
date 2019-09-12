package com.thetabergroup.tessentials.commands;

import com.thetabergroup.tessentials.taberessentials;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Fly implements CommandExecutor {

    private Plugin plugin = taberessentials.getPlugin(taberessentials.class);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;

        if (label.equalsIgnoreCase("fly")) {
            if (player.hasPermission("tess.fly")) {
                if (player.getAllowFlight() == true) {
                    player.sendMessage(taberessentials.chatFormat + " Flight has been " + ChatColor.RED + "disabled.");
                    player.setAllowFlight(false);
                    player.setFlying(false);
                } else if (player.getAllowFlight() == false) {
                    player.sendMessage(taberessentials.chatFormat + " Flight has been " + ChatColor.GREEN + "enabled.");
                    player.setAllowFlight(true);
                    player.setFlying(true);
                }
            }
        }
        return false;
    }
}
