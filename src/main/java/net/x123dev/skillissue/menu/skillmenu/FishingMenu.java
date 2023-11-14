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

public class FishingMenu implements InventoryMenu {
    public static String getTitle() {
        return "Fishing";
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

        ItemStack miningOverview = new ItemStack(Material.COD);
        ItemMeta miningOverviewMeta = miningOverview.getItemMeta();
        miningOverviewMeta.setDisplayName(""+ ChatColor.GOLD+ChatColor.BOLD+"FISHING "+sh.getSkillLvlFor(uuid,Skills.FISHING));
        miningOverviewMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        miningOverviewMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        miningOverviewMeta.setLore(List.of(ChatColor.GRAY+"current Exp: "+(sh.getSkillExpFor(uuid,Skills.FISHING)-getExpByLvl(sh.getSkillLvlFor(uuid,Skills.FISHING)))+"/"+(getExpByLvl(sh.getSkillLvlFor(uuid,Skills.FISHING)+1)-getExpByLvl(sh.getSkillLvlFor(uuid,Skills.FISHING))),
                ChatColor.GRAY+"total Exp: "+sh.getSkillExpFor(uuid, Skills.FISHING),
                ChatColor.WHITE+"|"+("-".repeat((int)(getLvlProgressByExp(sh.getSkillExpFor(uuid,Skills.FISHING))*20)))+ChatColor.DARK_GRAY+("-".repeat((int)((1-getLvlProgressByExp(sh.getSkillExpFor(uuid,Skills.FISHING)))*20)))+ChatColor.WHITE+"| "+getFormattedLvlProgressByExp(sh.getSkillExpFor(uuid,Skills.FISHING)),
                "",
                "<current bonus placeholder>"));
        miningOverview.setItemMeta(miningOverviewMeta);
        menu.setItem(13,miningOverview);

        boolean perksUnlocked = sh.getSkillLvlFor(uuid,Skills.FISHING)>=10;

        ItemStack skillPerk1 = new ItemStack(Material.IRON_PICKAXE);
        ItemMeta skillPerk1Meta = skillPerk1.getItemMeta();
        skillPerk1Meta.setDisplayName(""+ ChatColor.GOLD+ChatColor.BOLD+"<PERK 1 PLACEHOLDER>");
        skillPerk1Meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        skillPerk1Meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        skillPerk1Meta.setLore(List.of("",
                "<current bonus placeholder>",
                "",
                (!perksUnlocked?ChatColor.DARK_RED+"Reach Level 10 to unlock perks for this skill!":(sh.getSkillPerkFor(uuid,Skills.FISHING)==1?ChatColor.GOLD+"This perk is currently selected!":ChatColor.GREEN+"CLICK here to select this perk!"))));
        skillPerk1.setItemMeta(skillPerk1Meta);
        if(sh.getSkillPerkFor(uuid,Skills.FISHING)==1)
            skillPerk1.addUnsafeEnchantment(Enchantment.DURABILITY,1);
        menu.setItem(29,skillPerk1);

        ItemStack skillPerk2 = new ItemStack(Material.IRON_PICKAXE);
        ItemMeta skillPerk2Meta = skillPerk2.getItemMeta();
        skillPerk2Meta.setDisplayName(""+ ChatColor.GOLD+ChatColor.BOLD+"<PERK 2 PLACEHOLDER>");
        skillPerk2Meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        skillPerk2Meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        skillPerk2Meta.setLore(List.of("",
                "<current bonus placeholder>",
                "",
                (!perksUnlocked?ChatColor.DARK_RED+"Reach Level 10 to unlock perks for this skill!":(sh.getSkillPerkFor(uuid,Skills.FISHING)==2?ChatColor.GOLD+"This perk is currently selected!":ChatColor.GREEN+"CLICK here to select this perk!"))));
        skillPerk2.setItemMeta(skillPerk2Meta);
        if(sh.getSkillPerkFor(uuid,Skills.FISHING)==2)
            skillPerk2.addUnsafeEnchantment(Enchantment.DURABILITY,1);
        menu.setItem(31,skillPerk2);

        ItemStack skillPerk3 = new ItemStack(Material.IRON_PICKAXE);
        ItemMeta skillPerk3Meta = skillPerk3.getItemMeta();
        skillPerk3Meta.setDisplayName(""+ ChatColor.GOLD+ChatColor.BOLD+"<PERK 3 PLACEHOLDER>");
        skillPerk3Meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        skillPerk3Meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        skillPerk3Meta.setLore(List.of("",
                "<current bonus placeholder>",
                "",
                (!perksUnlocked?ChatColor.DARK_RED+"Reach Level 10 to unlock perks for this skill!":(sh.getSkillPerkFor(uuid,Skills.FISHING)==3?ChatColor.GOLD+"This perk is currently selected!":ChatColor.GREEN+"CLICK here to select this perk!"))));
        skillPerk3.setItemMeta(skillPerk3Meta);
        if(sh.getSkillPerkFor(uuid,Skills.FISHING)==3)
            skillPerk3.addUnsafeEnchantment(Enchantment.DURABILITY,1);
        menu.setItem(33,skillPerk3);



        return menu;
    }

    @Override
    public void handleEvent(InventoryClickEvent event) {

        event.setCancelled(true);

        switch(event.getRawSlot()){
            case 29:
                event.getWhoClicked().closeInventory();
                if(MainClass.INSTANCE.getSkillHandler().getSkillLvlFor(event.getWhoClicked().getUniqueId().toString(),Skills.FISHING)>=10){
                    MainClass.INSTANCE.getSkillHandler().setSkillPerkFor(event.getWhoClicked().getUniqueId().toString(),Skills.FISHING,1);
                    event.getWhoClicked().sendMessage(ChatColor.GREEN+"FISHING perk 1 selected!");
                }else{
                    event.getWhoClicked().sendMessage(ChatColor.DARK_RED+"Reach Level 10 to unlock perks for this skill!");
                }

                break;
            case 31:
                event.getWhoClicked().closeInventory();
                if(MainClass.INSTANCE.getSkillHandler().getSkillLvlFor(event.getWhoClicked().getUniqueId().toString(),Skills.FISHING)>=10){
                    MainClass.INSTANCE.getSkillHandler().setSkillPerkFor(event.getWhoClicked().getUniqueId().toString(),Skills.FISHING,2);
                    event.getWhoClicked().sendMessage(ChatColor.GREEN+"FISHING perk 2 selected!");
                }else{
                    event.getWhoClicked().sendMessage(ChatColor.DARK_RED+"Reach Level 10 to unlock perks for this skill!");
                }
                break;
            case 33:
                event.getWhoClicked().closeInventory();
                if(MainClass.INSTANCE.getSkillHandler().getSkillLvlFor(event.getWhoClicked().getUniqueId().toString(),Skills.FISHING)>=10){
                    MainClass.INSTANCE.getSkillHandler().setSkillPerkFor(event.getWhoClicked().getUniqueId().toString(),Skills.FISHING,3);
                    event.getWhoClicked().sendMessage(ChatColor.GREEN+"FISHING perk 3 selected!");
                }else{
                    event.getWhoClicked().sendMessage(ChatColor.DARK_RED+"Reach Level 10 to unlock perks for this skill!");
                }
                break;
            default:
                break;
        }
    }
}
