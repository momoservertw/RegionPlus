package tw.momocraft.regionplus.utils;


import com.bekvon.bukkit.residence.Residence;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.utils.locationutils.LocationMap;

import java.util.*;

public class ConfigPath {

    private boolean playerPreventFly;
    private String playerPreventFlyPerm;

    private boolean resAllAreas;
    private boolean resIgnoreWithin;

    private boolean resPrevent;
    private boolean resPreventPotion;
    private boolean resPreventFly;
    private boolean resPreventPainting;
    private boolean resPreventItemFrame;
    private boolean resPreventArmorStand;
    private boolean resPreventZombieDoor;
    private boolean resPreventEndermanPick;
    private boolean resPreventBlockDamage;

    private boolean resSMClimb;
    private boolean resSMCrawl;
    private boolean resSMFlight;
    private boolean resSMMobkick;
    private boolean resSMRoofhang;
    private boolean resSMSlide;
    private boolean resSMSwim;
    private boolean resSMWallkick;

    private boolean points;
    private boolean pointsSelectInfo;
    private Material pointsSelectTool;
    private boolean pointsMode;
    private boolean resIgnoreYPoints;
    private boolean resIgnoreYExpand;

    private Long pointsDefault;
    private Map<String, Long> pointsMap;
    private Map<String, String> pointsDisplayMap;

    private boolean resFlag;
    private int resFlagLimit;
    private int resFlagMaxInterval;
    private boolean resFlagMaxMessage;
    private boolean resFlagBypassCustom;
    private boolean resFlagBypassPerms;
    private boolean resFlagRemove;
    private boolean resFlagRemoveOnly;
    private List<String> resFlagRemoveIgnore;
    private boolean resFlagUpdate;
    private List<String> resFlagUpdateIgnore;
    private boolean resFlagPermsRemove;
    private List<String> resFlagPermsRemoveIgnore;

    private boolean resMsg;
    private boolean resMsgBypassPerm;
    private boolean resMsgMsg;
    private Table<String, String, List<String>> resMsgGroupTable;

    private boolean visitor;
    private boolean visResCreate;
    private boolean visResCreateMsg;
    private boolean visInteractBlock;
    private boolean visInteractBlockUse;
    private boolean visInteractBlockContainer;
    private boolean visInteractEnt;
    private boolean visInteractEntNPC;
    private boolean visDamageEnt;
    private boolean visDamageEntPlayer;
    private boolean visDropItems;
    private boolean visPickupItems;
    private boolean visUseItems;
    private boolean visItemsConsume;
    private boolean visItemsBucket;
    private boolean visItemsProjectile;
    private boolean visItemsFishing;
    private boolean visItemJoin;
    private boolean visInteractBlockMsg;
    private boolean visInteractEntMsg;
    private boolean visDamageEntMsg;
    private boolean visDropItemsMsg;
    private boolean visPickupItemsMsg;
    private boolean visUseItemsMsg;
    private Map<String, LocationMap> visLocMaps;

    public ConfigPath() {
        setUp();
    }

    private void setUp() {
        playerPreventFly = ConfigHandler.getConfig("config.yml").getBoolean("Player.Prevent.Fly-Disable.Enable");
        playerPreventFlyPerm = ConfigHandler.getConfig("config.yml").getString("Player.Prevent.Fly-Disable.Permission");

        resAllAreas = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Settings.All-Areas.Enable");
        resIgnoreWithin = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Settings.All-Areas.Ignore-Within-Area");

        resSMClimb = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Custom-Flags.SurvivalMechanics.Climb");
        resSMCrawl = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Custom-Flags.SurvivalMechanics.Crawl");
        resSMFlight = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Custom-Flags.SurvivalMechanics.Flight");
        resSMMobkick = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Custom-Flags.SurvivalMechanics.Mobkick");
        resSMRoofhang = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Custom-Flags.SurvivalMechanics.Roofhang");
        resSMSlide = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Custom-Flags.SurvivalMechanics.Slide");
        resSMSwim = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Custom-Flags.SurvivalMechanics.Swim");
        resSMWallkick = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Custom-Flags.SurvivalMechanics.Wallkick");

        resPrevent = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Enable");
        resPreventFly = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Fly-Disable");
        resPreventPotion = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Potion-Damage");
        resPreventPainting = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Painting-Destroy");
        resPreventItemFrame = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Item-Frame-Destroy");
        resPreventArmorStand = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Armor-Stand-Destroy");
        resPreventZombieDoor = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent. Zombie-Door-Destruction");
        resPreventEndermanPick = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Enderman-Pickup-Block");
        resPreventBlockDamage = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Block-Damage");

        points = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Points.Enable");
        pointsSelectInfo = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Points.Select-Info");
        pointsSelectTool = Residence.getInstance().getConfigManager().getSelectionTool().getMaterial();
        pointsMode = Residence.getInstance().getConfigManager().isSelectionIgnoreY();
        pointsDefault = ConfigHandler.getConfig("config.yml").getLong("Residence.Points.Groups.Default.Limit");
        pointsMap = new HashMap<>();
        pointsDisplayMap = new HashMap<>();
        ConfigurationSection resPointsGroupsConfig = ConfigHandler.getConfig("config.yml").getConfigurationSection("Residence.Points.Groups");
        if (resPointsGroupsConfig != null) {
            for (String key : resPointsGroupsConfig.getKeys(false)) {
                pointsMap.put(key.toLowerCase(), ConfigHandler.getConfig("config.yml").getLong("Residence.Points.Groups." + key + ".Limit"));
                pointsDisplayMap.put(key.toLowerCase(), ConfigHandler.getConfig("config.yml").getString("Residence.Points.Groups." + key + ".Display"));
            }
            pointsMap = Utils.sortByValue(pointsMap);
        }


        resIgnoreYPoints = ConfigHandler.getConfig("config.yml").getBoolean("Residence.IgnoreY-Changed.Points");
        resIgnoreYExpand = ConfigHandler.getConfig("config.yml").getBoolean("Residence.IgnoreY-Changed.Expand");

        resFlag = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Flags-Editor.Enable");
        /*
        RFAutoCheck = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Flags-Editor.Settings.Auto-Check.Enable");
        RFAutoCheckDelay = ConfigHandler.getConfig("config.yml").getLong("Residence.Flags-Editor.Settings.Auto-Check.Delay") * 20;
         */
        resFlagLimit = ConfigHandler.getConfig("config.yml").getInt("Residence.Flags-Editor.Settings.Max-Edit-Players.Limit");
        resFlagMaxInterval = ConfigHandler.getConfig("config.yml").getInt("Residence.Flags-Editor.Settings.Max-Edit-Players.Interval");
        resFlagMaxMessage = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Flags-Editor.Settings.Max-Edit-Players.Message");
        resFlagBypassCustom = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Flags-Editor.Settings.Bypass.Custom-Flags");
        resFlagBypassPerms = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Flags-Editor.Settings.Bypass.Permission");
        resFlagUpdate = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Flags-Editor.Default.Update.Enable");
        resFlagUpdateIgnore = ConfigHandler.getConfig("config.yml").getStringList("Residence.Flags-Editor.Default.Update.Ignore");
        resFlagRemove = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Flags-Editor.Default.Remove.Enable");
        resFlagRemoveOnly = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Flags-Editor.Default.Remove.Only-No-Perms");
        resFlagRemoveIgnore = ConfigHandler.getConfig("config.yml").getStringList("Residence.Flags-Editor.Default.Remove.Ignore-List");
        resFlagPermsRemove = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Flags-Editor.Permissions.Remove.Enable");
        resFlagPermsRemoveIgnore = ConfigHandler.getConfig("config.yml").getStringList("Residence.Flags-Editor.Permissions.Remove.Ignore-List");

        resMsg = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Message-Editor.Enable");
        resMsgBypassPerm = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Message-Editor.Settings.Bypass.Permission");
        resMsgMsg = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Message-Editor.Settings.Message");
        ConfigurationSection resMsgOldEnterConfig = ConfigHandler.getConfig("config.yml").getConfigurationSection("Residence.Message-Editor.Groups");
        ConfigurationSection resMsgOldLeaveConfig = ConfigHandler.getConfig("config.yml").getConfigurationSection("Residence.Message-Editor.Groups");
        resMsgGroupTable = HashBasedTable.create();
        if (resMsgOldEnterConfig != null) {
            for (String group : resMsgOldEnterConfig.getKeys(false)) {
                resMsgGroupTable.put(group, "enter", ConfigHandler.getConfig("config.yml").getStringList("Residence.Message-Editor.Groups." + group + ".Enter"));
            }
        }
        if (resMsgOldLeaveConfig != null) {
            for (String group : resMsgOldLeaveConfig.getKeys(false)) {
                resMsgGroupTable.put(group, "leave", ConfigHandler.getConfig("config.yml").getStringList("Residence.Message-Editor.Groups." + group + ".Leave"));
            }
        }

        visitor = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Enable");
        visResCreate = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Create-Residence.Enable");
        visResCreateMsg = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Create-Residence.Message");
        visInteractBlock = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Interact-Blocks.Enable");
        visInteractBlockMsg = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Interact-Blocks.Message");
        visInteractBlockUse = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Interact-Blocks.Allow-Use");
        visInteractBlockContainer = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Interact-Blocks.Allow-Container");
        visInteractEnt = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Interact-Entities.Enable");
        visInteractEntMsg = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Interact-Entities.Message");
        visInteractEntNPC = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Interact-Entities.Allow-NPC");
        visDamageEnt = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Damage-Entities.Enable");
        visDamageEntMsg = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Damage-Entities.Message");
        visDamageEntPlayer = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Damage-Entities.Allow-Player");
        visDropItems = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Drop-Items.Enable");
        visDropItemsMsg = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Drop-Items.Message");
        visPickupItems = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Pickup-Items.Enable");
        visPickupItemsMsg = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Pickup-Items.Message");
        visUseItems = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Use-Items.Enable");
        visUseItemsMsg = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Use-Items.Message");
        visItemsConsume = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Use-Items.Allow-Consume");
        visItemsBucket = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Use-Items.Allow-Bucket");
        visItemsProjectile = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Use-Items.Allow-Projectile");
        visItemsFishing = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Use-Items.Allow-Fishing");
        visItemJoin = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Prevent.List.Use-Items.Allow-ItemJoin");
        visLocMaps = getLocationMaps("Visitor.Location");
    }

    private Map<String, LocationMap> getLocationMaps(String path) {
        Map<String, LocationMap> locMaps = new HashMap<>();
        ConfigurationSection locConfig = ConfigHandler.getConfig("config.yml").getConfigurationSection(path);
        ConfigurationSection areaConfig;
        LocationMap locMap;
        LocationMap locWorldMap = new LocationMap();
        List<String> worlds = new ArrayList<>();
        if (locConfig != null) {
            for (String group : locConfig.getKeys(false)) {
                if (ConfigHandler.getConfig("config.yml").getConfigurationSection(path + "." + group) == null) {
                    worlds.add(group);
                    continue;
                }
                locMap = new LocationMap();
                locMap.setWorlds(ConfigHandler.getConfig("config.yml").getStringList(path + "." + group + ".Worlds"));
                areaConfig = ConfigHandler.getConfig("config.yml").getConfigurationSection(path + "." + group + ".Area");
                if (areaConfig != null) {
                    for (String type : areaConfig.getKeys(false)) {
                        locMap.addCord(type, ConfigHandler.getConfig("config.yml").getString(path + "." + group + ".Area." + type));
                    }
                }
                locMaps.put(group, locMap);
            }
        } else {
            worlds.addAll(ConfigHandler.getConfig("config.yml").getStringList(path));
        }
        locWorldMap.setWorlds(worlds);
        locMaps.put("worldList", locWorldMap);
        return locMaps;
    }


    public boolean isPlayerPreventFly() {
        return playerPreventFly;
    }

    public String getPlayerPreventFlyPerm() {
        return playerPreventFlyPerm;
    }


    public boolean isResAllAreas() {
        return resAllAreas;
    }

    public boolean isResIgnoreWithin() {
        return resIgnoreWithin;
    }


    public boolean isResPrevent() {
        return resPrevent;
    }

    public boolean isResPreventFly() {
        return resPreventFly;
    }

    public boolean isResPreventPotion() {
        return resPreventPotion;
    }

    public boolean isResPreventItemFrame() {
        return resPreventItemFrame;
    }

    public boolean isResPreventPainting() {
        return resPreventPainting;
    }

    public boolean isResPreventArmorStand() {
        return resPreventArmorStand;
    }

    public boolean isResPreventZombieDoor() {
        return resPreventZombieDoor;
    }

    public boolean isResPreventEndermanPick() {
        return resPreventEndermanPick;
    }

    public boolean isResPreventBlockDamage() {
        return resPreventBlockDamage;
    }


    public boolean isResSMClimb() {
        return resSMClimb;
    }

    public boolean isResSMCrawl() {
        return resSMCrawl;
    }

    public boolean isResSMFlight() {
        return resSMFlight;
    }

    public boolean isResSMMobkick() {
        return resSMMobkick;
    }

    public boolean isResSMRoofhang() {
        return resSMRoofhang;
    }

    public boolean isResSMSlide() {
        return resSMSlide;
    }

    public boolean isResSMSwim() {
        return resSMSwim;
    }

    public boolean isResSMWallkick() {
        return resSMWallkick;
    }


    public boolean isPoints() {
        return points;
    }

    public boolean isPointsSelectInfo() {
        return pointsSelectInfo;
    }

    public Material getPointsSelectTool() {
        return pointsSelectTool;
    }

    public boolean isPointsMode() {
        return pointsMode;
    }


    public boolean isResIgnoreYPoints() {
        return resIgnoreYPoints;
    }

    public boolean isResIgnoreYExpand() {
        return resIgnoreYExpand;
    }


    public Long getPointsDefault() {
        return pointsDefault;
    }

    public Map<String, Long> getPointsMap() {
        return pointsMap;
    }

    public Map<String, String> getPointsDisplayMap() {
        return pointsDisplayMap;
    }


    public boolean isResFlag() {
        return resFlag;
    }

    /*
    public boolean isRFAutoCheck() {
        return RFAutoCheck;
    }

    public long getRFAutoCheckDelay() {
        return RFAutoCheckDelay;
    }
     */

    public int getResFlagLimit() {
        return resFlagLimit;
    }

    public int getResFlagMaxInterval() {
        return resFlagMaxInterval;
    }

    public boolean isResFlagMaxMessage() {
        return resFlagMaxMessage;
    }

    public boolean isResFlagBypassCustom() {
        return resFlagBypassCustom;
    }

    public boolean isResFlagBypassPerms() {
        return resFlagBypassPerms;
    }

    public boolean isResFlagUpdate() {
        return resFlagUpdate;
    }

    public List<String> getResFlagUpdateIgnore() {
        return resFlagUpdateIgnore;
    }

    public boolean isResFlagRemove() {
        return resFlagRemove;
    }

    public boolean isResFlagRemoveOnly() {
        return resFlagRemoveOnly;
    }

    public List<String> getResFlagRemoveIgnore() {
        return resFlagRemoveIgnore;
    }

    public List<String> getResFlagPermsRemoveIgnore() {
        return resFlagPermsRemoveIgnore;
    }

    public boolean isResFlagPermsRemove() {
        return resFlagPermsRemove;
    }


    public boolean isResMsg() {
        return resMsg;
    }

    public boolean isResMsgBypassPerm() {
        return resMsgBypassPerm;
    }

    public boolean isResMsgMsg() {
        return resMsgMsg;
    }

    public Table<String, String, List<String>> getResMsgGroupTable() {
        return resMsgGroupTable;
    }

    public boolean isVisitor() {
        return visitor;
    }

    public boolean isVisResCreate() {
        return visResCreate;
    }

    public boolean isVisResCreateMsg() {
        return visResCreateMsg;
    }

    public boolean isVisInteractBlock() {
        return visInteractBlock;
    }

    public boolean isVisInteractEnt() {
        return visInteractEnt;
    }

    public boolean isVisInteractBlockContainer() {
        return visInteractBlockContainer;
    }

    public boolean isVisInteractBlockUse() {
        return visInteractBlockUse;
    }

    public boolean isVisInteractEntNPC() {
        return visInteractEntNPC;
    }

    public boolean isVisDamageEnt() {
        return visDamageEnt;
    }

    public boolean isVisDamageEntPlayer() {
        return visDamageEntPlayer;
    }

    public boolean isVisDropItems() {
        return visDropItems;
    }

    public boolean isVisItemsBucket() {
        return visItemsBucket;
    }

    public boolean isVisItemsConsume() {
        return visItemsConsume;
    }

    public boolean isVisItemsFishing() {
        return visItemsFishing;
    }

    public boolean isVisItemJoin() {
        return visItemJoin;
    }

    public boolean isVisItemsProjectile() {
        return visItemsProjectile;
    }

    public boolean isVisPickupItems() {
        return visPickupItems;
    }

    public boolean isVisUseItems() {
        return visUseItems;
    }

    public boolean isVisDamageEntMsg() {
        return visDamageEntMsg;
    }

    public boolean isVisDropItemsMsg() {
        return visDropItemsMsg;
    }

    public boolean isVisInteractBlockMsg() {
        return visInteractBlockMsg;
    }

    public boolean isVisInteractEntMsg() {
        return visInteractEntMsg;
    }

    public boolean isVisPickupItemsMsg() {
        return visPickupItemsMsg;
    }

    public boolean isVisUseItemsMsg() {
        return visUseItemsMsg;
    }

    public Map<String, LocationMap> getVisLocMaps() {
        return visLocMaps;
    }
}