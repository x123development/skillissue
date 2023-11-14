package net.x123dev.skillissue;

import net.x123dev.skillissue.event.GeneralEvents;
import net.x123dev.skillissue.skills.MiningSkill;
import org.bukkit.plugin.java.JavaPlugin;

public final class MainClass extends JavaPlugin {

    public static MainClass INSTANCE;
    private GeneralEvents eventHandler;
    private SkillHandler skillHandler;

    public MainClass(){
        INSTANCE=this;
    }

    @Override
    public void onEnable() {
        skillHandler=new SkillHandler();
        skillHandler.loadFromFile();
        skillHandler.runTaskTimer(this,20,20);
        eventHandler=new GeneralEvents();
        getServer().getPluginManager().registerEvents(eventHandler,this);
        MiningSkill.init();
        getLogger().info("enabled!");
    }

    @Override
    public void onDisable() {
        skillHandler.saveToFile();
        getLogger().info("disabled!");
    }

    public SkillHandler getSkillHandler(){
        return skillHandler;
    }

    public GeneralEvents getEventHandler(){
        return eventHandler;
    }

    public static void info(String info){
        INSTANCE.getLogger().info(info);
    }
}
