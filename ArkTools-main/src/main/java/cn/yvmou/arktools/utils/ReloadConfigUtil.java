package cn.yvmou.arktools.utils;

import cn.yvmou.arktools.ArkTools;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class ReloadConfigUtil {

    // 插件实例
    private final ArkTools plugin;

    public ReloadConfigUtil(ArkTools plugin) {
        this.plugin = plugin;
    }

    // 重载所有世界边界
    public static void reloadWorldBorders(ArkTools plugin) {
        for (World world : Bukkit.getWorlds()) {
            double centerX = plugin.getConfig().getDouble("world-border." + world.getName() + ".centerX");
            double centerZ = plugin.getConfig().getDouble("world-border." + world.getName() + ".centerZ");
            double size = plugin.getConfig().getDouble("world-border." + world.getName() + ".size");
            world.getWorldBorder().setCenter(centerX, centerZ);
            world.getWorldBorder().setSize(size);
        }
    }
}
