package io.github.stonley890.creativesandbox;

import io.github.stonley890.creativesandbox.commands.CmdSandbox;
import io.github.stonley890.creativesandbox.data.PlayerUtility;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Objects;

public final class Main extends JavaPlugin {

    public static Main plugin;
    public static final String PREFIX = ChatColor.GRAY + "[" + ChatColor.BLUE + "Sandbox" + ChatColor.GRAY + "] " + ChatColor.RESET;
    public static String VERSION;

    @Override
    public void onEnable() {

        plugin = this;
        VERSION = getDescription().getVersion();

        Objects.requireNonNull(getPlugin().getCommand("sandbox")).setExecutor(new CmdSandbox());

    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            try {
                PlayerUtility.savePlayerMemory(player.getUniqueId());
                PlayerUtility.clearPlayerMemory(player.getUniqueId());
            } catch (IOException e) {
                Bukkit.getLogger().severe("Unable to save player memory! Does the server have write access?");
            }
        }
    }

    public static Main getPlugin() {
        return plugin;
    }
}
