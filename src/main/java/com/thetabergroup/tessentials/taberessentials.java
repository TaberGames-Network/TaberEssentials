package com.thetabergroup.tessentials;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class taberessentials extends JavaPlugin {

    public static taberessentials instance;

    //MAIL COUNT:
    public int notificationDefault = 0;

 // REFERENCE STRINGS FOR CHAT HANDLERS, ETC
    private static String chatPrefix = ChatColor.GOLD + "" + ChatColor.BOLD + "TABER:";
    public static String chatFormat = chatPrefix + ChatColor.GRAY;

   public FileConfiguration config = this.getConfig();

    @Override
    public void onEnable(){

        config.addDefault("disableFlyOnJoin", true);
        config.addDefault("notifyMailOnJoin", true);
        config.options().copyDefaults(true);
        saveConfig();
        instance = this;

    }

    @Override
    public void onDisable(){

        instance = null;

    }

}
