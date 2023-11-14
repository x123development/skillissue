package net.x123dev.skillissue.command;

import net.x123dev.skillissue.MainClass;
import net.x123dev.skillissue.SkillHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

import static net.x123dev.skillissue.SkillHandler.*;

public class SkillCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String uuid="";
        if(args.length==0){
            if(sender instanceof Player) uuid=((Player) sender).getUniqueId().toString();
            else return false;
        }else if(args.length==1){
            if(Bukkit.getPlayer(args[0])!=null)
                uuid= Bukkit.getPlayer(args[0]).getUniqueId().toString();
            else{
                sender.sendMessage(ChatColor.RED+"This player is not online!");
                return true;
            }
        }else{
            return false;
        }
        if(sender instanceof Player){
            Player senderPlayer = (Player) sender;
            SkillHandler sh = MainClass.INSTANCE.getSkillHandler();
            senderPlayer.sendMessage(""+ChatColor.AQUA+ChatColor.BOLD+"Skills of player: "+Bukkit.getPlayer(UUID.fromString(uuid)).getName());
            senderPlayer.sendMessage(ChatColor.AQUA+"COMBAT "+sh.getSkillLvlFor(uuid,Skills.COMBAT)+ChatColor.WHITE+" - "+getFormattedLvlProgressByExp(sh.getSkillExpFor(uuid, SkillHandler.Skills.COMBAT))+" to next level, "+sh.getSkillExpFor(uuid,Skills.COMBAT)+" total Exp");
            senderPlayer.sendMessage(ChatColor.AQUA+"MINING "+sh.getSkillLvlFor(uuid,Skills.MINING)+ChatColor.WHITE+" - "+getFormattedLvlProgressByExp(sh.getSkillExpFor(uuid, SkillHandler.Skills.MINING))+" to next level, "+sh.getSkillExpFor(uuid,Skills.MINING)+" total Exp");
            senderPlayer.sendMessage(ChatColor.AQUA+"FARMING "+sh.getSkillLvlFor(uuid,Skills.FARMING)+ChatColor.WHITE+" - "+getFormattedLvlProgressByExp(sh.getSkillExpFor(uuid, SkillHandler.Skills.FARMING))+" to next level, "+sh.getSkillExpFor(uuid,Skills.FARMING)+" total Exp");
            senderPlayer.sendMessage(ChatColor.AQUA+"FISHING "+sh.getSkillLvlFor(uuid,Skills.FISHING)+ChatColor.WHITE+" - "+getFormattedLvlProgressByExp(sh.getSkillExpFor(uuid, SkillHandler.Skills.FISHING))+" to next level, "+sh.getSkillExpFor(uuid,Skills.FISHING)+" total Exp");
            senderPlayer.sendMessage(ChatColor.AQUA+"ALCHEMY "+sh.getSkillLvlFor(uuid,Skills.ALCHEMY)+ChatColor.WHITE+" - "+getFormattedLvlProgressByExp(sh.getSkillExpFor(uuid, SkillHandler.Skills.ALCHEMY))+" to next level, "+sh.getSkillExpFor(uuid,Skills.ALCHEMY)+" total Exp");
            senderPlayer.sendMessage(ChatColor.AQUA+"EXPLORATION "+sh.getSkillLvlFor(uuid,Skills.EXPLORATION)+ChatColor.WHITE+" - "+getFormattedLvlProgressByExp(sh.getSkillExpFor(uuid, SkillHandler.Skills.EXPLORATION))+" to next level, "+sh.getSkillExpFor(uuid,Skills.EXPLORATION)+" total Exp");
        }else{
            SkillHandler sh = MainClass.INSTANCE.getSkillHandler();
            sender.sendMessage("Skills of player: "+Bukkit.getPlayer(UUID.fromString(uuid)).getName());
            sender.sendMessage("COMBAT "+sh.getSkillLvlFor(uuid,Skills.COMBAT)+ChatColor.WHITE+" - "+getFormattedLvlProgressByExp(sh.getSkillExpFor(uuid, SkillHandler.Skills.COMBAT))+" to next level, "+sh.getSkillExpFor(uuid,Skills.COMBAT)+" total Exp");
            sender.sendMessage("MINING "+sh.getSkillLvlFor(uuid,Skills.MINING)+ChatColor.WHITE+" - "+getFormattedLvlProgressByExp(sh.getSkillExpFor(uuid, SkillHandler.Skills.MINING))+" to next level, "+sh.getSkillExpFor(uuid,Skills.MINING)+" total Exp");
            sender.sendMessage("FARMING "+sh.getSkillLvlFor(uuid,Skills.FARMING)+ChatColor.WHITE+" - "+getFormattedLvlProgressByExp(sh.getSkillExpFor(uuid, SkillHandler.Skills.FARMING))+" to next level, "+sh.getSkillExpFor(uuid,Skills.FARMING)+" total Exp");
            sender.sendMessage("FISHING "+sh.getSkillLvlFor(uuid,Skills.FISHING)+ChatColor.WHITE+" - "+getFormattedLvlProgressByExp(sh.getSkillExpFor(uuid, SkillHandler.Skills.FISHING))+" to next level, "+sh.getSkillExpFor(uuid,Skills.FISHING)+" total Exp");
            sender.sendMessage("ALCHEMY "+sh.getSkillLvlFor(uuid,Skills.ALCHEMY)+ChatColor.WHITE+" - "+getFormattedLvlProgressByExp(sh.getSkillExpFor(uuid, SkillHandler.Skills.ALCHEMY))+" to next level, "+sh.getSkillExpFor(uuid,Skills.ALCHEMY)+" total Exp");
            sender.sendMessage("EXPLORATION "+sh.getSkillLvlFor(uuid,Skills.EXPLORATION)+ChatColor.WHITE+" - "+getFormattedLvlProgressByExp(sh.getSkillExpFor(uuid, SkillHandler.Skills.EXPLORATION))+" to next level, "+sh.getSkillExpFor(uuid,Skills.EXPLORATION)+" total Exp");

        }


        return true;
    }
}
