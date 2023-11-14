package net.x123dev.skillissue.event;

import net.x123dev.skillissue.MainClass;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class GeneralEvents implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        MainClass.INSTANCE.getSkillHandler().addPlayer(event.getPlayer().getUniqueId().toString());
    }
}
