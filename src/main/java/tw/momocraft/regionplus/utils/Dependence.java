package tw.momocraft.regionplus.utils;

import com.bekvon.bukkit.residence.protection.FlagPermissions;
import org.bukkit.Bukkit;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.regionplus.handlers.ConfigHandler;

public class Dependence {
    private boolean Residence = false;
    private boolean MultiverseCore = false;
    private boolean SurvivalMechanics = false;
    private boolean Vehicles = false;

    public Dependence() {
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Settings.Features.Hook.Residence")) {
            Residence = Bukkit.getServer().getPluginManager().getPlugin("Residence") != null;
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Settings.Features.Hook.MultiverseCore")) {
            MultiverseCore = Bukkit.getServer().getPluginManager().getPlugin("MultiverseCore") != null;
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Settings.Features.Hook.SurvivalMechanics")) {
            SurvivalMechanics = Bukkit.getServer().getPluginManager().getPlugin("SurvivalMechanics") != null;
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Settings.Features.Hook.Vehicles")) {
            Vehicles = Bukkit.getServer().getPluginManager().getPlugin("Vehicles") != null;
        }
    }

    private void sendUtilityDepends() {
        String hookMsg = "&fHooked: ["
                + (ResidenceEnabled() ? "Residence, " : "")
                + (MultiverseCoreEnabled() ? "Multiverse-Core, " : "")
                + (SurvivalMechanicsEnabled() ? "SurvivalMechanics, " : "")
                + (VehiclesEnabled() ? "Vehicles, " : "");
        try {
            CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPlugin(), hookMsg.substring(0, hookMsg.lastIndexOf(", ")) + "]");
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
                CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPlugin(), hookMsg.substring(0, hookMsg.lastIndexOf(", ")) + "]");
            } catch (Exception ignored) {
            }
        }
    }

    public boolean ResidenceEnabled() {
        return this.Residence;
    }

    public boolean MultiverseCoreEnabled() {
        return this.MultiverseCore;
    }

    public boolean SurvivalMechanicsEnabled() {
        return this.SurvivalMechanics;
    }

    public boolean VehiclesEnabled() {
        return this.Vehicles;
    }
}
