package cn.yvmou.arktools.commands;

import cn.yvmou.arktools.utils.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class HelpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission("arktools.help")) {
            MessageUtil.sendHelpMessage(sender);
        } else {
            sender.sendMessage("你没有权限执行此命令.");
        }
        return true;
    }
}