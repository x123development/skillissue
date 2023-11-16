package net.x123dev.skillissue.skills;

import net.x123dev.skillissue.MainClass;
import net.x123dev.skillissue.SkillHandler;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class CombatSkill implements Listener {

    public static void init(){
        MainClass.INSTANCE.getServer().getPluginManager().registerEvents(new CombatSkill(),MainClass.INSTANCE);
    }

    public CombatSkill(){
        new PotionEffectRunnable().runTaskTimer(MainClass.INSTANCE,13,20);
    }

    private class PotionEffectRunnable extends BukkitRunnable {
        @Override
        public void run() {
            for(Player player : Bukkit.getOnlinePlayers()){
                if(MainClass.INSTANCE.getSkillHandler().getSkillLvlFor(player.getUniqueId().toString(), SkillHandler.Skills.COMBAT)>50)
                    player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(30);
                else
                    player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20+Math.floor(((double)(MainClass.INSTANCE.getSkillHandler().getSkillLvlFor(player.getUniqueId().toString(), SkillHandler.Skills.COMBAT)))/5d));

                if(MainClass.INSTANCE.getSkillHandler().getSkillPerkFor(player.getUniqueId().toString(), SkillHandler.Skills.COMBAT)==1){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,22,0,false,false));
                }else if(MainClass.INSTANCE.getSkillHandler().getSkillPerkFor(player.getUniqueId().toString(), SkillHandler.Skills.COMBAT)==2){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,22,0,false,false));
                }
            }
        }
    }

    @EventHandler
    public void onPlayerKillEntity(EntityDeathEvent event){
        if(event.getEntity().getKiller()==null) return;
        if(event.getEntity().getType()== EntityType.WITHER){
            MainClass.INSTANCE.getSkillHandler().addSkillExpFor(event.getEntity().getKiller().getUniqueId().toString(), SkillHandler.Skills.COMBAT, 5000);
        }else if(event.getEntity().getType()==EntityType.ENDER_DRAGON){
            MainClass.INSTANCE.getSkillHandler().addSkillExpFor(event.getEntity().getKiller().getUniqueId().toString(), SkillHandler.Skills.COMBAT, 5000);
        }else{
            MainClass.INSTANCE.getSkillHandler().addSkillExpFor(event.getEntity().getKiller().getUniqueId().toString(), SkillHandler.Skills.COMBAT, (long)(Objects.requireNonNull(event.getEntity().getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue()*2.5d));
        }
    }

    @EventHandler
    public void onPlayerHitEntity(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player&&event.getEntity() instanceof LivingEntity){
            if(MainClass.INSTANCE.getSkillHandler().getSkillPerkFor(((Player)(event.getDamager())).getUniqueId().toString(), SkillHandler.Skills.COMBAT)==3)
                ((LivingEntity) event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,100,0));
        }
    }

}
