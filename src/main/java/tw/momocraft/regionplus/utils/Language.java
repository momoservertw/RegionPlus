package tw.momocraft.regionplus.utils;

import org.bukkit.command.CommandSender;
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
        if (sender == null) {
            return;
        }
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
        return langMessage
                .replace("%command%", langHolder[0])
                .replace("%player%", langHolder[1])
                .replace("%targetplayer%", langHolder[2])
                .replace("%pricetype%", langHolder[3])
                .replace("%price%", langHolder[4])
                .replace("%balance%", langHolder[5])
                .replace("%amount%", langHolder[6])
                .replace("%item%", langHolder[7])
                .replace("%points_group%", langHolder[21])
                .replace("%points_limit%", langHolder[22])
                .replace("%points_used%", langHolder[23])
                .replace("%points_last%", langHolder[24])
                .replace("%points_need%", langHolder[25])
                .replace("%points_newlast%", langHolder[26])
                .replace("%points_nextgroup%", langHolder[27])
                .replace("%points_nextlimit%", langHolder[28])
                .replace("%points_nextbonus%", langHolder[29])
                .replace("%res_cost%", langHolder[30])
                ;
    }

    public static String[] newString() {
        return new String[40];
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