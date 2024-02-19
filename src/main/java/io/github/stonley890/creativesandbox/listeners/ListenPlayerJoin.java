package io.github.stonley890.creativesandbox.listeners;

import io.github.stonley890.creativesandbox.Main;
import io.github.stonley890.creativesandbox.data.PlayerMemory;
import io.github.stonley890.creativesandbox.data.PlayerUtility;
import io.github.stonley890.creativesandbox.functions.Sandbox;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public class ListenPlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(@NotNull PlayerJoinEvent event) {

        PlayerMemory memory = PlayerUtility.getPlayerMemory(event.getPlayer().getUniqueId());

        if (memory.sandbox) {
            boolean sandboxerOnline = false;
            for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
                if (onlinePlayer.hasPermission("dreamvisitor.sandbox")) {
                    sandboxerOnline = true;
                    onlinePlayer.sendMessage(Main.PREFIX + event.getPlayer().getName() + " is currently in sandbox mode.");
                }
            }
            if (!sandboxerOnline) {
                Sandbox.disableSandbox(event.getPlayer());
            }
        }

    }

}
