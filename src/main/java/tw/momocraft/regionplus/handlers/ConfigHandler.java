package tw.momocraft.regionplus.handlers;

import com.bekvon.bukkit.residence.protection.FlagPermissions;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import tw.momocraft.regionplus.Commands;
import tw.momocraft.regionplus.RegionPlus;
import tw.momocraft.regionplus.listeners.*;
import tw.momocraft.regionplus.listeners.ResidenceDelete;
import tw.momocraft.regionplus.utils.*;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ConfigHandler {

    private static YamlConfiguration configYAML;
    private static YamlConfiguration spigotYAML;
    private static DependAPI depends;
    private static UpdateHandler updater;
    private static ConfigPath region;
    private static FlagsEditor editor;


    public static void generateData(boolean reload) {
        genConfigFile("config.yml");
        setDepends(new DependAPI());
        sendUtilityDepends();
        setRegionConfig(new ConfigPath());
        setUpdater(new UpdateHandler());
        setEditor(new FlagsEditor());

        if (getDepends().LuckPermsEnabled()) {
            if (getConfigPath().isResFlagRemove()) {
                ServerHandler.sendConsoleMessage("&6Flags-Edit need to check the permissions of offline players.");
                ServerHandler.sendConsoleMessage("&6You need to enable the option \"vault-unsafe-lookups\" in LuckPerms\'s config.yml.");
            }
        }
        /*
        if (getDepends().ResidenceEnabled()) {
            if (!reload && getConfigPath().isRFAutoCheck()) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        ServerHandler.sendConsoleMessage("&6Starting to check residence flags...");
                        ResidenceUtils.editFlags();
                    }
                }.runTaskLater(RegionPlus.getInstance(), getConfigPath().getRFAutoCheckDelay());
            }
        }
         */
    }

    public static void registerEvents() {
        RegionPlus.getInstance().getCommand("regionplus").setExecutor(new Commands());
        RegionPlus.getInstance().getCommand("regionplus").setTabCompleter(new TabComplete());

        List<String> flagsList = new ArrayList<>();

        RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new EntityBreakDoor(), RegionPlus.getInstance());
        ServerHandler.sendFeatureMessage("Register-Event", "Residence", "EntityBreakDoor", "continue",
                new Throwable().getStackTrace()[0]);
        RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new EntityChangeBlock(), RegionPlus.getInstance());
        ServerHandler.sendFeatureMessage("Register-Event", "Residence", "EntityChangeBlock", "continue",
                new Throwable().getStackTrace()[0]);
        RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new EntityDamage(), RegionPlus.getInstance());
        ServerHandler.sendFeatureMessage("Register-Event", "Residence", "EntityDamage", "continue",
                new Throwable().getStackTrace()[0]);
        RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new EntityDamageByEntity(), RegionPlus.getInstance());
        ServerHandler.sendFeatureMessage("Register-Event", "Visitor", "EntityDamageByEntity", "continue",
                new Throwable().getStackTrace()[0]);
        RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new PlayerBucketFill(), RegionPlus.getInstance());
        ServerHandler.sendFeatureMessage("Register-Event", "Visitor", "PlayerBucketFill", "continue",
                new Throwable().getStackTrace()[0]);
        RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new PlayerDropItem(), RegionPlus.getInstance());
        ServerHandler.sendFeatureMessage("Register-Event", "Visitor", "PlayerDropItem", "continue",
                new Throwable().getStackTrace()[0]);
        RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new PlayerFish(), RegionPlus.getInstance());
        ServerHandler.sendFeatureMessage("Register-Event", "Visitor", "PlayerFish", "continue",
                new Throwable().getStackTrace()[0]);
        RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new PlayerInteract(), RegionPlus.getInstance());
        ServerHandler.sendFeatureMessage("Register-Event", "Visitor", "PlayerInteract", "continue",
                new Throwable().getStackTrace()[0]);
        RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new PlayerInteractEntity(), RegionPlus.getInstance());
        ServerHandler.sendFeatureMessage("Register-Event", "Visitor", "PlayerInteractEntity", "continue",
                new Throwable().getStackTrace()[0]);
        RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new PlayerItemConsume(), RegionPlus.getInstance());
        ServerHandler.sendFeatureMessage("Register-Event", "Visitor", "PlayerItemConsume", "continue",
                new Throwable().getStackTrace()[0]);
        RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new PlayerPickupItem(), RegionPlus.getInstance());
        ServerHandler.sendFeatureMessage("Register-Event", "Visitor", "PlayerPickupItem", "continue",
                new Throwable().getStackTrace()[0]);
        RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new ProjectileLaunch(), RegionPlus.getInstance());
        ServerHandler.sendFeatureMessage("Register-Event", "Visitor", "ProjectileLaunch", "continue",
                new Throwable().getStackTrace()[0]);
        if (ConfigHandler.getDepends().ResidenceEnabled()) {
            RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new ResidenceCreation(), RegionPlus.getInstance());
            ServerHandler.sendFeatureMessage("Register-Event", "Residence", "ProjectileLaunch", "continue",
                    new Throwable().getStackTrace()[0]);
            RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new ResidenceSizeChange(), RegionPlus.getInstance());
            ServerHandler.sendFeatureMessage("Register-Event", "Residence", "ProjectileLaunch", "continue",
                    new Throwable().getStackTrace()[0]);
            RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new ResidenceOwnerChange(), RegionPlus.getInstance());
            ServerHandler.sendFeatureMessage("Register-Event", "Residence", "ProjectileLaunch", "continue",
                    new Throwable().getStackTrace()[0]);
            RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new ResidenceDelete(), RegionPlus.getInstance());
            ServerHandler.sendFeatureMessage("Register-Event", "Residence", "ProjectileLaunch", "continue",
                    new Throwable().getStackTrace()[0]);

            if (ConfigHandler.getDepends().SurvivalMechanicsEnabled()) {
                RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new SurvivalMechanics(), RegionPlus.getInstance());
                ServerHandler.sendFeatureMessage("Register-Event", "Residence", "SurvivalMechanics", "continue",
                        new Throwable().getStackTrace()[0]);
                FlagPermissions.addFlag("climb");
                FlagPermissions.addFlag("crawl");
                FlagPermissions.addFlag("mobkick");
                FlagPermissions.addFlag("roofhang");
                FlagPermissions.addFlag("slide");
                FlagPermissions.addFlag("swim");
                FlagPermissions.addFlag("wallkick");
            }
            if (ConfigHandler.getDepends().VehiclesEnabled()) {
                RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new Vehicles(), RegionPlus.getInstance());
                ServerHandler.sendFeatureMessage("Register-Event", "Residence", "Vehicles", "continue",
                        new Throwable().getStackTrace()[0]);
            }
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
            case "spigot.yml":
                filePath = Bukkit.getServer().getWorldContainer();
                if (spigotYAML == null) {
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
                ServerHandler.sendErrorMessage("&cCannot save " + fileName + " to disk!");
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
            case "spigot.yml":
                if (saveData) {
                    spigotYAML = YamlConfiguration.loadConfiguration(file);
                }
                return spigotYAML;
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
                    ServerHandler.sendConsoleMessage("&4The file \"" + fileName + "\" is out of date, generating a new one!");
                }
            }
        }
        getConfig(fileName).options().copyDefaults(false);
    }

    private static void sendUtilityDepends() {
        ServerHandler.sendConsoleMessage("&fHooked: "
                + (getDepends().getVault().vaultEnabled() ? "Vault, " : "")
                + (getDepends().ResidenceEnabled() ? "Residence, " : "")
                + (getDepends().PlaceHolderAPIEnabled() ? "PlaceHolderAPI, " : "")
                + (getDepends().ItemJoinEnabled() ? "ItemJoin, " : "")
                + (getDepends().PvPManagerEnabled() ? "PvPManager, " : "")
                + (getDepends().MultiverseCoreEnabled() ? "Multiverse-Core, " : "")
                + (getDepends().LuckPermsEnabled() ? "LuckPerms, " : "")
                + (getDepends().SurvivalMechanicsEnabled() ? "SurvivalMechanics, " : "")
                + (getDepends().VehiclesEnabled() ? "GPS, " : "")
        );
        if (getDepends().ResidenceEnabled()) {
            ServerHandler.sendConsoleMessage("&fAdd Residence flags: "
                    + (FlagPermissions.getPosibleAreaFlags().contains("climb") ? "climb, " : "")
                    + (FlagPermissions.getPosibleAreaFlags().contains("crawl") ? "crawl, " : "")
                    + (FlagPermissions.getPosibleAreaFlags().contains("mobkick") ? "mobkick, " : "")
                    + (FlagPermissions.getPosibleAreaFlags().contains("roofhang") ? "roofhang, " : "")
                    + (FlagPermissions.getPosibleAreaFlags().contains("slide") ? "slide, " : "")
                    + (FlagPermissions.getPosibleAreaFlags().contains("swim") ? "swim, " : "")
                    + (FlagPermissions.getPosibleAreaFlags().contains("wallkick") ? "wallkick, " : "")
            );
        }
    }

    public static DependAPI getDepends() {
        return depends;
    }

    private static void setDepends(DependAPI depend) {
        depends = depend;
    }

    private static void setUpdater(UpdateHandler update) {
        updater = update;
    }

    static boolean getDebugging() {
        return ConfigHandler.getConfig("config.yml").getBoolean("Debugging");
    }

    public static UpdateHandler getUpdater() {
        return updater;
    }


    public static ConfigPath getConfigPath() {
        return region;
    }

    public static void setRegionConfig(ConfigPath regionConfig) {
        region = regionConfig;
    }

    public static FlagsEditor getEditor() {
        return editor;
    }

    public static void setEditor(FlagsEditor flagsEditor) {
        editor = flagsEditor;
    }

    /**
     * Converts a serialized location to a Location. Returns null if string is empty
     *
     * @param s - serialized location in format "world:x:y:z"
     * @return Location
     */
    public static Location getLocationString(final String s) {
        if (s == null || s.trim().equals("")) {
            return null;
        }
        final String[] parts = s.split(":");
        if (parts.length == 4) {
            final World w = Bukkit.getServer().getWorld(parts[0]);
            final int x = Integer.parseInt(parts[1]);
            final int y = Integer.parseInt(parts[2]);
            final int z = Integer.parseInt(parts[3]);
            return new Location(w, x, y, z);
        }
        return null;
    }

    public static boolean getEnable(String path, Boolean defalut) {
        String enable = ConfigHandler.getConfig("config.yml").getString(path);
        if (enable == null) {
            return defalut;
        }
        return enable.equals("true");
    }
}