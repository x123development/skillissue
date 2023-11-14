package net.x123dev.skillissue;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SkillHandler extends BukkitRunnable {

    private HashMap<String,PlayerData> playerData;
    String folderPath = System.getProperty("user.dir")+ File.separator+"plugins"+File.separator+"skillissue"+File.separator+"playerdata"+File.separator;
    public SkillHandler(){
        playerData=new HashMap<String,PlayerData>();
        new SaveRunnable().runTaskTimer(MainClass.INSTANCE,101,1200);
    }

    @Override
    public void run() {

    }



    public void loadFromFile(){
        playerData.clear();
        File folderFile = new File(folderPath);
        MainClass.info("loading from file: "+folderPath);
        folderFile.mkdirs();
        File[] dataFiles = folderFile.listFiles();
        for(int i=0;i<dataFiles.length;i++){
            PlayerData loaded = getPlayerDataFromFile(dataFiles[i]);
            playerData.put(loaded.uuid,loaded);
        }
    }

    public void saveToFile(){
        for(Map.Entry<String,PlayerData> entry : playerData.entrySet()){
            savePlayerDataToFile(new File(folderPath+entry.getKey()), entry.getValue());
        }
    }

    private PlayerData getPlayerDataFromFile(File file){
        PlayerData data = new PlayerData();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line=reader.readLine();
            while(line!=null&&!line.equals("")){
                switch (line.substring(0,line.indexOf("="))){
                    case "UUID":
                        data.uuid=line.substring(line.indexOf("=")+1);
                        break;
                    case "combatExp":
                        data.combatExp=Long.parseLong(line.substring(line.indexOf("=")+1));
                        break;
                    case "miningExp":
                        data.miningExp=Long.parseLong(line.substring(line.indexOf("=")+1));
                        break;
                    case "fishingExp":
                        data.fishingExp=Long.parseLong(line.substring(line.indexOf("=")+1));
                        break;
                    case "farmingExp":
                        data.farmingExp=Long.parseLong(line.substring(line.indexOf("=")+1));
                        break;
                    case "alchemyExp":
                        data.alchemyExp=Long.parseLong(line.substring(line.indexOf("=")+1));
                        break;
                    case "exploringExp":
                        data.exploringExp=Long.parseLong(line.substring(line.indexOf("=")+1));
                        break;
                }
                line= reader.readLine();
            }

            reader.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        return data;
    }

    private void savePlayerDataToFile(File file,PlayerData playerData){
        try{
            if(file.exists()) file.delete();
            file.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write("UUID="+playerData.uuid);
            writer.newLine();
            writer.write("combatExp="+playerData.combatExp);
            writer.newLine();
            writer.write("farmingExp="+playerData.farmingExp);
            writer.newLine();
            writer.write("fishingExp="+playerData.fishingExp);
            writer.newLine();
            writer.write("alchemyExp="+playerData.alchemyExp);
            writer.newLine();
            writer.write("exploringExp="+playerData.exploringExp);
            writer.newLine();
            writer.write("miningExp="+playerData.miningExp);
            writer.newLine();
            writer.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public class PlayerData {

        public String uuid="";
        public long miningExp=0;
        public long farmingExp=0;
        public long combatExp=0;
        public long fishingExp=0;
        public long alchemyExp=0;
        public long exploringExp=0;
    }

    public long getSkillExpFor(String uuid,Skills skill){
        if(playerData.containsKey(uuid)){
            switch(skill){
                case COMBAT:
                    return playerData.get(uuid).combatExp;
                case MINING:
                    return playerData.get(uuid).miningExp;
                case FARMING:
                    return playerData.get(uuid).farmingExp;
                case FISHING:
                    return playerData.get(uuid).fishingExp;
                case ALCHEMY:
                    return playerData.get(uuid).alchemyExp;
                case EXPLORING:
                    return playerData.get(uuid).exploringExp;
                default:
                    return -1;
            }
        }else{
            return -1;
        }
    }

    public void addSkillExpFor(String uuid,Skills skill,long amount){
        if(playerData.containsKey(uuid)){
            switch(skill){
                case COMBAT:
                    playerData.get(uuid).combatExp+=amount;
                    break;
                case MINING:
                    playerData.get(uuid).miningExp+=amount;
                    break;
                case FARMING:
                    playerData.get(uuid).farmingExp+=amount;
                    break;
                case FISHING:
                    playerData.get(uuid).fishingExp+=amount;
                    break;
                case ALCHEMY:
                    playerData.get(uuid).alchemyExp+=amount;
                    break;
                case EXPLORING:
                    playerData.get(uuid).exploringExp+=amount;
                    break;
            }
            Bukkit.getPlayer(UUID.fromString(uuid)).spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent("+"+amount+" "+skill+" Exp ("+getSkillExpFor(uuid,skill)+")"));
            Bukkit.getPlayer(UUID.fromString(uuid)).playSound(Bukkit.getPlayer(UUID.fromString(uuid)), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,0.75f,2.75f);
        }
    }

    public void addPlayer(String uuid){
        if(!playerData.containsKey(uuid)){
            PlayerData newPlayerData = new PlayerData();
            newPlayerData.uuid=uuid;
            playerData.put(uuid,newPlayerData);
            MainClass.info("new playerdata created for: "+uuid);
        }
    }

    public enum Skills{
        MINING,FARMING,COMBAT,FISHING,ALCHEMY,EXPLORING
    }

    private class SaveRunnable extends BukkitRunnable{
        @Override
        public void run() {
            saveToFile();
        }
    }

}
