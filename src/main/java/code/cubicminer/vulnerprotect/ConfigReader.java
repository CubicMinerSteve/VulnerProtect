package code.cubicminer.vulnerprotect;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import com.google.common.io.ByteStreams;

public class ConfigReader {

    public static Plugin thisPlugin = Bukkit.getPluginManager().getPlugin("VulnerProtect");

    public static FileConfiguration configFile = ConfigReader.getFile("config.yml");

    public static List<String> whitelistedWorld = new ArrayList<>();

    public static boolean isPluginEnabled = true;
    public static boolean isMessageHintEnabled = false;

    public static void checkDataFolder() {
        if (!thisPlugin.getDataFolder().exists()) {
            thisPlugin.getDataFolder().mkdir();
        }
    }

    public static FileConfiguration getFile(String configName) {
		File configFile = new File(thisPlugin.getDataFolder(), configName);
        if (!configFile.exists()) {
            checkDataFolder();
            try {
                configFile.createNewFile();
                configFile = new File(thisPlugin.getDataFolder(), configName);
                writeResource(configFile, configName);
            } catch (Exception exception) {
                exception.printStackTrace();
                Bukkit.getConsoleSender().sendMessage("[VulnerProtect] " + ChatColor.RED + "Unable to create file " + configName + "!");
            }
        }
        FileConfiguration fConfiguration = YamlConfiguration.loadConfiguration(configFile);
        return fConfiguration;
	}

    public static void saveFile(String configName) {
		File configFile = new File(thisPlugin.getDataFolder(), configName);
        FileConfiguration fConfiguration = YamlConfiguration.loadConfiguration(configFile);
        try {
            fConfiguration.save(configFile);
        } catch (Exception exception) {
            Bukkit.getConsoleSender().sendMessage("[VulnerProtect] " + ChatColor.RED + "Couldn't save " + configName + "!");
        }
    }

    public static void releaseResources() {
        List<String> configList = new ArrayList<String>();
        configList.add("config.yml");
        configList.add("messages.yml");
        if (!thisPlugin.getDataFolder().exists()) {
            // Ensure the directory exists.
            thisPlugin.getDataFolder().mkdir();
        }
        for (String str : configList) {
            File configFile = new File(thisPlugin.getDataFolder(), str);
            if (!configFile.exists()) {
                try {
                    configFile.createNewFile();
                } catch (Exception exception) {
                    Bukkit.getConsoleSender().sendMessage("[VulnerProtect] " + ChatColor.RED + "Unable to create file " + str + "!");
                }
            }
            try {
                InputStream iStream = thisPlugin.getResource(str);
                OutputStream oStream = new FileOutputStream(configFile);
                // Copies file from Plugin.jar into newly created file.
                ByteStreams.copy(iStream, oStream);
                oStream.flush();
                oStream.close();
            } catch (Exception exception) {
                Bukkit.getConsoleSender().sendMessage("[VulnerProtect] " + ChatColor.RED + "Couldn't write into file " + configFile.getName() + "!");
            }
        }
    }

    public static void writeResource(File configFile, String str) {
        try {
                InputStream iStream = thisPlugin.getResource(str);
                OutputStream oStream = new FileOutputStream(configFile);
                // Copies file from Plugin.jar into newly created file.
                ByteStreams.copy(iStream, oStream);
                oStream.flush();
                oStream.close();
            } catch (Exception exception) {
                Bukkit.getConsoleSender().sendMessage("[VulnerProtect] " + ChatColor.RED + "Couldn't write resource " + str + " to " + configFile.getName() + "!");
            }
    }

    public static void loadWhitelistedWorld() {
        FileConfiguration configFile = ConfigReader.getFile("config.yml");
        whitelistedWorld = configFile.getStringList("Whitelisted-World");
    }

}
