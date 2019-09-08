package com.thetabergroup.tessentials.listeners;

import com.thetabergroup.tessentials.taberessentials;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

        private static taberessentials instance;

    public void onPlayerJoin(PlayerJoinEvent e){

        FileConfiguration config = instance.getConfig();
        Player player = e.getPlayer();

        //Fly Disable on Join Check:
        if(config.getBoolean("disableFlyOnJoin")) {
            if (player.isFlying() == true) {
                player.setFlying(false);
                player.sendMessage(taberessentials.chatFormat + "Fly has been disabled.");
            }
        }else{

        }

        // Notify Mail on Join:

        if(config.getBoolean("notifyMailOnJoin")){

            if(taberessentials.instance.newMail > 0){

                player.sendMessage(instance.chatFormat + "You have " + instance.newMail + " new messages in your inbox.");

            }else if(taberessentials.instance.newMail == 0){

                player.sendMessage(instance.chatFormat + "You have no new mail.");

            }

        }

        //Notification Center


    }

}
