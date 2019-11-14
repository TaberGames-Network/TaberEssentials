package com.craftingfactions.diego.CFEssentials;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import org.bukkit.Location;

public class CFEssentialsConfig {

	CFEssentials plugin;
	
	public CFEssentialsConfig(CFEssentials p)
	{
		plugin = p;
	}
	
	public void saveConfiguration()
	{
		File configFile;
		configFile = new File(CFEssentials.maindirectory, "config.txt");
		if(!configFile.exists())
		{
			try
			{
				configFile.createNewFile();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		try
		{
			PrintWriter configData = new PrintWriter(new FileWriter(configFile));
			configData.println("motd:" + CFEssentials.motd);
			for(Map.Entry<String, Location> entry : CFEssentials.jailMap.entrySet())
				configData.println("jail:" + entry.getKey() + ":" + entry.getValue().getWorld().getName() + ":" + entry.getValue().getBlockX() + ":" + entry.getValue().getBlockY() + ":" + entry.getValue().getBlockZ() + ":" + entry.getValue().getYaw() + ":" + entry.getValue().getPitch());
			for(Map.Entry<String, Location> entry : CFEssentials.spawnMap.entrySet())
				configData.println("spawn:" + entry.getKey() + ":" + entry.getValue().getBlockX() + ":" + entry.getValue().getBlockY() + ":" + entry.getValue().getBlockZ() + ":" + entry.getValue().getYaw() + ":" + entry.getValue().getPitch());
			configData.close();
		}
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
	}
	
	public void savePlayerConfiguration()
	{
		File configFile;
		File homeFile;
		configFile = new File(CFEssentials.maindirectory, "players.txt");
		homeFile = new File(CFEssentials.maindirectory, "homes.txt");
		if(!configFile.exists())
		{
			try
			{
				configFile.createNewFile();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		try
		{
			PrintWriter configData = new PrintWriter(new FileWriter(configFile));
			for(Map.Entry<String, CFEssentialsPlayer> entry : CFEssentials.playerMap.entrySet())
				configData.println(entry.getKey() + ":" + (entry.getValue().nickname != null ? entry.getValue().nickname : "null") + ":" + (entry.getValue().jailName != null ? entry.getValue().jailName : "null") + ":" + entry.getValue().hasPendingTeleportRequest + ":" + entry.getValue().isMuted + ":" + entry.getValue().isNew + ":" + entry.getValue().teleportType + ":" + (entry.getValue().lastPlayerTpRequest != null ? entry.getValue().lastPlayerTpRequest : "null"));
			configData.close();
		}
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
		
		if(!homeFile.exists())
		{
			try
			{
				homeFile.createNewFile();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		try
		{
			PrintWriter configData = new PrintWriter(new FileWriter(homeFile));
			for(Map.Entry<String, CFEssentialsPlayer> entry : CFEssentials.playerMap.entrySet())
			{
				if(entry.getValue().mainHome != null)
					configData.println("mainhome:" + entry.getKey() + ":" + entry.getValue().mainHome.getWorld().getName() + ":" + entry.getValue().mainHome.getBlockX() + ":" + entry.getValue().mainHome.getBlockY() + ":" + entry.getValue().mainHome.getBlockZ() + ":" + entry.getValue().mainHome.getYaw() + ":" + entry.getValue().mainHome.getPitch());
				for(Map.Entry<String, Location> entryHome : entry.getValue().homeMap.entrySet())
					configData.println(entry.getKey() + ":" + entryHome.getKey() + ":" + entryHome.getValue().getWorld().getName() + ":" + entryHome.getValue().getBlockX() + ":" + entryHome.getValue().getBlockY() + ":" + entryHome.getValue().getBlockZ() + ":" + entryHome.getValue().getYaw() + ":" + entryHome.getValue().getPitch());
			}
			configData.close();
		}
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
	}
	
	public void loadConfiguration()
	{
		File configData = null;
		configData = new File(CFEssentials.maindirectory, "config.txt");
    	try
    	{
    		if(configData.exists())
    		{
    			BufferedReader bufferedreader = new BufferedReader(new FileReader(configData));
    			for (String s = ""; (s = bufferedreader.readLine()) != null;)
    			{
    				String[] as;
    				as = s.split(":");
    				if(as[0].equalsIgnoreCase("motd"))
    				{
    					String finalString = "";
    					for(int i = 1; i < as.length; i++)
    						finalString += as[i] + " ";
    					
    					CFEssentials.motd = finalString.trim();
    				}
    				if(as[0].equalsIgnoreCase("spawn"))
    				{
    					CFEssentials.spawnMap.put(as[1], new Location(plugin.getServer().getWorld(as[1]), Float.parseFloat(as[2]), Float.parseFloat(as[3]), Float.parseFloat(as[4]), Float.parseFloat(as[5]), Float.parseFloat(as[6])));
    				}
    				if(as[0].equalsIgnoreCase("jail"))
    				{
    					CFEssentials.jailMap.put(as[1], new Location(plugin.getServer().getWorld(as[2]), Float.parseFloat(as[3]), Float.parseFloat(as[4]), Float.parseFloat(as[5]), Float.parseFloat(as[6]), Float.parseFloat(as[7])));
    				}
    			}
				bufferedreader.close();
    		}
    	}
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
	}
	
	public void loadPlayerConfiguration()
	{
		File configData = null;
		File homeData = null;
		configData = new File(CFEssentials.maindirectory, "players.txt");
		homeData = new File(CFEssentials.maindirectory, "homes.txt");
    	try
    	{
    		if(configData.exists())
    		{
    			BufferedReader bufferedreader = new BufferedReader(new FileReader(configData));
    			for (String s = ""; (s = bufferedreader.readLine()) != null;)
    			{
    				String[] as;
    				as = s.split(":");
    				CFEssentials.playerMap.put(as[0], new CFEssentialsPlayer(as[0], false).setNickname((as[1].equals("null") ? null : as[1])).setJailed(plugin.getServer(), as[2], (as[2].equals("null") ? false : true)).setTeleport(Boolean.parseBoolean(as[3]), Integer.parseInt(as[6]), as[7]).setMuted(Boolean.parseBoolean(as[4])).setNew(Boolean.parseBoolean(as[5])));
    			}
    			bufferedreader.close();
    		}
    	}
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    	
    	try
    	{
    		if(homeData.exists())
    		{
    			BufferedReader bufferedreader = new BufferedReader(new FileReader(homeData));
    			for (String s = ""; (s = bufferedreader.readLine()) != null;)
    			{
    				String[] as;
    				as = s.split(":");
    				if(as[0].equals("mainhome"))
    					CFEssentials.playerMap.put(as[1], CFEssentials.playerMap.get(as[1]).setMainHome(plugin.getServer(), as[2], Float.parseFloat(as[3]), Float.parseFloat(as[4]), Float.parseFloat(as[5]), Float.parseFloat(as[6]), Float.parseFloat(as[7])));
    				else
    					CFEssentials.playerMap.put(as[0], CFEssentials.playerMap.get(as[0]).addHome(as[1], new Location(plugin.getServer().getWorld(as[2]), Float.parseFloat(as[3]), Float.parseFloat(as[4]), Float.parseFloat(as[5]), Float.parseFloat(as[6]), Float.parseFloat(as[7]))));
    			}
    			bufferedreader.close();
    		}
    	}
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
	}
}
