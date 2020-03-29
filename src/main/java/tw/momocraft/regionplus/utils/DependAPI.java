package tw.momocraft.regionplus.utils;

import org.bukkit.Bukkit;

public class DependAPI {
	private boolean Residence = false;
	private boolean PlaceHolderAPI = false;
	private boolean WorldBorder = false;
	private VaultAPI vault;
	
	public DependAPI() {
		this.setResidenceStatus(Bukkit.getServer().getPluginManager().getPlugin("Residence") != null);
		this.setPlaceHolderStatus(Bukkit.getServer().getPluginManager().getPlugin("PlaceHolderAPI") != null);
		this.setWorldBorderStatus(Bukkit.getServer().getPluginManager().getPlugin("WorldBorder") != null);
		this.setVault();
	}

	public boolean ResidenceEnabled() {
		return this.Residence;
	}
	public boolean PlaceHolderAPIEnabled() {
		return this.PlaceHolderAPI;
	}
	public boolean WorldBorderEnabled() {
		return this.WorldBorder;
	}

	public void setResidenceStatus(boolean bool) {
		this.Residence = bool;
	}
	public void setPlaceHolderStatus(boolean bool) {
		this.PlaceHolderAPI = bool;
	}
	public void setWorldBorderStatus(boolean bool) {
		this.WorldBorder = bool;
	}

	public VaultAPI getVault() {
		return this.vault;
	}

	private void setVault() {
		this.vault = new VaultAPI();
	}
}
