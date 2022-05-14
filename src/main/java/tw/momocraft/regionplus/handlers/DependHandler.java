package tw.momocraft.regionplus.handlers;

import org.bukkit.Bukkit;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.regionplus.Commands;
import tw.momocraft.regionplus.RegionPlus;
import tw.momocraft.regionplus.TabComplete;
import tw.momocraft.regionplus.listeners.*;

public class DependHandler {

    public void setup(boolean reload) {
        if (!reload) {
            registerEvents();
            checkUpdate();
        }
        setupHooks();
    }

    public void checkUpdate() {
        if (!ConfigHandler.isCheckUpdates())
            return;
        CorePlusAPI.getUpdate().check(ConfigHandler.getPluginName(),
                ConfigHandler.getPluginPrefix(), Bukkit.getConsoleSender(),
                RegionPlus.getInstance().getDescription().getName(),
                RegionPlus.getInstance().getDescription().getVersion(), true);
    }

    private void registerEvents() {
        RegionPlus.getInstance().getCommand("regionplus").setExecutor(new Commands());
        RegionPlus.getInstance().getCommand("regionplus").setTabCompleter(new TabComplete());

        RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new WorldControl(), RegionPlus.getInstance());
        RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new Visitor(), RegionPlus.getInstance());
        if (ResidenceEnabled()) {
            RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new VisitorResidence(), RegionPlus.getInstance());
            RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new ResidenceImprove(), RegionPlus.getInstance());
            RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new ResidencePoints(), RegionPlus.getInstance());
            if (SurvivalMechanicsEnabled()) {
                RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new SurvivalMechanics(), RegionPlus.getInstance());
                CorePlusAPI.getCond().registerFlag("climb");
                CorePlusAPI.getCond().registerFlag("crawl");
                CorePlusAPI.getCond().registerFlag("mobkick");
                CorePlusAPI.getCond().registerFlag("roofhang");
                CorePlusAPI.getCond().registerFlag("slide");
                CorePlusAPI.getCond().registerFlag("swim");
                CorePlusAPI.getCond().registerFlag("wallkick");
            }
            if (VehiclesEnabled()) {
                RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new Vehicles(), RegionPlus.getInstance());
            }
        }
    }

    private boolean Residence = false;
    private boolean SurvivalMechanics = false;
    private boolean Vehicles = false;

    private void setupHooks() {
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Settings.Features.Hook.Residence"))
            Residence = Bukkit.getServer().getPluginManager().getPlugin("SkinsRestorer") != null;
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Settings.Features.Hook.SurvivalMechanics"))
            SurvivalMechanics = Bukkit.getServer().getPluginManager().getPlugin("DiscordSRV") != null;
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Settings.Features.Hook.Vehicles"))
            Vehicles = Bukkit.getServer().getPluginManager().getPlugin("MyPet") != null;
    }

    public boolean ResidenceEnabled() {
        return this.Residence;
    }

    public boolean SurvivalMechanicsEnabled() {
        return this.SurvivalMechanics;
    }

    public boolean VehiclesEnabled() {
        return this.Vehicles;
    }
}
