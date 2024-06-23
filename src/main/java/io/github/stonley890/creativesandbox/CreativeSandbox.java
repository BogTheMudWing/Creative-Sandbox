package io.github.stonley890.creativesandbox;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import io.github.stonley890.creativesandbox.commands.CmdSandbox;
import io.github.stonley890.creativesandbox.data.PlayerUtility;
import io.github.stonley890.creativesandbox.functions.Sandbox;
import io.github.stonley890.creativesandbox.listeners.ListenPlayerJoin;
import io.github.stonley890.creativesandbox.listeners.ListenPlayerQuit;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class CreativeSandbox extends JavaPlugin {

    public static CreativeSandbox plugin;
    public static final String PREFIX = ChatColor.GRAY + "[" + ChatColor.BLUE + "Sandbox" + ChatColor.GRAY + "] " + ChatColor.RESET;
    public static String VERSION;

    @Override
    public void onEnable() {

        plugin = this;
        VERSION = getDescription().getVersion();

        CommandAPI.onLoad(new CommandAPIBukkitConfig(this).silentLogs(false));
        CommandAPI.onEnable();
        CmdSandbox.getCommand().register(this);
        getServer().getPluginManager().registerEvents(new ListenPlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new ListenPlayerQuit(), this);
        getServer().getPluginManager().registerEvents(new Sandbox(), this);
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

    public static CreativeSandbox getPlugin() {
        return plugin;
    }
}