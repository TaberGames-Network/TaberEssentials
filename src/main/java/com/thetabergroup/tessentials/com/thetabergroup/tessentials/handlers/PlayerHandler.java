package com.thetabergroup.tessentials.com.thetabergroup.tessentials.handlers;

import com.thetabergroup.tessentials.taberessentials;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;

public class PlayerHandler {

    private static taberessentials instance;

    public void SetupPlayer(Player p){



         File f = new File("plugins/taberessentials/playerdata" + p.getName() + ".yml");
         YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
        yml.addDefault("Name:", p.getName());
        yml.addDefault("Last Played:", p.getLastPlayed());
        yml.addDefault("Bed Spawn Location:", p.getBedSpawnLocation());
        yml.addDefault("Most Recent Killer:", p.getKiller());
        yml.addDefault("Current Gamemode:", p.getGameMode());
        yml.addDefault("First Location:", p.getLocation());
        yml.addDefault("###########################", null);
        yml.addDefault("Notifications:", null);
        yml.addDefault("NotificationAmount:" , instance.notificationDefault);
        yml.options().copyDefaults();
        try{
            yml.save(f);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

}
