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

        if (CorePlusAPI.getDepend().ResidenceEnabled()) {
            RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new VisitorResidence(), RegionPlus.getInstance());
            RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new ResidenceImprove(), RegionPlus.getInstance());
            RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new ResidencePoints(), RegionPlus.getInstance());
            if (CorePlusAPI.getDepend().SurvivalMechanicsEnabled()) {
                RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new SurvivalMechanics(), RegionPlus.getInstance());
                CorePlusAPI.getCond().registerFlag("climb");
                CorePlusAPI.getCond().registerFlag("crawl");
                CorePlusAPI.getCond().registerFlag("mobkick");
                CorePlusAPI.getCond().registerFlag("roofhang");
                CorePlusAPI.getCond().registerFlag("slide");
                CorePlusAPI.getCond().registerFlag("swim");
                CorePlusAPI.getCond().registerFlag("wallkick");
            }
            if (CorePlusAPI.getDepend().VehiclesEnabled()) {
                RegionPlus.getInstance().getServer().getPluginManager().registerEvents(new Vehicles(), RegionPlus.getInstance());
            }
        }
    }
}
