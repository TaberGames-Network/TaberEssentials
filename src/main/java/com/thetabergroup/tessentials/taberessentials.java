package com.thetabergroup.tessentials;

import com.thetabergroup.tessentials.commands.Notifications;
import com.thetabergroup.tessentials.handlers.PlayerHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class taberessentials extends JavaPlugin {

    public static taberessentials instance;

    //MAIL COUNT:
    public int notificationDefault = 0;

 // REFERENCE STRINGS FOR CHAT HANDLERS, ETC
    private static String chatPrefix = ChatColor.GOLD + "" + ChatColor.BOLD + "TABER:";
    public static String chatFormat = chatPrefix + ChatColor.GRAY;

   public FileConfiguration config = this.getConfig();

    public void onEnable(Player player){
        instance = this;
        PlayerHandler.SetupPlayer(player);
        config.addDefault("disableFlyOnJoin", true);
        config.addDefault("notifyMailOnJoin", true);
        config.options().copyDefaults(true);
        saveConfig();
        this.getCommand("notifications").setExecutor((CommandExecutor)new Notifications());

    }

    @Override
    public void onDisable(){

        instance = null;

    }

}
