package net.x123dev.skillissue.skills;

import net.x123dev.skillissue.MainClass;
import net.x123dev.skillissue.SkillHandler;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MasterySkill implements Listener {

    public static void init(){
        MainClass.INSTANCE.getServer().getPluginManager().registerEvents(new MasterySkill(),MainClass.INSTANCE);

        new BukkitRunnable(){
            @Override
            public void run() {
                for(Player player: Bukkit.getOnlinePlayers()) {

                    if(MainClass.INSTANCE.getSkillHandler().getSettingFor(player.getUniqueId().toString(),"skillsDisabled"))
                        continue;

                    if( MainClass.INSTANCE.getSkillHandler().getMasteryPerkFor(player.getUniqueId().toString())==4) {
                        List<Entity> entities = player.getNearbyEntities(10,10,10);
                        for(Entity entity : entities){
                            if(!player.isDead()){
                                if(entity.getType()== EntityType.EXPERIENCE_ORB){
                                    player.giveExp(((ExperienceOrb)entity).getExperience());
                                    entity.remove();
                                }else if(entity.getType()==EntityType.DROPPED_ITEM){
                                    if(!(entity.getCustomName()!=null&&entity.getCustomName().equals("drop"))){
                                        HashMap<Integer, ItemStack> didntFit = player.getInventory().addItem(((Item)entity).getItemStack());
                                        for(Map.Entry<Integer, ItemStack> entry : didntFit.entrySet()){
                                            Item newDrop = player.getWorld().dropItem(player.getLocation(),entry.getValue());
                                        }
                                        entity.remove();
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }.runTaskTimer(MainClass.INSTANCE, 12, 10);
    }

    public MasterySkill(){
        new PotionEffectRunnable().runTaskTimer(MainClass.INSTANCE,13,20);
    }

    private class PotionEffectRunnable extends BukkitRunnable {
        @Override
        public void run() {
            for(Player player : Bukkit.getOnlinePlayers()){

                if(MainClass.INSTANCE.getSkillHandler().getSettingFor(player.getUniqueId().toString(),"skillsDisabled"))
                    continue;

                if(MainClass.INSTANCE.getSkillHandler().getMasteryPerkFor(player.getUniqueId().toString())==1){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,322,0,false,false));
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDamaged(EntityDamageEvent event){
        if(event.getEntity() instanceof Player){

            if(MainClass.INSTANCE.getSkillHandler().getSettingFor(((Player)(event.getEntity())).getUniqueId().toString(),"skillsDisabled"))
                return;

            if(MainClass.INSTANCE.getSkillHandler().getMasteryPerkFor(((Player)(event.getEntity())).getUniqueId().toString())==3){
                if(((Player) event.getEntity()).getHealth()<event.getDamage()){
                    event.setDamage(0);
                    ((Player) event.getEntity()).setHealth(1);
                    ((Player) event.getEntity()).setFoodLevel(0);
                    ((Player) event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,600,0));
                    Location spawn = ((Player) event.getEntity()).getBedSpawnLocation();
                    if(spawn==null){
                        spawn=Bukkit.getWorld("world").getSpawnLocation();
                    }
                    event.getEntity().teleport(spawn);
                    Bukkit.broadcastMessage(ChatColor.GREEN+((Player)(event.getEntity())).getName()+" just got a second chance!");
                }
            }
        }
    }

    @EventHandler
    public void onPlayerGainExperience(PlayerExpChangeEvent event){

        if(MainClass.INSTANCE.getSkillHandler().getSettingFor(event.getPlayer().getUniqueId().toString(),"skillsDisabled"))
            return;

        if(event.getAmount()>0&&MainClass.INSTANCE.getSkillHandler().getMasteryPerkFor(event.getPlayer().getUniqueId().toString())==2){
            event.setAmount((int)(1.5d*((double)(event.getAmount()))));
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event){
        event.getItemDrop().setCustomName("drop");
        event.getItemDrop().setCustomNameVisible(false);
    }



}
