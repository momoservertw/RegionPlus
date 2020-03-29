package tw.momocraft.regionplus.utils;

import org.bukkit.Bukkit;

public class DependAPI {
	private boolean Residence = false;
	private boolean PlaceHolderAPI = false;
	private boolean ItemJoin = false;
	private boolean Citizens = false;
	private VaultAPI vault;
	
	public DependAPI() {
		this.setResidenceStatus(Bukkit.getServer().getPluginManager().getPlugin("Residence") != null);
		this.setPlaceHolderStatus(Bukkit.getServer().getPluginManager().getPlugin("PlaceHolderAPI") != null);
		this.setItemJoinStatus(Bukkit.getServer().getPluginManager().getPlugin("ItemJoin") != null);
		this.setCitizensStatus(Bukkit.getServer().getPluginManager().getPlugin("Citizens") != null);
		this.setVault();
	}

	public boolean ResidenceEnabled() {
		return this.Residence;
	}
	public boolean PlaceHolderAPIEnabled() {
		return this.PlaceHolderAPI;
	}
	public boolean ItemJoinEnabled() {
		return this.ItemJoin;
	}
	public boolean CitizensEnabled() {
		return this.Citizens;
	}

	public void setResidenceStatus(boolean bool) {
		this.Residence = bool;
	}
	public void setPlaceHolderStatus(boolean bool) {
		this.PlaceHolderAPI = bool;
	}
	public void setItemJoinStatus(boolean bool) {
		this.ItemJoin = bool;
	}
	public void setCitizensStatus(boolean bool) {
		this.Citizens = bool;
	}

	public VaultAPI getVault() {
		return this.vault;
	}

	private void setVault() {
		this.vault = new VaultAPI();
	}
}
