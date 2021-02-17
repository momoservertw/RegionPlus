package tw.momocraft.regionplus.utils;

import com.bekvon.bukkit.residence.protection.FlagPermissions;
import org.bukkit.Bukkit;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.regionplus.handlers.ConfigHandler;

public class Dependence {
    private boolean Residence;
    private boolean SurvivalMechanics;
    private boolean Vehicles;
    private boolean MultiverseCore;

    public Dependence() {
        Residence = Bukkit.getServer().getPluginManager().getPlugin("Residence") != null;
        SurvivalMechanics = Bukkit.getServer().getPluginManager().getPlugin("SurvivalMechanics") != null;
        Vehicles = Bukkit.getServer().getPluginManager().getPlugin("Vehicles") != null;
        MultiverseCore = Bukkit.getServer().getPluginManager().getPlugin("MultiverseCore") != null;
        sendUtilityDepends();
    }

    private void sendUtilityDepends() {
        String hookMsg = "&fHooked: ["
                + (ResidenceEnabled() ? "Residence, " : "")
                + (SurvivalMechanicsEnabled() ? "SurvivalMechanics, " : "")
                + (VehiclesEnabled() ? "Vehicles, " : "")
                + (MultiverseCoreEnabled() ? "MultiverseCore, " : "");
        try {
            CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPluginPrefix(), hookMsg.substring(0, hookMsg.lastIndexOf(", ")) + "]");
        } catch (Exception ignored) {
        }

        if (ResidenceEnabled()) {
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

    public boolean ResidenceEnabled() {
        return this.Residence;
    }

    public boolean SurvivalMechanicsEnabled() {
        return this.SurvivalMechanics;
    }

    public boolean VehiclesEnabled() {
        return this.Vehicles;
    }

    public boolean MultiverseCoreEnabled() {
        return this.MultiverseCore;
    }
}
