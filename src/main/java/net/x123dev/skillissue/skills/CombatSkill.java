package net.x123dev.skillissue.skills;

import net.x123dev.skillissue.MainClass;
import net.x123dev.skillissue.SkillHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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

import java.util.HashMap;
import java.util.Objects;

public class CombatSkill implements Listener {

    private HashMap<Player,Killstreak> killstreakHashMap;

    public static void init(){
        MainClass.INSTANCE.getServer().getPluginManager().registerEvents(new CombatSkill(),MainClass.INSTANCE);
    }

    public CombatSkill(){
        killstreakHashMap=new HashMap<>();
        new PotionEffectRunnable().runTaskTimer(MainClass.INSTANCE,13,20);
    }

    private class PotionEffectRunnable extends BukkitRunnable {
        @Override
        public void run() {
            for(Player player : Bukkit.getOnlinePlayers()){

                if(MainClass.INSTANCE.getSkillHandler().getSettingFor(player.getUniqueId().toString(),"skillsDisabled")){
                    player.getAttribute(Attribute.MAX_HEALTH).setBaseValue(20);
                    continue;
                }


                if(MainClass.INSTANCE.getSkillHandler().getSkillLvlFor(player.getUniqueId().toString(), SkillHandler.Skills.COMBAT)>50)
                    player.getAttribute(Attribute.MAX_HEALTH).setBaseValue(30);
                else
                    player.getAttribute(Attribute.MAX_HEALTH).setBaseValue(20+Math.floor(((double)(MainClass.INSTANCE.getSkillHandler().getSkillLvlFor(player.getUniqueId().toString(), SkillHandler.Skills.COMBAT)))/5d));

                if(MainClass.INSTANCE.getSkillHandler().getSkillPerkFor(player.getUniqueId().toString(), SkillHandler.Skills.COMBAT)==1){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH,22,0,false,false));
                }else if(MainClass.INSTANCE.getSkillHandler().getSkillPerkFor(player.getUniqueId().toString(), SkillHandler.Skills.COMBAT)==2){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE,22,0,false,false));
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
            Location last=null;
            if(killstreakHashMap.containsKey(event.getEntity().getKiller())){
                last=killstreakHashMap.get(event.getEntity().getKiller()).lastPosition;
                if(killstreakHashMap.get(event.getEntity().getKiller()).type==event.getEntityType()){
                    killstreakHashMap.put(event.getEntity().getKiller(),new Killstreak((last!=null&& Objects.requireNonNull(last.getWorld()).getName().equals(Objects.requireNonNull(event.getEntity().getKiller().getLocation().getWorld()).getName())&&last.distance(event.getEntity().getKiller().getLocation())<20)?killstreakHashMap.get(event.getEntity().getKiller()).count+1:(killstreakHashMap.get(event.getEntity().getKiller()).count>=5?6:killstreakHashMap.get(event.getEntity().getKiller()).count+1),event.getEntityType(),event.getEntity().getKiller().getLocation()));
                }else{
                    killstreakHashMap.put(event.getEntity().getKiller(),new Killstreak(1,event.getEntityType(),event.getEntity().getKiller().getLocation()));
                }
            }else{
                killstreakHashMap.put(event.getEntity().getKiller(),new Killstreak(1,event.getEntityType(),event.getEntity().getKiller().getLocation()));
            }
            if(killstreakHashMap.get(event.getEntity().getKiller()).count>10&&last!=null&& Objects.requireNonNull(last.getWorld()).getName().equals(Objects.requireNonNull(event.getEntity().getKiller().getLocation().getWorld()).getName())&&last.distance(event.getEntity().getKiller().getLocation())<20){
                MainClass.INSTANCE.getSkillHandler().addSkillExpFor(event.getEntity().getKiller().getUniqueId().toString(), SkillHandler.Skills.COMBAT, (long)(Objects.requireNonNull(event.getEntity().getAttribute(Attribute.MAX_HEALTH)).getValue()*0.25d));
                event.getEntity().getKiller().sendMessage(ChatColor.RED+"You are gaining reduced Combat Exp!");
            }
            else{
                MainClass.INSTANCE.getSkillHandler().addSkillExpFor(event.getEntity().getKiller().getUniqueId().toString(), SkillHandler.Skills.COMBAT, (long)(Objects.requireNonNull(event.getEntity().getAttribute(Attribute.MAX_HEALTH)).getValue()*2.5d));
            }

        }
    }

    @EventHandler
    public void onPlayerHitEntity(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player&&event.getEntity() instanceof LivingEntity){

            if(MainClass.INSTANCE.getSkillHandler().getSettingFor(((Player)(event.getDamager())).getUniqueId().toString(),"skillsDisabled"))
                return;

            if(MainClass.INSTANCE.getSkillHandler().getSkillPerkFor(((Player)(event.getDamager())).getUniqueId().toString(), SkillHandler.Skills.COMBAT)==3)
                ((LivingEntity) event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,100,0));
        }
    }

    private class Killstreak{

        public Killstreak(int count, EntityType type,Location lastPosition){
            this.count=count;
            this.type=type;
            this.lastPosition=lastPosition;
        }

        public int count;
        public EntityType type;
        public Location lastPosition;
    }

}
