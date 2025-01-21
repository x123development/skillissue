package net.x123dev.skillissue.skills;

import net.x123dev.skillissue.MainClass;
import net.x123dev.skillissue.SkillHandler;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AlchemySkill implements Listener {

    public static void init(){
        MainClass.INSTANCE.getServer().getPluginManager().registerEvents(new AlchemySkill(),MainClass.INSTANCE);
    }

    @EventHandler
    public void onBrew(BrewEvent event){
        long amount=0;

        List<ItemStack> results = event.getResults();
        for(ItemStack stack : results){
            if(stack.getType()== Material.POTION&&stack.getItemMeta() instanceof PotionMeta) {
                switch(((PotionMeta)(stack.getItemMeta())).getBasePotionType()){
                    case THICK:
                    case MUNDANE:
                    case OOZING:
                    case INFESTED:
                        amount+=10;
                        break;
                    case AWKWARD:
                        amount+=50;
                        break;
                    case LEAPING:
                    case LUCK:
                    case WEAVING:
                    case WIND_CHARGED:
                    case REGENERATION:
                    case SWIFTNESS:
                    case POISON:
                    case SLOWNESS:
                    case STRENGTH:
                    case WEAKNESS:
                    case HEALING:
                    case INVISIBILITY:
                    case NIGHT_VISION:
                    case SLOW_FALLING:
                    case TURTLE_MASTER:
                    case HARMING:
                    case FIRE_RESISTANCE:
                    case WATER_BREATHING:
                        amount+=200;
                        break;
                    case LONG_POISON:
                    case LONG_LEAPING:
                    case LONG_SLOWNESS:
                    case LONG_STRENGTH:
                    case LONG_WEAKNESS:
                    case STRONG_POISON:
                    case LONG_SWIFTNESS:
                    case STRONG_HARMING:
                    case STRONG_HEALING:
                    case STRONG_LEAPING:
                    case STRONG_SLOWNESS:
                    case STRONG_STRENGTH:
                    case STRONG_SWIFTNESS:
                    case STRONG_REGENERATION:
                    case LONG_INVISIBILITY:
                    case LONG_NIGHT_VISION:
                    case LONG_REGENERATION:
                    case LONG_SLOW_FALLING:
                    case LONG_TURTLE_MASTER:
                    case LONG_FIRE_RESISTANCE:
                    case LONG_WATER_BREATHING:
                    case STRONG_TURTLE_MASTER:
                        amount+=100;
                }
            }
        }


        Block brewingStand = event.getBlock();
        Collection<Entity> nearby = brewingStand.getWorld().getNearbyEntities(brewingStand.getLocation(),32,32,32);
        for(Entity entity : nearby){
            if(entity instanceof Player){
                MainClass.INSTANCE.getSkillHandler().addSkillExpFor(((Player)entity).getUniqueId().toString(), SkillHandler.Skills.ALCHEMY, amount);
            }
        }
    }


    @EventHandler
    public void onPlayerDrinkPotion(PlayerItemConsumeEvent event){
        if(event.getItem().getType()==Material.POTION&&event.getItem().hasItemMeta()&&event.getItem().getItemMeta() instanceof PotionMeta&&((PotionMeta) event.getItem().getItemMeta()).getBasePotionType()!= PotionType.WATER){
            MainClass.INSTANCE.getSkillHandler().addSkillExpFor(event.getPlayer().getUniqueId().toString(), SkillHandler.Skills.ALCHEMY, 15);

            if(MainClass.INSTANCE.getSkillHandler().getSettingFor(event.getPlayer().getUniqueId().toString(),"skillsDisabled"))
                return;

            PotionType drunk = ((PotionMeta) event.getItem().getItemMeta()).getBasePotionType();
            for(PotionEffect potionEffect:drunk.getPotionEffects()){
                if(event.getPlayer().getPotionEffect(potionEffect.getType())!=null&&event.getPlayer().getPotionEffect(potionEffect.getType()).getAmplifier()==potionEffect.getAmplifier()&&MainClass.INSTANCE.getSkillHandler().getSkillPerkFor(event.getPlayer().getUniqueId().toString(), SkillHandler.Skills.ALCHEMY)==2){
                    event.getPlayer().addPotionEffect(new PotionEffect(potionEffect.getType(),potionEffect.getDuration()+potionEffect.getDuration()*(MainClass.INSTANCE.getSkillHandler().getSkillLvlFor(event.getPlayer().getUniqueId().toString(), SkillHandler.Skills.ALCHEMY)>50?100:((int)(MainClass.INSTANCE.getSkillHandler().getSkillLvlFor(event.getPlayer().getUniqueId().toString(), SkillHandler.Skills.ALCHEMY)))*2)/100+event.getPlayer().getPotionEffect(potionEffect.getType()).getDuration(),potionEffect.getAmplifier()));
                }
                event.getPlayer().addPotionEffect(new PotionEffect(potionEffect.getType(),potionEffect.getDuration()+potionEffect.getDuration()*(MainClass.INSTANCE.getSkillHandler().getSkillLvlFor(event.getPlayer().getUniqueId().toString(), SkillHandler.Skills.ALCHEMY)>50?100:((int)(MainClass.INSTANCE.getSkillHandler().getSkillLvlFor(event.getPlayer().getUniqueId().toString(), SkillHandler.Skills.ALCHEMY)))*2)/100,potionEffect.getAmplifier()));
            }
        }
    }

    @EventHandler
    public void onPotionSplash(PotionSplashEvent event){
        if(event.getEntity().getShooter() instanceof Player){
            MainClass.INSTANCE.getSkillHandler().addSkillExpFor(((Player)event.getEntity().getShooter()).getUniqueId().toString(), SkillHandler.Skills.ALCHEMY, 15);

            if(MainClass.INSTANCE.getSkillHandler().getSettingFor(((Player) event.getEntity().getShooter()).getUniqueId().toString(),"skillsDisabled"))
                return;

            if(MainClass.INSTANCE.getSkillHandler().getSkillPerkFor(((Player) event.getEntity().getShooter()).getUniqueId().toString(), SkillHandler.Skills.ALCHEMY)==1){
                event.getEntity().getWorld().dropItem(event.getEntity().getLocation(),new ItemStack(Material.GLASS_BOTTLE));
            }
        }
    }
}
