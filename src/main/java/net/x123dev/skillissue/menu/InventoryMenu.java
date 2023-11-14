package net.x123dev.skillissue.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public interface InventoryMenu {

    public abstract Inventory build(Player player);

    public abstract void handleEvent(InventoryClickEvent event);


}
