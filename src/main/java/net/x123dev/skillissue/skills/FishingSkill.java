package net.x123dev.skillissue.skills;

import net.x123dev.skillissue.MainClass;
import net.x123dev.skillissue.SkillHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class FishingSkill implements Listener {

    public static void init(){
        MainClass.INSTANCE.getServer().getPluginManager().registerEvents(new FishingSkill(),MainClass.INSTANCE);
    }

    public FishingSkill(){
        new PotionEffectRunnable().runTaskTimer(MainClass.INSTANCE,13,20);
    }

    private class PotionEffectRunnable extends BukkitRunnable {
        @Override
        public void run() {
            for(Player player : Bukkit.getOnlinePlayers()){

                if(MainClass.INSTANCE.getSkillHandler().getSkillPerkFor(player.getUniqueId().toString(), SkillHandler.Skills.FISHING)==1&&player.isInWater()){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE,22,0,false,false));
                }else if(MainClass.INSTANCE.getSkillHandler().getSkillPerkFor(player.getUniqueId().toString(), SkillHandler.Skills.FISHING)==2&&player.isInWater()){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING,22,0,false,false));
                }
            }
        }
    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event){
        if(event.getState()== PlayerFishEvent.State.FISHING)
            event.getHook().setMaxWaitTime(MainClass.INSTANCE.getSkillHandler().getSkillLvlFor(event.getPlayer().getUniqueId().toString(), SkillHandler.Skills.FISHING)>50?300:(600-(int)(MainClass.INSTANCE.getSkillHandler().getSkillLvlFor(event.getPlayer().getUniqueId().toString(), SkillHandler.Skills.FISHING)*6)));


        if(event.getState()== PlayerFishEvent.State.CAUGHT_FISH){
            if(MainClass.INSTANCE.getSkillHandler().getSkillPerkFor(event.getPlayer().getUniqueId().toString(), SkillHandler.Skills.FISHING)==3){
                ItemStack bonusdrop = null;
                switch(MainClass.INSTANCE.random.nextInt(200)){
                    case 0:
                        bonusdrop=new ItemStack(Material.GOLD_INGOT);
                        break;
                    case 1:
                        bonusdrop=new ItemStack(Material.DIAMOND);
                        break;
                    case 2:
                        bonusdrop=new ItemStack(Material.EMERALD);
                        break;
                    case 3:
                        bonusdrop=new ItemStack(Material.ENDER_PEARL);
                        break;
                    case 4:
                        bonusdrop=new ItemStack(Material.GHAST_TEAR);
                        break;
                    case 5:
                        bonusdrop=new ItemStack(Material.ARROW,8);
                        break;
                    case 6:
                        bonusdrop=new ItemStack(Material.EXPERIENCE_BOTTLE,8);
                        break;
                    case 7:
                        bonusdrop=new ItemStack(Material.GOLDEN_CARROT,4);
                        break;
                    case 8:
                        bonusdrop=new ItemStack(Material.PACKED_ICE,8);
                        break;
                    case 9:
                        bonusdrop=new ItemStack(Material.IRON_INGOT,2);
                        break;
                    case 10:
                        bonusdrop=new ItemStack(Material.BOOK,3);
                        break;
                    case 11:
                        bonusdrop=new ItemStack(Material.DRAGON_BREATH);
                        break;
                    case 12:
                        bonusdrop=new ItemStack(Material.GUNPOWDER,5);
                        break;
                    case 13:
                        bonusdrop=new ItemStack(Material.BONE_BLOCK);
                        break;
                    case 14:
                        bonusdrop=new ItemStack(Material.GOLDEN_APPLE);
                        break;
                    case 15:
                        bonusdrop=new ItemStack(Material.ANCIENT_DEBRIS);
                        break;
                    case 16:
                        bonusdrop=new ItemStack(Material.WITHER_SKELETON_SKULL);
                        break;
                    case 17:
                        bonusdrop=new ItemStack(Material.LAPIS_LAZULI,8);
                        break;
                    case 18:
                        bonusdrop=new ItemStack(Material.COAL,8);
                        break;
                    case 19:
                        bonusdrop=new ItemStack(Material.CRYING_OBSIDIAN,4);
                        break;

                }
                if(bonusdrop!=null){
                    event.getPlayer().getWorld().dropItemNaturally(event.getPlayer().getLocation(),bonusdrop);
                    event.getPlayer().sendMessage(ChatColor.GRAY+"extra drop: "+bonusdrop.getAmount()+"x "+bonusdrop.getType().toString());
                }
            }
            if(event.getCaught() instanceof Item){
                switch(((Item) event.getCaught()).getItemStack().getType()){
                    case COD:
                    case SALMON:
                    case TROPICAL_FISH:
                    case PUFFERFISH:
                        MainClass.INSTANCE.getSkillHandler().addSkillExpFor(event.getPlayer().getUniqueId().toString(), SkillHandler.Skills.FISHING, 100);
                        break;
                    case BOW:
                    case ENCHANTED_BOOK:
                    case FISHING_ROD:
                        MainClass.INSTANCE.getSkillHandler().addSkillExpFor(event.getPlayer().getUniqueId().toString(), SkillHandler.Skills.FISHING, 200);
                        break;
                    case NAUTILUS_SHELL:
                        MainClass.INSTANCE.getSkillHandler().addSkillExpFor(event.getPlayer().getUniqueId().toString(), SkillHandler.Skills.FISHING, 300);
                        break;
                    default:
                        MainClass.INSTANCE.getSkillHandler().addSkillExpFor(event.getPlayer().getUniqueId().toString(), SkillHandler.Skills.FISHING, 25);
                        break;
                }
            }else{
                MainClass.INSTANCE.getSkillHandler().addSkillExpFor(event.getPlayer().getUniqueId().toString(), SkillHandler.Skills.FISHING, 100);
            }
        }else if(event.getState()== PlayerFishEvent.State.CAUGHT_ENTITY){
            MainClass.INSTANCE.getSkillHandler().addSkillExpFor(event.getPlayer().getUniqueId().toString(), SkillHandler.Skills.FISHING, 3);
        }
    }
}
