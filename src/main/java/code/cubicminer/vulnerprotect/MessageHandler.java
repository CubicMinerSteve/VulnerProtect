package code.cubicminer.vulnerprotect;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class MessageHandler {

	public static MessageHandler msgHandlerInstance = new MessageHandler();

	public static FileConfiguration msgFile = ConfigReader.getFile("messages.yml");

	public static HashMap<String, String> loadedMsg = new HashMap<>();

	public static String prefix = msgFile.getString("Prefix");

	public static boolean msgInit = true;

	public static void initializeMessages() {
		// The following code is for setting messages.
		msgFile.set("Prefix", "[VulnerProtect] ");
		msgFile.set("Event-Cancelled", prefixSupplier("Sorry, but you can't do this here."));
		// Save configuration file.
		ConfigReader.saveFile("messages.yml");
	}

	public static void loadMessages() {
		loadedMsg.put("Prefix", prefixSupplier("Prefix"));
		loadedMsg.put("Event-Cancelled", prefixSupplier("Event-Cancelled"));
		for (String Key : loadedMsg.keySet()) {
			if (loadedMsg.get(Key) == "nullnull" )  msgInit = false;
		}
	}

	public static String prefixSupplier(String str) {
		return ChatColor.translateAlternateColorCodes('&', prefix + msgFile.getString(str));
	}

}
