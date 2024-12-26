package cn.yvmou.arktools;

import cn.yvmou.arktools.listeners.CustomItemListener;
import cn.yvmou.arktools.listeners.PlayerDeathListener;
import cn.yvmou.arktools.listeners.PlayerOffHandListener;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Objects;

public final class ArkTools extends JavaPlugin {
    public final static String VERSION = "1.0.0";
    public ShapedRecipe enchantedGoldenAppleRecipe;
    public FileConfiguration config;

    @Override
    public void onEnable() {
        // 如果配置文件不存在，Bukkit 会保存默认的配置
        saveDefaultConfig();
        // 注册命令执行器
        Objects.requireNonNull(getCommand("arktools")).setExecutor(new MainCommand(this));
        // 注册命令补全器
        Objects.requireNonNull(getCommand("arktools")).setTabCompleter(new MainCommand(this));
        // 注册事件监听器
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
        getServer().getPluginManager().registerEvents(new CustomItemListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerOffHandListener(this), this);
        // 注册自定义物品的配方
        FileConfiguration config = getConfig();
        registerCustomRecipes(config);

        Bukkit.getConsoleSender().sendMessage("ArkTools 已启用");
    }

    // 注册自定义物品的配方
    public void registerCustomRecipes(FileConfiguration config) {
        enchantedGoldenAppleRecipe = CustomItems.createEnchantedGoldenApple(config);
        getServer().addRecipe(enchantedGoldenAppleRecipe);
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("ArkTools 已关闭");
    }
}


