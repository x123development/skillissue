package net.x123dev.skillissue.skills;

import net.x123dev.skillissue.MainClass;
import net.x123dev.skillissue.SkillHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class ExplorationSkill implements Listener {

    HashMap<Player, Location> lastLocations;
    HashMap<Player, Double> distanceTraveled;
    HashMap<Player,Long> grappleCooldowns;

    public static void init(){
        MainClass.INSTANCE.getServer().getPluginManager().registerEvents(new ExplorationSkill(),MainClass.INSTANCE);
    }

    public ExplorationSkill(){
        lastLocations=new HashMap<Player, Location>();
        distanceTraveled=new HashMap<Player, Double>();
        grappleCooldowns=new HashMap<Player,Long>();
        new ExpRunnable().runTaskTimer(MainClass.INSTANCE,101,6000);
        new DistanceRunnable().runTaskTimer(MainClass.INSTANCE,103,200);
        new PotionEffectRunnable().runTaskTimer(MainClass.INSTANCE,14,20);
    }

    @EventHandler
    public void onPlayerAdvancementDone(PlayerAdvancementDoneEvent event){
        MainClass.INSTANCE.getSkillHandler().addSkillExpFor(event.getPlayer().getUniqueId().toString(), SkillHandler.Skills.EXPLORATION,300);
    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event){

        if(MainClass.INSTANCE.getSkillHandler().getSettingFor(event.getPlayer().getUniqueId().toString(),"skillsDisabled"))
            return;

        if(event.getState()!= PlayerFishEvent.State.IN_GROUND&&event.getState()!= PlayerFishEvent.State.REEL_IN) return;
        if(MainClass.INSTANCE.getSkillHandler().getSkillPerkFor(event.getPlayer().getUniqueId().toString(), SkillHandler.Skills.EXPLORATION)!=3) return;

        if(grappleCooldowns.containsKey(event.getPlayer())){
            if(grappleCooldowns.get(event.getPlayer())>System.currentTimeMillis())
                return;
        }
        grappleCooldowns.put(event.getPlayer(), System.currentTimeMillis()+3000);

        Vector diff = event.getHook().getLocation().toVector().subtract(event.getPlayer().getLocation().toVector());
        event.getPlayer().setVelocity(diff.normalize().add(new Vector(0,0.25d,0)).multiply(1.5d));

    }

    private class PotionEffectRunnable extends BukkitRunnable {
        @Override
        public void run() {
            for(Player player : Bukkit.getOnlinePlayers()){

                if(MainClass.INSTANCE.getSkillHandler().getSettingFor(player.getUniqueId().toString(),"skillsDisabled")){
                    player.getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(0.1d);
                    continue;
                }


                if(MainClass.INSTANCE.getSkillHandler().getSkillLvlFor(player.getUniqueId().toString(), SkillHandler.Skills.EXPLORATION)>50)
                    player.getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(0.15d);
                else
                    player.getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(0.1d+((double)(MainClass.INSTANCE.getSkillHandler().getSkillLvlFor(player.getUniqueId().toString(), SkillHandler.Skills.EXPLORATION))/1000d));

                if(MainClass.INSTANCE.getSkillHandler().getSkillPerkFor(player.getUniqueId().toString(), SkillHandler.Skills.EXPLORATION)==1){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST,22,1,false,false));
                }
            }
        }
    }


    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event){

        if(MainClass.INSTANCE.getSkillHandler().getSettingFor(event.getPlayer().getUniqueId().toString(),"skillsDisabled"))
            return;

        if(event.getCause()== PlayerTeleportEvent.TeleportCause.ENDER_PEARL&&MainClass.INSTANCE.getSkillHandler().getSkillPerkFor(event.getPlayer().getUniqueId().toString(), SkillHandler.Skills.EXPLORATION)==2){
            event.getPlayer().getInventory().addItem(new ItemStack(Material.ENDER_PEARL));
        }
    }

    private class ExpRunnable extends BukkitRunnable {
        @Override
        public void run() {
            for(Player player : Bukkit.getOnlinePlayers()){
                if(distanceTraveled.containsKey(player)){
                    if(distanceTraveled.get(player)>300){
                        MainClass.INSTANCE.getSkillHandler().addSkillExpFor(player.getUniqueId().toString(), SkillHandler.Skills.EXPLORATION,getXpFromDistance(distanceTraveled.get(player)));
                    }
                }
                distanceTraveled.put(player,0d);
            }
        }
    }

    private class DistanceRunnable extends BukkitRunnable {

        @Override
        public void run() {
            for(Player player :Bukkit.getOnlinePlayers()){
                if(lastLocations.containsKey(player)&&lastLocations.get(player).getWorld().getName().equals(player.getLocation().getWorld().getName())){
                    if(distanceTraveled.containsKey(player)){
                        if(!(player.isInsideVehicle()&&player.getVehicle().getType()==EntityType.MINECART))
                            distanceTraveled.put(player,distanceTraveled.get(player)+player.getLocation().distance(lastLocations.get(player)));
                    }else{
                        distanceTraveled.put(player,player.getLocation().distance(lastLocations.get(player)));
                    }
                    lastLocations.put(player,player.getLocation());
                }else{
                    lastLocations.put(player,player.getLocation());
                }
            }
        }
    }

    private long getXpFromDistance(double dist){
        return (long)(1000-Math.pow(Math.E,-(0.0015d*(dist-4700))));
    }

}
