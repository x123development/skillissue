package net.x123dev.skillissue;

import net.x123dev.skillissue.menu.skillmenu.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.InventoryView;

import java.util.HashMap;
import java.util.UUID;

public class MenuHandler implements Listener {

    private HashMap<UUID, InventoryView> lastOpenMenu;

    public MenuHandler(){
        lastOpenMenu=new HashMap<UUID, InventoryView>();
    }

    @EventHandler
    public void onSneakClick(PlayerInteractEvent event){
        if(event.getAction()== Action.LEFT_CLICK_AIR||event.getAction()==Action.LEFT_CLICK_BLOCK) return;
        if(event.getHand()== EquipmentSlot.OFF_HAND) return;
        if(!event.getPlayer().isSneaking()) return;
        openSkillOverview(event.getPlayer());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(!(lastOpenMenu.containsKey(event.getWhoClicked().getUniqueId())&&lastOpenMenu.get(event.getWhoClicked().getUniqueId())==event.getView()))
            return;

        switch(event.getView().getTitle()){
            case "Skills":
                new SkillOverviewMenu().handleEvent(event);
                break;
            case "Mining":
                new MiningMenu().handleEvent(event);
                break;
            case "Combat":
                new CombatMenu().handleEvent(event);
                break;
            case "Farming":
                new FarmingMenu().handleEvent(event);
                break;
            case "Fishing":
                new FishingMenu().handleEvent(event);
                break;
            case "Alchemy":
                new AlchemyMenu().handleEvent(event);
                break;
            case "Exploration":
                new ExplorationMenu().handleEvent(event);
                break;
        }
    }

    public void openSkillOverview(Player player){
        player.closeInventory();
        InventoryView view = player.openInventory(new SkillOverviewMenu().build(player));
        lastOpenMenu.put(player.getUniqueId(),view);
    }

    public void openSkillDetails(Player player, SkillHandler.Skills skill){
        player.closeInventory();
        player.sendMessage("trying to open "+skill+" menu");
        InventoryView view=null;
        switch (skill){
            case MINING:
                view = player.openInventory(new MiningMenu().build(player));
                break;
            case COMBAT:
                view = player.openInventory(new CombatMenu().build(player));
                break;
            case FARMING:
                view = player.openInventory(new FarmingMenu().build(player));
                break;
            case FISHING:
                view = player.openInventory(new FishingMenu().build(player));
                break;
            case ALCHEMY:
                view = player.openInventory(new AlchemyMenu().build(player));
                break;
            case EXPLORATION:
                view = player.openInventory(new ExplorationMenu().build(player));

                break;
        }
        if(view!=null)
            lastOpenMenu.put(player.getUniqueId(),view);
    }

}
