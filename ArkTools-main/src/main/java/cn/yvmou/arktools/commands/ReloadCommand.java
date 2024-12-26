package cn.yvmou.arktools.commands;

import cn.yvmou.arktools.ArkTools;
import cn.yvmou.arktools.utils.ReloadConfigUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand implements CommandExecutor {
    private final ArkTools plugin;

    public ReloadCommand(ArkTools plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission("arktools.reload")) {
            plugin.reloadConfig();
            ReloadConfigUtil.reloadWorldBorders(plugin);
            sender.sendMessage("配置文件已重新加载.");
        } else {
            sender.sendMessage("你没有权限执行此命令.");
        }
        return true;
    }
}