package taberessentials.core;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/* Player class to store the player data
 *
 * Author: Proteh.
 */

public class CFEssentialsPlayer {

    public String nickname; // Store in DB
    public String username; // Store in DB
    public String jailName; // Store in DB
    public boolean hasPendingTeleportRequest; // Store in DB
    public boolean isJailed; // Store in DB
    public boolean isTeleporting;
    public boolean isDonator; // Store in DB
    public boolean isMuted; // Store in DB
    public boolean isNew = true; // Store in DB
    public boolean kitsLocked = false;
    public static final byte NONE = -1;
    public static final byte TPA = 0;
    public static final byte TPAHERE = 1;
    public int teleportType = NONE; // Store in DB
    public byte MAX_HOMES = 3;
    public String lastPlayerTpRequest; // Store in DB
    public Location mainHome; // Main home // Store in DB
    public Player lastPlayerMsg;
    public Map<String, Location> homeMap = new HashMap<String, Location>(); // Store in DB // Map with all the secondary homes and their location

    public CFEssentialsPlayer(String username, boolean isDonator)
    {
        this.username = username;
        this.isDonator = isDonator;
        if(this.isDonator)
            MAX_HOMES = 5;
    }

    public CFEssentialsPlayer setTeleport(boolean flag, int type, String name)
    {
        hasPendingTeleportRequest = flag;
        teleportType = type;
        lastPlayerTpRequest = name;
        return this;
    }

    public CFEssentialsPlayer setMainHome(Server server, String worldName, float x, float y, float z, float ya, float p)
    {
        mainHome = new Location(server.getWorld(worldName), x, y, z, ya, p);
        return this;
    }

    public CFEssentialsPlayer addHome(String homeName, Location loc)
    {
        homeMap.put(homeName, loc);
        return this;
    }

    public CFEssentialsPlayer setTeleporting(boolean flag)
    {
        isTeleporting = flag;
        return this;
    }

    public CFEssentialsPlayer setDonator(boolean flag)
    {
        isDonator = flag;
        MAX_HOMES = flag ? (byte)5 : (byte)3;
        return this;
    }

    public CFEssentialsPlayer setMuted(boolean flag)
    {
        isMuted = flag;
        return this;
    }

    public CFEssentialsPlayer setNew(boolean flag)
    {
        isNew = flag;
        return this;
    }

    public CFEssentialsPlayer setNickname(String nick)
    {
        nickname = nick;
        return this;
    }

    public CFEssentialsPlayer setLastPlayerMsg(Player p)
    {
        lastPlayerMsg = p;
        return this;
    }

    public CFEssentialsPlayer setJailed(Server server, String jail, boolean flag)
    {
        isJailed = flag;
        jailName = flag ? jail : null;
        if(flag)
        {
            if(server.getPlayer(this.username) != null)
                teleportToJail(server, jailName);
        }
        else
        {
            if(server.getPlayer(this.username) != null)
                unjail(server);
        }
        return this;
    }

    public CFEssentialsPlayer setKitLock(boolean flag)
    {
        kitsLocked = flag;
        return this;
    }

    public void tpaRequest(Server server, CommandSender player)
    {
        Player thisPlayer = Bukkit.getPlayer(this.username);
        if(thisPlayer.getName().equalsIgnoreCase(username))
        {
            lastPlayerTpRequest = player.getName();
            thisPlayer.sendMessage(ChatColor.GRAY + player.getName() + " has requested to teleport to you.");
            thisPlayer.sendMessage(ChatColor.GRAY + "Type " + ChatColor.RED + "/tpaccept " + ChatColor.GRAY + "to accept.");
            hasPendingTeleportRequest = true;
            teleportType = TPA;
            CFEssentials.config.savePlayerConfiguration();
        }
    }

    public void tpahereRequest(Server server, CommandSender player)
    {
        Player thisPlayer = Bukkit.getPlayer(this.username);
        if(thisPlayer.getName().equalsIgnoreCase(username))
        {
            lastPlayerTpRequest = player.getName();
            thisPlayer.sendMessage(ChatColor.GRAY + player.getName() + " has requested that you teleport to them.");
            thisPlayer.sendMessage(ChatColor.GRAY + "Type " + ChatColor.RED + "/tpaccept " + ChatColor.GRAY + "to accept.");
            hasPendingTeleportRequest = true;
            teleportType = TPAHERE;
            CFEssentials.config.savePlayerConfiguration();
        }
    }

    public void tpahereAccept(Server server)
    {
        Player thisPlayer = Bukkit.getPlayer(this.username);
        if(thisPlayer.getName().equalsIgnoreCase(username))
        {
            for(Player player2 : server.getOnlinePlayers())
            {
                if(player2.getName().equalsIgnoreCase(lastPlayerTpRequest))
                {
                    if(!isTeleporting)
                    {
                        boolean wasTeleported = false;
                        for(CFEssentialsTeleport teleport : CFEssentials.teleportList)
                        {
                            if(teleport.isAvaliable)
                            {
                                thisPlayer.sendMessage(ChatColor.GRAY + "Teleport request accepted. Teleporting in 5 seconds...");
                                player2.sendMessage(ChatColor.GRAY + thisPlayer.getName() + " has accepted your teleport request.");
                                hasPendingTeleportRequest = false;
                                teleportType = NONE;
                                teleport.setTeleport(thisPlayer, player2, 5);
                                teleport.start();
                                CFEssentials.config.savePlayerConfiguration();
                                wasTeleported = true;
                                break;
                            }
                        }
                        if(!wasTeleported)
                        {
                            thisPlayer.sendMessage(ChatColor.RED + "Too many players teleporting at once! Wait a few seconds and try again.");
                        }
                    }
                    else
                    {
                        thisPlayer.sendMessage(ChatColor.RED + "Please wait until the current teleport is over.");
                    }
                }
            }
        }
    }

    public void tpaAccept(Server server)
    {
        Player thisPlayer = Bukkit.getPlayer(this.username);
        if(thisPlayer.getName().equalsIgnoreCase(username))
        {
            thisPlayer.sendMessage(ChatColor.GRAY + "Teleport request accepted. Teleporting in 5 seconds...");
            for(Player player2 : server.getOnlinePlayers())
            {
                if(player2.getName().equalsIgnoreCase(lastPlayerTpRequest))
                {
                    if(!isTeleporting)
                    {
                        boolean wasTeleported = false;
                        for(CFEssentialsTeleport teleport : CFEssentials.teleportList)
                        {
                            if(teleport.isAvaliable)
                            {
                                thisPlayer.sendMessage(ChatColor.GRAY + "Teleport request accepted. Teleporting in 5 seconds...");
                                player2.sendMessage(ChatColor.GRAY + thisPlayer.getName() + " has accepted your teleport request.");
                                hasPendingTeleportRequest = false;
                                teleportType = NONE;
                                teleport.setTeleport(player2, thisPlayer, 5);
                                teleport.start();
                                CFEssentials.config.savePlayerConfiguration();
                                wasTeleported = true;
                                break;
                            }
                        }
                        if(!wasTeleported)
                        {
                            player2.sendMessage(ChatColor.RED + "Too many players teleporting at once! Wait a few seconds and try again.");
                        }
                    }
                    else
                    {
                        player2.sendMessage(ChatColor.RED + "Please wait until the current teleport is over.");
                    }
                }
            }
        }
    }

    public void setHome(Server server, String homename)
    {
        Player player = Bukkit.getPlayer(this.username);
        if(homename == null || homename.length() == 0)
        {
            if(player.getName().equalsIgnoreCase(username))
            {
                mainHome = player.getLocation();
                player.sendMessage(ChatColor.GRAY + "Home set.");
                CFEssentials.config.savePlayerConfiguration();
            }
        }
        else
        {
            if(player.getName().equalsIgnoreCase(username))
            {
                int count = 1;

                for(Map.Entry<String, Location> entry : homeMap.entrySet())
                    count++;

                if(count < MAX_HOMES)
                {
                    homeMap.put(homename.toLowerCase(), player.getLocation());
                    CFEssentials.config.savePlayerConfiguration();
                    player.sendMessage(ChatColor.GRAY + "Home \"" + homename + "\" set.");
                }
                else
                {
                    if(homeMap.containsKey(homename))
                    {
                        // Replace the old home with the new one
                        homeMap.put(homename.toLowerCase(), player.getLocation());
                        CFEssentials.config.savePlayerConfiguration();
                        player.sendMessage(ChatColor.GRAY + "Home \"" + homename + "\" set.");
                    }
                    else
                    {
                        if(!isDonator)
                            player.sendMessage(ChatColor.RED + "You can't set more homes! Donate to get 2 more homes.");
                        else
                            player.sendMessage(ChatColor.RED + "You can't set more homes!");
                    }
                }
            }
        }
    }

    public void tpToHome(Server server, String homename)
    {
        Player player = Bukkit.getPlayer(this.username);
        if(homename == null || homename.length() == 0)
        {
            if(player.getName().equalsIgnoreCase(username))
            {
                if(mainHome != null)
                {
                    if(!isTeleporting)
                    {
                        boolean wasTeleported = false;
                        for(CFEssentialsTeleport teleport : CFEssentials.teleportList)
                        {
                            if(teleport.isAvaliable)
                            {
                                player.sendMessage(ChatColor.GRAY + "Teleporting in 5 seconds...");
                                teleport.setTeleport(player, mainHome, 5);
                                teleport.start();
                                CFEssentials.config.savePlayerConfiguration();
                                wasTeleported = true;
                                break;
                            }
                        }
                        if(!wasTeleported)
                        {
                            player.sendMessage(ChatColor.RED + "Too many players teleporting at once! Wait a few seconds and try again.");
                        }
                    }
                    else
                    {
                        player.sendMessage(ChatColor.RED + "Please wait until the current teleport is over.");
                    }
                }
                else
                {
                    player.sendMessage(ChatColor.RED + "Your main home is not set yet!");
                }
            }
        }
        else
        {
            if(player.getName().equalsIgnoreCase(username))
            {
                if(homeMap.containsKey(homename.toLowerCase()))
                {
                    if(!isTeleporting)
                    {
                        boolean wasTeleported = false;
                        for(CFEssentialsTeleport teleport : CFEssentials.teleportList)
                        {
                            if(teleport.isAvaliable)
                            {
                                player.sendMessage(ChatColor.GRAY + "Teleporting in 5 seconds...");
                                teleport.setTeleport(player, homeMap.get(homename.toLowerCase()), 5);
                                teleport.start();
                                CFEssentials.config.savePlayerConfiguration();
                                wasTeleported = true;
                                break;
                            }
                        }
                        if(!wasTeleported)
                        {
                            player.sendMessage(ChatColor.RED + "Too many players teleporting at once! Wait a few seconds and try again.");
                        }
                    }
                    else
                    {
                        player.sendMessage(ChatColor.RED + "Please wait until the current teleport is over.");
                    }

                }
                else
                {
                    player.sendMessage(ChatColor.RED + "Unknown home \"" + homename + "\".");
                }
            }
        }
    }

    public void delHome(Server server, String homename)
    {
        Player player = Bukkit.getPlayer(this.username);
        if(player.getName().equalsIgnoreCase(this.username))
        {
            if(!homeMap.containsKey(homename.toLowerCase()))
            {
                player.sendMessage(ChatColor.RED + "Unknown home \"" + homename + "\".");
                return;
            }
            else
            {
                homeMap.remove(homename.toLowerCase());
                CFEssentials.config.savePlayerConfiguration();
                player.sendMessage(ChatColor.GRAY + "Home \"" + homename + "\" deleted.");
                return;
            }
        }
    }

    public void homeList(Server server)
    {
        Player player = Bukkit.getPlayer(this.username);
        if(player.getName().equalsIgnoreCase(this.username))
        {
            Iterator iterator = homeMap.keySet().iterator();
            String homes = "";
            while (iterator.hasNext())
            {
                String key = iterator.next().toString();
                homes += key + ", ";
            }
            if(homes.length() > 0)
            {
                player.sendMessage(ChatColor.GRAY + "Home list: Main home, " + homes.substring(0, homes.length() - 2) + ".");
            }
            else
            {
                player.sendMessage(ChatColor.GRAY + "Home list: Main home.");
            }
        }
    }

    public void teleportToJail(Server server, String jail)
    {
        Player player = Bukkit.getPlayer(this.username);
        if(player.getName().equalsIgnoreCase(this.username))
        {
            if(CFEssentials.jailMap.containsKey(jail))
            {
                player.teleport(CFEssentials.jailMap.get(jail));
                player.sendMessage(ChatColor.RED + "You have been jailed!");
            }
        }
    }

    public void teleportToSpawn(Server server)
    {
        Player player = Bukkit.getPlayer(this.username);
        if(player.getName().equalsIgnoreCase(this.username))
        {
            if(player.getWorld().getName().equalsIgnoreCase("world_battle"))
            {
                player.sendMessage(ChatColor.RED + "You can't teleport to spawn while in the Battle Dome.");
                return;
            }
            if(!isTeleporting)
            {
                Location spawnLocation;

                if(!CFEssentials.spawnMap.containsKey("world"))
                    spawnLocation = server.getWorld("world").getSpawnLocation();
                else
                    spawnLocation = CFEssentials.spawnMap.get("world");
                boolean wasTeleported = false;
                for(CFEssentialsTeleport teleport : CFEssentials.teleportList)
                {
                    if(teleport.isAvaliable)
                    {
                        player.sendMessage(ChatColor.GRAY + "Teleporting in 5 seconds...");
                        teleport.setTeleport(player, spawnLocation, 5);
                        teleport.start();
                        CFEssentials.config.savePlayerConfiguration();
                        wasTeleported = true;
                        break;
                    }
                }
                if(!wasTeleported)
                {
                    player.sendMessage(ChatColor.RED + "Too many players teleporting at once! Wait a few seconds and try again.");
                }
            }
            else
            {
                player.sendMessage(ChatColor.RED + "Please wait until the current teleport is over.");
            }
        }
    }

    public void unjail(Server server)
    {
        Player player = Bukkit.getPlayer(this.username);
        if(player.getName().equalsIgnoreCase(this.username))
        {
            player.sendMessage(ChatColor.GREEN + "You have been unjailed.");
            player.teleport(server.getWorld("world").getSpawnLocation());
        }
    }
}
