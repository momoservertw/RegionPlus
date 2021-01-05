package tw.momocraft.regionplus.utils;

public class RegionUtils {

    public static boolean isCanUse(String blockType) {
        if (blockType.endsWith("PRESSURE_PLATE")) {
            return true;
        }
        if (blockType.equals("TRIPWIRE")) {
            return true;
        }
        if (blockType.endsWith("DOOR")) {
            return true;
        }
        if (blockType.endsWith("FENCE_GATE")) {
            return true;
        }
        if (blockType.endsWith("BUTTON")) {
            return true;
        }
        switch (blockType) {
            // Crafting
            case "CRAFTING_TABLE":
            case "ENCHANTING_TABLE":
            case "FLETCHING_TABLE":
            case "SMITHING_TABLE":
            case "NOTE_BLOCK":
            case "ANVIL":
            case "BREWING_STAND":
                // Redstone Machine
            case "LEVER":
            case "DIODE":
            case "COMPARATOR":
            case "REPEATER":
            case "REDSTONE_COMPARATOR":
            case "DAYLIGHT_DETECTOR":
                // Other
            case "BEACON":
            case "ITEM_FRAME":
            case "FLOWER_POT":
            case "BED_BLOCK":
            case "CAKE_BLOCK":
            case "COMMAND":
                return true;
            default:
                return false;
        }
    }

    public static boolean isContainer(String blockType) {
        if (blockType.endsWith("CHEST")) {
            return true;
        }
        if (blockType.endsWith("SHULKER_BOX")) {
            return true;
        }
        switch (blockType) {
            // Crafting
            case "BREWING_STAND":
            case "DISPENSER":
            case "DROPPER":
            case "FURNACE":
            case "HOPPER":
            case "SMOKER":
            case "BARREL":
            case "BLAST_FURNACE":
            case "LOOM":
                // Other
            case "ITEM_FRAME":
            case "JUKEBOX":
            case "ARMOR_STAND":
                return true;
            default:
                return false;
        }
    }
}
