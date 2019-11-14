package com.craftingfactions.diego.CFEssentials;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class CFEssentialsTeleport implements Runnable {

	private Thread runner;
	public Player player;
	public Location location;
	public Player playerLocation;
	private int secondsDelay;
	private int timeElapsed;
	public boolean isAvaliable;
	public boolean doStuff;
	public boolean wasAborted;
	
	public CFEssentialsTeleport()
	{
		isAvaliable = true;
		runner = new Thread(this);
		runner.start();
	}
	
	public CFEssentialsTeleport setTeleport(Player player, Location location, int secondsDelay)
	{
		this.playerLocation = null;
		this.player = player;
		this.location = location;
		this.secondsDelay = secondsDelay;
		return this;
	}
	
	public CFEssentialsTeleport setTeleport(Player player, Player location, int secondsDelay)
	{
		this.location = null;
		this.player = player;
		this.playerLocation = location;
		this.secondsDelay = secondsDelay;
		return this;
	}
	
	public void start()
	{
		timeElapsed = 0;
		isAvaliable = false;
		doStuff = true;
	}
	
	@Override
	public void run()
	{
		while(!doStuff && runner.isAlive()){try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}}
		if(doStuff)
		{
			int lastX = player.getLocation().getBlockX();
			int lastY = player.getLocation().getBlockY();
			int lastZ = player.getLocation().getBlockZ();
			
			if(CFEssentials.playerMap.containsKey(player.getName().toLowerCase()))
				CFEssentials.playerMap.put(player.getName().toLowerCase(), CFEssentials.playerMap.get(player.getName().toLowerCase()).setTeleporting(true));
	
			if(!player.isOp() || !player.hasPermission("CF.cfmod.admin"))
			{
				while(timeElapsed < secondsDelay - 1)
				{
					if(player.getLocation().getBlockX() != lastX || player.getLocation().getBlockY() != lastY || player.getLocation().getBlockZ() != lastZ)
					{
						CFEssentials.playerMap.put(player.getName().toLowerCase(), CFEssentials.playerMap.get(player.getName().toLowerCase()).setTeleporting(false));
						player.sendMessage(ChatColor.RED + "Current teleport operation aborted.");
						wasAborted = true;
						break;
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					timeElapsed++;
				}
			}
			if(!wasAborted)
			{
				if(location == null)
					player.teleport(playerLocation.getLocation());
				else
					player.teleport(location);
			}
			CFEssentials.playerMap.put(player.getName().toLowerCase(), CFEssentials.playerMap.get(player.getName().toLowerCase()).setTeleporting(false));
			isAvaliable = true;
			doStuff = false;
			wasAborted = false;
			run();
		}
	}
}
