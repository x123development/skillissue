package net.x123dev.skillissue;

import net.x123dev.skillissue.command.SkillCommand;
import net.x123dev.skillissue.event.GeneralEvents;
import net.x123dev.skillissue.skills.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.Random;

public final class MainClass extends JavaPlugin{

    public static MainClass INSTANCE;
    private GeneralEvents eventHandler;
    private SkillHandler skillHandler;
    private MenuHandler menuHandler;
    public Random random;

    public MainClass(){
        INSTANCE=this;
    }

    @Override
    public void onEnable() {
        random=new Random();
        skillHandler=new SkillHandler();
        skillHandler.loadFromFile();
        skillHandler.runTaskTimer(this,20,20);
        eventHandler=new GeneralEvents();
        menuHandler=new MenuHandler();
        getServer().getPluginManager().registerEvents(eventHandler,this);
        getServer().getPluginManager().registerEvents(menuHandler,this);
        MiningSkill.init();
        CombatSkill.init();
        FishingSkill.init();
        FarmingSkill.init();
        AlchemySkill.init();
        MasterySkill.init();
        ExplorationSkill.init();
        Objects.requireNonNull(Bukkit.getPluginCommand("skills")).setExecutor(new SkillCommand());
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
    public MenuHandler getMenuHandler(){
        return menuHandler;
    }

    public GeneralEvents getEventHandler(){
        return eventHandler;
    }

    public static void info(String info){
        INSTANCE.getLogger().info(info);
    }
}
