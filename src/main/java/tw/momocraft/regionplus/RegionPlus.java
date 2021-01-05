package tw.momocraft.regionplus;

import org.bukkit.plugin.java.JavaPlugin;
import tw.momocraft.regionplus.handlers.ConfigHandler;

public class RegionPlus extends JavaPlugin {
    private static RegionPlus instance;

    @Override
    public void onEnable() {
        instance = this;
        ConfigHandler.generateData(false);
        ConfigHandler.registerEvents();
        CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPlugin(), "&fhas been Enabled.");
    }

    @Override
    public void onDisable() {
        CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPlugin(), "&fhas been Disabled.");
    }

    public static RegionPlus getInstance() {
        return instance;
    }
}