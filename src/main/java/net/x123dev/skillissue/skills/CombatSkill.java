package net.x123dev.skillissue.skills;

import net.x123dev.skillissue.MainClass;
import net.x123dev.skillissue.SkillHandler;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class CombatSkill implements Listener {

    public static void init(){
        MainClass.INSTANCE.getServer().getPluginManager().registerEvents(new CombatSkill(),MainClass.INSTANCE);
    }

    @EventHandler
    public void onPlayerKillEntity(EntityDeathEvent event){
        if(event.getEntity().getKiller()==null) return;
        if(event.getEntity().getType()== EntityType.WITHER){
            MainClass.INSTANCE.getSkillHandler().addSkillExpFor(event.getEntity().getKiller().getUniqueId().toString(), SkillHandler.Skills.COMBAT, 5000);
        }else if(event.getEntity().getType()==EntityType.ENDER_DRAGON){
            MainClass.INSTANCE.getSkillHandler().addSkillExpFor(event.getEntity().getKiller().getUniqueId().toString(), SkillHandler.Skills.COMBAT, 5000);
        }else{
            MainClass.INSTANCE.getSkillHandler().addSkillExpFor(event.getEntity().getKiller().getUniqueId().toString(), SkillHandler.Skills.COMBAT, (long)(event.getEntity().getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()*2.5d));
        }
    }

}
