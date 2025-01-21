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

public class MasteryMenu implements InventoryMenu {
    public static String getTitle() {
        return "Skill Mastery";
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

        ItemStack skillOverview = new ItemStack(Material.NETHER_STAR);
        ItemMeta skillOverviewMeta = skillOverview.getItemMeta();
        skillOverviewMeta.setDisplayName(""+ ChatColor.WHITE+ChatColor.BOLD+"Skill Mastery "+ChatColor.WHITE+ChatColor.BOLD+"["+SkillHandler.getLevelColor(sh.getSkillAverageFor(uuid))+ChatColor.BOLD+sh.getSkillAverageFor(uuid)+ChatColor.WHITE+ChatColor.BOLD+"]");
        skillOverviewMeta.setLore(List.of(ChatColor.GRAY+"Next Level: ("+(sh.getTotalLevelsFor(uuid)%6)+"/6)"));
        skillOverview.setItemMeta(skillOverviewMeta);
        menu.setItem(13,skillOverview);

        boolean perks10Unlocked = sh.getSkillAverageFor(uuid)>=10;
        boolean perks20Unlocked = sh.getSkillAverageFor(uuid)>=20;

        ItemStack skillPerk1 = new ItemStack(Material.GHAST_TEAR);
        ItemMeta skillPerk1Meta = skillPerk1.getItemMeta();
        skillPerk1Meta.setDisplayName(""+ ChatColor.GOLD+ChatColor.BOLD+"NIGHT VISION");
        skillPerk1Meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        skillPerk1Meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        skillPerk1Meta.setLore(List.of("Grants you permanent Night Vision",
                "",
                (!perks10Unlocked?ChatColor.DARK_RED+"Reach Mastery Level 10 to unlock this perk!":(sh.getMasteryPerkFor(uuid)==1?ChatColor.GOLD+"This perk is currently selected!":ChatColor.GREEN+"CLICK here to select this perk!"))));
        skillPerk1.setItemMeta(skillPerk1Meta);
        if(sh.getMasteryPerkFor(uuid)==1)
            skillPerk1.addUnsafeEnchantment(Enchantment.UNBREAKING,1);
        menu.setItem(28,skillPerk1);

        ItemStack skillPerk2 = new ItemStack(Material.EXPERIENCE_BOTTLE);
        ItemMeta skillPerk2Meta = skillPerk2.getItemMeta();
        skillPerk2Meta.setDisplayName(""+ ChatColor.GOLD+ChatColor.BOLD+"EXPERIENCED");
        skillPerk2Meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        skillPerk2Meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        skillPerk2Meta.setLore(List.of("Gain 1.5x XP (not Skill XP)",
                "",
                (!perks10Unlocked?ChatColor.DARK_RED+"Reach Mastery Level 10 to unlock this perk!":(sh.getMasteryPerkFor(uuid)==2?ChatColor.GOLD+"This perk is currently selected!":ChatColor.GREEN+"CLICK here to select this perk!"))));
        skillPerk2.setItemMeta(skillPerk2Meta);
        if(sh.getMasteryPerkFor(uuid)==2)
            skillPerk2.addUnsafeEnchantment(Enchantment.UNBREAKING,1);
        menu.setItem(30,skillPerk2);

        ItemStack skillPerk3 = new ItemStack(Material.TOTEM_OF_UNDYING);
        ItemMeta skillPerk3Meta = skillPerk3.getItemMeta();
        skillPerk3Meta.setDisplayName(""+ ChatColor.GOLD+ChatColor.BOLD+"SECOND CHANCE");
        skillPerk3Meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        skillPerk3Meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        skillPerk3Meta.setLore(List.of("Upon death get teleported backto your spawnpoint instead",
                "",
                (!perks20Unlocked?ChatColor.DARK_RED+"Reach Mastery Level 20 to unlock this perk!":(sh.getMasteryPerkFor(uuid)==3?ChatColor.GOLD+"This perk is currently selected!":ChatColor.GREEN+"CLICK here to select this perk!"))));
        skillPerk3.setItemMeta(skillPerk3Meta);
        if(sh.getMasteryPerkFor(uuid)==3)
            skillPerk3.addUnsafeEnchantment(Enchantment.UNBREAKING,1);
        menu.setItem(32,skillPerk3);

        ItemStack skillPerk4 = new ItemStack(Material.RAW_IRON);
        ItemMeta skillPerk4Meta = skillPerk4.getItemMeta();
        skillPerk4Meta.setDisplayName(""+ ChatColor.GOLD+ChatColor.BOLD+"MAGNETIZED");
        skillPerk4Meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        skillPerk4Meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        skillPerk4Meta.setLore(List.of("Automatically pick up all items and XP orbs within 10 blocks",
                "",
                (!perks10Unlocked?ChatColor.DARK_RED+"Reach Mastery Level 10 to unlock this perk!":(sh.getMasteryPerkFor(uuid)==4?ChatColor.GOLD+"This perk is currently selected!":ChatColor.GREEN+"CLICK here to select this perk!"))));
        skillPerk4.setItemMeta(skillPerk4Meta);
        if(sh.getMasteryPerkFor(uuid)==4)
            skillPerk4.addUnsafeEnchantment(Enchantment.UNBREAKING,1);
        menu.setItem(34,skillPerk4);



        return menu;
    }

    @Override
    public void handleEvent(InventoryClickEvent event) {

        event.setCancelled(true);

        switch(event.getRawSlot()){
            case 28:
                event.getWhoClicked().closeInventory();
                if(MainClass.INSTANCE.getSkillHandler().getSkillAverageFor(event.getWhoClicked().getUniqueId().toString())>=10){
                    MainClass.INSTANCE.getSkillHandler().setMasteryPerkFor(event.getWhoClicked().getUniqueId().toString(),1);
                    event.getWhoClicked().sendMessage(ChatColor.GREEN+"MASTERY perk 1 selected!");
                }else{
                    event.getWhoClicked().sendMessage(ChatColor.DARK_RED+"Reach Mastery Level 10 to unlock this perk!");
                }

                break;
            case 30:
                event.getWhoClicked().closeInventory();
                if(MainClass.INSTANCE.getSkillHandler().getSkillAverageFor(event.getWhoClicked().getUniqueId().toString())>=10){
                    MainClass.INSTANCE.getSkillHandler().setMasteryPerkFor(event.getWhoClicked().getUniqueId().toString(),2);
                    event.getWhoClicked().sendMessage(ChatColor.GREEN+"MASTERY perk 2 selected!");
                }else{
                    event.getWhoClicked().sendMessage(ChatColor.DARK_RED+"Reach Mastery Level 10 to unlock this perk!");
                }
                break;
            case 32:
                event.getWhoClicked().closeInventory();
                if(MainClass.INSTANCE.getSkillHandler().getSkillAverageFor(event.getWhoClicked().getUniqueId().toString())>=20){
                    MainClass.INSTANCE.getSkillHandler().setMasteryPerkFor(event.getWhoClicked().getUniqueId().toString(),3);
                    event.getWhoClicked().sendMessage(ChatColor.GREEN+"MASTERY perk 3 selected!");
                }else{
                    event.getWhoClicked().sendMessage(ChatColor.DARK_RED+"Reach Mastery Level 20 to unlock this perk!");
                }
                break;
            case 34:
                event.getWhoClicked().closeInventory();
                if(MainClass.INSTANCE.getSkillHandler().getSkillAverageFor(event.getWhoClicked().getUniqueId().toString())>=10){
                    MainClass.INSTANCE.getSkillHandler().setMasteryPerkFor(event.getWhoClicked().getUniqueId().toString(),4);
                    event.getWhoClicked().sendMessage(ChatColor.GREEN+"MASTERY perk 4 selected!");
                }else{
                    event.getWhoClicked().sendMessage(ChatColor.DARK_RED+"Reach Mastery Level 10 to unlock this perk!");
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
