package tw.momocraft.regionplus.utils;


import tw.momocraft.regionplus.handlers.ConfigHandler;

public class RegionConfig {


    private boolean resBlockPainting;
    private boolean resBlockItemFrame;
    private boolean resBlockArmorStand;
    private boolean resBlockDoor;
    private boolean resBlockEnderman;
    private String resPointsMode;
    private boolean resPointsIgnoreXYZ;
    private long resPointsDefault;
    private boolean resFlagAutoCheck;
    private long resFlagAutoCheckDelay;
    private boolean resFlagEdit;
    private boolean resFlagDefaultUpdate;
    private boolean resFlagDefaultRemove;
    private boolean resFlagPermissionRemove;
    private boolean wbVistorEnable;
    private boolean wbVistorPreventBlock;
    private boolean wbVistorPreventEntity;

    public RegionConfig() {
        setUp();
    }

    private void setUp() {
        resBlockPainting = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Block.Painting-Destroy");
        resBlockItemFrame = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Block.Item-Frame-Destroy");
        resBlockArmorStand = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Armor-Stand-Destroy");
        resBlockDoor = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Block.Zombie-Door-Destruction");
        resBlockEnderman = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Block.Enderman-Pick-Block");
        resPointsMode = ConfigHandler.getConfig("config.yml").getString("Residence.Points.Check.Mode");
        resPointsIgnoreXYZ = ConfigHandler.getConfig("config.yml").getBoolean("Residence.Points.Check.Ignore-XYZ");
        resPointsDefault = ConfigHandler.getConfig("config.yml").getLong("Residence.Points.Default-Limit");
        resFlagAutoCheck = ConfigHandler.getConfig("config.yml").getBoolean("ResidenceFlag-Editor.Auto-Check.Enable");
        resFlagAutoCheckDelay = ConfigHandler.getConfig("config.yml").getLong("ResidenceFlag-Editor.Auto-Check.Delay") * 20;
        resFlagEdit = ConfigHandler.getConfig("config.yml").getBoolean("ResidenceFlag-Editor.Enable");
        resFlagDefaultUpdate = ConfigHandler.getConfig("config.yml").getBoolean("ResidenceFlag-Editor.Default.Update");
        resFlagDefaultRemove = ConfigHandler.getConfig("config.yml").getBoolean("ResidenceFlag-Editor.Default.Remove-No-Perms");
        resFlagPermissionRemove = ConfigHandler.getConfig("config.yml").getBoolean("ResidenceFlag-Editor.Permission.Remove-No-Perms");
        wbVistorEnable = ConfigHandler.getConfig("config.yml").getBoolean("WorldBorder.Visitor.Enable");
        wbVistorPreventBlock = ConfigHandler.getConfig("config.yml").getBoolean("WorldBorder.Visitor.Prevent.Interact-Block");
        wbVistorPreventEntity = ConfigHandler.getConfig("config.yml").getBoolean("WorldBorder.Visitor.Prevent.Interact-Entity");


    }

    public boolean isResBlockItemFrame() {
        return resBlockItemFrame;
    }

    public boolean isResBlockPainting() {
        return resBlockPainting;
    }

    public boolean isResBlockArmorStand() {
        return resBlockArmorStand;
    }

    public boolean isResBlockDoor() {
        return resBlockDoor;
    }

    public String getResPointsMode() {
        return resPointsMode;
    }

    public boolean isResPointsIgnoreXYZ() {
        return resPointsIgnoreXYZ;
    }

    public long getResPointsDefault() {
        return resPointsDefault;
    }

    public boolean isResBlockEnderman() {
        return resBlockEnderman;
    }

    public boolean isResFlagAutoCheck() {
        return resFlagAutoCheck;
    }

    public long getResFlagAutoCheckDelay() {
        return resFlagAutoCheckDelay;
    }

    public boolean isResFlagEdit() {
        return resFlagEdit;
    }

    public boolean isResFlagDefaultUpdate() {
        return resFlagDefaultUpdate;
    }

    public boolean isResFlagDefaultRemove() {
        return resFlagDefaultRemove;
    }

    public boolean isResFlagPermissionRemove() {
        return resFlagPermissionRemove;
    }

    public boolean isWbVistorEnable() {
        return wbVistorEnable;
    }

    public boolean isWbVistorPreventBlock() {
        return wbVistorPreventBlock;
    }

    public boolean isWbVistorPreventEntity() {
        return wbVistorPreventEntity;
    }
}