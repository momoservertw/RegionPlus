package tw.momocraft.regionplus.handlers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;
import tw.momocraft.regionplus.Commands;
import tw.momocraft.regionplus.RegionPlus;
import tw.momocraft.regionplus.listeners.*;
import tw.momocraft.regionplus.utils.DependAPI;
import tw.momocraft.regionplus.utils.RegionConfig;
import tw.momocraft.regionplus.utils.RegionUtils;
import tw.momocraft.regionplus.utils.TabComplete;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConfigHandler {

    private static YamlConfiguration configYAML;
    private static DependAPI depends;
    private static UpdateHandler updater;
    private static RegionConfig region;


    public static void generateData(boolean reload) {
        configFile();
        setDepends(new DependAPI());
        sendUtilityDepends();
        setRegionConfig(new RegionConfig());
        //setUpdater(new UpdateHandler());

        if (!reload && getRegionConfig().isResFlagAutoCheck()) {
            long delay = getRegionConfig().getResFlagAutoCheckDelay();
            new BukkitRunnable() {
                @Override
                public void run() {
                    ServerHandler.sendConsoleMessage("&6Starting to check residence flags...");
                    RegionUtils.resetNoPermsFlags();
                }
            }.runTaskLater(RegionPlus.getInstance(), delay);
        }
    }

    public static void registerEvents() {
        RegionPlus.getInstance().getCommand("regionplus").setExecutor(new Commands());
        RegionPlus.getInstance().getCommand("regionplus").setTabCompleter(new TabComplete());
        RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new EntityDamage(), RegionPlus.getInstance());
        RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new EntityChangeBlock(), RegionPlus.getInstance());
        RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new EntityBreakDoor(), RegionPlus.getInstance());

        if (ConfigHandler.getDepends().ResidenceEnabled()) {
            if (getRegionConfig().isVisitorEnable()) {
                RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new ResidenceCreation(), RegionPlus.getInstance());
                RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new ResidenceOwnerChange(), RegionPlus.getInstance());
                if (getRegionConfig().isResFlagEdit()) {
                    RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new ResidenceFlagCheck(), RegionPlus.getInstance());
                }
            }
        }
        if (getRegionConfig().isVisitorEnable()) {
            RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new VistorCheck(), RegionPlus.getInstance());
        }
    }

    public static FileConfiguration getConfig(String path) {
        File file = new File(RegionPlus.getInstance().getDataFolder(), path);
        if (configYAML == null) {
            getConfigData(path);
        }
        return getPath(path, file, false);
    }

    private static FileConfiguration getConfigData(String path) {
        File file = new File(RegionPlus.getInstance().getDataFolder(), path);
        if (!(file).exists()) {
            try {
                RegionPlus.getInstance().saveResource(path, false);
            } catch (Exception e) {
                RegionPlus.getInstance().getLogger().warning("Cannot save " + path + " to disk!");
                return null;
            }
        }
        return getPath(path, file, true);
    }

    private static YamlConfiguration getPath(String path, File file, boolean saveData) {
        if (path.contains("config.yml")) {
            if (saveData) {
                configYAML = YamlConfiguration.loadConfiguration(file);
            }
            return configYAML;
        }
        return null;
    }

    private static void configFile() {
        getConfigData("config.yml");
        File File = new File(RegionPlus.getInstance().getDataFolder(), "config.yml");
        if (File.exists() && getConfig("config.yml").getInt("Config-Version") != 1) {
            if (RegionPlus.getInstance().getResource("config.yml") != null) {
                LocalDateTime currentDate = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
                String currentTime = currentDate.format(formatter);
                String newGen = "config " + currentTime + ".yml";
                File newFile = new File(RegionPlus.getInstance().getDataFolder(), newGen);
                if (!newFile.exists()) {
                    File.renameTo(newFile);
                    File configFile = new File(RegionPlus.getInstance().getDataFolder(), "config.yml");
                    configFile.delete();
                    getConfigData("config.yml");
                    ServerHandler.sendConsoleMessage("&e*            *            *");
                    ServerHandler.sendConsoleMessage("&e *            *            *");
                    ServerHandler.sendConsoleMessage("&e  *            *            *");
                    ServerHandler.sendConsoleMessage("&cYour config.yml is out of date, generating a new one!");
                    ServerHandler.sendConsoleMessage("&e    *            *            *");
                    ServerHandler.sendConsoleMessage("&e     *            *            *");
                    ServerHandler.sendConsoleMessage("&e      *            *            *");
                }
            }
        }
        getConfig("config.yml").options().copyDefaults(false);
    }

    private static void sendUtilityDepends() {
        ServerHandler.sendConsoleMessage("&fHooked [ &e"
                + (getDepends().getVault().vaultEnabled() ? "Vault, " : "")
                + (getDepends().ResidenceEnabled() ? "Residence, " : "")
                + (getDepends().PlaceHolderAPIEnabled() ? "PlaceHolderAPI," : "")
                + (getDepends().ResidenceEnabled() ? "WorldBorder, " : "")
                + " &f]");
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


    public static RegionConfig getRegionConfig() {
        return region;
    }

    public static void setRegionConfig(RegionConfig regionConfig) {
        region = regionConfig;
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