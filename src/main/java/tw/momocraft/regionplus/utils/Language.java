package tw.momocraft.regionplus.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import tw.momocraft.regionplus.handlers.ConfigHandler;

public class Language {
    private static Lang langType = Lang.ENGLISH;

    public static void dispatchMessage(CommandSender sender, String langMessage, boolean hasPrefix) {
        if (hasPrefix) {
            Player player = null;
            if (sender instanceof Player) {
                player = (Player) sender;
            }
            langMessage = Utils.translateLayout(langMessage, player);
            String prefix = Utils.translateLayout(ConfigHandler.getConfig(langType.nodeLocation()).getString("Message.prefix"), player);
            if (prefix == null) {
                prefix = "";
            } else {
                prefix += "";
            }
            langMessage = prefix + langMessage;
            sender.sendMessage(langMessage);
        } else {
            Player player = null;
            if (sender instanceof Player) {
                player = (Player) sender;
            }
            langMessage = Utils.translateLayout(langMessage, player);
            sender.sendMessage(langMessage);
        }
    }

    public static void dispatchMessage(CommandSender sender, String langMessage) {
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }
        langMessage = Utils.translateLayout(langMessage, player);
        sender.sendMessage(langMessage);
    }

    public static void sendLangMessage(String nodeLocation, CommandSender sender, String... placeHolder) {
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }
        String langMessage = ConfigHandler.getConfig(langType.nodeLocation()).getString(nodeLocation);
        String prefix = Utils.translateLayout(ConfigHandler.getConfig(langType.nodeLocation()).getString("Message.prefix"), player);
        if (prefix == null) {
            prefix = "";
        } else {
            prefix += "";
        }
        if (langMessage != null && !langMessage.isEmpty()) {
            langMessage = translateLangHolders(langMessage, initializeRows(placeHolder));
            langMessage = Utils.translateLayout(langMessage, player);
            String[] langLines = langMessage.split(" /n ");
            for (String langLine : langLines) {
                String langStrip = prefix + langLine;
                sender.sendMessage(langStrip);
            }
        }
    }

    public static void sendLangMessage(String nodeLocation, CommandSender sender, boolean hasPrefix, String... placeHolder) {
        if (hasPrefix) {
            Player player = null;
            if (sender instanceof Player) {
                player = (Player) sender;
            }
            String langMessage = ConfigHandler.getConfig(langType.nodeLocation()).getString(nodeLocation);
            String prefix = Utils.translateLayout(ConfigHandler.getConfig(langType.nodeLocation()).getString("Message.prefix"), player);
            if (prefix == null) {
                prefix = "";
            } else {
                prefix += "";
            }
            if (langMessage != null && !langMessage.isEmpty()) {
                langMessage = translateLangHolders(langMessage, initializeRows(placeHolder));
                langMessage = Utils.translateLayout(langMessage, player);
                String[] langLines = langMessage.split(" /n ");
                for (String langLine : langLines) {
                    String langStrip = prefix + langLine;
                    sender.sendMessage(langStrip);
                }
            }
        } else {
            Player player = null;
            if (sender instanceof Player) {
                player = (Player) sender;
            }
            String langMessage = ConfigHandler.getConfig(langType.nodeLocation()).getString(nodeLocation);
            if (langMessage != null && !langMessage.isEmpty()) {
                langMessage = translateLangHolders(langMessage, initializeRows(placeHolder));
                langMessage = Utils.translateLayout(langMessage, player);
                String[] langLines = langMessage.split(" /n ");
                for (String langLine : langLines) {
                    sender.sendMessage(langLine);
                }
            }
        }
    }

    private static String[] initializeRows(String... placeHolder) {
        if (placeHolder == null || placeHolder.length != newString().length) {
            String[] langHolder = Language.newString();
            for (int i = 0; i < langHolder.length; i++) {
                langHolder[i] = "null";
            }
            return langHolder;
        } else {
            String[] langHolder = placeHolder;
            for (int i = 0; i < langHolder.length; i++) {
                if (langHolder[i] == null) {
                    langHolder[i] = "null";
                }
            }
            return langHolder;
        }
    }

    private static String translateLangHolders(String langMessage, String... langHolder) {
        ConfigurationSection itemName = ConfigHandler.getConfig("config.yml").getConfigurationSection("Message.RegionPlus.Items");
        if (itemName != null && itemName.getKeys(false).contains(langHolder[7])) {
            langHolder[7] = ConfigHandler.getConfig("config.yml").getString("Message.RegionPlus.Items." + langHolder[7]);
        }
        ConfigurationSection priceTypeName = ConfigHandler.getConfig("config.yml").getConfigurationSection("Message.RegionPlus.PriceTypes");
        if (priceTypeName != null && priceTypeName.getKeys(false).contains(langHolder[3])) {
            langHolder[3] = ConfigHandler.getConfig("config.yml").getString("Message.RegionPlus.PriceTypes." + langHolder[3]);
        }
        return langMessage
                .replace("%command%", langHolder[0])
                .replace("%player%", langHolder[1])
                .replace("%targetplayer%", langHolder[2])
                .replace("%pricetype%", langHolder[3])
                .replace("%price%", langHolder[4])
                .replace("%balance%", langHolder[5])
                .replace("%amount%", langHolder[6])
                .replace("%item%", langHolder[7])
                .replace("%points_limit%", langHolder[8])
                .replace("%points_used%", langHolder[9])
                .replace("%points_remainder%", langHolder[10])
                .replace("%points_need%", langHolder[11])
                .replace("%res_cost%", langHolder[12])
                ;
    }

    public static String[] newString() {
        return new String[14];
    }


    private enum Lang {
        DEFAULT("config.yml", 0), ENGLISH("config.yml", 1);

        private Lang(final String nodeLocation, final int i) {
            this.nodeLocation = nodeLocation;
        }

        private final String nodeLocation;

        private String nodeLocation() {
            return nodeLocation;
        }
    }

    private static boolean isConsoleMessage(String nodeLocation) {
        return false;
    }
}