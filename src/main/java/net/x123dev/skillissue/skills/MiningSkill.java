package net.x123dev.skillissue.skills;

import net.x123dev.skillissue.MainClass;
import net.x123dev.skillissue.SkillHandler;
import net.x123dev.skillissue.SkillHandler.Skills;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class MiningSkill implements Listener {

    public static void init(){
        MainClass.INSTANCE.getServer().getPluginManager().registerEvents(new MiningSkill(),MainClass.INSTANCE);
    }

    @EventHandler
    public void onBreakPlayerBlock(BlockBreakEvent event){
        if(event.getPlayer()==null) return;
        MainClass.INSTANCE.getSkillHandler().addSkillExpFor(event.getPlayer().getUniqueId().toString(), Skills.MINING,1);
    }

}
