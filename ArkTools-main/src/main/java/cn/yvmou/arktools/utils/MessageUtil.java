package cn.yvmou.arktools.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;

public class MessageUtil {
    // 发送帮助信息
    public static void sendHelpMessage(CommandSender sender) {
        sender.sendMessage(Component.text("ArkTools 帮助", NamedTextColor.GOLD));
        sender.sendMessage(Component.text("-------------------", NamedTextColor.WHITE));
        sender.sendMessage(Component.text("/arktools setworldborder", NamedTextColor.WHITE).append(Component.text(" 设置世界边界", NamedTextColor.GRAY)));
        sender.sendMessage(Component.text("/arktools deathpunish setmaxhealth", NamedTextColor.WHITE).append(Component.text(" 设置玩家生命上限", NamedTextColor.GRAY)));
        sender.sendMessage(Component.text("/arktools deathpunish add", NamedTextColor.WHITE).append(Component.text(" 增加玩家生命上限", NamedTextColor.GRAY)));
        sender.sendMessage(Component.text("/arktools deathpunish get", NamedTextColor.WHITE).append(Component.text(" 获取玩家生命上限", NamedTextColor.GRAY)));
        sender.sendMessage(Component.text("/arktools reload", NamedTextColor.WHITE).append(Component.text(" 重载插件", NamedTextColor.GRAY)));
        sender.sendMessage(Component.text("/arktools help", NamedTextColor.WHITE).append(Component.text(" 查看帮助", NamedTextColor.GRAY)));
        sender.sendMessage(Component.text("-------------------", NamedTextColor.WHITE));
    }
}
