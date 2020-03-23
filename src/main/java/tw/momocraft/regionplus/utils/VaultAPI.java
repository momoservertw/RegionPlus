package tw.momocraft.regionplus.utils;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import tw.momocraft.regionplus.RegionPlus;

public class VaultAPI {
    private Economy econ = null;
    private boolean isEnabled = false;
    private Permission perms = null;

    VaultAPI() {
        this.setVaultStatus(Bukkit.getServer().getPluginManager().getPlugin("Vault") != null);
    }

    private void enableFeatures() {
        if (RegionPlus.getInstance().getServer().getPluginManager().getPlugin("Vault") != null) {
            if (!this.setupEconomy()) {
                //ServerHandler.sendErrorMessage("&cCan not find the Economy plugin.");
            }
            if (!this.setupPermissions()) {
                //ServerHandler.sendErrorMessage("&cCan not find the Permission plugin.");
                //ServerHandler.sendErrorMessage("&cYou can only clean the data: Logs and Regions");
            }
        }
    }

    private boolean setupEconomy() {
        if (RegionPlus.getInstance().getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = RegionPlus.getInstance().getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        this.econ = rsp.getProvider();
        return true;
    }

    private boolean setupPermissions() {
        if (RegionPlus.getInstance().getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Permission> rsp = RegionPlus.getInstance().getServer().getServicesManager().getRegistration(Permission.class);
        if (rsp == null) {
            return false;
        }
        this.perms = rsp.getProvider();
        return true;
    }

    public boolean vaultEnabled() {
        return this.isEnabled;
    }

    private void setVaultStatus(boolean bool) {
        if (bool) {
            this.enableFeatures();
        }
        this.isEnabled = bool;
    }

    public Economy getEconomy() {
        return this.econ;
    }

    public Permission getPermissions() {
        return this.perms;
    }
}