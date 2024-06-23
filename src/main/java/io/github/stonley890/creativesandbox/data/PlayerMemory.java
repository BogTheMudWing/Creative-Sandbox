package io.github.stonley890.creativesandbox.data;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PlayerMemory {

    public FileConfiguration toFileConfig() {
        FileConfiguration fileConfig = new YamlConfiguration();
        fileConfig.set("creative", creative);
        fileConfig.set("survivalInv", survivalInv);
        fileConfig.set("creativeInv", creativeInv);
        fileConfig.set("sandbox", sandbox);

        return fileConfig;
    }

    public static @NotNull PlayerMemory getFromFileConfig(@NotNull FileConfiguration fileConfig) {
        PlayerMemory memory = new PlayerMemory();
        memory.creative = fileConfig.getBoolean("creative");
        List<ItemStack> survivalInvList = (List<ItemStack>) fileConfig.getList("survivalInv");
        List<ItemStack> creativeInvList = (List<ItemStack>) fileConfig.getList("creativeInv");

        if (survivalInvList == null) survivalInvList = new ArrayList<>();
        if (creativeInvList == null) creativeInvList = new ArrayList<>();

        memory.survivalInv = survivalInvList.toArray(ItemStack[]::new);
        memory.creativeInv = creativeInvList.toArray(ItemStack[]::new);

        memory.sandbox = fileConfig.getBoolean("sandbox");

        return memory;
    }

    /**
     * Tracks the user's current inventory. May not necessarily correlate to actual gamemode.
     */
    public boolean creative;
    public ItemStack[] survivalInv;
    public ItemStack[] creativeInv;
    public boolean sandbox;

}