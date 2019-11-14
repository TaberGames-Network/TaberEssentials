package taberessentials.core;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.ChatColor;

public class CFEssentialsKit implements Runnable {

    private Thread runner;
    public List<ItemStack> itemList = new ArrayList();
    public String kitName;
    public Player player;

    public CFEssentialsKit(String name)
    {
        kitName = name;
    }

    public CFEssentialsKit addItem(ItemStack item)
    {
        itemList.add(item);
        return this;
    }

    public CFEssentialsKit setPlayer(Player p)
    {
        player = p;
        return this;
    }

    public boolean isInventoryFull()
    {
        int c = 0;

        for(int i = 0; i < player.getInventory().getSize(); i++)
        {
            if(player.getInventory().getItem(i) == null)
            {
                c++;
            }
        }

        if(c > 0)
            return false;

        return true;
    }

    public void giveToPlayer()
    {
        boolean wasFull = false;
        for(ItemStack item : itemList)
        {
            if(!isInventoryFull())
                player.getInventory().setItem(player.getInventory().firstEmpty(), item);
            else
            {
                wasFull = true;
                player.getWorld().dropItemNaturally(player.getLocation(), item);
            }
        }
        CFEssentials.playerMap.put(player.getName().toLowerCase(), CFEssentials.playerMap.get(player.getName().toLowerCase()).setKitLock(true));
        if(!wasFull)
            player.sendMessage(ChatColor.GRAY + "Kit \"" + kitName + "\" given.");
        else
            player.sendMessage(ChatColor.GRAY + "Inventory full. Kit dropped on the floor.");
    }

    @Override
    public void run()
    {
        int timeElapsed = 0;
        giveToPlayer();
        while(timeElapsed < 3600)
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            timeElapsed++;
        }
        CFEssentials.playerMap.put(player.getName().toLowerCase(), CFEssentials.playerMap.get(player.getName().toLowerCase()).setKitLock(false));
    }
}
