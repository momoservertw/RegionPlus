package tw.momocraft.regionplus.listeners;

import com.bekvon.bukkit.residence.event.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.regionplus.handlers.ConfigHandler;

public class TestEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onResidenceEvent(ResidenceEvent e) {
        CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPlugin(), "ResidenceEvent");
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onResidenceFlagEvent(ResidenceFlagEvent e) {
        CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPlugin(), "ResidenceFlagEvent");
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onResidencePlayerEvent(ResidencePlayerEvent e) {
        CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPlugin(), "ResidencePlayerEvent");
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onResidenceChangedEvent(ResidenceChangedEvent e) {
        CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPlugin(), "ResidenceChangedEvent");
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onResidenceCommandEvent(ResidenceCommandEvent e) {
        CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPlugin(), "ResidenceCommandEvent");
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onResidenceCreationEvent(ResidenceCreationEvent e) {
        CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPlugin(), "ResidenceCreationEvent");
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onResidenceDeleteEvent(ResidenceDeleteEvent e) {
        CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPlugin(), "ResidenceDeleteEvent");
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onResidenceFlagCheckEvent(ResidenceFlagCheckEvent e) {
        CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPlugin(), "ResidenceFlagCheckEvent");
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onResidenceOwnerChangeEvent(ResidenceOwnerChangeEvent e) {
        CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPlugin(), "ResidenceOwnerChangeEvent");
        if (e.getResidence().isForSell()) {
            CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPlugin(), "isForSell");
        }
        e.setCancelled(true);
        if (e.isCancelled()) {
            CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPlugin(), "isCancelled");
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onResidenceRaidStartEvent(ResidenceRaidStartEvent e) {
        CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPlugin(), "ResidenceRaidStartEvent");
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onResidenceRaidPreStartEvent(ResidenceRaidPreStartEvent e) {
        CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPlugin(), "ResidenceDeleteEvent");
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onResidenceSizeChangeEvent(ResidenceSizeChangeEvent e) {
        CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPlugin(), "ResidenceSizeChangeEvent");
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onResidenceSizeChangeEvent(ResidenceAreaAddEvent e) {
        CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPlugin(), "ResidenceChangedEvent");
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onResidenceSizeChangeEvent(ResidenceAreaDeleteEvent e) {
        CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPlugin(), "ResidenceChangedEvent");
    }
}
