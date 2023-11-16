package net.x123dev.skillissue.skills;

import net.x123dev.skillissue.MainClass;
import net.x123dev.skillissue.SkillHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.data.Ageable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFertilizeEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.Objects;

public class FarmingSkill implements Listener {

    public static void init(){
        MainClass.INSTANCE.getServer().getPluginManager().registerEvents(new FarmingSkill(),MainClass.INSTANCE);
    }


    @EventHandler
    public void onPlayerConsumeItem(PlayerItemConsumeEvent event){
        if(event.getItem().getType()==Material.BREAD
                ||event.getItem().getType()==Material.BAKED_POTATO
                ||event.getItem().getType()==Material.CARROT
                ||event.getItem().getType()==Material.MUSHROOM_STEW
                ||event.getItem().getType()==Material.BEETROOT_SOUP
                ||event.getItem().getType()==Material.RABBIT_STEW
                ||event.getItem().getType()==Material.COOKED_BEEF
                ||event.getItem().getType()==Material.COOKED_MUTTON
                ||event.getItem().getType()==Material.COOKED_CHICKEN
                ||event.getItem().getType()==Material.COOKED_PORKCHOP
                ||event.getItem().getType()==Material.COOKED_RABBIT
                ||event.getItem().getType()==Material.COOKED_COD
                ||event.getItem().getType()==Material.COOKED_SALMON
                ||event.getItem().getType()==Material.GOLDEN_CARROT
                ||event.getItem().getType()==Material.GOLDEN_APPLE
                ||event.getItem().getType()==Material.ENCHANTED_GOLDEN_APPLE){
            if(MainClass.INSTANCE.getSkillHandler().getSkillPerkFor(event.getPlayer().getUniqueId().toString(), SkillHandler.Skills.FARMING)==2&&MainClass.INSTANCE.random.nextInt(3)==0){
                event.getPlayer().sendMessage(ChatColor.GRAY+"delicious");
                event.getPlayer().setFoodLevel(20);
            }
        }
    }


    @EventHandler
    public void onBreakPlayerBlock(BlockBreakEvent event){

        if(event.getPlayer()==null) return;
        if(event.getPlayer().getInventory().getItemInMainHand()!=null
                &&event.getPlayer().getInventory().getItemInMainHand().hasItemMeta()
                &&event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(Enchantment.SILK_TOUCH)) return;


        int amount=0;
        switch(event.getBlock().getType()){
            case WHEAT:
            case CARROTS:
            case POTATOES:
            case BEETROOTS:
                amount=5;
                break;
            case NETHER_WART:
            case COCOA:
                amount=7;
                break;
            case SUGAR_CANE:
            case CACTUS:
            case MUSHROOM_STEM:
            case BROWN_MUSHROOM:
            case BROWN_MUSHROOM_BLOCK:
            case RED_MUSHROOM:
            case RED_MUSHROOM_BLOCK:
                amount=1;
                break;
            case MELON:
                amount=5;
                break;
            case PUMPKIN:
                amount=1;
                break;
            default:
                amount=0;
                break;
        }
        if(event.getBlock().getBlockData() instanceof Ageable) {
            if (((Ageable) (event.getBlock().getBlockData())).getAge() == ((Ageable) (event.getBlock().getBlockData())).getMaximumAge()) {
                if(amount>0)
                    MainClass.INSTANCE.getSkillHandler().addSkillExpFor(event.getPlayer().getUniqueId().toString(), SkillHandler.Skills.FARMING, amount);
            }
        }else{
            if(amount>0)
                MainClass.INSTANCE.getSkillHandler().addSkillExpFor(event.getPlayer().getUniqueId().toString(), SkillHandler.Skills.FARMING, amount);
        }



        //perks
        if(event.getBlock().getType()==Material.WHEAT
                ||event.getBlock().getType()==Material.CARROTS
                ||event.getBlock().getType()==Material.POTATOES
                ||event.getBlock().getType()==Material.NETHER_WART
                ||event.getBlock().getType()==Material.COCOA_BEANS
                ||event.getBlock().getType()==Material.BEETROOTS){
            if(event.getBlock().getBlockData() instanceof Ageable) {
                if (((Ageable) (event.getBlock().getBlockData())).getAge() == ((Ageable) (event.getBlock().getBlockData())).getMaximumAge()) {
                    if(MainClass.INSTANCE.getSkillHandler().getSkillPerkFor(event.getPlayer().getUniqueId().toString(), SkillHandler.Skills.FARMING)==1){
                        Material m = event.getBlock().getType();
                        new BukkitRunnable(){
                            @Override
                            public void run(){
                                event.getBlock().setType(m);
                                ((Ageable)(event.getBlock().getBlockData())).setAge(0);
                            }
                        }.runTaskLater(MainClass.INSTANCE, 5);
                    }
                    event.setCancelled(true);
                    Collection<ItemStack> drops = event.getBlock().getDrops(event.getPlayer().getInventory().getItemInMainHand());
                    for(ItemStack itemStack : drops){
                        if(itemStack.getType()== Material.WHEAT
                                ||itemStack.getType()== Material.CARROT
                                ||itemStack.getType()== Material.POTATO
                                ||itemStack.getType()== Material.BEETROOT
                                ||itemStack.getType()== Material.NETHER_WART
                                ||itemStack.getType()== Material.MELON_SLICE
                                ||itemStack.getType()==Material.COCOA_BEANS){
                            if(MainClass.INSTANCE.random.nextLong(50)<MainClass.INSTANCE.getSkillHandler().getSkillLvlFor(event.getPlayer().getUniqueId().toString(), SkillHandler.Skills.FARMING))
                                itemStack.setAmount(itemStack.getAmount()*2);
                        }
                    }
                    event.getBlock().setType(Material.AIR);
                    for(ItemStack itemStack : drops){
                        event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(),itemStack);
                    }

                }else{
                    if(MainClass.INSTANCE.getSkillHandler().getSkillPerkFor(event.getPlayer().getUniqueId().toString(), SkillHandler.Skills.FARMING)==1){
                        event.setCancelled(true);
                    }
                }
            }
        }

    }

    @EventHandler
    public void onBLockFertilize(BlockFertilizeEvent event){
        MainClass.INSTANCE.getSkillHandler().addSkillExpFor(event.getPlayer().getUniqueId().toString(), SkillHandler.Skills.FARMING, 10);
    }

}
