package tw.momocraft.regionplus.handlers;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.regionplus.RegionPlus;
import tw.momocraft.regionplus.utils.ConfigPath;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConfigHandler {

    private static YamlConfiguration configYAML;
    private static ConfigPath configPath;


    public static void generateData(boolean reload) {
        genConfigFile("config.yml");
        UtilsHandler.setupFirst(reload);
        setConfigPath(new ConfigPath());

        if (CorePlusAPI.getDepend().LuckPermsEnabled()) {
            if (getConfigPath().isresUpdateFlagsRemove()) {
                CorePlusAPI.getMsg().sendConsoleMsg(ConfigHandler.getPluginPrefix(), "&6Flags-Edit need to check the permissions of offline players.");
                CorePlusAPI.getMsg().sendConsoleMsg(ConfigHandler.getPluginPrefix(), "&6You need to enable the option \"vault-unsafe-lookups\" in LuckPerms\'s config.yml.");
            }
        }
        if (!reload) {
            CorePlusAPI.getUpdate().check(getPlugin(), getPluginPrefix(), Bukkit.getConsoleSender(),
                    RegionPlus.getInstance().getDescription().getName(),
                    RegionPlus.getInstance().getDescription().getVersion(), true);
        }
    }

    public static FileConfiguration getConfig(String fileName) {
        File filePath = RegionPlus.getInstance().getDataFolder();
        File file;
        switch (fileName) {
            case "config.yml":
                filePath = Bukkit.getWorldContainer();
                if (configYAML == null) {
                    getConfigData(filePath, fileName);
                }
                break;
            default:
                break;
        }
        file = new File(filePath, fileName);
        return getPath(fileName, file, false);
    }

    private static FileConfiguration getConfigData(File filePath, String fileName) {
        File file = new File(filePath, fileName);
        if (!(file).exists()) {
            try {
                RegionPlus.getInstance().saveResource(fileName, false);
            } catch (Exception e) {
                CorePlusAPI.getMsg().sendErrorMsg(ConfigHandler.getPlugin(), "Cannot save " + fileName + " to disk!");
                return null;
            }
        }
        return getPath(fileName, file, true);
    }

    private static YamlConfiguration getPath(String fileName, File file, boolean saveData) {
        switch (fileName) {
            case "config.yml":
                if (saveData) {
                    configYAML = YamlConfiguration.loadConfiguration(file);
                }
                return configYAML;
        }
        return null;
    }

    private static void genConfigFile(String fileName) {
        String[] fileNameSlit = fileName.split("\\.(?=[^\\.]+$)");
        int configVersion = 0;
        File filePath = RegionPlus.getInstance().getDataFolder();
        switch (fileName) {
            case "config.yml":
                configVersion = 4;
                break;
        }
        getConfigData(filePath, fileName);
        File File = new File(filePath, fileName);
        if (File.exists() && getConfig(fileName).getInt("Config-Version") != configVersion) {
            if (RegionPlus.getInstance().getResource(fileName) != null) {
                LocalDateTime currentDate = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
                String currentTime = currentDate.format(formatter);
                String newGen = fileNameSlit[0] + " " + currentTime + "." + fileNameSlit[0];
                File newFile = new File(filePath, newGen);
                if (!newFile.exists()) {
                    File.renameTo(newFile);
                    File configFile = new File(filePath, fileName);
                    configFile.delete();
                    getConfigData(filePath, fileName);
                    CorePlusAPI.getMsg().sendConsoleMsg(ConfigHandler.getPluginPrefix(), "&4The file \"" + fileName + "\" is out of date, generating a new one!");
                }
            }
        }
        getConfig(fileName).options().copyDefaults(false);
    }

    public static ConfigPath getConfigPath() {
        return configPath;
    }

    public static void setConfigPath(ConfigPath configPaths) {
        configPath = configPaths;
    }

    public static String getPlugin() {
        return RegionPlus.getInstance().getDescription().getName();
    }

    public static String getPluginPrefix() {
        return "[" + RegionPlus.getInstance().getDescription().getName() + "] ";
    }

    public static String getPrefix() {
        return getConfig("config.yml").getString("Message.prefix");
    }

    public static boolean isDebug() {
        return ConfigHandler.getConfig("config.yml").getBoolean("Debugging");
    }
}