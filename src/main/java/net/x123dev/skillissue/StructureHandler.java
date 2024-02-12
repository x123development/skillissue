package net.x123dev.skillissue;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class StructureHandler implements Listener {

    private ArrayList<Location> spawnerData;
    String folderPath = System.getProperty("user.dir")+ File.separator+"plugins"+File.separator+"skillissue"+File.separator+"structuredata"+File.separator;
    public StructureHandler(){
        spawnerData=new ArrayList<>();
        new SaveRunnable().runTaskTimer(MainClass.INSTANCE,302,1200);
    }


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        if(event.getAction()!= Action.RIGHT_CLICK_BLOCK) return;
        if(event.getHand()!= EquipmentSlot.HAND) return;
        if(Objects.requireNonNull(event.getClickedBlock()).getType()!= Material.SPAWNER) return;
        checkSpawnerFor(event.getPlayer(), Objects.requireNonNull(event.getClickedBlock()).getLocation());
    }


    public void loadFromFile(){
        spawnerData.clear();
        File folderFile = new File(folderPath);
        MainClass.info("loading structures from file: "+folderPath);
        folderFile.mkdirs();
        File[] dataFiles = folderFile.listFiles();
        for(int i=0;i<dataFiles.length;i++){
            if(dataFiles[i].getName().equals("spawners")){
                ArrayList<Location> loaded = getSpawnerDataFromFile(dataFiles[i]);
                spawnerData= new ArrayList<>(List.copyOf(loaded));
            }
        }
    }

    public void saveToFile(){
        for(Location entry : spawnerData){
            saveSpawnerDataToFile(new File(folderPath+"spawners"));
        }
    }

    private ArrayList<Location> getSpawnerDataFromFile(File file){
        ArrayList<Location> spawners=new ArrayList<>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line=reader.readLine();
            while(line!=null&&!line.equals("")){
                String[] coords = line.substring(line.indexOf(":")+1).split(";");
                //Bukkit.getLogger().info("trying to parse: "+line.substring(0,line.indexOf(":"))+"  "+coords[0]+"  "+coords[1]+"  "+coords[2]);
                Location lineLocation = new Location(Bukkit.getWorld(line.substring(0,line.indexOf(":"))),coords[0].startsWith("-")?-Double.parseDouble(coords[0].substring(1)):Double.parseDouble(coords[0]),coords[1].startsWith("-")?-Double.parseDouble(coords[1].substring(1)):Double.parseDouble(coords[1]),coords[2].startsWith("-")?-Double.parseDouble(coords[2].substring(1)):Double.parseDouble(coords[2]));
                spawners.add(lineLocation);
                line= reader.readLine();
            }
            reader.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        return spawners;
    }

    private void saveSpawnerDataToFile(File file){
        try{
            if(file.exists()) file.delete();
            file.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for(Location spawner:spawnerData){
                writer.write(spawner.getWorld().getName()+":"+spawner.getX()+";"+spawner.getY()+";"+spawner.getZ());
                writer.newLine();
            }
            writer.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }




    public void checkSpawnerFor(Player player,Location spawner){
        if(spawnerData.contains(spawner)){
            player.sendMessage(ChatColor.RED+"This spawner has already been found!");
        }else{
            spawnerData.add(spawner);
            MainClass.INSTANCE.getSkillHandler().addSkillExpFor(player.getUniqueId().toString(), SkillHandler.Skills.EXPLORATION,2500);
            player.sendMessage(ChatColor.GREEN+"You gained some EXPLORATION Experience for finding a new spawner!");
        }
    }

    private class SaveRunnable extends BukkitRunnable{
        @Override
        public void run() {
            saveToFile();
        }
    }


}
