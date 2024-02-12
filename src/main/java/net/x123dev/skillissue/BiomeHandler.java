package net.x123dev.skillissue;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BiomeHandler extends BukkitRunnable{

    private HashMap<String,BiomeData> biomeData;
    String folderPath = System.getProperty("user.dir")+ File.separator+"plugins"+File.separator+"skillissue"+File.separator+"biomedata"+File.separator;
    public BiomeHandler(){
        biomeData=new HashMap<String,BiomeData>();
        new SaveRunnable().runTaskTimer(MainClass.INSTANCE,601,1200);
    }

    @Override
    public void run() {
        for(Player player:Bukkit.getOnlinePlayers()){
            checkBiomeFor(player,player.getWorld().getBiome(player.getLocation()).getKey().toString());
        }
    }


    public void loadFromFile(){
        biomeData.clear();
        File folderFile = new File(folderPath);
        MainClass.info("loading biomes from file: "+folderPath);
        folderFile.mkdirs();
        File[] dataFiles = folderFile.listFiles();
        for(int i=0;i<dataFiles.length;i++){
            BiomeData loaded = getBiomeDataFromFile(dataFiles[i]);
            biomeData.put(dataFiles[i].getName(),loaded);
        }
    }

    public void saveToFile(){
        for(Map.Entry<String,BiomeData> entry : biomeData.entrySet()){
            saveBiomeDataToFile(new File(folderPath+entry.getKey()), entry.getValue());
        }
    }

    private BiomeData getBiomeDataFromFile(File file){
        BiomeData data = new BiomeData();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line=reader.readLine();
            while(line!=null&&!line.equals("")){
                data.visitedBiomes.add(line);
                line= reader.readLine();
            }

            reader.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        return data;
    }

    private void saveBiomeDataToFile(File file,BiomeData biomeData1){
        try{
            if(file.exists()) file.delete();
            file.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for(String biome:biomeData1.visitedBiomes){
                writer.write(biome);
                writer.newLine();
            }
            writer.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public class BiomeData {

        public ArrayList<String> visitedBiomes;

        public BiomeData(){
            visitedBiomes=new ArrayList<>();
        }

    }



    public void checkBiomeFor(Player player,String biomeKey){
        if(biomeData.containsKey(player.getUniqueId().toString())){
            if(!biomeData.get(player.getUniqueId().toString()).visitedBiomes.contains(biomeKey)){
                biomeData.get(player.getUniqueId().toString()).visitedBiomes.add(biomeKey);
                MainClass.INSTANCE.getSkillHandler().addSkillExpFor(player.getUniqueId().toString(), SkillHandler.Skills.EXPLORATION,3000);
                player.sendMessage(ChatColor.GREEN+"You gained some EXPLORATION Experience for finding a new biome!");
            }
        }else{
            addPlayer(player);
        }
    }

    public void addPlayer(Player player){
        if(!biomeData.containsKey(player.getUniqueId().toString())){
            BiomeData newPlayerData = new BiomeData();
            newPlayerData.visitedBiomes.add(player.getWorld().getBiome(player.getLocation()).getKey().toString());
            biomeData.put(player.getUniqueId().toString(),newPlayerData);
            MainClass.info("new biomedata created for: "+player.getUniqueId().toString());
        }
    }


    private class SaveRunnable extends BukkitRunnable{
        @Override
        public void run() {
            saveToFile();
        }
    }


}
