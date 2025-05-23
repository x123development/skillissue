package net.x123dev.skillissue.menu.skillmenu;

import net.x123dev.skillissue.MainClass;
import net.x123dev.skillissue.SkillHandler;
import net.x123dev.skillissue.menu.InventoryMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

import static net.x123dev.skillissue.SkillHandler.*;

public class ExplorationMenu implements InventoryMenu {
    public static String getTitle() {
        return "Exploration";
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

        ItemStack backArrow = new ItemStack(Material.SPECTRAL_ARROW);
        ItemMeta backArrowMeta = backArrow.getItemMeta();
        backArrowMeta.setDisplayName("go back");
        backArrowMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        backArrowMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        backArrow.setItemMeta(backArrowMeta);
        menu.setItem(0,backArrow);

        ItemStack miningOverview = new ItemStack(Material.COMPASS);
        ItemMeta miningOverviewMeta = miningOverview.getItemMeta();
        miningOverviewMeta.setDisplayName(""+ ChatColor.GOLD+ChatColor.BOLD+"EXPLORATION "+sh.getSkillLvlFor(uuid,Skills.EXPLORATION));
        miningOverviewMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        miningOverviewMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        miningOverviewMeta.setLore(List.of(ChatColor.GRAY+"current Exp: "+(sh.getSkillExpFor(uuid,Skills.EXPLORATION)-getExpByLvl(sh.getSkillLvlFor(uuid,Skills.EXPLORATION)))+"/"+(getExpByLvl(sh.getSkillLvlFor(uuid,Skills.EXPLORATION)+1)-getExpByLvl(sh.getSkillLvlFor(uuid,Skills.EXPLORATION))),
                ChatColor.GRAY+"total Exp: "+sh.getSkillExpFor(uuid, Skills.EXPLORATION),
                ChatColor.WHITE+"|"+("-".repeat((int)(getLvlProgressByExp(sh.getSkillExpFor(uuid,Skills.EXPLORATION))*20)))+ChatColor.DARK_GRAY+("-".repeat((int)((1-getLvlProgressByExp(sh.getSkillExpFor(uuid,Skills.EXPLORATION)))*20)))+ChatColor.WHITE+"| "+getFormattedLvlProgressByExp(sh.getSkillExpFor(uuid,Skills.EXPLORATION)),
                "",
                "Increases your base movement speed by "+(sh.getSkillLvlFor(uuid,Skills.EXPLORATION)>50?50:sh.getSkillLvlFor(uuid,Skills.EXPLORATION))+"%",
                "This perk improves with your exploration level"));
        miningOverview.setItemMeta(miningOverviewMeta);
        menu.setItem(13,miningOverview);

        boolean perksUnlocked = sh.getSkillLvlFor(uuid,Skills.EXPLORATION)>=10;

        ItemStack skillPerk1 = new ItemStack(Material.RABBIT_FOOT);
        ItemMeta skillPerk1Meta = skillPerk1.getItemMeta();
        skillPerk1Meta.setDisplayName(""+ ChatColor.GOLD+ChatColor.BOLD+"JUMP BOOST");
        skillPerk1Meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        skillPerk1Meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        skillPerk1Meta.setLore(List.of("Grants you permanent Jump Boost I",
                "",
                (!perksUnlocked?ChatColor.DARK_RED+"Reach Level 10 to unlock perks for this skill!":(sh.getSkillPerkFor(uuid,Skills.EXPLORATION)==1?ChatColor.GOLD+"This perk is currently selected!":ChatColor.GREEN+"CLICK here to select this perk!"))));
        skillPerk1.setItemMeta(skillPerk1Meta);
        if(sh.getSkillPerkFor(uuid,Skills.EXPLORATION)==1)
            skillPerk1.addUnsafeEnchantment(Enchantment.UNBREAKING,1);
        menu.setItem(29,skillPerk1);

        ItemStack skillPerk2 = new ItemStack(Material.ENDER_PEARL);
        ItemMeta skillPerk2Meta = skillPerk2.getItemMeta();
        skillPerk2Meta.setDisplayName(""+ ChatColor.GOLD+ChatColor.BOLD+"RENEWABLE PEARLS");
        skillPerk2Meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        skillPerk2Meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        skillPerk2Meta.setLore(List.of("Throwing Ender Pearls doesn't consume them",
                "",
                (!perksUnlocked?ChatColor.DARK_RED+"Reach Level 10 to unlock perks for this skill!":(sh.getSkillPerkFor(uuid,Skills.EXPLORATION)==2?ChatColor.GOLD+"This perk is currently selected!":ChatColor.GREEN+"CLICK here to select this perk!"))));
        skillPerk2.setItemMeta(skillPerk2Meta);
        if(sh.getSkillPerkFor(uuid,Skills.EXPLORATION)==2)
            skillPerk2.addUnsafeEnchantment(Enchantment.UNBREAKING,1);
        menu.setItem(31,skillPerk2);

        ItemStack skillPerk3 = new ItemStack(Material.FISHING_ROD);
        ItemMeta skillPerk3Meta = skillPerk3.getItemMeta();
        skillPerk3Meta.setDisplayName(""+ ChatColor.GOLD+ChatColor.BOLD+"I'M BATMAN!");
        skillPerk3Meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        skillPerk3Meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        skillPerk3Meta.setLore(List.of("Enables you to use a Fishing Rod as a Grappling Hook",
                "",
                (!perksUnlocked?ChatColor.DARK_RED+"Reach Level 10 to unlock perks for this skill!":(sh.getSkillPerkFor(uuid,Skills.EXPLORATION)==3?ChatColor.GOLD+"This perk is currently selected!":ChatColor.GREEN+"CLICK here to select this perk!"))));
        skillPerk3.setItemMeta(skillPerk3Meta);
        if(sh.getSkillPerkFor(uuid,Skills.EXPLORATION)==3)
            skillPerk3.addUnsafeEnchantment(Enchantment.UNBREAKING,1);
        menu.setItem(33,skillPerk3);



        return menu;
    }

    @Override
    public void handleEvent(InventoryClickEvent event) {

        event.setCancelled(true);

        switch(event.getRawSlot()){
            case 29:
                event.getWhoClicked().closeInventory();
                if(MainClass.INSTANCE.getSkillHandler().getSkillLvlFor(event.getWhoClicked().getUniqueId().toString(),Skills.EXPLORATION)>=10){
                    MainClass.INSTANCE.getSkillHandler().setSkillPerkFor(event.getWhoClicked().getUniqueId().toString(),Skills.EXPLORATION,1);
                    event.getWhoClicked().sendMessage(ChatColor.GREEN+"EXPLORATION perk 1 selected!");
                }else{
                    event.getWhoClicked().sendMessage(ChatColor.DARK_RED+"Reach Level 10 to unlock perks for this skill!");
                }

                break;
            case 31:
                event.getWhoClicked().closeInventory();
                if(MainClass.INSTANCE.getSkillHandler().getSkillLvlFor(event.getWhoClicked().getUniqueId().toString(),Skills.EXPLORATION)>=10){
                    MainClass.INSTANCE.getSkillHandler().setSkillPerkFor(event.getWhoClicked().getUniqueId().toString(),Skills.EXPLORATION,2);
                    event.getWhoClicked().sendMessage(ChatColor.GREEN+"EXPLORATION perk 2 selected!");
                }else{
                    event.getWhoClicked().sendMessage(ChatColor.DARK_RED+"Reach Level 10 to unlock perks for this skill!");
                }
                break;
            case 33:
                event.getWhoClicked().closeInventory();
                if(MainClass.INSTANCE.getSkillHandler().getSkillLvlFor(event.getWhoClicked().getUniqueId().toString(),Skills.EXPLORATION)>=10){
                    MainClass.INSTANCE.getSkillHandler().setSkillPerkFor(event.getWhoClicked().getUniqueId().toString(),Skills.EXPLORATION,3);
                    event.getWhoClicked().sendMessage(ChatColor.GREEN+"EXPLORATION perk 3 selected!");
                }else{
                    event.getWhoClicked().sendMessage(ChatColor.DARK_RED+"Reach Level 10 to unlock perks for this skill!");
                }
                break;
            case 0:
                MainClass.INSTANCE.getMenuHandler().openSkillOverview((Player) event.getWhoClicked());
                break;
            default:
                break;
        }
    }
}
