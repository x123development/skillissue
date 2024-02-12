package net.x123dev.skillissue.menu.skillmenu;

import net.x123dev.skillissue.MainClass;
import static net.x123dev.skillissue.SkillHandler.*;
import net.x123dev.skillissue.SkillHandler;
import net.x123dev.skillissue.menu.InventoryMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class SkillOverviewMenu implements InventoryMenu {

    public static String getTitle() {
        return "Skills";
    }

    @Override
    public Inventory build(Player player) {

        Inventory menu = Bukkit.createInventory(null,45,getTitle());

        ItemStack filler = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta fillerMeta = filler.getItemMeta();
        fillerMeta.setDisplayName(" ");
        fillerMeta.addItemFlags(ItemFlag.HIDE_DYE);
        filler.setItemMeta(fillerMeta);

        for(int i=0;i<45;i++){
            menu.setItem(i,filler.clone());
        }

        SkillHandler sh = MainClass.INSTANCE.getSkillHandler();
        String uuid = player.getUniqueId().toString();

        ItemStack skillOverview = new ItemStack(Material.NETHER_STAR);
        ItemMeta skillOverviewMeta = skillOverview.getItemMeta();
        skillOverviewMeta.setDisplayName(""+ ChatColor.WHITE+ChatColor.BOLD+"Skill Mastery "+ChatColor.WHITE+ChatColor.BOLD+"["+SkillHandler.getLevelColor(sh.getSkillAverageFor(uuid))+ChatColor.BOLD+sh.getSkillAverageFor(uuid)+ChatColor.WHITE+ChatColor.BOLD+"]");
        skillOverviewMeta.setLore(List.of("",
                ChatColor.GREEN+"CLICK here for more info!"));
        skillOverview.setItemMeta(skillOverviewMeta);
        menu.setItem(13,skillOverview);

        ItemStack combatOverview = new ItemStack(Material.IRON_SWORD);
        ItemMeta combatOverviewMeta = combatOverview.getItemMeta();
        combatOverviewMeta.setDisplayName(""+ ChatColor.GOLD+ChatColor.BOLD+"COMBAT "+sh.getSkillLvlFor(uuid,Skills.COMBAT));
        combatOverviewMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        combatOverviewMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        combatOverviewMeta.setLore(List.of(ChatColor.GRAY+"current Exp: "+(sh.getSkillExpFor(uuid,Skills.COMBAT)-getExpByLvl(sh.getSkillLvlFor(uuid,Skills.COMBAT)))+"/"+(getExpByLvl(sh.getSkillLvlFor(uuid,Skills.COMBAT)+1)-getExpByLvl(sh.getSkillLvlFor(uuid,Skills.COMBAT))),
                ChatColor.GRAY+"total Exp: "+sh.getSkillExpFor(uuid, Skills.COMBAT),
                ChatColor.WHITE+"|"+("-".repeat((int)(getLvlProgressByExp(sh.getSkillExpFor(uuid,Skills.COMBAT))*20)))+ChatColor.DARK_GRAY+("-".repeat((int)((1-getLvlProgressByExp(sh.getSkillExpFor(uuid,Skills.COMBAT)))*20)))+ChatColor.WHITE+"| "+getFormattedLvlProgressByExp(sh.getSkillExpFor(uuid,Skills.COMBAT)),
                "",
                ChatColor.GREEN+"CLICK here for more info!"));
        combatOverview.setItemMeta(combatOverviewMeta);
        menu.setItem(28,combatOverview);

        ItemStack miningOverview = new ItemStack(Material.IRON_PICKAXE);
        ItemMeta miningOverviewMeta = miningOverview.getItemMeta();
        miningOverviewMeta.setDisplayName(""+ ChatColor.GOLD+ChatColor.BOLD+"MINING "+sh.getSkillLvlFor(uuid,Skills.MINING));
        miningOverviewMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        miningOverviewMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        miningOverviewMeta.setLore(List.of(ChatColor.GRAY+"current Exp: "+(sh.getSkillExpFor(uuid,Skills.MINING)-getExpByLvl(sh.getSkillLvlFor(uuid,Skills.MINING)))+"/"+(getExpByLvl(sh.getSkillLvlFor(uuid,Skills.MINING)+1)-getExpByLvl(sh.getSkillLvlFor(uuid,Skills.MINING))),
                ChatColor.GRAY+"total Exp: "+sh.getSkillExpFor(uuid, Skills.MINING),
                ChatColor.WHITE+"|"+("-".repeat((int)(getLvlProgressByExp(sh.getSkillExpFor(uuid,Skills.MINING))*20)))+ChatColor.DARK_GRAY+("-".repeat((int)((1-getLvlProgressByExp(sh.getSkillExpFor(uuid,Skills.MINING)))*20)))+ChatColor.WHITE+"| "+getFormattedLvlProgressByExp(sh.getSkillExpFor(uuid,Skills.MINING)),
                "",
                ChatColor.GREEN+"CLICK here for more info!"));
        miningOverview.setItemMeta(miningOverviewMeta);
        menu.setItem(29,miningOverview);

        ItemStack farmingOverview = new ItemStack(Material.WHEAT);
        ItemMeta farmingOverviewMeta = farmingOverview.getItemMeta();
        farmingOverviewMeta.setDisplayName(""+ ChatColor.GOLD+ChatColor.BOLD+"FARMING "+sh.getSkillLvlFor(uuid,Skills.FARMING));
        farmingOverviewMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        farmingOverviewMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        farmingOverviewMeta.setLore(List.of(ChatColor.GRAY+"current Exp: "+(sh.getSkillExpFor(uuid,Skills.FARMING)-getExpByLvl(sh.getSkillLvlFor(uuid,Skills.FARMING)))+"/"+(getExpByLvl(sh.getSkillLvlFor(uuid,Skills.FARMING)+1)-getExpByLvl(sh.getSkillLvlFor(uuid,Skills.FARMING))),
                ChatColor.GRAY+"total Exp: "+sh.getSkillExpFor(uuid, Skills.FARMING),
                ChatColor.WHITE+"|"+("-".repeat((int)(getLvlProgressByExp(sh.getSkillExpFor(uuid,Skills.FARMING))*20)))+ChatColor.DARK_GRAY+("-".repeat((int)((1-getLvlProgressByExp(sh.getSkillExpFor(uuid,Skills.FARMING)))*20)))+ChatColor.WHITE+"| "+getFormattedLvlProgressByExp(sh.getSkillExpFor(uuid,Skills.FARMING)),
                "",
                ChatColor.GREEN+"CLICK here for more info!"));
        farmingOverview.setItemMeta(farmingOverviewMeta);
        menu.setItem(30,farmingOverview);

        ItemStack fishingOverview = new ItemStack(Material.COD);
        ItemMeta fishingOverviewMeta = fishingOverview.getItemMeta();
        fishingOverviewMeta.setDisplayName(""+ ChatColor.GOLD+ChatColor.BOLD+"FISHING "+sh.getSkillLvlFor(uuid,Skills.FISHING));
        fishingOverviewMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        fishingOverviewMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        fishingOverviewMeta.setLore(List.of(ChatColor.GRAY+"current Exp: "+(sh.getSkillExpFor(uuid,Skills.FISHING)-getExpByLvl(sh.getSkillLvlFor(uuid,Skills.FISHING)))+"/"+(getExpByLvl(sh.getSkillLvlFor(uuid,Skills.FISHING)+1)-getExpByLvl(sh.getSkillLvlFor(uuid,Skills.FISHING))),
                ChatColor.GRAY+"total Exp: "+sh.getSkillExpFor(uuid, Skills.FISHING),
                ChatColor.WHITE+"|"+("-".repeat((int)(getLvlProgressByExp(sh.getSkillExpFor(uuid,Skills.FISHING))*20)))+ChatColor.DARK_GRAY+("-".repeat((int)((1-getLvlProgressByExp(sh.getSkillExpFor(uuid,Skills.FISHING)))*20)))+ChatColor.WHITE+"| "+getFormattedLvlProgressByExp(sh.getSkillExpFor(uuid,Skills.FISHING)),
                "",
                ChatColor.GREEN+"CLICK here for more info!"));
        fishingOverview.setItemMeta(fishingOverviewMeta);
        menu.setItem(32,fishingOverview);

        ItemStack alchemyOverview = new ItemStack(Material.GLASS_BOTTLE);
        ItemMeta alchemyOverviewMeta = alchemyOverview.getItemMeta();
        alchemyOverviewMeta.setDisplayName(""+ ChatColor.GOLD+ChatColor.BOLD+"ALCHEMY "+sh.getSkillLvlFor(uuid,Skills.ALCHEMY));
        alchemyOverviewMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        alchemyOverviewMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        alchemyOverviewMeta.setLore(List.of(ChatColor.GRAY+"current Exp: "+(sh.getSkillExpFor(uuid,Skills.ALCHEMY)-getExpByLvl(sh.getSkillLvlFor(uuid,Skills.ALCHEMY)))+"/"+(getExpByLvl(sh.getSkillLvlFor(uuid,Skills.ALCHEMY)+1)-getExpByLvl(sh.getSkillLvlFor(uuid,Skills.ALCHEMY))),
                ChatColor.GRAY+"total Exp: "+sh.getSkillExpFor(uuid, Skills.ALCHEMY),
                ChatColor.WHITE+"|"+("-".repeat((int)(getLvlProgressByExp(sh.getSkillExpFor(uuid,Skills.ALCHEMY))*20)))+ChatColor.DARK_GRAY+("-".repeat((int)((1-getLvlProgressByExp(sh.getSkillExpFor(uuid,Skills.ALCHEMY)))*20)))+ChatColor.WHITE+"| "+getFormattedLvlProgressByExp(sh.getSkillExpFor(uuid,Skills.ALCHEMY)),
                "",
                ChatColor.GREEN+"CLICK here for more info!"));
        alchemyOverview.setItemMeta(alchemyOverviewMeta);
        menu.setItem(33,alchemyOverview);

        ItemStack explorationOverview = new ItemStack(Material.COMPASS);
        ItemMeta explorationOverviewMeta = explorationOverview.getItemMeta();
        explorationOverviewMeta.setDisplayName(""+ ChatColor.GOLD+ChatColor.BOLD+"EXPLORATION "+sh.getSkillLvlFor(uuid,Skills.EXPLORATION));
        explorationOverviewMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        explorationOverviewMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        explorationOverviewMeta.setLore(List.of(ChatColor.GRAY+"current Exp: "+(sh.getSkillExpFor(uuid,Skills.EXPLORATION)-getExpByLvl(sh.getSkillLvlFor(uuid,Skills.EXPLORATION)))+"/"+(getExpByLvl(sh.getSkillLvlFor(uuid,Skills.EXPLORATION)+1)-getExpByLvl(sh.getSkillLvlFor(uuid,Skills.EXPLORATION))),
                ChatColor.GRAY+"total Exp: "+sh.getSkillExpFor(uuid, Skills.EXPLORATION),
                ChatColor.WHITE+"|"+("-".repeat((int)(getLvlProgressByExp(sh.getSkillExpFor(uuid,Skills.EXPLORATION))*20)))+ChatColor.DARK_GRAY+("-".repeat((int)((1-getLvlProgressByExp(sh.getSkillExpFor(uuid,Skills.EXPLORATION)))*20)))+ChatColor.WHITE+"| "+getFormattedLvlProgressByExp(sh.getSkillExpFor(uuid,Skills.EXPLORATION)),
                "",
                ChatColor.GREEN+"CLICK here for more info!"));
        explorationOverview.setItemMeta(explorationOverviewMeta);
        menu.setItem(34,explorationOverview);

        ItemStack expSoundToggle = new ItemStack(sh.getSettingFor(uuid,"expSound")?Material.GREEN_DYE:Material.RED_DYE);
        ItemMeta expSoundToggleMeta = expSoundToggle.getItemMeta();
        expSoundToggleMeta.setDisplayName(""+ ChatColor.WHITE+"Exp Sounds are currently "+(sh.getSettingFor(uuid,"expSound")?ChatColor.GREEN+"ENABLED":ChatColor.RED+"DISABLED"));
        expSoundToggleMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        expSoundToggleMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        expSoundToggleMeta.setLore(List.of(ChatColor.YELLOW+"CLICK here to toggle!"));
        expSoundToggle.setItemMeta(expSoundToggleMeta);
        menu.setItem(8,expSoundToggle);

        ItemStack skillToggle = new ItemStack(sh.getSettingFor(uuid,"skillsDisabled")?Material.RED_DYE:Material.GREEN_DYE);
        ItemMeta skillToggleMeta = skillToggle.getItemMeta();
        skillToggleMeta.setDisplayName(""+ ChatColor.WHITE+"Skill Perks are currently "+(sh.getSettingFor(uuid,"skillsDisabled")?ChatColor.RED+"DISABLED":ChatColor.GREEN+"ENABLED"));
        skillToggleMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        skillToggleMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        skillToggleMeta.setLore(List.of(ChatColor.YELLOW+"CLICK here to toggle!"));
        skillToggle.setItemMeta(skillToggleMeta);
        menu.setItem(7,skillToggle);


        return menu;
    }

    @Override
    public void handleEvent(InventoryClickEvent event) {

        event.setCancelled(true);

        switch(event.getRawSlot()){
            case 13:
                MainClass.INSTANCE.getMenuHandler().openMasteryDetails((Player)event.getWhoClicked());
                break;
            case 28:
                MainClass.INSTANCE.getMenuHandler().openSkillDetails((Player)event.getWhoClicked(),Skills.COMBAT);
                break;
            case 29:
                MainClass.INSTANCE.getMenuHandler().openSkillDetails((Player)event.getWhoClicked(),Skills.MINING);
                break;
            case 30:
                MainClass.INSTANCE.getMenuHandler().openSkillDetails((Player)event.getWhoClicked(),Skills.FARMING);
                break;
            case 32:
                MainClass.INSTANCE.getMenuHandler().openSkillDetails((Player)event.getWhoClicked(),Skills.FISHING);
                break;
            case 33:
                MainClass.INSTANCE.getMenuHandler().openSkillDetails((Player)event.getWhoClicked(),Skills.ALCHEMY);
                break;
            case 34:
                MainClass.INSTANCE.getMenuHandler().openSkillDetails((Player)event.getWhoClicked(),Skills.EXPLORATION);
                break;
            case 8:
                MainClass.INSTANCE.getSkillHandler().setSettingFor(event.getWhoClicked().getUniqueId().toString(),"expSound",!MainClass.INSTANCE.getSkillHandler().getSettingFor(event.getWhoClicked().getUniqueId().toString(),"expSound"));
                MainClass.INSTANCE.getMenuHandler().openSkillOverview((Player) event.getWhoClicked());
                break;
            case 7:
                MainClass.INSTANCE.getSkillHandler().setSettingFor(event.getWhoClicked().getUniqueId().toString(),"skillsDisabled",!MainClass.INSTANCE.getSkillHandler().getSettingFor(event.getWhoClicked().getUniqueId().toString(),"skillsDisabled"));
                MainClass.INSTANCE.getMenuHandler().openSkillOverview((Player) event.getWhoClicked());
                break;
            default:
                break;
        }
    }
}
