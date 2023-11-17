package code.cubicminer.vulnerprotect;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import code.cubicminer.vulnerprotect.Listeners.EventListeners;

public class VulnerProtect extends JavaPlugin implements Listener{

	@Override
	public void onEnable()
	{
		try {
			MessageHandler.loadMessages();
			if (MessageHandler.msgInit == false) {
				Exception onLoadException = new Exception();
				throw onLoadException;
			}
		} catch (Exception exception) {
			MessageHandler.initializeMessages();
			MessageHandler.loadMessages();
		}
		Bukkit.getConsoleSender().sendMessage("[VulnerProtect] " + ChatColor.GREEN + "Successfully" + ChatColor.RESET + " loaded VulnerProtect Plugin!");

		ConfigReader.isPluginEnabled = ConfigReader.configFile.getBoolean("Main-Settings.Plugin-Enabled");
		ConfigReader.isMessageHintEnabled = ConfigReader.configFile.getBoolean("Main-Settings.Message-Hint");
		Bukkit.getServer().getPluginManager().registerEvents(new EventListeners(), this);
	}

}