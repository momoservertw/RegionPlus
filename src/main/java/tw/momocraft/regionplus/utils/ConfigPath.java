package tw.momocraft.regionplus.utils;


import com.bekvon.bukkit.residence.Residence;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import tw.momocraft.regionplus.handlers.ConfigHandler;
import tw.momocraft.regionplus.handlers.ServerHandler;
import tw.momocraft.regionplus.utils.locationutils.LocationMap;
import tw.momocraft.regionplus.utils.locationutils.LocationUtils;

import java.util.*;

public class ConfigPath {
    public ConfigPath() {
        setUp();
    }

    //  ============================================== //
    //         General Settings                        //
    //  ============================================== //
    private static LocationUtils locationUtils;


    //  ============================================== //
    //         Player Settings                         //
    //  ============================================== //
    private boolean playerPreventFly;
    private String playerPreventFlyPerm;

    //  ============================================== //
    //         Residence Settings                      //
    //  ============================================== //
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

    //  ============================================== //
    //         Visitor Settings                        //
    //  ============================================== //
    private boolean visitor;

    private Map<String, Map<String, VisitorMap>> visitorProp = new HashMap<>();

    private void setUp() {
        locationUtils = new LocationUtils();

        setPlayer();
        setResidence();
        setVisitor();
    }

    private void setPlayer() {
        playerPreventFly = ConfigHandler.getConfig("config.yml").getBoolean("Player.Prevent.Fly-Disable.Enable");
        playerPreventFlyPerm = ConfigHandler.getConfig("config.yml").getString("Player.Prevent.Fly-Disable.Permission");
    }

    private void setResidence() {
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
    }

    private void setVisitor() {
        visitor = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Enable");
        boolean resOwn = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Ignore.Has-Residence-Permissions");

        boolean resCreate = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Prevent.Create-Residence.Enable");
        boolean resCreateMsg = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Prevent.Create-Residence.Message");

        boolean useItems = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Prevent.Use-Items.Enable");
        boolean useItemsMsg = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Prevent.Use-Items.Message");
        boolean itemsConsume = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Prevent.Use-Items.Allow-Consume");
        boolean itemsBucket = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Prevent.Use-Items.Allow-Bucket");
        boolean itemsProjectile = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Prevent.Use-Items.Allow-Projectile");
        boolean itemsFishing = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Prevent.Use-Items.Allow-Fishing");
        boolean visItemJoin = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Prevent.Use-Items.Allow-ItemJoin");

        boolean visInterBlock = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Prevent.Interact-Blocks.Enable");
        boolean visInterBlockMsg = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Prevent.Interact-Blocks.Message");
        boolean visInterBlockUse = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Prevent.Interact-Blocks.Allow-Use");
        boolean visInterBlockCont = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Prevent.Interact-Blocks.Allow-Container");

        boolean visInterEnt = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Prevent.Interact-Entities.Enable");
        boolean visInterEntMsg = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Prevent.Interact-Entities.Message");
        boolean visInterEntNPC = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Prevent.Interact-Entities.Allow-NPC");

        boolean visDamageEnt = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Prevent.Damage-Entities.Enable");
        boolean visDamageEntMsg = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Prevent.Damage-Entities.Message");
        boolean visDamageEntPlayer = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Prevent.Damage-Entities.Allow-Player");

        boolean visDropItems = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Prevent.Drop-Items.Enable");
        boolean visDropItemsMsg = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Prevent.Drop-Items.Message");

        boolean visPickupItems = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Prevent.Pickup-Items.Enable");
        boolean visPickupItemsMsg = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Prevent.Pickup-Items.Message");


        ConfigurationSection groupsConfig = ConfigHandler.getConfig("config.yml").getConfigurationSection("Visitor.Groups");
        if (groupsConfig != null) {
            String groupEnable;
            VisitorMap visitorMap;
            List<LocationMap> locMaps;
            for (String group : groupsConfig.getKeys(false)) {
                groupEnable = ConfigHandler.getConfig("config.yml").getString("Visitor.Groups." + group + ".Enable");
                if (groupEnable == null || groupEnable.equals("true")) {
                    visitorMap = new VisitorMap();
                    visitorMap.setPriority(ConfigHandler.getConfig("config.yml").getLong("Visitor.Groups." + group + ".Priority"));
                    visitorMap.setValue("resOwn", ConfigHandler.getConfig("config.yml").getString("Visitor.Groups." + group + ".Ignore.Has-Residence-Permissions"), resOwn);

                    visitorMap.setValue("resCreate", ConfigHandler.getConfig("config.yml").getString("Visitor.Groups." + group + ".Prevent.Create-Residence.Enable"), resCreate);
                    visitorMap.setValue("resCreateMsg", ConfigHandler.getConfig("config.yml").getString("Visitor.Groups." + group + ".Prevent.Create-Residence.Message"), resCreateMsg);

                    visitorMap.setValue("useItems", ConfigHandler.getConfig("config.yml").getString("Visitor.Groups." + group + ".Prevent.Use-Items.Enable"), useItems);
                    visitorMap.setValue("useItemsMsg", ConfigHandler.getConfig("config.yml").getString("Visitor.Groups." + group + ".Prevent.Use-Items.Message"), useItemsMsg);
                    visitorMap.setValue("itemsConsume", ConfigHandler.getConfig("config.yml").getString("Visitor.Groups." + group + ".Prevent.Use-Items.Allow-Consume"), itemsConsume);
                    visitorMap.setValue("itemsBucket", ConfigHandler.getConfig("config.yml").getString("Visitor.Groups." + group + ".Prevent.Use-Items.Allow-Bucket"), itemsBucket);
                    visitorMap.setValue("itemProjectile", ConfigHandler.getConfig("config.yml").getString("Visitor.Groups." + group + ".Prevent.Use-Items.Allow-Projectile"), itemsProjectile);
                    visitorMap.setValue("itemFishing", ConfigHandler.getConfig("config.yml").getString("Visitor.Groups." + group + ".Prevent.Use-Items.Allow-Fishing"), itemsFishing);
                    visitorMap.setValue("itemJoin", ConfigHandler.getConfig("config.yml").getString("Visitor.Groups." + group + ".Prevent.Use-Items.Allow-ItemJoin"), visItemJoin);

                    visitorMap.setValue("interBlock", ConfigHandler.getConfig("config.yml").getString("Visitor.Groups." + group + ".Prevent.Interact-Blocks.Enable"), visInterBlock);
                    visitorMap.setValue("interBlockMsg", ConfigHandler.getConfig("config.yml").getString("Visitor.Groups." + group + ".Prevent.Interact-Blocks.Message"), visInterBlockMsg);
                    visitorMap.setValue("interBlockUse", ConfigHandler.getConfig("config.yml").getString("Visitor.Groups." + group + ".Prevent.Interact-Blocks.Allow-Use"), visInterBlockUse);
                    visitorMap.setValue("interBlockCont", ConfigHandler.getConfig("config.yml").getString("Visitor.Groups." + group + ".Prevent.Interact-Blocks.Allow-Container"), visInterBlockCont);

                    visitorMap.setValue("interEnt", ConfigHandler.getConfig("config.yml").getString("Visitor.Groups." + group + ".Prevent.Interact-Entities.Enable"), visInterEnt);
                    visitorMap.setValue("interEntMsg", ConfigHandler.getConfig("config.yml").getString("Visitor.Groups." + group + ".Prevent.Interact-Entities.Message"), visInterEntMsg);
                    visitorMap.setValue("interEntNPC", ConfigHandler.getConfig("config.yml").getString("Visitor.Groups." + group + ".Prevent.Interact-Entities.Allow-NPC"), visInterEntNPC);

                    visitorMap.setValue("dropItems", ConfigHandler.getConfig("config.yml").getString("Visitor.Groups." + group + ".Prevent.Drop-Items.Enable"), visDropItems);
                    visitorMap.setValue("dropItems", ConfigHandler.getConfig("config.yml").getString("Visitor.Groups." + group + ".Prevent.Drop-Items.Message"), visDropItemsMsg);

                    visitorMap.setValue("pickupItems", ConfigHandler.getConfig("config.yml").getString("Visitor.Groups." + group + ".Prevent.Pickup-Items.Enable"), visPickupItems);
                    visitorMap.setValue("pickupItems", ConfigHandler.getConfig("config.yml").getString("Visitor.Groups." + group + ".Prevent.Pickup-Items.Message"), visPickupItemsMsg);

                    locMaps = locationUtils.getSpeLocMaps("config.yml", "Visitor.Groups." + group + ".Location");
                    if (!locMaps.isEmpty()) {
                        visitorMap.setLocMaps(locMaps);
                    }
                    // Add properties to all Worolds.
                    for (LocationMap locationMap : visitorMap.getLocMaps()) {
                        for (String worldName : locationMap.getWorlds()) {
                            try {
                                visitorProp.get(worldName).put(group, visitorMap);
                            } catch (Exception ex) {
                                visitorProp.put(worldName, new HashMap<>());
                                visitorProp.get(worldName).put(group, visitorMap);
                            }
                        }
                    }
                }
            }
            Map<String, Long> sortMap;
            Map<String, VisitorMap> newMap;
            Iterator<String> i = visitorProp.keySet().iterator();
            String worldName;
            while (i.hasNext()) {
                worldName = i.next();
                sortMap = new HashMap<>();
                newMap = new LinkedHashMap<>();
                for (String group : visitorProp.get(worldName).keySet()) {
                    sortMap.put(group, visitorProp.get(worldName).get(group).getPriority());
                }
                sortMap = Utils.sortByValue(sortMap);
                for (String group : sortMap.keySet()) {
                    ServerHandler.sendFeatureMessage("Visitor", worldName, "setup", "continue", group,
                            new Throwable().getStackTrace()[0]);
                    newMap.put(group, visitorProp.get(worldName).get(group));
                }
                visitorProp.replace(worldName, newMap);
            }
        }
    }

    public Map<String, Map<String, VisitorMap>> getVisitorProp() {
        return visitorProp;
    }


    public LocationUtils getLocationUtils() {
        return locationUtils;
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
}