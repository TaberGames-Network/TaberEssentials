package com.craftingfactions.diego.CFEssentials;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/* Description: This plugin is meant to replace Essentials
 * with this less laggy approach which does basically the same thing
 * without the 100+ useless features Essentials has.
 * 
 * Author: Proteh.
 */

public class CFEssentials extends JavaPlugin implements Listener, CommandExecutor {

	public static Map<String, CFEssentialsPlayer> playerMap = new HashMap<String, CFEssentialsPlayer>();
	public static Map<String, Location> jailMap = new HashMap<String, Location>(); // Store in DB
	public static Map<String, Location> spawnMap = new HashMap<String, Location>(); // Store in DB
	public static Map<String, CFEssentialsKit> kitMap = new HashMap<String, CFEssentialsKit>();
	public static List<CFEssentialsTeleport> teleportList = new ArrayList();
	public static String motd = ""; // Store in DB
	public static String maindirectory = "plugins/CFEssentials/";
	public static CFEssentialsConfig config;
	
	@Override
	public void onEnable()
	{
		// Initialize all the teleports.
		for(int i = 0; i <= 10; i++)
			teleportList.add(new CFEssentialsTeleport());

		new File(maindirectory).mkdirs();
		config = new CFEssentialsConfig(this);
		config.loadConfiguration();
		config.loadPlayerConfiguration();
		// Set up the kits
		kitMap.put("pvp", new CFEssentialsKit("pvp").addItem(new ItemStack(298)).addItem(new ItemStack(299)).addItem(new ItemStack(300)).addItem(new ItemStack(301)).addItem(new ItemStack(262, 16)).addItem(new ItemStack(261)).addItem(new ItemStack(272)));
		getServer().getPluginManager().registerEvents(this, this);
		// Fetch all the data from the SQL DB here
	}
	
	public boolean isOnline(String name)
	{
		return (getServer().getPlayer(name) != null ? true : false);
	}
	
	public Player getPlayer(String name)
	{
		return getServer().getPlayer(name);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		if(!playerMap.containsKey(event.getPlayer().getName().toLowerCase()))
			playerMap.put(event.getPlayer().getName().toLowerCase(), new CFEssentialsPlayer(event.getPlayer().getName(), isDonator(event.getPlayer().getName())));
		
		// Check for donator status changes and also welcome the player if he is new to the server
		
		if(playerMap.containsKey(event.getPlayer().getName().toLowerCase()))
		{
			if(!playerMap.get(event.getPlayer().getName().toLowerCase()).isDonator && isDonator(event.getPlayer().getName().toLowerCase()))
				playerMap.put(event.getPlayer().getName().toLowerCase(), playerMap.get(event.getPlayer().getName().toLowerCase()).setDonator(true));
			else if(playerMap.get(event.getPlayer().getName().toLowerCase()).isDonator && !isDonator(event.getPlayer().getName().toLowerCase()))
				playerMap.put(event.getPlayer().getName().toLowerCase(), playerMap.get(event.getPlayer().getName().toLowerCase()).setDonator(false));
			
			if(playerMap.get(event.getPlayer().getName().toLowerCase()).isNew)
			{
				playerMap.put(event.getPlayer().getName().toLowerCase(), playerMap.get(event.getPlayer().getName().toLowerCase()).setNew(false));
				getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "Hey everyone, fresh meat! " + event.getPlayer().getName() + " seems a little lost!");
			}
			
			if(playerMap.get(event.getPlayer().getName().toLowerCase()).nickname != null)
			{
				event.getPlayer().setDisplayName(playerMap.get(event.getPlayer().getName().toLowerCase()).nickname);
				event.getPlayer().setPlayerListName(playerMap.get(event.getPlayer().getName().toLowerCase()).nickname);
			}
		}
		
		// Show the motd to the player
		if(motd.length() > 0)
			showMotd(event.getPlayer());
		
		config.savePlayerConfiguration();
	}
	
	public void showMotd(Player player)
	{
		int lineCount = 0;
		String[] motdLines = motd.split("/n");
		
		for(int i = 0; i < motdLines.length; i++)
			player.sendMessage(motdLines[i]);
	}
	
	@Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args)
	{
        String commandName = command.getName().toLowerCase();

        if(commandName.equals("tpa"))
        	return tpa(sender, args);
        if(commandName.equals("tpaccept"))
        	return tpaccept(sender);
        if(commandName.equals("tpahere"))
        	return tpahere(sender, args);
        if(commandName.equals("spawn"))
        	return spawn(sender);
        if(commandName.equals("sethome"))
        	return sethome(sender, args);
        if(commandName.equals("home"))
        	return home(sender, args);
        if(commandName.equals("delhome"))
        	return delhome(sender, args);
        if(commandName.equals("homelist"))
        	return homelist(sender);
        if(commandName.equals("setjail"))
        	return setjail(sender, args);
        if(commandName.equals("jail"))
        	return jail(sender, args);
        if(commandName.equals("unjail"))
        	return unjail(sender, args);
        if(commandName.equals("jaillist"))
        	return jailList(sender);
        if(commandName.equals("deljail"))
        	return delJail(sender, args);
        if(commandName.equals("mute"))
        	return mute(sender, args);
        if(commandName.equals("unmute"))
        	return unmute(sender, args);
        if(commandName.equals("broadcast"))
        	return broadcast(sender, args);
        if(commandName.equals("list"))
        	return list(sender);
        if(commandName.equals("help"))
        	return help(sender);
        if(commandName.equals("gm"))
        	return gm(sender, args);
        if(commandName.equals("invsee"))
        	return invsee(sender, args);
        if(commandName.equals("setmotd"))
        	return setmotd(sender, args);
        if(commandName.equals("setspawn"))
        	return setspawn(sender);
        if(commandName.equals("nick"))
        	return nick(sender, args);
        if(commandName.equals("motd"))
        	return motd(sender);
        if(commandName.equals("delnick"))
        	return delnick(sender, args);
        if(commandName.equals("msg"))
        	return msg(sender, args);
        if(commandName.equals("r"))
        	return r(sender, args);
        if(commandName.equals("kit"))
        	return kit(sender, args);
        if(commandName.equals("rules"))
        	return rules(sender);
        
        return false;
    }
	
	public boolean hasPermission(Player player)
	{
		if(player.hasPermission("CF.cfmod.mod") || player.hasPermission("CF.cfmod.gmod") || player.hasPermission("CF.cfmod.admin") || player.isOp())
			return true;
		
		return false;
	}
	
	public boolean hasPermission(CommandSender player)
	{
		if(player.hasPermission("CF.cfmod.mod") || player.hasPermission("CF.cfmod.gmod") || player.hasPermission("CF.cfmod.admin") || player.isOp())
			return true;
		
		return false;
	}
	
	public boolean isDonator(String player)
	{
		if(getServer().getPlayer(player) != null)
			if(getServer().getPlayer(player).hasPermission("CF.vip.announce"))
				return true;
		
		return false;
	}
	
	public boolean tpa(CommandSender sender, String[] args)
	{
		if(args.length <= 0)
		{
        	sender.sendMessage(ChatColor.RED + "Usage: /tpa <playername> - Teleports you to <playername>");
			return true;
		}
		
		if(sender != null)
		{
			for(Map.Entry<String, CFEssentialsPlayer> entry : playerMap.entrySet())
			{
				if(entry.getKey().equalsIgnoreCase(args[0].toLowerCase()) || (entry.getKey().startsWith(args[0].toLowerCase()) && getServer().getPlayer(entry.getKey()) != null))
				{
					entry.getValue().tpaRequest(getServer(), sender);
					sender.sendMessage(ChatColor.GRAY + "Teleport request sent to " + entry.getValue().username + ".");
					return true;
				}
			}
			sender.sendMessage(ChatColor.RED + "Unknown player \"" + args[0] + "\".");
		}
		
		return true;
	}
	
	public boolean tpahere(CommandSender sender, String[] args)
	{
		if(args.length <= 0)
		{
        	sender.sendMessage(ChatColor.RED + "Usage: /tpahere <playername> - Teleports <playername> to you.");
			return true;
		}
		
		if(sender != null)
		{
			for(Map.Entry<String, CFEssentialsPlayer> entry : playerMap.entrySet())
			{
				if(entry.getKey().equalsIgnoreCase(args[0].toLowerCase()) || (entry.getKey().startsWith(args[0].toLowerCase()) && getServer().getPlayer(entry.getKey()) != null))
				{
					entry.getValue().tpahereRequest(getServer(), sender);
					sender.sendMessage(ChatColor.GRAY + "Teleport request sent to " + entry.getValue().username + ".");
					return true;
				}
			}
			sender.sendMessage(ChatColor.RED + "Unknown player \"" + args[0] + "\".");
		}
		return true;
	}
	
	public boolean tpo(CommandSender sender, String[] args)
	{
		if(args.length <= 0)
		{
        	sender.sendMessage(ChatColor.RED + "Usage: /tpo <playername> - Teleports you to <playername>");
			return true;
		}
		
		if(sender != null)
		{
			for(Map.Entry<String, CFEssentialsPlayer> entry : playerMap.entrySet())
			{
				if(entry.getKey().equalsIgnoreCase(args[0].toLowerCase()) || (entry.getKey().startsWith(args[0].toLowerCase()) && getServer().getPlayer(entry.getKey()) != null))
				{
					getServer().getPlayer(sender.getName()).teleport(getServer().getPlayer(entry.getKey()));
					sender.sendMessage(ChatColor.GRAY + "Teleported to " + entry.getValue().username + ".");
					return true;
				}
			}
			sender.sendMessage(ChatColor.RED + "Unknown player \"" + args[0] + "\".");
		}
		
		return true;
	}
	
	public boolean tpohere(CommandSender sender, String[] args)
	{
		if(args.length <= 0)
		{
        	sender.sendMessage(ChatColor.RED + "Usage: /tpohere <playername> - Teleports <playername> to you.");
			return true;
		}
		
		if(sender != null)
		{
			for(Map.Entry<String, CFEssentialsPlayer> entry : playerMap.entrySet())
			{
				if(entry.getKey().equalsIgnoreCase(args[0].toLowerCase()) || (entry.getKey().startsWith(args[0].toLowerCase()) && getServer().getPlayer(entry.getKey()) != null))
				{
					getServer().getPlayer(entry.getKey()).teleport(getServer().getPlayer(sender.getName()));
					sender.sendMessage(ChatColor.GRAY + "Teleported " + entry.getValue().username + " to you.");
					return true;
				}
			}
			sender.sendMessage(ChatColor.RED + "Unknown player \"" + args[0] + "\".");
		}
		return true;
	}
	
	public boolean tpaccept(CommandSender sender)
	{
		if(sender != null)
		{
			Player player = getPlayer(sender.getName());
			if(player.getName().equalsIgnoreCase(sender.getName()) && playerMap.containsKey(player.getName().toLowerCase()))
			{
				if(player.getWorld().getName().equalsIgnoreCase("world_battle"))
				{
					player.sendMessage(ChatColor.RED + "You can't accept teleport requests while in the Battle Dome.");
					return true;
				}
				if(playerMap.get(player.getName().toLowerCase()).hasPendingTeleportRequest)
				{
					switch(playerMap.get(player.getName().toLowerCase()).teleportType)
					{
						case CFEssentialsPlayer.TPA:
							playerMap.get(player.getName().toLowerCase()).tpaAccept(getServer());
							break;
						case CFEssentialsPlayer.TPAHERE:
							playerMap.get(player.getName().toLowerCase()).tpahereAccept(getServer());
							break;
						case CFEssentialsPlayer.NONE:
							player.sendMessage(ChatColor.RED + "Unknown error. Try again.");
							playerMap.get(player.getName().toLowerCase()).hasPendingTeleportRequest = false;
					}
					config.savePlayerConfiguration();
				}
				else
				{
					sender.sendMessage(ChatColor.RED + "You don't have any pending teleport requests.");
				}
				return true;
			}
		}
		return true;
	}
	
	public boolean spawn(CommandSender sender)
	{
		if(playerMap.containsKey(sender.getName().toLowerCase()))
			playerMap.get(sender.getName().toLowerCase()).teleportToSpawn(getServer());
		return true;
	}
	
	public boolean sethome(CommandSender sender, String[] args)
	{
		Player player = getPlayer(sender.getName());

		if(player.getName().equalsIgnoreCase(sender.getName()))
		{
			if(player.getWorld().getName().equalsIgnoreCase("world_battle"))
			{
				player.sendMessage(ChatColor.RED + "You can't set your home while in the Battle Dome.");
				return true;
			}
			if(player.getName().equalsIgnoreCase(sender.getName()))
			{
				if(args == null || args.length <= 0)
					playerMap.get(player.getName().toLowerCase()).setHome(getServer(), null);
				else
					playerMap.get(player.getName().toLowerCase()).setHome(getServer(), args[0]);
				return true;
			}
		}
		return true;
	}
	
	public boolean home(CommandSender sender, String[] args)
	{
		Player player = getPlayer(sender.getName());
		if(player.getName().equalsIgnoreCase(sender.getName()))
		{
			if(player.getWorld().getName().equalsIgnoreCase("world_battle"))
			{
				player.sendMessage(ChatColor.RED + "You can't teleport to your home while in the Battle Dome.");
				return true;
			}

			if(args == null || args.length <= 0)
				playerMap.get(sender.getName().toLowerCase()).tpToHome(getServer(), null);
			else
				playerMap.get(sender.getName().toLowerCase()).tpToHome(getServer(), args[0]);
			return true;
		}
		return true;
	}
	
	public boolean delhome(CommandSender sender, String[] args)
	{
		Player player = getPlayer(sender.getName());
		if(args.length <= 0)
		{
			sender.sendMessage(ChatColor.RED + "Usage: /delhome <home> - Deletes a home");
			return true;
		}
		if(player.getName().equalsIgnoreCase(sender.getName()))
		{
			playerMap.get(player.getName().toLowerCase()).delHome(getServer(), args[0]);
			return true;
		}
		return true;
	}
	
	public boolean homelist(CommandSender sender)
	{
		for(Player player : getServer().getOnlinePlayers())
		{
			if(player.getName().equalsIgnoreCase(sender.getName()))
			{
				playerMap.get(player.getName().toLowerCase()).homeList(getServer());
				return true;
			}
		}
		return true;
	}
	
	public boolean setjail(CommandSender sender, String[] args)
	{
		Player player = getPlayer(sender.getName());
		if(!sender.hasPermission("CF.cfmod.admin"))
		{
			sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");
			return true;
		}
		if(args.length >= 1)
		{
			if(sender.getName().equalsIgnoreCase(player.getName()))
			{
				jailMap.put(args[0].toLowerCase(), player.getLocation());
				player.sendMessage(ChatColor.GRAY + "Jail \"" + args[0].toLowerCase() + "\" set at your current location.");
				config.saveConfiguration();
			}
		}
		else
		{
			player.sendMessage(ChatColor.RED + "Usage: /setjail <jailname> - Sets a jail at your current location");
		}
		return true;
	}
	
	public boolean jail(CommandSender sender, String[] args)
	{
		if(!hasPermission(sender))
		{
			sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");
			return true;
		}
		if(args.length < 2)
		{
			sender.sendMessage(ChatColor.RED + "Usage: /jail <player> <jail> - Jails <player> in <jail>");
			return true;
		}
		if(jailMap.containsKey(args[1].toLowerCase()))
		{
			if(playerMap.containsKey(args[0].toLowerCase()))
			{
				playerMap.get(args[0].toLowerCase()).setJailed(getServer(), args[1].toLowerCase(), true);
				sender.sendMessage(ChatColor.GRAY + "Player \"" + args[0] + "\" jailed.");
			}
			else
			{
				sender.sendMessage(ChatColor.GRAY + "Player \"" + args[0] + "\" not found.");
			}
		}
		else
		{
			sender.sendMessage(ChatColor.RED + "Unknown jail \"" + args[1].toLowerCase() + "\".");
		}
		return true;
	}
	
	public boolean unjail(CommandSender sender, String[] args)
	{
		if(!hasPermission(sender))
		{
			sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");
			return true;
		}
		if(args.length < 1)
		{
			sender.sendMessage(ChatColor.RED + "Usage: /unjail <player> - Unjails a player");
			return true;
		}
		if(playerMap.containsKey(args[0].toLowerCase()))
		{
			playerMap.get(args[0].toLowerCase()).setJailed(getServer(), null, false);
			sender.sendMessage(ChatColor.GRAY + "Player \"" + args[0] + "\" unjailed.");
		}
		else
		{
			sender.sendMessage(ChatColor.GRAY + "Player \"" + args[0] + "\" not found.");
		}
		return true;
	}
	
	public boolean jailList(CommandSender sender)
	{
		if(!hasPermission(sender))
		{
			sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");
			return true;
		}
	    Iterator iterator = jailMap.keySet().iterator();
	    String jails = "";
	    while (iterator.hasNext())
	    {  
	       String key = iterator.next().toString();    
	       jails += key + ", ";
	    }
	    if(jails.length() > 0)
	    {
	    	sender.sendMessage(ChatColor.GRAY + "Jail list: " + jails.substring(0, jails.length() - 2) + ".");
	    }
	    else
	    {
	    	sender.sendMessage(ChatColor.RED + "There are no jails set.");
	    }
	    return true;
	}
	
	public boolean delJail(CommandSender sender, String[] args)
	{
		if(!sender.hasPermission("CF.cfmod.admin"))
		{
			sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");
			return true;
		}
		if(args.length <= 0)
		{
			sender.sendMessage(ChatColor.RED + "Usage: /deljail <jail> - Deletes a jail");
			return true;
		}
		if(jailMap.containsKey(args[0].toLowerCase()))
		{
			for(Map.Entry<String, CFEssentialsPlayer> entry : playerMap.entrySet())
			{
				if(entry.getValue().jailName.equalsIgnoreCase(args[0]))
					entry.getValue().setJailed(getServer(), null, false);
			}
			jailMap.remove(args[0].toLowerCase());
			sender.sendMessage(ChatColor.GRAY + "Jail \"" + args[0].toLowerCase() + "\" deleted. All the players who were in that jail have been unjailed.");
			config.saveConfiguration();
		}
		return true;
	}
	
	public boolean mute(CommandSender sender, String[] args)
	{
		if(!hasPermission(sender))
		{
			sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");
			return true;
		}
		if(args.length <= 0)
		{
			sender.sendMessage(ChatColor.RED + "Usage: /mute <player> - Mutes a player");
			return true;
		}
		if(getServer().getPlayer(args[0]) != null)
		{
			Player player = getServer().getPlayer(args[0]);
			playerMap.get(player.getName().toLowerCase()).setMuted(true);
			player.sendMessage(ChatColor.RED + "You have been muted.");
			sender.sendMessage(ChatColor.GRAY + "Player \"" + player.getName() + "\" muted.");
			config.savePlayerConfiguration();
		}
		return true;
	}
	
	public boolean unmute(CommandSender sender, String[] args)
	{
		if(!hasPermission(sender))
		{
			sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");
			return true;
		}
		if(args.length <= 0)
		{
			sender.sendMessage(ChatColor.RED + "Usage: /unmute <player> - Unmutes a player");
			return true;
		}
		if(getServer().getPlayer(args[0]) != null)
		{
			Player player = getServer().getPlayer(args[0]);
			playerMap.get(player.getName().toLowerCase()).setMuted(false);
			player.sendMessage(ChatColor.GREEN + "You have been unmuted.");
			sender.sendMessage(ChatColor.GRAY + "Player \"" + player.getName() + "\" unmuted.");
			config.savePlayerConfiguration();
		}
		return true;
	}
	
	public boolean broadcast(CommandSender sender, String[] args)
	{
		if(!sender.hasPermission("CF.cfmod.gmod"))
		{
			sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");
			return true;
		}
		String finalString = "";
		for(int i = 0; i < args.length; i++)
			finalString += args[i] + " ";
		
		if(finalString.length() > 0)
			getServer().broadcastMessage(ChatColor.RED + "BROADCAST: " + ChatColor.GREEN + finalString.trim());
		else
			sender.sendMessage(ChatColor.RED + "Usage: /broadcast <message> - Broadcasts a message");
		
		return true;
	}
	
	public boolean list(CommandSender sender)
	{
		String adminList = "";
		String modList = "";
		String gmodList = "";
		String vipList = "";
		String playerList = "";
		sender.sendMessage(ChatColor.BLUE + "There are " + ChatColor.RED + getServer().getOnlinePlayers().length + ChatColor.BLUE + " out of " + ChatColor.RED + getServer().getMaxPlayers() + ChatColor.BLUE + " players online.");
		for(Player player : getServer().getOnlinePlayers())
		{
			if(player.hasPermission("CF.cfmod.admin"))
				adminList += player.getName() + ", ";
			else if(player.hasPermission("CF.cfmod.gmod"))
				gmodList += player.getName() + ", ";
			else if(player.hasPermission("CF.cfmod.mod"))
				modList += player.getName() + ", ";
			else if(player.hasPermission("CF.vip.announce"))
				vipList += player.getName() + ", ";
			else
				playerList += player.getName() + ", ";
		}
		if(adminList.length() > 0)
			sender.sendMessage(ChatColor.DARK_GREEN + "Administrators online" + ChatColor.WHITE + ": " + adminList.substring(0, adminList.length() - 2) + ".");
		if(gmodList.length() > 0)
			sender.sendMessage(ChatColor.DARK_AQUA + "Head-Moderators online" + ChatColor.WHITE +  ": " + gmodList.substring(0, gmodList.length() - 2) + ".");
		if(modList.length() > 0)
			sender.sendMessage(ChatColor.DARK_PURPLE + "Moderators online" + ChatColor.WHITE +  ": " + modList.substring(0, modList.length() - 2) + ".");
		if(vipList.length() > 0)
			sender.sendMessage(ChatColor.GREEN + "VIPs online" + ChatColor.WHITE +  ": " + vipList.substring(0, vipList.length() - 2) + ".");
		if(playerList.length() > 0)
			sender.sendMessage("Regular players online: " + playerList.substring(0, playerList.length() - 2) + ".");
		return true;
	}
	
	public boolean help(CommandSender sender)
	{
		sender.sendMessage(ChatColor.RED + "/tpa <player> " + ChatColor.GRAY + "- Send a request to teleport to <player>");
		sender.sendMessage(ChatColor.RED + "/tpahere <player> " + ChatColor.GRAY + "- Send a request to teleport <player> to you");
		sender.sendMessage(ChatColor.RED + "/tpaccept " + ChatColor.GRAY + "- Accepts a pending teleport request");
		sender.sendMessage(ChatColor.RED + "/sethome <name> (optional) " + ChatColor.GRAY + "- Sets your main home, or <home> if specified");
		sender.sendMessage(ChatColor.RED + "/home <home> (optional) " + ChatColor.GRAY + "- Teleports you to your main home, or <home> if specified");
		sender.sendMessage(ChatColor.RED + "/delhome <home> " + ChatColor.GRAY + "- Deletes a home");
		sender.sendMessage(ChatColor.RED + "/homelist " + ChatColor.GRAY + "- Prints a list of your homes");
		sender.sendMessage(ChatColor.RED + "/list " + ChatColor.GRAY + "- Shows a list of online players");
		sender.sendMessage(ChatColor.RED + "/help " + ChatColor.GRAY + "- Shows this page");
		return true;
	}
	
	public boolean rules(CommandSender sender)
	{
		sender.sendMessage(ChatColor.WHITE + "Check this link for a detailed list of rules.");
		sender.sendMessage(ChatColor.WHITE + "http://forum.craftingfactions.com/threads/rules.823/");
		return true;
	}
	
	public boolean gm(CommandSender sender, String[] args)
	{
		if(!hasPermission(sender))
		{
			sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");
			return true;
		}
		if(sender.hasPermission("CF.cfmod.admin") || sender.isOp())
		{
			switch(args.length)
			{
				case 0:
					if(getServer().getPlayer(sender.getName()) != null)
					{
						if(getServer().getPlayer(sender.getName()).getGameMode() == GameMode.CREATIVE)
						{
							getServer().getPlayer(sender.getName()).setGameMode(GameMode.SURVIVAL);
							sender.sendMessage(ChatColor.GRAY + "Gamemode set to survival.");
						}
						else
						{
							getServer().getPlayer(sender.getName()).setGameMode(GameMode.CREATIVE);
							sender.sendMessage(ChatColor.GRAY + "Gamemode set to creative.");
						}
					}
					break;
				case 1:
					if(getServer().getPlayer(sender.getName()) != null)
					{
						if(args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("creative"))
						{
							getServer().getPlayer(sender.getName()).setGameMode(GameMode.CREATIVE);
							sender.sendMessage(ChatColor.GRAY + "Set gamemode to creative.");
						}
						else if(args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("survival"))
						{
							getServer().getPlayer(sender.getName()).setGameMode(GameMode.SURVIVAL);
							sender.sendMessage(ChatColor.GRAY + "Set gamemode to survival.");
						}
						else if(args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("adventure"))
						{
							getServer().getPlayer(sender.getName()).setGameMode(GameMode.ADVENTURE);
							sender.sendMessage(ChatColor.GRAY + "Set gamemode to adventure.");
						}
						else
						{
							sender.sendMessage(ChatColor.RED + "Unknown gamemode \"" + args[0] + "\".");
						}
					}
					break;
				case 2:
					if(getServer().getPlayer(sender.getName()) != null && getServer().getPlayer(args[0]) != null)
					{
						if(args[1].equalsIgnoreCase("1") || args[1].equalsIgnoreCase("creative"))
						{
							getServer().getPlayer(args[0]).setGameMode(GameMode.CREATIVE);
							sender.sendMessage(ChatColor.GRAY + "Set gamemode to creative.");
						}
						else if(args[1].equalsIgnoreCase("0") || args[1].equalsIgnoreCase("survival"))
						{
							getServer().getPlayer(args[0]).setGameMode(GameMode.SURVIVAL);
							sender.sendMessage(ChatColor.GRAY + "Set gamemode to survival.");
						}
						else if(args[1].equalsIgnoreCase("2") || args[1].equalsIgnoreCase("adventure"))
						{
							getServer().getPlayer(args[0]).setGameMode(GameMode.ADVENTURE);
							sender.sendMessage(ChatColor.GRAY + "Set gamemode to adventure.");
						}
						else
						{
							sender.sendMessage(ChatColor.RED + "Unknown gamemode \"" + args[1] + "\".");
						}
					}
					else
					{
						sender.sendMessage(ChatColor.RED + "Unknown player \"" + args[0] + "\".");
					}
					break;
				default:
					sender.sendMessage(ChatColor.RED + "Usage: /gm <gamemode/player> <gamemode> - Switches gamemodes");
			}
		}
		else
		{
			sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");
			return true;
		}

		return true;
	}
	
	public boolean invsee(CommandSender sender, String[] args)
	{
		if(!sender.hasPermission("CF.cfmod.gmod") || !sender.hasPermission("CF.cfmod.admin"))
		{
			sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");
			return true;
		}
		if(args.length <= 0)
		{
			sender.sendMessage(ChatColor.RED + "Usage: /invsee <player> - Shows the inventory of a player");
			return true;
		}
		
		if(getServer().getPlayer(args[0]) != null)
		{
			getServer().getPlayer(sender.getName()).openInventory(getServer().getPlayer(args[0]).getInventory());
		}
		
		return true;
	}
	
	public boolean setmotd(CommandSender sender, String[] args)
	{
		if(getServer().getPlayer(sender.getName()) != null)
		{
			sender.sendMessage(ChatColor.RED + "This command can only be executed via console.");
			return true;
		}
		
		String finalMotd = "";
		if(args.length > 0)
			for(int i = 0; i < args.length; i++)
				finalMotd += args[i] + " ";
		
		motd = finalMotd;
		config.saveConfiguration();
		return true;
	}
	
	public boolean setspawn(CommandSender sender)
	{
		if(sender.hasPermission("CF.cfmod.admin") || sender.isOp())
		{
			getServer().getWorld(getServer().getPlayer(sender.getName()).getWorld().getName()).setSpawnLocation(getServer().getPlayer(sender.getName()).getLocation().getBlockX(), getServer().getPlayer(sender.getName()).getLocation().getBlockY(), getServer().getPlayer(sender.getName()).getLocation().getBlockZ());
			String worldName = getServer().getPlayer(sender.getName()).getWorld().getName();
			spawnMap.put(worldName, getServer().getPlayer(sender.getName()).getLocation());
			sender.sendMessage(ChatColor.GRAY + "Spawn set at your current location.");
			config.saveConfiguration();
		}
		else
		{
			sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");
		}
		return true;
	}
	
	public boolean nick(CommandSender sender, String[] args)
	{
		if(!hasPermission(sender))
		{
			sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");
			return true;
		}
		if(args.length <= 1)
		{
			sender.sendMessage(ChatColor.RED + "Usage: /nick <player> <nickname> - Sets a nickname for a player");
			return true;
		}
		if(getServer().getPlayer(args[0]) != null)
		{
			if(playerMap.containsKey(args[0].toLowerCase()))
			{
				String fullNick = "";
				for(int i = 1; i < args.length; i++)
					fullNick += args[i] + " ";
				fullNick = fullNick.trim();
				Player player = getServer().getPlayer(args[0]);
				playerMap.put(args[0].toLowerCase(), playerMap.get(args[0].toLowerCase()).setNickname(fullNick));
				player.setDisplayName(fullNick);
				player.setPlayerListName(fullNick);
				sender.sendMessage(ChatColor.GRAY + "Nickname for \"" + args[0].toLowerCase() + "\" set to \"" + fullNick + "\".");
				player.sendMessage(ChatColor.GRAY + "Your nickname has been set to \"" + fullNick + "\".");
				config.savePlayerConfiguration();
			}
		}
		return true;
	}
	
	public boolean delnick(CommandSender sender, String[] args)
	{
		if(!hasPermission(sender))
		{
			sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");
			return true;
		}
		if(args.length <= 0)
		{
			sender.sendMessage(ChatColor.RED + "Usage: /nick <player> <nickname> - Sets a nickname for a player");
			return true;
		}
		if(getServer().getPlayer(args[0]) != null)
		{
			if(playerMap.containsKey(args[0].toLowerCase()))
			{
				Player player = getServer().getPlayer(args[0]);
				playerMap.put(args[0].toLowerCase(), playerMap.get(args[0].toLowerCase()).setNickname(null));
				player.setDisplayName(player.getName());
				player.setPlayerListName(player.getName());
				sender.sendMessage(ChatColor.GRAY + "Nickname for \"" + args[0].toLowerCase() + "\" deleted.");
				config.savePlayerConfiguration();
			}
		}
		return true;
	}
	
	public boolean motd(CommandSender sender)
	{
		showMotd(getServer().getPlayer(sender.getName()));
		return true;
	}
	
	public boolean msg(CommandSender sender, String[] args)
	{
		if(args.length <= 1)
		{
			sender.sendMessage(ChatColor.RED + "Usage: /msg <player> <message>");
			return true;
		}
		for(Player player : getServer().getOnlinePlayers())
		{
			if(player.getName().equalsIgnoreCase(args[0]) || player.getName().toLowerCase().startsWith(args[0]))
			{
				String message = "";
				for(int i = 1; i < args.length; i++)
					message += args[i] + " ";
				message = message.trim();
				sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.WHITE + "you" + ChatColor.GRAY + " -> " + ChatColor.WHITE + player.getName() + ChatColor.GRAY + "] " + ChatColor.WHITE + message);
				player.sendMessage(ChatColor.GRAY + "[" + ChatColor.WHITE + sender.getName() + ChatColor.GRAY + " -> " + ChatColor.WHITE + "me" + ChatColor.GRAY + "] " + ChatColor.WHITE + message);
				playerMap.put(player.getName().toLowerCase(), playerMap.get(player.getName().toLowerCase()).setLastPlayerMsg(getServer().getPlayer(sender.getName())));
				playerMap.put(sender.getName().toLowerCase(), playerMap.get(sender.getName().toLowerCase()).setLastPlayerMsg(player));
				config.savePlayerConfiguration();
				return true;
			}
		}
		sender.sendMessage(ChatColor.RED + "Unknown player \"" + args[0] + "\".");
		return true;
	}
	
	public boolean r(CommandSender sender, String[] args)
	{
		if(args.length <= 0)
		{
			sender.sendMessage(ChatColor.RED + "Usage: /r <message>");
			return true;
		}
		if(playerMap.get(sender.getName().toLowerCase()).lastPlayerMsg == null)
		{
			sender.sendMessage(ChatColor.RED + "You don't have no one to reply to!");
			return true;
		}
		if(getServer().getPlayer(playerMap.get(sender.getName().toLowerCase()).lastPlayerMsg.getName()) != null)
		{
			String message = "";
			for(int i = 0; i < args.length; i++)
				message += args[i] + " ";
			message = message.trim();
			Player p = playerMap.get(sender.getName().toLowerCase()).lastPlayerMsg;
			sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.WHITE + "you" + ChatColor.GRAY + " -> " + ChatColor.WHITE + p.getName() + ChatColor.GRAY + "] " + ChatColor.WHITE + message);
			p.sendMessage(ChatColor.GRAY + "[" + ChatColor.WHITE + sender.getName() + ChatColor.GRAY + " -> " + ChatColor.WHITE + "me" + ChatColor.GRAY + "] " + ChatColor.WHITE + message);
			playerMap.put(p.getName().toLowerCase(), playerMap.get(p.getName().toLowerCase()).setLastPlayerMsg(getServer().getPlayer(sender.getName())));
			playerMap.put(sender.getName().toLowerCase(), playerMap.get(sender.getName().toLowerCase()).setLastPlayerMsg(p));
		}
		else
		{
			sender.sendMessage(ChatColor.RED + "Unknown player \"" + args[0] + "\".");
		}
		return true;
	}
	
	public boolean kit(CommandSender sender, String[] args)
	{
		if(args.length <= 0)
		{
			sender.sendMessage(ChatColor.RED + "Usage: /kit <kitname>");
			return true;
		}
		if(kitMap.containsKey(args[0].toLowerCase()))
		{
			CFEssentialsKit kitToGive = kitMap.get(args[0].toLowerCase());
			if(!playerMap.get(sender.getName().toLowerCase()).kitsLocked)
			{
				Thread kit = new Thread(kitToGive.setPlayer(getServer().getPlayer(sender.getName())));
				kit.start();
			}
			else
			{
				sender.sendMessage(ChatColor.RED + "You have to wait an hour to use this kit again.");
			}
		}
		else
		{
			sender.sendMessage(ChatColor.RED + "Unknown kit \"" + args[0].toLowerCase() + "\".");
		}
		return true;
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event)
	{
		if(playerMap.containsKey(event.getPlayer().getName().toLowerCase()))
		{
			if(playerMap.get(event.getPlayer().getName().toLowerCase()).isJailed && playerMap.get(event.getPlayer().getName().toLowerCase()).jailName != null)
			{
				event.getPlayer().teleport(jailMap.get(playerMap.get(event.getPlayer().getName().toLowerCase()).jailName));
				event.getPlayer().sendMessage(ChatColor.RED + "You are jailed! You are not allowed to move.");
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event)
	{
		if(playerMap.containsKey(event.getPlayer().getName().toLowerCase()))
		{
			if(playerMap.get(event.getPlayer().getName().toLowerCase()).isMuted)
			{
				event.getPlayer().sendMessage(ChatColor.RED + "You are muted!");
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event)
	{
		if(playerMap.containsKey(event.getPlayer().getName().toLowerCase()))
		{
			if(playerMap.get(event.getPlayer().getName().toLowerCase()).isMuted)
			{
				if(event.getMessage().startsWith("/me") || event.getMessage().startsWith("/f desc") || event.getMessage().startsWith("/msg") || event.getMessage().startsWith("/r") || event.getMessage().startsWith("/tell"))
				{
					event.getPlayer().sendMessage(ChatColor.RED + "You are muted!");
					event.setCancelled(true);
				}
			}
			if(playerMap.get(event.getPlayer().getName().toLowerCase()).isJailed)
			{
				event.setCancelled(true);
			}
		}
		
		// Cancel some commands
		
		if(event.getMessage().startsWith("/plugins") || event.getMessage().startsWith("/seed") || event.getMessage().startsWith("/version"))
			event.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event)
	{
		if(spawnMap.containsKey("world"))
			event.setRespawnLocation(spawnMap.get("world"));
		else
			event.setRespawnLocation(getServer().getWorld("world").getSpawnLocation());
	}
}
