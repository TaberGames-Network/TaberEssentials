package com.thetabergroup.tessentials;

import com.thetabergroup.tessentials.commands.Notifications;
import com.thetabergroup.tessentials.events.InventoryClick;
import com.thetabergroup.tessentials.handlers.PlayerHandler;
import com.thetabergroup.tessentials.listeners.PlayerJoin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedMainHandEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class taberessentials extends JavaPlugin implements Listener{

    PlayerHandler PlayerHandler = new PlayerHandler();
    public static taberessentials instance;

    //MAIL COUNT:
    public int notificationDefault = 0;

 // REFERENCE STRINGS FOR CHAT HANDLERS, ETC
    private static String chatPrefix = ChatColor.GOLD + "" + ChatColor.BOLD + "TABER:";
    public static String chatFormat = chatPrefix + ChatColor.GRAY;

   private FileConfiguration config = this.getConfig();
    @Override
    public void onEnable(){
        instance = this;
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new InventoryClick(), this);
        this.getCommand("notifications").setExecutor((CommandExecutor)new Notifications());
        config.addDefault("disableFlyOnJoin", true);
        config.addDefault("notifyMailOnJoin", true);
        config.options().copyDefaults(true);
        saveConfig();


    }


    @Override
    public void onDisable(){

        instance = null;

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        PlayerHandler.SetupPlayer(player);


        //Fly Disable on Join Check:
        if(config.getBoolean("disableFlyOnJoin")) {
            if (player.isFlying()) {
                player.setFlying(false);
                player.sendMessage(taberessentials.chatFormat + "Fly has been disabled.");
            }
        }


    }


}
