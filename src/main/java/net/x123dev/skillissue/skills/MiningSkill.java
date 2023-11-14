package net.x123dev.skillissue.skills;

import net.x123dev.skillissue.MainClass;
import net.x123dev.skillissue.SkillHandler;
import net.x123dev.skillissue.SkillHandler.Skills;
import org.bukkit.enchantments.Enchantment;
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
                amount=5;
                break;
            case AMETHYST_CLUSTER:
                amount=5;
                break;
            case IRON_ORE:
            case DEEPSLATE_IRON_ORE:
                amount=10;
                break;
            case GOLD_ORE:
            case DEEPSLATE_GOLD_ORE:
                amount=20;
                break;
            case REDSTONE_ORE:
            case DEEPSLATE_REDSTONE_ORE:
                amount=10;
                break;
            case LAPIS_ORE:
            case DEEPSLATE_LAPIS_ORE:
                amount=15;
                break;
            case DIAMOND_ORE:
            case DEEPSLATE_DIAMOND_ORE:
                amount=50;
                break;
            case EMERALD_ORE:
            case DEEPSLATE_EMERALD_ORE:
                amount=50;
                break;
            case COPPER_ORE:
            case DEEPSLATE_COPPER_ORE:
                amount=5;
                break;
            case COAL_ORE:
            case DEEPSLATE_COAL_ORE:
                amount=5;
                break;
            case NETHER_QUARTZ_ORE:
                amount=5;
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
        MainClass.INSTANCE.getSkillHandler().addSkillExpFor(event.getPlayer().getUniqueId().toString(), Skills.MINING, amount);
    }

}
