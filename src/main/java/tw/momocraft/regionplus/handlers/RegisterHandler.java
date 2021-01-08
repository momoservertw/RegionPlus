package tw.momocraft.regionplus.handlers;

import com.bekvon.bukkit.residence.protection.FlagPermissions;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.regionplus.Commands;
import tw.momocraft.regionplus.RegionPlus;
import tw.momocraft.regionplus.listeners.*;
import tw.momocraft.regionplus.utils.TabComplete;

public class RegisterHandler {

    public static void registerEvents() {
        RegionPlus.getInstance().getCommand("regionplus").setExecutor(new Commands());
        RegionPlus.getInstance().getCommand("regionplus").setTabCompleter(new TabComplete());

        RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new Visitor(), RegionPlus.getInstance());
        CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Register-Event", "Spawn", "CreatureSpawn", "continue",
                new Throwable().getStackTrace()[0]);

        if (ConfigHandler.getDepends().ResidenceEnabled()) {
            RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new VisitorResidence(), RegionPlus.getInstance());
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Register-Event", "Visitor", "VisitorResidence", "continue",
                    new Throwable().getStackTrace()[0]);
            RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new ResidenceImprove(), RegionPlus.getInstance());
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Register-Event", "Residence-Improve", "ResidenceImprove", "continue",
                    new Throwable().getStackTrace()[0]);
            RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new ResidencePoints(), RegionPlus.getInstance());
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Register-Event", "Residence-Points", "ResidencePoints", "continue",
                    new Throwable().getStackTrace()[0]);
            /*
            RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new TestEvent(), RegionPlus.getInstance());
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Register-Event", "Residence-Test", "TestEvent", "continue",
                    new Throwable().getStackTrace()[0]);
             */
            if (ConfigHandler.getDepends().SurvivalMechanicsEnabled()) {
                RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new SurvivalMechanics(), RegionPlus.getInstance());
                CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Register-Event", "Residence", "SurvivalMechanics", "continue",
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
                CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.getPlugin(), "Register-Event", "Residence", "Vehicles", "continue",
                        new Throwable().getStackTrace()[0]);
            }
        }

    }
}
