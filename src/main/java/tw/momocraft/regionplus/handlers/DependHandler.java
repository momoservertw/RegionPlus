package tw.momocraft.regionplus.handlers;

import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.regionplus.Commands;
import tw.momocraft.regionplus.RegionPlus;
import tw.momocraft.regionplus.TabComplete;
import tw.momocraft.regionplus.listeners.*;

public class DependHandler {

    public DependHandler() {
        registerEvents();
    }

    private void registerEvents() {
        RegionPlus.getInstance().getCommand("regionplus").setExecutor(new Commands());
        RegionPlus.getInstance().getCommand("regionplus").setTabCompleter(new TabComplete());

        RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new WorldControl(), RegionPlus.getInstance());
        RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new Visitor(), RegionPlus.getInstance());

        if (CorePlusAPI.getDependManager().ResidenceEnabled()) {
            RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new VisitorResidence(), RegionPlus.getInstance());
            RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new ResidenceImprove(), RegionPlus.getInstance());
            RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new ResidencePoints(), RegionPlus.getInstance());
            if (CorePlusAPI.getDependManager().SurvivalMechanicsEnabled()) {
                RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new SurvivalMechanics(), RegionPlus.getInstance());
                CorePlusAPI.getConditionManager().registerFlag("climb");
                CorePlusAPI.getConditionManager().registerFlag("crawl");
                CorePlusAPI.getConditionManager().registerFlag("mobkick");
                CorePlusAPI.getConditionManager().registerFlag("roofhang");
                CorePlusAPI.getConditionManager().registerFlag("slide");
                CorePlusAPI.getConditionManager().registerFlag("swim");
                CorePlusAPI.getConditionManager().registerFlag("wallkick");
            }
            if (CorePlusAPI.getDependManager().VehiclesEnabled()) {
                RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new Vehicles(), RegionPlus.getInstance());
            }
        }
    }
}
