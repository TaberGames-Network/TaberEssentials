package com.thetabergroup.tessentials.commands;


public class Teleport implements CommandExecutor{

public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

  Player player = (Player) sender;
  if(label.equalsIgnoreCase("teleport")){
    
      if(args.length == 0){ 
      player.sendMessage(Main.chatFormat + "Invalid parameters."); 
      player.sendMessage(main.chatFormat + "/tp <target>"); 
      }
      if(args.length == 1){
      
    }
  }

}
