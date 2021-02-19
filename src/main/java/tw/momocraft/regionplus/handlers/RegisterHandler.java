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

        RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new WorldControl(), RegionPlus.getInstance());
        CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPluginName(),
                "Register-Event", "World", "WorldControl", "continue",
                new Throwable().getStackTrace()[0]);
        RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new Visitor(), RegionPlus.getInstance());
        CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPluginName(),
                "Register-Event", "Visitor", "Visitor", "continue",
                new Throwable().getStackTrace()[0]);

        if (CorePlusAPI.getDependManager().ResidenceEnabled()) {
            RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new VisitorResidence(), RegionPlus.getInstance());
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPluginName(),
                    "Register-Event", "Visitor", "Residence", "continue",
                    new Throwable().getStackTrace()[0]);
            RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new ResidenceImprove(), RegionPlus.getInstance());
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPluginName(),
                    "Register-Event", "Residence", "RImprove", "continue",
                    new Throwable().getStackTrace()[0]);
            RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new ResidencePoints(), RegionPlus.getInstance());
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPluginName(),
                    "Register-Event", "Residence", "Points", "continue",
                    new Throwable().getStackTrace()[0]);
            if (CorePlusAPI.getDependManager().SurvivalMechanicsEnabled()) {
                RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new SurvivalMechanics(), RegionPlus.getInstance());
                CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPluginName(),
                        "Register-Event", "Residence", "SurvivalMechanics", "continue",
                        new Throwable().getStackTrace()[0]);
                FlagPermissions.addFlag("climb");
                FlagPermissions.addFlag("crawl");
                FlagPermissions.addFlag("mobkick");
                FlagPermissions.addFlag("roofhang");
                FlagPermissions.addFlag("slide");
                FlagPermissions.addFlag("swim");
                FlagPermissions.addFlag("wallkick");
            }
            if (CorePlusAPI.getDependManager().VehiclesEnabled()) {
                RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new Vehicles(), RegionPlus.getInstance());
                CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPluginName(), "Register-Event", "Residence", "Vehicles", "continue",
                        new Throwable().getStackTrace()[0]);
            }
        }
        sendUtilityDepends();
    }

    private static void sendUtilityDepends() {
        String hookMsg = "&fHooked: ["
                + (CorePlusAPI.getDependManager().ResidenceEnabled() ? "Residence, " : "")
                + (CorePlusAPI.getDependManager().SurvivalMechanicsEnabled() ? "SurvivalMechanics, " : "")
                + (CorePlusAPI.getDependManager().VehiclesEnabled() ? "Vehicles, " : "")
                + (CorePlusAPI.getDependManager().MultiverseCoreEnabled() ? "MultiverseCore, " : "");
        try {
            CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPluginPrefix(), hookMsg.substring(0, hookMsg.lastIndexOf(", ")) + "]");
        } catch (Exception ignored) {
        }

        if (CorePlusAPI.getDependManager().ResidenceEnabled()) {
            hookMsg = "&fResidence Flags: ["
                    + (FlagPermissions.getPosibleAreaFlags().contains("climb") ? "climb, " : "")
                    + (FlagPermissions.getPosibleAreaFlags().contains("crawl") ? "crawl, " : "")
                    + (FlagPermissions.getPosibleAreaFlags().contains("mobkick") ? "mobkick, " : "")
                    + (FlagPermissions.getPosibleAreaFlags().contains("roofhang") ? "roofhang, " : "")
                    + (FlagPermissions.getPosibleAreaFlags().contains("slide") ? "slide, " : "")
                    + (FlagPermissions.getPosibleAreaFlags().contains("swim") ? "swim, " : "")
                    + (FlagPermissions.getPosibleAreaFlags().contains("wallkick") ? "wallkick, " : "")
            ;
            try {
                CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPluginPrefix(), hookMsg.substring(0, hookMsg.lastIndexOf(", ")) + "]");
            } catch (Exception ignored) {
            }
        }
    }
}