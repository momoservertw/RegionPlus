package tw.momocraft.regionplus.utils;


import com.bekvon.bukkit.residence.Residence;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import tw.momocraft.coreplus.api.CorePlusAPI;
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
    private String msgFlagseditCmd;
    private String msgPointsCmd;
    private String msgPointsOtherCmd;

    private String msgPoints;
    private String msgTargetPoints;
    private String msgPointsSelect;
    private String msgSizeChangeFailed;
    private String msgAreaAddFailed;

    private String msgVisResCreate;
    private String msgVisResGet;
    private String msgVisResGetTarget;
    private String msgVisItemUse;
    private String msgVisInteractBlock;
    private String msgVisInteractEntity;
    private String msgVisDamageEntity;
    private String msgVisDropItem;
    private String msgVisPickupItem;
    private String msgVisDeathDrop;

    //  ============================================== //
    //         General Variables                       //
    //  ============================================== //

    //  ============================================== //
    //         World Variables                         //
    //  ============================================== //
    private boolean worldPreventCreeper;
    private boolean worldPreventExplode;
    private boolean worldPreventEnderdrangon;
    private boolean worldPreventFireSpread;

    //  ============================================== //
    //         Player Variables                        //
    //  ============================================== //
    private boolean playerPreventFly;
    private String playerPreventFlyPerm;

    //  ============================================== //
    //         Residence Variables                     //
    //  ============================================== //
    private boolean resPrevent;
    private boolean resPreventPotion;
    private boolean resPreventFly;
    private boolean resPreventPainting;
    private boolean resPreventItemFrame;
    private boolean resPreventArmorStand;
    private boolean resPreventEnderCrystal;
    private boolean resPreventZombieDoor;
    private boolean resPreventEndermanPick;
    private boolean resPreventBlockDamage;

    private boolean resSurvivalMechanics;
    private boolean resVehicles;

    private boolean points;
    private boolean pointsSelectInfo;
    private Material pointsSelectTool;
    private boolean pointsIgnoreY;
    private boolean pointsAddArea;
    private boolean pointsAreasSize;
    private boolean pointsMode;
    private Map<String, Integer> pointsMap = new HashMap<>();
    private Map<String, String> pointsDisplayMap = new HashMap<>();

    private boolean resFlag;
    private boolean resFlagBypassCustom;
    private boolean resFlagBypassPerms;
    private List<String> resFlagByPassRes;
    private List<String> resFlagByPassResOwners;
    private boolean resFlagRemove;
    private List<String> resFlagRemoveIgnore;
    private boolean resFlagUpdate;
    private List<String> resFlagUpdateIgnore;
    private boolean resFlagPlayerRemove;
    private List<String> resFlagPlayerRemoveIgnore;

    private boolean resMsg;
    private boolean resMsgBypassPerm;
    private List<String> resMsgByPassRes;
    private List<String> resMsgByPassResOwners;
    private boolean resMsgMsg;
    private Table<String, String, List<String>> resMsgGroupTable;

    //  ============================================== //
    //         Visitor Variables                       //
    //  ============================================== //
    private boolean visitor;
    private boolean visResBypass;
    private boolean visResCreate;
    private boolean visResGet;
    private boolean visUseItem;
    private boolean visUseItemMsg;
    private boolean visItemConsume;
    private boolean visItemBucket;
    private boolean visItemFishing;
    private boolean visItemProjectile;
    private boolean visItemJoin;
    private boolean visInterBlock;
    private boolean visInterBlockMsg;
    private boolean visInterBlockUse;
    private boolean visInterBlockCont;
    private boolean visInterEnt;
    private boolean visInterEntMsg;
    private boolean visInterEntNPC;
    private boolean visDamageEnt;
    private boolean visDamageEntMsg;
    private boolean visDamageEntPlayer;
    private boolean visDropItem;
    private boolean visDropItemMsg;
    private boolean visPickupItem;
    private boolean visPickupItemMsg;
    private boolean visDeathDrop;
    private boolean visDeathDropMsg;
    private List<String> visLocList;


    //  ============================================== //
    //         Setup all configuration                 //
    //  ============================================== //
    private void setUp() {
        setupMsg();
        setWorld();
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
        msgFlagseditCmd = ConfigHandler.getConfig("config.yml").getString("Message.Commands.flagsedit");
        msgPointsCmd = ConfigHandler.getConfig("config.yml").getString("Message.Commands.points");
        msgPointsOtherCmd = ConfigHandler.getConfig("config.yml").getString("Message.Commands.pointsOther");

        msgPoints = ConfigHandler.getConfig("config.yml").getString("Message.points");
        msgTargetPoints = ConfigHandler.getConfig("config.yml").getString("Message.targetPoints");
        msgPointsSelect = ConfigHandler.getConfig("config.yml").getString("Message.pointsSelect");
        msgAreaAddFailed = ConfigHandler.getConfig("config.yml").getString("Message.sizeChangeFailed");
        msgSizeChangeFailed = ConfigHandler.getConfig("config.yml").getString("Message.areaAddFailed");

        msgVisResCreate = ConfigHandler.getConfig("config.yml").getString("Message.visitorResidenceCreate");
        msgVisResGet = ConfigHandler.getConfig("config.yml").getString("Message.visitorResidenceGet");
        msgVisResGetTarget = ConfigHandler.getConfig("config.yml").getString("Message.visitorResidenceGetTarget");
        msgVisItemUse = ConfigHandler.getConfig("config.yml").getString("Message.visitorUseItem");
        msgVisInteractBlock = ConfigHandler.getConfig("config.yml").getString("Message.visitorInteractBlock");
        msgVisInteractEntity = ConfigHandler.getConfig("config.yml").getString("Message.visitorInteractEntity");
        msgVisDamageEntity = ConfigHandler.getConfig("config.yml").getString("Message.visitorDamageEntity");
        msgVisDropItem = ConfigHandler.getConfig("config.yml").getString("Message.visitorDropItem");
        msgVisPickupItem = ConfigHandler.getConfig("config.yml").getString("Message.visitorPickupItem");
        msgVisDeathDrop = ConfigHandler.getConfig("config.yml").getString("Message.visitorDeathDrop");
    }

    //  ============================================== //
    //         World Setter                            //
    //  ============================================== //
    private void setWorld() {
        if (!ConfigHandler.getConfig("config.yml").getBoolean("World.Enable")) {
            return;
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("World.Prevent.Enable")) {
            worldPreventCreeper = ConfigHandler.getConfig("config.yml").getBoolean("World.Prevent.Creeper-Block-Damage");
            worldPreventExplode = ConfigHandler.getConfig("config.yml").getBoolean("World.Prevent.Explode-Block-Damage");
            worldPreventEnderdrangon = ConfigHandler.getConfig("config.yml").getBoolean("World.Prevent.Enderdragon-Block-Damage");
            worldPreventFireSpread = ConfigHandler.getConfig("config.yml").getBoolean("World.Prevent.Fire-Spread");
        }
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
        if (!ConfigHandler.getConfig("config.yml").getBoolean("Residence.Enable") ||
                !ConfigHandler.getDepends().ResidenceEnabled()) {
            return;
        }
        resSurvivalMechanics = ConfigHandler.getConfig("config.yml").getBoolean("Residence.SurvivalMechanics.Enable");
        resVehicles = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Vehicles.Enable");

        resPrevent = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Enable");
        if (resPrevent) {
            resPreventFly = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Fly-Disable");
            resPreventPotion = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Potion-Damage");
            resPreventZombieDoor = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent. Zombie-Door-Destruction");
            resPreventEndermanPick = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Enderman-Pickup-Block");
            resPreventBlockDamage = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Block-Damage");
            resPreventPainting = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Painting-Destroy");
            resPreventItemFrame = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Item-Frame-Destroy");
            resPreventArmorStand = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Armor-Stand-Destroy");
            resPreventEnderCrystal = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Prevent.Ender-Crystal-Destroy");
        }
        points = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Points.Enable");
        if (points) {
            pointsSelectInfo = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Points.Select-Info");
            pointsSelectTool = Residence.getInstance().getConfigManager().getSelectionTool().getMaterial();
            pointsIgnoreY = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Points.Settings.Ignore-Y-Residences");
            pointsAddArea = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Points.Settings.Prevent.Add-Area");
            pointsAreasSize = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Points.Settings.Prevent.Multiple-Areas-Size-Change");
            pointsMode = Residence.getInstance().getConfigManager().isSelectionIgnoreY();
            ConfigurationSection resPointsGroupsConfig = ConfigHandler.getConfig("config.yml").getConfigurationSection("Residence.Points.Groups");
            if (resPointsGroupsConfig != null) {
                for (String key : resPointsGroupsConfig.getKeys(false)) {
                    key = key.toLowerCase();
                    pointsMap.put(key, ConfigHandler.getConfig("config.yml").getInt("Residence.Points.Groups." + key + ".Limit"));
                    pointsDisplayMap.put(key, ConfigHandler.getConfig("config.yml").getString("Residence.Points.Groups." + key + ".Display"));
                }
                pointsMap = CorePlusAPI.getUtilsManager().sortByValue(pointsMap);
            }
        }
        resFlag = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Flags-Editor.Enable");
        if (resFlag) {
            resFlagBypassPerms = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Flags-Editor.Settings.Bypass.Permission");
            resFlagBypassCustom = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Flags-Editor.Settings.Bypass.Missing-Custom-Flags");
            resFlagByPassRes = ConfigHandler.getConfig("config.yml").getStringList("Residence.Flags-Editor.Settings.Bypass.Residence-List");
            resFlagByPassResOwners = ConfigHandler.getConfig("config.yml").getStringList("Residence.Flags-Editor.Settings.Bypass.Residence-Owners");
            resFlagRemove = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Flags-Editor.Environment.Remove.Enable");
            resFlagRemoveIgnore = ConfigHandler.getConfig("config.yml").getStringList("Residence.Flags-Editor.Environment.Remove.Ignore-List");
            resFlagUpdate = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Flags-Editor.Environment.Update.Enable");
            resFlagUpdateIgnore = ConfigHandler.getConfig("config.yml").getStringList("Residence.Flags-Editor.Environment.Update.Ignore-List");
            resFlagPlayerRemove = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Flags-Editor.Player.Remove.Enable");
            resFlagPlayerRemoveIgnore = ConfigHandler.getConfig("config.yml").getStringList("Residence.Flags-Editor.Player.Remove.Ignore-List");
        }

        resMsg = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Message-Editor.Enable");
        if (resMsg) {
            resMsgBypassPerm = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Message-Editor.Settings.Bypass.Permission");
            resMsgByPassRes = ConfigHandler.getConfig("config.yml").getStringList("Residence.Message-Editor.Settings.Bypass.Residence-List");
            resMsgByPassResOwners = ConfigHandler.getConfig("config.yml").getStringList("Residence.Message-Editor.Settings.Bypass.Residence-Owners");
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
    }

    //  ============================================== //
    //         Visitor Setter                          //
    //  ============================================== //
    private void setVisitor() {
        visitor = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Enable");
        if (!visitor) {
            return;
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Residence.Enable")) {
            visResBypass = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Residence.Bypass");
            visResCreate = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Residence.Allow-Create");
            visResGet = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Residence.Allow-Get");
        }
        visUseItem = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Use-Item.Enable");
        visUseItemMsg = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Use-Item.Message");
        visItemConsume = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Use-Item.Allow-Consume");
        visItemBucket = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Use-Item.Allow-Bucket");
        visItemProjectile = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Use-Item.Allow-Projectile");
        visItemFishing = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Use-Item.Allow-Fishing");
        visItemJoin = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Use-Item.Allow-ItemJoin");

        visInterBlock = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Interact-Block.Enable");
        visInterBlockMsg = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Interact-Block.Message");
        visInterBlockUse = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Interact-Block.Allow-Use");
        visInterBlockCont = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Interact-Block.Allow-Container");

        visInterEnt = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Interact-Entity.Enable");
        visInterEntMsg = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Interact-Entity.Message");
        visInterEntNPC = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Interact-Entity.Allow-NPC");

        visDamageEnt = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Damage-Entity.Enable");
        visDamageEntMsg = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Damage-Entity.Message");
        visDamageEntPlayer = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Damage-Entity.Allow-Player");

        visDropItem = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Drop-Item.Enable");
        visDropItemMsg = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Drop-Item.Message");
        visPickupItem = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Pickup-Item.Enable");
        visPickupItemMsg = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Pickup-Item.Message");
        visDeathDrop = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Death-Drop.Enable");
        visDeathDropMsg = ConfigHandler.getConfig("config.yml").getBoolean("Visitor.Settings.Death-Drop.Message");

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


    public String getMsgFlagseditCmd() {
        return msgFlagseditCmd;
    }

    public String getMsgPointsCmd() {
        return msgPointsCmd;
    }

    public String getMsgPointsOtherCmd() {
        return msgPointsOtherCmd;
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

    public String getMsgVisResGet() {
        return msgVisResGet;
    }

    public String getMsgVisResGetTarget() {
        return msgVisResGet;
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

    public String getMsgVisDeathDrop() {
        return msgVisDeathDrop;
    }

    //  ============================================== //
    //         General Getter                          //
    //  ============================================== //


    //  ============================================== //
    //         World Getter                            //
    //  ============================================== //
    public boolean isWorldPreventCreeper() {
        return worldPreventCreeper;
    }

    public boolean isWorldPreventExplode() {
        return worldPreventExplode;
    }

    public boolean isWorldPreventEnderdrangon() {
        return worldPreventEnderdrangon;
    }

    public boolean isWorldPreventFireSpread() {
        return worldPreventFireSpread;
    }

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

    public boolean isResPreventEnderCrystal() {
        return resPreventEnderCrystal;
    }

    public boolean isResPreventBlockDamage() {
        return resPreventBlockDamage;
    }

    public boolean isResSurvivalMechanics() {
        return resSurvivalMechanics;
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

    public boolean isPointsIgnoreY() {
        return pointsIgnoreY;
    }

    public boolean isPointsAddArea() {
        return pointsAddArea;
    }

    public boolean isPointsAreasSize() {
        return pointsAreasSize;
    }

    public boolean isPointsMode() {
        return pointsMode;
    }

    public Map<String, Integer> getPointsMap() {
        return pointsMap;
    }

    public Map<String, String> getPointsDisplayMap() {
        return pointsDisplayMap;
    }

    public boolean isResFlag() {
        return resFlag;
    }

    public boolean isResFlagBypassCustom() {
        return resFlagBypassCustom;
    }

    public boolean isResFlagBypassPerms() {
        return resFlagBypassPerms;
    }

    public List<String> getResFlagByPassRes() {
        return resFlagByPassRes;
    }

    public List<String> getResFlagByPassResOwners() {
        return resFlagByPassResOwners;
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

    public List<String> getResFlagRemoveIgnore() {
        return resFlagRemoveIgnore;
    }

    public List<String> getResFlagPlayerRemoveIgnore() {
        return resFlagPlayerRemoveIgnore;
    }

    public boolean isResFlagPlayerRemove() {
        return resFlagPlayerRemove;
    }


    public boolean isResMsg() {
        return resMsg;
    }

    public boolean isResMsgBypassPerm() {
        return resMsgBypassPerm;
    }

    public List<String> getResMsgByPassRes() {
        return resMsgByPassRes;
    }

    public List<String> getResMsgByPassResOwners() {
        return resMsgByPassResOwners;
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

    public boolean isVisResBypass() {
        return visResBypass;
    }

    public boolean isVisResCreate() {
        return visResCreate;
    }

    public boolean isVisResGet() {
        return visResGet;
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

    public boolean isVisPickupItemMsg() {
        return visPickupItemMsg;
    }


    public boolean isVisDeathDrop() {
        return visDeathDrop;
    }

    public boolean isVisUseItemMsg() {
        return visUseItemMsg;
    }

    public boolean isVisInterBlockMsg() {
        return visInterBlockMsg;
    }

    public boolean isVisInterEntMsg() {
        return visInterEntMsg;
    }

    public boolean isVisDamageEntMsg() {
        return visDamageEntMsg;
    }

    public boolean isVisDropItemMsg() {
        return visDropItemMsg;
    }

    public boolean isVisDeathDropMsg() {
        return visDeathDropMsg;
    }

    public List<String> getVisLocList() {
        return visLocList;
    }
}