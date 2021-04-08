package tw.momocraft.regionplus.listeners;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.api.ResidenceApi;
import com.bekvon.bukkit.residence.event.ResidenceCreationEvent;
import com.bekvon.bukkit.residence.event.ResidenceDeleteEvent;
import com.bekvon.bukkit.residence.event.ResidenceOwnerChangeEvent;
import com.bekvon.bukkit.residence.event.ResidenceSizeChangeEvent;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.CuboidArea;
import com.bekvon.bukkit.residence.selection.SelectionManager;
import javafx.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.regionplus.RegionPlus;
import tw.momocraft.regionplus.handlers.ConfigHandler;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ResidencePoints implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        if (!ConfigHandler.getConfigPath().isPoints())
            return;
        new BukkitRunnable() {
            @Override
            public void run() {
                refreshPointsMap(e.getPlayer());
            }
        }.runTaskLater(RegionPlus.getInstance(), 20);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onResidenceDeleteEvent(ResidenceDeleteEvent e) {
        if (!ConfigHandler.getConfigPath().isPoints())
            return;
        new BukkitRunnable() {
            @Override
            public void run() {
                refreshPointsMap(e.getPlayer());
            }
        }.runTaskLater(RegionPlus.getInstance(), 10);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerQuitEvent(PlayerQuitEvent e) {
        if (!ConfigHandler.getConfigPath().isPoints())
            return;
        new BukkitRunnable() {
            @Override
            public void run() {
                removePointsMap(e.getPlayer().getName());
            }
        }.runTaskLater(RegionPlus.getInstance(), 20);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void selectInform(PlayerInteractEvent e) {
        if (!ConfigHandler.getConfigPath().isPoints())
            return;
        if (!ConfigHandler.getConfigPath().isPointsSelectInfo())
            return;
        if (!e.getMaterial().equals(ConfigHandler.getConfigPath().getPointsSelectTool()))
            return;
        Player player = e.getPlayer();
        SelectionManager sm = Residence.getInstance().getSelectionManager();
        Location loc1 = sm.getSelection(player).getBaseLoc1();
        Location loc2 = sm.getSelection(player).getBaseLoc2();
        if (loc1 == null || loc2 == null)
            return;
        World world1 = loc1.getWorld();
        World world2 = loc2.getWorld();
        if (world2 == null || world1 != world2)
            return;
        double size;
        if (ConfigHandler.getConfigPath().isResIgnoreY()) {
            size = Math.abs(loc1.getBlockX() - loc2.getBlockX()) * Math.abs(loc1.getBlockZ() - loc2.getBlockZ());
        } else {
            size = Math.abs(loc1.getBlockX() - loc2.getBlockX()) * Math.abs(loc1.getBlockZ() - loc2.getBlockZ())
                    * Math.abs(loc1.getBlockY() - loc2.getBlockY());
        }
        /////
        Residence.getInstance().getConfigManager().useActionBarOnSelection();
        CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                ConfigHandler.getConfigPath().getMsgPointsSelect(), player,
                getPointsPlaceholders(player, size));
        CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPlugin(),
                "Residence-Points", player.getName(), "selectInform", "return", "show",
                new Throwable().getStackTrace()[0]);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onResidenceCreation(ResidenceCreationEvent e) {
        if (!ConfigHandler.getConfigPath().isPoints())
            return;
        Player player = e.getPlayer();
        String playerName = player.getName();
        if (CorePlusAPI.getPlayer().hasPerm(player, "regionplus.bypass.points.limit")) {
            CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPlugin(),
                    "Residence-Points", playerName, "Create", "return", "bypass permission",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        ClaimedResidence res = e.getResidence();
        CuboidArea area = res.getMainArea();
        refreshPointsMap(e.getPlayer());
        double size = getResidenceSize(area);
        double last = getPointsLastMap().get(playerName);
        if (size > last) {
            e.setCancelled(true);
            // Sending "not enough points "message.
            CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                    "Message.notEnoughMoney",
                    player, getPointsPlaceholders(player, size));
            // Sending "return money" message.
            double cost = res.getBlockSellPrice() * size;
            String[] placeHolders = CorePlusAPI.getMsg().newString();
            placeHolders[9] = "money"; // %pricetype%
            placeHolders[10] = String.valueOf(cost); // %price%
            CorePlusAPI.getPlayer().giveCurrency(player.getUniqueId(), "money", cost);
            CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                    ConfigHandler.getConfigPath().getMsgCmdResReturnIgnoreY(), player, placeHolders);
            CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPlugin(),
                    "Residence-Points", playerName, "Create Residence", "cancel", "notEnoughPoints",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        // Sending "new points balance" message.
        new BukkitRunnable() {
            @Override
            public void run() {
                refreshPointsMap(player);
                sendPointsMsg(player);
                CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPlugin(),
                        "Residence-Points", playerName, "Create Residence", "success", "final",
                        new Throwable().getStackTrace()[0]);
            }
        }.runTaskLater(RegionPlus.getInstance(), 10);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onResidenceOwnerChange(ResidenceOwnerChangeEvent e) {
        if (!ConfigHandler.getConfigPath().isPoints())
            return;
        ClaimedResidence res = e.getResidence();
        String resName = res.getName();
        UUID newOwnerUUID = e.getNewOwnerUuid();
        Player newOwner = Bukkit.getPlayer(newOwnerUUID);
        String newOwnerName = e.getNewOwner();
        UUID ownerUUID = res.getOwnerUUID();
        Player owner = Bukkit.getPlayer(ownerUUID);
        // Cancel event if the new owner not online.
        if (newOwner == null) {
            e.setCancelled(true);
            String[] placeHolders = CorePlusAPI.getMsg().newString();
            placeHolders[1] = newOwnerName; // %targetplayer%
            CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                    "Message.targetNotFound", owner, placeHolders);
            //  Returning the money of trade.
            if (res.isForSell()) {
                double price = res.getSellPrice();
                CorePlusAPI.getPlayer().takeCurrency(newOwnerUUID, "money", price);
                CorePlusAPI.getPlayer().giveCurrency(ownerUUID, "money", price);
                placeHolders[9] = "money"; // %pricetype%
                placeHolders[10] = String.valueOf(price); // %price%
                CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                        ConfigHandler.getConfigPath().getMsgResReturnMoney(), owner, placeHolders);
            }
            return;
        }
        if (CorePlusAPI.getPlayer().hasPerm(newOwner, "regionplus.bypass.points.limit")) {
            CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPlugin(),
                    "Residence", newOwnerName, "Points", "return", "bypass permission, " + resName,
                    new Throwable().getStackTrace()[0]);
            return;
        }
        refreshPointsMap(newOwner);
        int size = getResidenceSize(res.getMainArea());
        double last = getPointsLastMap().get(newOwnerName);
        if (last < size) {
            e.setCancelled(true);
            String[] placeHolders = CorePlusAPI.getMsg().newString();
            placeHolders[1] = newOwnerName; // %targetplayer%
            placeHolders[9] = "res_points"; // %pricetype%
            placeHolders[10] = String.valueOf(size); // %price%
            CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                    "Message.notEnoughMoney", newOwner, placeHolders);
            CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                    "Message.notEnoughMoneyTarget", owner, placeHolders);
            //  Returning the money of trade.
            if (res.isForSell()) {
                double price = res.getSellPrice();
                CorePlusAPI.getPlayer().takeCurrency(newOwnerUUID, "money", price);
                CorePlusAPI.getPlayer().giveCurrency(ownerUUID, "money", price);
                placeHolders[9] = "money"; // %pricetype%
                placeHolders[10] = String.valueOf(price); // %price%
                CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                        ConfigHandler.getConfigPath().getMsgResReturnMoney(), owner, placeHolders);
            }
            CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPlugin(),
                    "Residence-Points", newOwnerName, "Owner Change", "cancel", "targetNotEnoughPoints, " + resName,
                    new Throwable().getStackTrace()[0]);
            return;
        }
        // Sending "new points balance" message.
        new BukkitRunnable() {
            @Override
            public void run() {
                refreshPointsMap(newOwner);
                sendPointsMsg(newOwner);
                if (owner != null) {
                    refreshPointsMap(owner);
                    sendPointsMsg(owner);
                }
                CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPlugin(),
                        "Residence-Points", newOwnerName, "Owner Change", "success", "final",
                        new Throwable().getStackTrace()[0]);
            }
        }.runTaskLater(RegionPlus.getInstance(), 10);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onResidenceSizeChange(ResidenceSizeChangeEvent e) {
        if (!ConfigHandler.getConfigPath().isPoints())
            return;
        Player player = e.getPlayer();
        String playerName = player.getName();
        if (CorePlusAPI.getPlayer().hasPerm(player, "regionplus.bypass.points.limit")) {
            CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPlugin(),
                    "Residence-Points", playerName, "Size Change", "return", "bypass permission",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        ClaimedResidence res = e.getResidence();
        String resName = res.getName();
        CuboidArea area = e.getOldArea();
        CuboidArea newArea = e.getNewArea();
        int size = getResidenceSize(newArea) - getResidenceSize(area);
        double cost = res.getBlockSellPrice() * (size);
        // Prevent Limit Y size change.
        if (ConfigHandler.getConfigPath().isPointsPreventLimitedYSizeChange()) {
            if (!isResidenceIgnoreY(area)) {
                CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                        ConfigHandler.getConfigPath().getMsgSizeChangeFailed(), player);
                if (cost >= 0) {
                    CorePlusAPI.getPlayer().giveCurrency(player.getUniqueId(), "money", cost);
                } else {
                    CorePlusAPI.getPlayer().takeCurrency(player.getUniqueId(), "money", -cost);
                }
                CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                        ConfigHandler.getConfigPath().getMsgResReturnMoney(), player);
                CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPlugin(),
                        "Residence-Points", playerName, "Size Change", "fail", "Limit Y size change",
                        new Throwable().getStackTrace()[0]);
                e.setCancelled(true);
                return;
            }
        }
        // Prevent multiple areas residence size change.
        if (ConfigHandler.getConfigPath().isPointsPreventAreasSizeChange()) {
            if (res.getAreaCount() > 1) {
                CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                        ConfigHandler.getConfigPath().getMsgSizeChangeFailed(), player);
                if (cost >= 0) {
                    CorePlusAPI.getPlayer().giveCurrency(player.getUniqueId(), "money", cost);
                } else {
                    CorePlusAPI.getPlayer().takeCurrency(player.getUniqueId(), "money", -cost);
                }
                CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                        ConfigHandler.getConfigPath().getMsgResReturnMoney(), player);
                CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPlugin(),
                        "Residence-Points", playerName, "Size Change", "fail", "Multiple areas size change",
                        new Throwable().getStackTrace()[0]);
                e.setCancelled(true);
                return;
            }
        }
        if (size <= 0) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    refreshPointsMap(e.getPlayer());
                    CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPlugin(),
                            "Residence-Points", playerName, "Size Change", "return", "Contract, " + resName,
                            new Throwable().getStackTrace()[0]);
                }
            }.runTaskLater(RegionPlus.getInstance(), 20);
            return;
        }
        refreshPointsMap(e.getPlayer());
        double last = getPointsLastMap().get(playerName);
        if (size > last) {
            String[] placeHolders = CorePlusAPI.getMsg().newString();
            placeHolders[9] = "res_points"; // %pricetype%
            placeHolders[10] = String.valueOf(size); // %price%
            placeHolders[11] = String.valueOf(last); // %balance%
            CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                    "Message.notEnoughMoney", player, placeHolders);
            CorePlusAPI.getPlayer().giveCurrency(player.getUniqueId(), "money", cost);
            CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                    ConfigHandler.getConfigPath().getMsgResReturnMoney(), player, placeHolders);
            CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPlugin(),
                    "Residence-Points", playerName, "Size Change", "cancel", "notEnoughPoints, " + resName,
                    new Throwable().getStackTrace()[0]);
            e.setCancelled(true);
            return;
        }
        // Sending "new points balance" message.
        new BukkitRunnable() {
            @Override
            public void run() {
                refreshPointsMap(player);
                sendPointsMsg(player);
                CorePlusAPI.getMsg().sendDetailMsg(ConfigHandler.isDebug(), ConfigHandler.getPlugin(),
                        "Residence-Points", playerName, "Size Change", "success", "final",
                        new Throwable().getStackTrace()[0]);
            }
        }.runTaskLater(RegionPlus.getInstance(), 10);
    }


    // Load the points status when player "join", "quit", "create residence", "expend/contract residence".
    private static final Map<String, Double> pointsLimitMap = new HashMap<>();
    private static final Map<String, Double> pointsUseMap = new HashMap<>();
    private static final Map<String, Double> pointsLastMap = new HashMap<>();

    public static void sendPointsMsg(Player player) {
        CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                ConfigHandler.getConfigPath().getMsgPoints(), player, getPointsPlaceholders(player, 0));
    }

    public static void sendTargetPointsMsg(CommandSender sender, Player player) {
        CorePlusAPI.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                ConfigHandler.getConfigPath().getMsgTargetPoints(), sender, getPointsPlaceholders(player, 0));
    }

    public static void refreshPointsMap(Player player) {
        String playerName = player.getName();
        double limit = getPointsLimit(player);
        pointsLimitMap.put(playerName, limit);
        double used = getPointsUsed(playerName);
        pointsUseMap.put(playerName, used);
        double last = limit - used;
        pointsLastMap.put(playerName, last);
        for (String key : pointsLimitMap.keySet())
            System.out.println(key + ": " + pointsLimitMap.get(key));
        for (String key : pointsUseMap.keySet())
            System.out.println(key + ": " + pointsUseMap.get(key));
    }

    public static void removePointsMap(String playerName) {
        pointsLimitMap.remove(playerName);
        pointsUseMap.remove(playerName);
        pointsLastMap.remove(playerName);
        for (String key : pointsLimitMap.keySet())
            System.out.println(key + ": " + pointsLimitMap.get(key));
        for (String key : pointsUseMap.keySet())
            System.out.println(key + ": " + pointsUseMap.get(key));
    }

    public static Map<String, Double> getPointsLimitMap() {
        return pointsLimitMap;
    }

    public static Map<String, Double> getPointsUseMap() {
        return pointsUseMap;
    }

    public static Map<String, Double> getPointsLastMap() {
        return pointsLastMap;
    }

    public static int getPointsUsed(String playerName) {
        int used = 0;
        CuboidArea area;
        for (ClaimedResidence res : ResidenceApi.getPlayerManager().getResidencePlayer(playerName).getResList()) {
            area = res.getMainArea();
            if (ConfigHandler.getConfigPath().isResIgnoreY() &&
                    !ConfigHandler.getConfigPath().isPointsLimitedYCalculate()) {
                if (isResidenceIgnoreY(area))
                    continue;
            }
            used += getResidenceSize(area);
        }
        return used;
    }

    public static double getPointsLimit(Player player) {
        double group = getPointsGroup(player).getValue();
        double level = getPointsLevel(player);
        return group + level;
    }

    private static double getPointsLevel(Player player) {
        return CorePlusAPI.getPlayer().getMaxPerm(player, "regionplus.points.level.", 0);
    }

    private static Pair<String, Integer> getPointsGroup(Player player) {
        Map<String, Integer> groupMap = ConfigHandler.getConfigPath().getPointsMap();
        if (player.isOp() || CorePlusAPI.getPlayer().hasPerm(player, "regionplus.points.group.*")) {
            String highestGroup = groupMap.keySet().iterator().next();
            return new Pair<>(highestGroup, groupMap.get(highestGroup));
        }
        for (String group : groupMap.keySet()) {
            if (CorePlusAPI.getPlayer().hasPerm(player, "regionplus.points.group." + group))
                return new Pair<>(group, groupMap.get(group));
        }
        return new Pair<>("default", groupMap.get("default"));
    }

    public static int getResidenceSize(CuboidArea area) {
        int size = 0;
        if (ConfigHandler.getConfigPath().isResIgnoreY())
            size += area.getXSize() * area.getZSize();
        else
            size += area.getXSize() * area.getZSize() * area.getYSize();
        return size;
    }

    public static boolean isResidenceIgnoreY(CuboidArea area) {
        if (area.getWorld().getEnvironment().name().equals("NETHER"))
            return area.getYSize() < 129;
        return area.getYSize() >= 256;
    }

    public static String[] getPointsPlaceholders(Player player, double size) {
        String[] placeHolders = CorePlusAPI.getMsg().newString();
        String playerName = player.getName();
        double limit = pointsLimitMap.get(playerName);
        double used = pointsUseMap.get(playerName);
        double last = pointsLastMap.get(playerName);
        // %group%
        //placeHolders[5] = ConfigHandler.getConfigPath().getPointsDisplayMap().get();
        // %size%
        placeHolders[15] = new DecimalFormat("0").format(size);
        // %pricetype%
        placeHolders[9] = "res_points";
        // %price%
        placeHolders[10] = new DecimalFormat("0").format(
                Residence.getInstance().getPlayerManager().getGroup(player.getName()).getCostPerBlock() * size);
        // %amount%
        placeHolders[6] = new DecimalFormat("0").format(used);
        // %limit%
        placeHolders[23] = new DecimalFormat("0").format(limit);
        // %balance%
        placeHolders[11] = new DecimalFormat("0").format(last);
        return placeHolders;
    }

    // Return the money difference if you changed the option "IgnoreY" in Residence config.
    public static void returnIgnoreY(CommandSender sender) {
        CorePlusAPI.getMsg().sendMsg(ConfigHandler.getPluginPrefix(), sender, "&6Starting to return money...");
        ClaimedResidence res;
        double price;
        CuboidArea area;
        Map<String, ClaimedResidence> resMap = Residence.getInstance().getResidenceManager().getResidences();
        for (String resName : resMap.keySet()) {
            res = resMap.get(resName);
            area = res.getMainArea();
            if (Residence.getInstance().getConfigManager().isSelectionIgnoreY() &&
                    !ConfigHandler.getConfigPath().isPointsLimitedYCalculate())
                if (isResidenceIgnoreY(area))
                    continue;
            if (res.getBlockSellPrice() == 0)
                continue;
            price = area.getXSize() * area.getZSize() * (area.getYSize() - 1) * res.getBlockSellPrice();
            CorePlusAPI.getPlayer().giveCurrency(res.getOwnerUUID(), "money", price);
            CorePlusAPI.getMsg().sendConsoleMsg(ConfigHandler.getPrefix(),
                    "Return - &6" + resName + ": &e" + price + ", &a" + res.getOwner());
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                CorePlusAPI.getMsg().sendMsg(ConfigHandler.getPrefix(), sender,
                        "&6Succeed to return money to all residences!");
            }
        }.runTaskLater(RegionPlus.getInstance(), 40);
    }
}
