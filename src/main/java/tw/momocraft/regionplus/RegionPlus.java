package tw.momocraft.regionplus;

import org.bukkit.plugin.java.JavaPlugin;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.handlers.ServerHandler;

public class RegionPlus extends JavaPlugin {
    private static RegionPlus instance;

    @Override
    public void onEnable() {
        instance = this;
        ConfigHandler.generateData(false);
        ConfigHandler.registerEvents();
        ServerHandler.sendConsoleMessage("&fhas been Enabled.");
    }

    @Override
    public void onDisable() {
        ServerHandler.sendConsoleMessage("&fhas been Disabled.");
    }

    public static RegionPlus getInstance() {
        return instance;
    }
}