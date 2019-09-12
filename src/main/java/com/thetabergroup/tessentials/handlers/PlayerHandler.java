package com.thetabergroup.tessentials.handlers;

import com.thetabergroup.tessentials.taberessentials;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class PlayerHandler {

private int DefaultNotifAmount = 0;

    public void SetupPlayer(Player p) {


        File f = new File("plugins/TaberEssentials/playerdata/" + p.getUniqueId() + ".yml");
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
            yml.addDefault("Name", p.getName());
            yml.addDefault("notifAmount", DefaultNotifAmount);

            yml.options().copyDefaults(true);
            try {
                yml.save(f);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }





    public static int getNotifications(Player p){

        File f = new File("plugins/taberessentials/playerdata" + p.getUniqueId() + ".yml");
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);

        return yml.getInt("notifAmount:");
    }

}
