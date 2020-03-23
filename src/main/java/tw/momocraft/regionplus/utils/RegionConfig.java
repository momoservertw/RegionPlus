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
}