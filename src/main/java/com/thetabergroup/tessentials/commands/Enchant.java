package com.thetabergroup.tessentials.commands;

import com.thetabergroup.tessentials.handlers.Enchantments;
import com.thetabergroup.tessentials.taberessentials;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Enchant implements CommandExecutor {
    private Plugin plugin = taberessentials.getPlugin(taberessentials.class);

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        Player player = (Player) sender;

        final ItemStack stack = player.getItemInHand();
        if(label.equalsIgnoreCase("enchant")){
            if(player.hasPermission("tess.enchant")){

                if (stack == null || stack.getType() == Material.AIR){
                    player.sendMessage(taberessentials.chatFormat + " You have nothing in your " + ChatColor.GOLD + "hand.");
                }
                if(args.length == 0){
                    final Set<String> enchantmentslist = new TreeSet<String>();
                    for(Map.Entry<String, Enchantment> entry : Enchantments.entrySet()){
                        final String enchantmentName = entry.getValue().getName().toLowerCase(Locale.ENGLISH);
                        if(enchantmentslist.contains(enchantmentName) || (player.hasPermission("tess.enchants." + enchantmentName) && entry.getValue().canEnchantItem(stack))){
                            enchantmentslist.add(entry.getKey());
                        }
                    }
                    player.sendMessage(taberessentials.chatFormat + " Missing parameters ENCHANTMENT_NAME and LEVEL_INT");
                }

                int level = -1;
                if (args.length > 1){
                    try{
                        level = Integer.parseInt(args[1]);

                    }catch (NumberFormatException ex){
                        level = -1;
                    }
                }

                final boolean allowUnsafe = taberessentials.instance.getConfig().getBoolean("allowUnsafeEnchantments") && player.hasPermission("tess.enchants.allowunsafe");

                final Map.Entry<String, Enchantment> entry = (Map.Entry<String, Enchantment>) Enchantments.entrySet();
                final String enchantmentName = entry.getValue().getName().toLowerCase(Locale.ENGLISH);
                final ItemMeta metaStack = stack.getItemMeta();
                final Enchantment enchantment = (Enchantment) stack.getEnchantments();
                stack.addEnchantment(enchantment, level);

                player.updateInventory();
                if(level == 0){
                    player.sendMessage(taberessentials.chatFormat + " Removed enchantment " + ChatColor.GOLD + enchantmentName.toString() + enchantmentName.replace('_', ' '));
                }else{
                    player.sendMessage(taberessentials.chatFormat + " Added enchantment" + ChatColor.GOLD + enchantmentName.toString() + enchantmentName.replace('_', ' '));
                }
            }
        }

        return false;
    }

}
