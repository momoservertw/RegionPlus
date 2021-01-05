package tw.momocraft.regionplus.utils;


import com.bekvon.bukkit.residence.Residence;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import tw.momocraft.regionplus.handlers.ConfigHandler;

import java.util.*;

public class ConfigPath {
    public ConfigPath() {
        setUp();
    }

    //  ============================================== //
    //         Message Variables                       //
    //  ============================================== //
    private String msgTitle;
    private String msgHelp;
    private String msgReload;
    private String msgVersion;

    private String msgPoints;
    private String msgTargetPoints;
    private String msgPointsSelect;
    private String msgSizeChangeFailed;
    private String msgAreaAddFailed;

    private String msgVisResCreate;
    private String msgVisResSize;
    private String msgVisItemUse;
    private String msgVisInteractBlock;
    private String msgVisInteractEntity;
    private String msgVisDamageEntity;
    private String msgVisDropItem;
    private String msgVisPickupItem;

    //  ============================================== //
    //         General Variables                       //
    //  ============================================== //

    //  ============================================== //
    //         Player Variables                        //
    //  ============================================== //
    private boolean playerPreventFly;
    private String playerPreventFlyPerm;

    //  ============================================== //
    //         Residence Variables                     //
    //  ============================================== //
    private boolean res;
    private boolean resPrevent;
    private boolean resPreventPotion;
    private boolean resPreventFly;
    private boolean resPreventPainting;
    private boolean resPreventItemFrame;
    private boolean resPreventArmorStand;
    private boolean resPreventZombieDoor;
    private boolean resPreventEndermanPick;
    private boolean resPreventBlockDamage;

    private boolean resIgnoreY;
    private boolean resIgnoreYPoints;
    private boolean resIgnoreYSize;
    private boolean resIgnoreYArea;

    private boolean resSM;
    private boolean resSMClimb;
    private boolean resSMCrawl;
    private boolean resSMMobkick;
    private boolean resSMRoofhang;
    private boolean resSMSlide;
    private boolean resSMSwim;
    private boolean resSMWallkick;
    private boolean resVehicles;

    private boolean points;
    private boolean pointsSelectInfo;
    private Material pointsSelectTool;
    private boolean pointsMode;

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
    //         Visitor Variables                       //
    //  ============================================== //
    private boolean visitor;
    private boolean visMessage;
    private boolean visRes;
    private boolean visResBypass;
    private boolean visResCreate;
    private boolean visResGet;
    private boolean visResSize;
    private boolean visUseItem;
    private boolean visItemConsume;
    private boolean visItemBucket;
    private boolean visItemFishing;
    private boolean visItemProjectile;
    private boolean visItemJoin;
    private boolean visInterBlock;
    private boolean visInterBlockUse;
    private boolean visInterBlockCont;
    private boolean visInterEnt;
    private boolean visInterEntNPC;
    private boolean visDamageEnt;
    private boolean visDamageEntPlayer;
    private boolean visDropItem;
    private boolean visPickupItem;
    private boolean visDeathDrop;
    private List<String> visLocList;


    //  ============================================== //
    //         Setup all configuration                 //
    //  ============================================== //
    private void setUp() {
        setupMsg();
        setPlayer();
        setResidence();
        setVisitor();
    }

    //  ============================================== //
    //         Message Setter                          //
    //  ============================================== //
    private void setupMsg() {
        msgTitle = ConfigHandler.getConfig("config.yml").getString("Message.Commands.title");
        msgHelp = ConfigHandler.getConfig("config.yml").getString("Message.Commands.help");
        msgReload = ConfigHandler.getConfig("config.yml").getString("Message.Commands.reload");
        msgVersion = ConfigHandler.getConfig("config.yml").getString("Message.Commands.version");


        msgPoints = ConfigHandler.getConfig("config.yml").getString("Message.points");
        msgTargetPoints = ConfigHandler.getConfig("config.yml").getString("Message.targetPoints");
        msgPointsSelect = ConfigHandler.getConfig("config.yml").getString("Message.pointsSelect");
        msgAreaAddFailed = ConfigHandler.getConfig("config.yml").getString("Message.sizeChangeFailed");
        msgSizeChangeFailed = ConfigHandler.getConfig("config.yml").getString("Message.areaAddFailed");

        msgVisResCreate = ConfigHandler.getConfig("config.yml").getString("Message.visitorResidenceCreate");
        msgVisResSize = ConfigHandler.getConfig("config.yml").getString("Message.visitorResidenceSize");
        msgVisItemUse = ConfigHandler.getConfig("config.yml").getString("Message.visitorUseItem");
        msgVisInteractBlock = ConfigHandler.getConfig("config.yml").getString("Message.visitorInteractBlock");
        msgVisInteractEntity = ConfigHandler.getConfig("config.yml").getString("Message.visitorInteractEntity");
        msgVisDamageEntity = ConfigHandler.getConfig("config.yml").getString("Message.visitorDamageEntity");
        msgVisDropItem = ConfigHandler.getConfig("config.yml").getString("Message.visitorDropItem");
        msgVisPickupItem = ConfigHandler.getConfig("config.yml").getString("Message.visitorPickupItem");
    }

    //  ============================================== //
    //         Player Setter                           //
    //  ============================================== //
    private void setPlayer() {
        playerPreventFly = ConfigHandler.getConfig("config.yml").getBoolean("Player.Prevent.Fly-Disable.Enable");
        playerPreventFlyPerm = ConfigHandler.getConfig("config.yml").getString("Player.Prevent.Fly-Disable.Permission");
    }

    //  ============================================== //
    //         Residence Setter                        //
    //  ============================================== //
    private void setResidence() {
        res = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Settings.All-Areas.Enable");
        if (!res) {
            return;
        }
        resSM = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Settings.Features.SurvivalMechanics.Enable");
        resSMClimb = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Settings.Features.SurvivalMechanics.Custom-Flags.Climb");
        resSMCrawl = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Settings.Features.SurvivalMechanics.Custom-Flags.Crawl");
        resSMMobkick = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Settings.Features.SurvivalMechanics.Custom-Flags.Mobkick");
        resSMRoofhang = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Settings.Features.SurvivalMechanics.Custom-Flags.Roofhang");
        resSMSlide = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Settings.Features.SurvivalMechanics.Custom-Flags.Slide");
        resSMSwim = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Settings.Features.SurvivalMechanics.Custom-Flags.Swim");
        resSMWallkick = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Settings.Features.SurvivalMechanics.Custom-Flags.Wallkick");

        resVehicles = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Settings.Features.Vehicles.Enable");

        resPrevent = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Enable");
        resPreventFly = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Fly-Disable");
        resPreventPotion = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Potion-Damage");
        resPreventPainting = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Painting-Destroy");
        resPreventItemFrame = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Item-Frame-Destroy");
        resPreventArmorStand = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Armor-Stand-Destroy");
        resPreventZombieDoor = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent. Zombie-Door-Destruction");
        resPreventEndermanPick = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Enderman-Pickup-Block");
        resPreventBlockDamage = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Block-Damage");

        resIgnoreY = ConfigHandler.getConfig("config.yml").getBoolean("Residence.IgnoreY-Changed.Enable");
        resIgnoreYPoints = ConfigHandler.getConfig("config.yml").getBoolean("Residence.IgnoreY-Changed.Points-Calculate");
        resIgnoreYArea = ConfigHandler.getConfig("config.yml").getBoolean("Residence.IgnoreY-Changed.Prevent.Add-Area");
        resIgnoreYSize = ConfigHandler.getConfig("config.yml").getBoolean("Residence.IgnoreY-Changed.Prevent.Change-Size");

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

    //  ============================================== //
    //         Visitor Setter                          //
    //  ============================================== //
    private void setVisitor() {
        visitor = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Enable");
        if (!visitor) {
            return;
        }
        visMessage = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Message");
        visRes = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Residence.Enable");
        visResBypass = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Residence.Allow-Has-Permission");
        visResCreate = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Residence.Allow-Create");
        visResGet = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Residence.Allow-Get");
        visResSize = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Residence.Allow-Change-Size");

        visUseItem = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Use-Item.Enable");
        visItemConsume = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Use-Item.Allow-Consume");
        visItemBucket = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Use-Item.Allow-Bucket");
        visItemProjectile = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Use-Item.Allow-Projectile");
        visItemFishing = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Use-Item.Allow-Fishing");
        visItemJoin = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Use-Item.Allow-ItemJoin");

        visInterBlock = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Interact-Block.Enable");
        visInterBlockUse = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Interact-Block.Allow-Use");
        visInterBlockCont = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Interact-Block.Allow-Container");

        visInterEnt = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Interact-Entity.Enable");
        visInterEntNPC = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Interact-Entity.Allow-NPC");

        visDamageEnt = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Damage-Entity.Enable");
        visDamageEntPlayer = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Damage-Entity.Allow-Player");

        visDropItem = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Drop-Item.Enable");
        visPickupItem = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Pickup-Item.Enable");
        visDeathDrop = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Death-Drop.Enable");

        visLocList = ConfigHandler.getConfig("config.yml").getStringList("Visitor.Location");
    }

    //  ============================================== //
    //         Message Getter                          //
    //  ============================================== //
    public String getMsgTitle() {
        return msgTitle;
    }

    public String getMsgHelp() {
        return msgHelp;
    }

    public String getMsgReload() {
        return msgReload;
    }

    public String getMsgVersion() {
        return msgVersion;
    }

    public String getMsgPoints() {
        return msgPoints;
    }

    public String getMsgTargetPoints() {
        return msgTargetPoints;
    }

    public String getMsgPointsSelect() {
        return msgPointsSelect;
    }

    public String getMsgSizeChangeFailed() {
        return msgSizeChangeFailed;
    }

    public String getMsgAreaAddFailed() {
        return msgAreaAddFailed;
    }

    public String getMsgVisResCreate() {
        return msgVisResCreate;
    }

    public String getMsgVisResSize() {
        return msgVisResSize;
    }

    public String getMsgVisItemUse() {
        return msgVisItemUse;
    }

    public String getMsgVisInteractBlock() {
        return msgVisInteractBlock;
    }

    public String getMsgVisInteractEntity() {
        return msgVisInteractEntity;
    }

    public String getMsgVisDamageEntity() {
        return msgVisDamageEntity;
    }

    public String getMsgVisDropItem() {
        return msgVisDropItem;
    }

    public String getMsgVisPickupItem() {
        return msgVisPickupItem;
    }

    //  ============================================== //
    //         General Getter                          //
    //  ============================================== //


    //  ============================================== //
    //         Player Getter                           //
    //  ============================================== //
    public boolean isPlayerPreventFly() {
        return playerPreventFly;
    }

    public String getPlayerPreventFlyPerm() {
        return playerPreventFlyPerm;
    }

    //  ============================================== //
    //         Residence Getter                        //
    //  ============================================== //

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


    public boolean isResIgnoreY() {
        return resIgnoreY;
    }

    public boolean isResIgnoreYPoints() {
        return resIgnoreYPoints;
    }

    public boolean isResIgnoreYSize() {
        return resIgnoreYSize;
    }

    public boolean isResIgnoreYArea() {
        return resIgnoreYArea;
    }



    public boolean isResSM() {
        return resSM;
    }

    public boolean isResSMClimb() {
        return resSMClimb;
    }

    public boolean isResSMCrawl() {
        return resSMCrawl;
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

    public boolean isResVehicles() {
        return resVehicles;
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


    //  ============================================== //
    //         Visitor Getter                          //
    //  ============================================== //
    public boolean isVisitor() {
        return visitor;
    }

    public boolean isVisMsg() {
        return visMessage;
    }

    public boolean isVisRes() {
        return visRes;
    }

    public boolean isVisResBypass() {
        return visResBypass;
    }

    public boolean isVisResCreate() {
        return visResCreate;
    }

    public boolean isVisResGet() {
        return visResGet;
    }

    public boolean isVisResSize() {
        return visResSize;
    }

    public boolean isVisUseItem() {
        return visUseItem;
    }

    public boolean isVisItemConsume() {
        return visItemConsume;
    }

    public boolean isVisItemBucket() {
        return visItemBucket;
    }

    public boolean isVisItemFishing() {
        return visItemFishing;
    }

    public boolean isVisItemProjectile() {
        return visItemProjectile;
    }

    public boolean isVisItemJoin() {
        return visItemJoin;
    }

    public boolean isVisInterBlock() {
        return visInterBlock;
    }

    public boolean isVisInterBlockUse() {
        return visInterBlockUse;
    }

    public boolean isVisInterBlockCont() {
        return visInterBlockCont;
    }

    public boolean isVisInterEnt() {
        return visInterEnt;
    }

    public boolean isVisInterEntNPC() {
        return visInterEntNPC;
    }

    public boolean isVisDamageEnt() {
        return visDamageEnt;
    }

    public boolean isVisDamageEntPlayer() {
        return visDamageEntPlayer;
    }

    public boolean isVisDropItem() {
        return visDropItem;
    }

    public boolean isVisPickupItem() {
        return visPickupItem;
    }

    public boolean isVisDeathDrop() {
        return visDeathDrop;
    }

    public List<String> getVisLocList() {
        return visLocList;
    }
}