package io.github.stonley890.creativesandbox.listeners;

import io.github.stonley890.creativesandbox.Main;
import io.github.stonley890.creativesandbox.data.PlayerMemory;
import io.github.stonley890.creativesandbox.data.PlayerUtility;
import io.github.stonley890.creativesandbox.functions.Sandbox;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class ListenPlayerQuit implements Listener {

    @EventHandler
    @SuppressWarnings({"null"})
    public void onPlayerQuitEvent(@NotNull PlayerQuitEvent event) {

        Player player = event.getPlayer();

        PlayerMemory memory = PlayerUtility.getPlayerMemory(event.getPlayer().getUniqueId());

        if (memory.sandbox) {
            for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
                if (onlinePlayer.hasPermission("dreamvisitor.sandbox"))
                    onlinePlayer.sendMessage(Main.PREFIX + event.getPlayer() + " left while in sandbox mode.");
            }
        }

        try {
            PlayerUtility.savePlayerMemory(player.getUniqueId());
            PlayerUtility.clearPlayerMemory(player.getUniqueId());
        } catch (IOException e) {
            Bukkit.getLogger().severe("Unable to save player memory! Does the server have write access? Player memory will remain in memory.");
        }

        Bukkit.getScheduler().runTask(Main.getPlugin(), () -> {

            // Check for sandboxed players
            boolean moderatorOnline = false;
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (onlinePlayer.hasPermission("dreamvisitor.sandbox")) {
                    moderatorOnline = true;
                    break;
                }
            }
            if (!moderatorOnline) {
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if (PlayerUtility.getPlayerMemory(onlinePlayer.getUniqueId()).sandbox) {
                        Sandbox.disableSandbox(onlinePlayer);
                        onlinePlayer.sendMessage("There are no sandbox managers available.");
                    }
                }
            }
        });



    }

}
