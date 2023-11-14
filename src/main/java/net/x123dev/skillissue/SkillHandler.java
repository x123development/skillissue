package net.x123dev.skillissue;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SkillHandler extends BukkitRunnable{

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
                        data.combatLvl=getFullLvlByExp(data.combatExp);
                        break;
                    case "miningExp":
                        data.miningExp=Long.parseLong(line.substring(line.indexOf("=")+1));
                        data.miningLvl=getFullLvlByExp(data.miningExp);
                        break;
                    case "fishingExp":
                        data.fishingExp=Long.parseLong(line.substring(line.indexOf("=")+1));
                        data.fishingLvl=getFullLvlByExp(data.fishingExp);
                        break;
                    case "farmingExp":
                        data.farmingExp=Long.parseLong(line.substring(line.indexOf("=")+1));
                        data.farmingLvl=getFullLvlByExp(data.farmingExp);
                        break;
                    case "alchemyExp":
                        data.alchemyExp=Long.parseLong(line.substring(line.indexOf("=")+1));
                        data.alchemyLvl=getFullLvlByExp(data.alchemyExp);
                        break;
                    case "explorationExp":
                        data.explorationExp=Long.parseLong(line.substring(line.indexOf("=")+1));
                        data.explorationLvl=getFullLvlByExp(data.explorationExp);
                        break;
                    case "miningPerk":
                        data.miningPerk=Long.parseLong(line.substring(line.indexOf("=")+1));
                        break;
                    case "combatPerk":
                        data.combatPerk=Long.parseLong(line.substring(line.indexOf("=")+1));
                        break;
                    case "farmingPerk":
                        data.farmingPerk=Long.parseLong(line.substring(line.indexOf("=")+1));
                        break;
                    case "fishingPerk":
                        data.fishingPerk=Long.parseLong(line.substring(line.indexOf("=")+1));
                        break;
                    case "alchemyPerk":
                        data.alchemyPerk=Long.parseLong(line.substring(line.indexOf("=")+1));
                        break;
                    case "explorationPerk":
                        data.explorationPerk=Long.parseLong(line.substring(line.indexOf("=")+1));
                        break;
                    case "expSound":
                        data.expSound=Boolean.parseBoolean(line.substring(line.indexOf("=")+1));
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
            writer.write("explorationExp="+playerData.explorationExp);
            writer.newLine();
            writer.write("miningExp="+playerData.miningExp);
            writer.newLine();
            writer.write("miningPerk="+playerData.miningPerk);
            writer.newLine();
            writer.write("combatPerk="+playerData.combatPerk);
            writer.newLine();
            writer.write("farmingPerk="+playerData.farmingPerk);
            writer.newLine();
            writer.write("fishingPerk="+playerData.fishingPerk);
            writer.newLine();
            writer.write("alchemyPerk="+playerData.alchemyPerk);
            writer.newLine();
            writer.write("explorationPerk="+playerData.explorationPerk);
            writer.newLine();
            writer.write("expSound="+playerData.expSound);
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
        public long explorationExp=0;
        public long miningLvl=0;
        public long combatLvl=0;
        public long farmingLvl=0;
        public long fishingLvl=0;
        public long alchemyLvl=0;
        public long explorationLvl=0;
        public long combatPerk=0;
        public long miningPerk=0;
        public long farmingPerk=0;
        public long fishingPerk=0;
        public long alchemyPerk=0;
        public long explorationPerk=0;
        public boolean expSound=true;
    }

    public long getSkillAverageFor(String uuid){
        if(playerData.containsKey(uuid)){
            long avg = playerData.get(uuid).combatLvl
                    +playerData.get(uuid).miningLvl
                    +playerData.get(uuid).farmingLvl
                    +playerData.get(uuid).fishingLvl
                    +playerData.get(uuid).alchemyLvl
                    +playerData.get(uuid).explorationLvl;
            avg = avg/6;
            return avg;
        }else{
            return -1;
        }
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
                case EXPLORATION:
                    return playerData.get(uuid).explorationExp;
                default:
                    return -1;
            }
        }else{
            return -1;
        }
    }

    public long getSkillLvlFor(String uuid,Skills skill){
        if(playerData.containsKey(uuid)){
            switch(skill){
                case COMBAT:
                    return playerData.get(uuid).combatLvl;
                case MINING:
                    return playerData.get(uuid).miningLvl;
                case FARMING:
                    return playerData.get(uuid).farmingLvl;
                case FISHING:
                    return playerData.get(uuid).fishingLvl;
                case ALCHEMY:
                    return playerData.get(uuid).alchemyLvl;
                case EXPLORATION:
                    return playerData.get(uuid).explorationLvl;
                default:
                    return -1;
            }
        }else{
            return -1;
        }
    }

    public void setSkillLvlFor(String uuid,Skills skill,long lvl){
        if(playerData.containsKey(uuid)){
            switch(skill){
                case COMBAT:
                    playerData.get(uuid).combatLvl=lvl;
                    break;
                case MINING:
                    playerData.get(uuid).miningLvl=lvl;
                    break;
                case FARMING:
                    playerData.get(uuid).farmingLvl=lvl;
                    break;
                case FISHING:
                    playerData.get(uuid).fishingLvl=lvl;
                    break;
                case ALCHEMY:
                    playerData.get(uuid).alchemyLvl=lvl;
                    break;
                case EXPLORATION:
                    playerData.get(uuid).explorationLvl=lvl;
                    break;
            }
        }
    }

    public long getSkillPerkFor(String uuid,Skills skill){
        if(playerData.containsKey(uuid)){
            switch(skill){
                case COMBAT:
                    return playerData.get(uuid).combatPerk;
                case MINING:
                    return playerData.get(uuid).miningPerk;
                case FARMING:
                    return playerData.get(uuid).farmingPerk;
                case FISHING:
                    return playerData.get(uuid).fishingPerk;
                case ALCHEMY:
                    return playerData.get(uuid).alchemyPerk;
                case EXPLORATION:
                    return playerData.get(uuid).explorationPerk;
                default:
                    return -1;
            }
        }else{
            return -1;
        }
    }

    public void setSkillPerkFor(String uuid,Skills skill,long perk){
        if(playerData.containsKey(uuid)){
            switch(skill){
                case COMBAT:
                    playerData.get(uuid).combatPerk=perk;
                    break;
                case MINING:
                    playerData.get(uuid).miningPerk=perk;
                    break;
                case FARMING:
                    playerData.get(uuid).farmingPerk=perk;
                    break;
                case FISHING:
                    playerData.get(uuid).fishingPerk=perk;
                    break;
                case ALCHEMY:
                    playerData.get(uuid).alchemyPerk=perk;
                    break;
                case EXPLORATION:
                    playerData.get(uuid).explorationPerk=perk;
                    break;
            }
        }
    }

    public boolean getSettingFor(String uuid,String setting){
        if(playerData.containsKey(uuid)){
            switch(setting){
                case "expSound":
                    return playerData.get(uuid).expSound;
                default:
                    return false;
            }
        }else{
            return false;
        }
    }

    public void setSettingFor(String uuid,String setting,boolean bool){
        if(playerData.containsKey(uuid)){
            switch(setting){
                case "expSound":
                    playerData.get(uuid).expSound=bool;
                    break;
            }
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
                case EXPLORATION:
                    playerData.get(uuid).explorationExp+=amount;
                    break;
            }

            Player player =Bukkit.getPlayer(UUID.fromString(uuid));
            if(getFullLvlByExp(getSkillExpFor(uuid,skill))>getSkillLvlFor(uuid,skill)){
                setSkillLvlFor(uuid,skill,getFullLvlByExp(getSkillExpFor(uuid,skill)));

                player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP,1.5f,1f);
                player.sendMessage(ChatColor.GRAY+"--------------------------");
                player.sendMessage(""+ChatColor.AQUA+ChatColor.BOLD+skill+ChatColor.WHITE+ChatColor.BOLD+" level "+getSkillLvlFor(uuid,skill)+" reached!");
                player.sendMessage(ChatColor.GRAY+"--------------------------");
            }else{
                if(getSettingFor(uuid,"expSound"))
                    player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP,0.6f,2.5f);
            }
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent(ChatColor.GRAY+"+"+amount+" "+skill+" Exp  "+getFormattedLvlProgressByExp(getSkillExpFor(uuid,skill))+" ("+(getSkillExpFor(uuid,skill)-getExpByLvl(getSkillLvlFor(uuid,skill)))+"/"+(getExpByLvl(getSkillLvlFor(uuid,skill)+1)-getExpByLvl(getSkillLvlFor(uuid,skill)))+")"));
        }
    }

    public static long getExpByLvl(long lvl){
        return (long) (500*Math.pow(lvl,2)+500*lvl);
    }

    public static double getLvlByExp(long exp){
        return ((-500+Math.sqrt(250000+2000*exp))/1000);
    }

    public static long getFullLvlByExp(long exp){
        return (long) Math.floor(getLvlByExp(exp));
    }

    public static double getLvlProgressByExp(long exp){
        return ((double)(exp-getExpByLvl(getFullLvlByExp(exp))))/((double)(getExpByLvl(getFullLvlByExp(exp)+1)-getExpByLvl(getFullLvlByExp(exp))));
    }

    public static String getFormattedLvlProgressByExp(long exp){
        return String.format("%.2f", getLvlProgressByExp(exp)*100)+"%";
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
        MINING,FARMING,COMBAT,FISHING,ALCHEMY,EXPLORATION
    }

    private class SaveRunnable extends BukkitRunnable{
        @Override
        public void run() {
            saveToFile();
        }
    }


}
