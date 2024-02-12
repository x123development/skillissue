package net.x123dev.skillissue.skills;

import net.x123dev.skillissue.MainClass;
import net.x123dev.skillissue.SkillHandler;
import net.x123dev.skillissue.SkillHandler.Skills;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;

import java.util.Collection;

public class MiningSkill implements Listener {

    public static void init(){
        MainClass.INSTANCE.getServer().getPluginManager().registerEvents(new MiningSkill(),MainClass.INSTANCE);
    }

    public MiningSkill(){
         new PotionEffectRunnable().runTaskTimer(MainClass.INSTANCE,13,20);
    }

    private class PotionEffectRunnable extends BukkitRunnable {
        @Override
        public void run() {
            for(Player player : Bukkit.getOnlinePlayers()){

                if(MainClass.INSTANCE.getSkillHandler().getSettingFor(player.getUniqueId().toString(),"skillsDisabled"))
                    continue;

                if(MainClass.INSTANCE.getSkillHandler().getSkillLvlFor(player.getUniqueId().toString(),Skills.MINING)>=50){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING,22,2,false,false));
                }else if(MainClass.INSTANCE.getSkillHandler().getSkillLvlFor(player.getUniqueId().toString(),Skills.MINING)>=30){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING,22,1,false,false));
                }else if(MainClass.INSTANCE.getSkillHandler().getSkillLvlFor(player.getUniqueId().toString(),Skills.MINING)>=10){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING,22,0,false,false));
                }
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
            case STONE:
                amount=2;
                break;
            case DEEPSLATE:
                amount=3;
                break;
            case NETHERRACK:
                amount=1;
                break;
            case OBSIDIAN:
                amount=10;
                break;
            case AMETHYST_CLUSTER:
                amount=10;
                break;
            case IRON_ORE:
            case DEEPSLATE_IRON_ORE:
                amount=20;
                break;
            case GOLD_ORE:
            case DEEPSLATE_GOLD_ORE:
                amount=40;
                break;
            case REDSTONE_ORE:
            case DEEPSLATE_REDSTONE_ORE:
                amount=30;
                break;
            case LAPIS_ORE:
            case DEEPSLATE_LAPIS_ORE:
                amount=40;
                break;
            case DIAMOND_ORE:
            case DEEPSLATE_DIAMOND_ORE:
                amount=100;
                break;
            case EMERALD_ORE:
            case DEEPSLATE_EMERALD_ORE:
                amount=150;
                break;
            case COPPER_ORE:
            case DEEPSLATE_COPPER_ORE:
                amount=10;
                break;
            case COAL_ORE:
            case DEEPSLATE_COAL_ORE:
                amount=10;
                break;
            case NETHER_QUARTZ_ORE:
            case NETHER_GOLD_ORE:
                amount=8;
                break;
            case ICE:
            case BLUE_ICE:
            case PACKED_ICE:
                amount=2;
                break;
            case BLACKSTONE:
                amount=1;
                break;
            case END_STONE:
                amount=2;
                break;
            case MAGMA_BLOCK:
                amount=1;
                break;
            case SANDSTONE:
                amount=1;
                break;
            default:
                amount=0;
                break;
        }
        if(amount>0)
            MainClass.INSTANCE.getSkillHandler().addSkillExpFor(event.getPlayer().getUniqueId().toString(), Skills.MINING, amount);

        if(MainClass.INSTANCE.getSkillHandler().getSettingFor(event.getPlayer().getUniqueId().toString(),"skillsDisabled"))
            return;

        //perks
        if(MainClass.INSTANCE.getSkillHandler().getSkillPerkFor(event.getPlayer().getUniqueId().toString(),Skills.MINING)==1&&event.getPlayer().getGameMode()== GameMode.SURVIVAL){
            if(event.getBlock().getType()==Material.COPPER_ORE
                    ||event.getBlock().getType()==Material.DEEPSLATE_COPPER_ORE
                    ||event.getBlock().getType()==Material.IRON_ORE
                    ||event.getBlock().getType()==Material.DEEPSLATE_IRON_ORE
                    ||event.getBlock().getType()==Material.GOLD_ORE
                    ||event.getBlock().getType()==Material.DEEPSLATE_GOLD_ORE
                    ||event.getBlock().getType()==Material.STONE
                    ||event.getBlock().getType()==Material.DEEPSLATE
                    ||event.getBlock().getType()==Material.SAND
                    ||event.getBlock().getType()==Material.NETHERRACK
                    ||event.getBlock().getType()==Material.CLAY
                    ||event.getBlock().getType()==Material.ANCIENT_DEBRIS
                    ||event.getBlock().getType()==Material.NETHER_GOLD_ORE){
                int xp = event.getExpToDrop();
                event.setCancelled(true);
                Collection<ItemStack> drops = event.getBlock().getDrops(event.getPlayer().getInventory().getItemInMainHand());
                for(ItemStack itemStack : drops){
                    if(itemStack.getType()== Material.RAW_COPPER)
                        itemStack.setType(Material.COPPER_INGOT);
                    if(itemStack.getType()== Material.RAW_IRON)
                        itemStack.setType(Material.IRON_INGOT);
                    if(itemStack.getType()== Material.RAW_GOLD)
                        itemStack.setType(Material.GOLD_INGOT);
                    if(itemStack.getType()== Material.COBBLESTONE)
                        itemStack.setType(Material.STONE);
                    if(itemStack.getType()== Material.NETHERRACK)
                        itemStack.setType(Material.NETHER_BRICK);
                    if(itemStack.getType()== Material.COBBLED_DEEPSLATE)
                        itemStack.setType(Material.DEEPSLATE);
                    if(itemStack.getType()== Material.NETHER_GOLD_ORE)
                        itemStack.setType(Material.GOLD_INGOT);
                    if(itemStack.getType()== Material.ANCIENT_DEBRIS)
                        itemStack.setType(Material.NETHERITE_SCRAP);
                    if(itemStack.getType()== Material.CLAY)
                        itemStack.setType(Material.TERRACOTTA);
                    if(itemStack.getType()== Material.SAND)
                        itemStack.setType(Material.GLASS);
                    if(itemStack.getType()== Material.CLAY_BALL)
                        itemStack.setType(Material.BRICK);
                }
                event.getBlock().setType(Material.AIR);
                if(xp>0)
                    ((ExperienceOrb)event.getBlock().getWorld().spawn(event.getBlock().getLocation(), ExperienceOrb.class)).setExperience(xp);
                for(ItemStack itemStack : drops){
                    event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(),itemStack);
                }
            }
        }

        if(MainClass.INSTANCE.getSkillHandler().getSkillPerkFor(event.getPlayer().getUniqueId().toString(),Skills.MINING)==3&&event.getPlayer().getGameMode()== GameMode.SURVIVAL){
            ItemStack extradrop=null;
            switch(event.getBlock().getType()){
                case COAL_ORE:
                case DEEPSLATE_COAL_ORE:
                    if(MainClass.INSTANCE.random.nextInt(3)==0)
                        extradrop = new ItemStack(Material.COAL,2);
                    break;
                case IRON_ORE:
                case DEEPSLATE_IRON_ORE:
                    if(MainClass.INSTANCE.random.nextInt(2)==0)
                        extradrop = new ItemStack(Material.RAW_IRON,1);
                    break;
                case COPPER_ORE:
                case DEEPSLATE_COPPER_ORE:
                    if(MainClass.INSTANCE.random.nextInt(2)==0)
                        extradrop = new ItemStack(Material.RAW_COPPER,1);
                    break;
                case GOLD_ORE:
                case DEEPSLATE_GOLD_ORE:
                    if(MainClass.INSTANCE.random.nextInt(2)==0)
                        extradrop = new ItemStack(Material.RAW_GOLD,1);
                    break;
                case DIAMOND_ORE:
                case DEEPSLATE_DIAMOND_ORE:
                    if(MainClass.INSTANCE.random.nextInt(2)==0)
                        extradrop = new ItemStack(Material.DIAMOND,1);
                    break;
                case REDSTONE_ORE:
                case DEEPSLATE_REDSTONE_ORE:
                    if(MainClass.INSTANCE.random.nextInt(4)==0)
                        extradrop = new ItemStack(Material.REDSTONE,8);
                    break;
                case LAPIS_ORE:
                case DEEPSLATE_LAPIS_ORE:
                    if(MainClass.INSTANCE.random.nextInt(4)==0)
                        extradrop = new ItemStack(Material.LAPIS_LAZULI,8);
                    break;
                default:
                    break;
            }
            if(extradrop!=null){
                event.getPlayer().sendMessage(ChatColor.GRAY+"extra drop: "+extradrop.getAmount()+"x "+extradrop.getType().toString());
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(),extradrop);
            }
        }

        if(MainClass.INSTANCE.getSkillHandler().getSkillPerkFor(event.getPlayer().getUniqueId().toString(),Skills.MINING)==2&&event.getPlayer().getGameMode()== GameMode.SURVIVAL&&!event.getPlayer().isSneaking()){
            if(isMineable(event.getBlock().getType())) {
                RayTraceResult result = event.getPlayer().rayTraceBlocks(6);
                if (result != null || result.getHitBlockFace() != null){
                    if (result.getHitBlockFace() == BlockFace.DOWN) {
                        breakIfIsMineable(event.getBlock().getRelative(BlockFace.UP));
                    }else if (result.getHitBlockFace() == BlockFace.UP) {
                        breakIfIsMineable(event.getBlock().getRelative(BlockFace.DOWN));
                    }else if (result.getHitBlockFace() == BlockFace.EAST) {
                        breakIfIsMineable(event.getBlock().getRelative(BlockFace.WEST));
                    }else if (result.getHitBlockFace() == BlockFace.WEST) {
                        breakIfIsMineable(event.getBlock().getRelative(BlockFace.EAST));
                    }else if (result.getHitBlockFace() == BlockFace.NORTH) {
                        breakIfIsMineable(event.getBlock().getRelative(BlockFace.SOUTH));
                    }else if (result.getHitBlockFace() == BlockFace.SOUTH) {
                        breakIfIsMineable(event.getBlock().getRelative(BlockFace.NORTH));
                    }
                }
            }
        }

    }

    private void breakIfIsMineable(Block block){
        if(isMineable(block.getType()))
            block.breakNaturally();
    }

    private boolean isMineable(Material material){
        return (material==Material.STONE
                ||material==Material.COBBLESTONE
                ||material==Material.DEEPSLATE
                ||material==Material.GRANITE
                ||material==Material.DIORITE
                ||material==Material.ANDESITE
                ||material==Material.NETHERRACK
                ||material==Material.BASALT
                ||material==Material.BLACKSTONE
                ||material==Material.COAL_ORE
                ||material==Material.IRON_ORE
                ||material==Material.DIAMOND_ORE
                ||material==Material.EMERALD_ORE
                ||material==Material.REDSTONE_ORE
                ||material==Material.LAPIS_ORE
                ||material==Material.COPPER_ORE
                ||material==Material.GOLD_ORE
                ||material==Material.DEEPSLATE_COAL_ORE
                ||material==Material.DEEPSLATE_GOLD_ORE
                ||material==Material.DEEPSLATE_IRON_ORE
                ||material==Material.DEEPSLATE_DIAMOND_ORE
                ||material==Material.DEEPSLATE_EMERALD_ORE
                ||material==Material.DEEPSLATE_REDSTONE_ORE
                ||material==Material.DEEPSLATE_LAPIS_ORE
                ||material==Material.DEEPSLATE_COPPER_ORE
                ||material==Material.TUFF);

    }

}
