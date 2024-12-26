package cn.yvmou.arktools.commands;

import cn.yvmou.arktools.ArkTools;
import cn.yvmou.arktools.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class WorldBorderCommand implements CommandExecutor {
    // 插件实例
    private final ArkTools plugin;

    public WorldBorderCommand(ArkTools plugin) {
        this.plugin = plugin;
    }

    // 执行命令
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length >= 1) {
            if (args[0].equals("setworldborder")) {
                if (sender.hasPermission("arktools.setworldborder")) {
                    setWorldBorderCommand(sender, args);
                } else {
                    sender.sendMessage("你没有权限执行此命令.");
                }
                return true;
            } else {
                sender.sendMessage("未知子命令.");
                return false;
            }
        } else {
            MessageUtil.sendHelpMessage(sender);
        }
        return false;
    }

    // 调整世界边界
    private void setWorldBorderCommand(CommandSender sender, String[] args) {
        if (args.length != 5) {
            sender.sendMessage("参数错误");
            return;
        }
        try {
            String worldName = args[1];
            double centerX = Double.parseDouble(args[2]);
            double centerZ = Double.parseDouble(args[3]);
            double size = Double.parseDouble(args[4]);

            World world = Bukkit.getWorld(worldName);
            if (world != null) {
                world.getWorldBorder().setCenter(centerX, centerZ);
                world.getWorldBorder().setSize(size);
                sender.sendMessage("世界边界已更新");
            } else {
                sender.sendMessage("未找到世界");
            }

            plugin.getConfig().set("world-border." + worldName + ".centerX", centerX);
            plugin.getConfig().set("world-border." + worldName + ".centerZ", centerZ);
            plugin.getConfig().set("world-border." + worldName + ".size", size);
            plugin.saveConfig();

        } catch (NumberFormatException e) {
            sender.sendMessage("参数错误");
        }
    }
}

//package cn.yvmou.arktools.commands;
//
//import cn.yvmou.arktools.ArkTools;
//import cn.yvmou.arktools.utils.MessageUtil;
//import net.kyori.adventure.text.Component;
//import net.kyori.adventure.text.format.NamedTextColor;
//import org.bukkit.Bukkit;
//import org.bukkit.World;
//import org.bukkit.command.Command;
//import org.bukkit.command.CommandExecutor;
//import org.bukkit.command.CommandSender;
//import org.jetbrains.annotations.NotNull;
//
//
//
//public class WorldBorderCommand implements CommandExecutor {
//    // 插件实例
//    private final ArkTools plugin;
//
//    public WorldBorderCommand(ArkTools plugin) {
//        this.plugin = plugin;
//    }
//
//    // 执行命令
//    @Override
//    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
//        if (args.length >= 1) {
//            if (args[0].equals("setworldborder")) {
//                if (sender.hasPermission("arktools.setworldborder")) {
//                    setWorldBorderCommand(sender, args);
//                } else {
//                    sender.sendMessage(Component.text("你没有权限执行此命令.", NamedTextColor.RED));
//                }
//                return true;
//            } else {
//                sender.sendMessage(Component.text("未知子命令.", NamedTextColor.RED));
//                return false;
//            }
//        } else {
//            MessageUtil.sendHelpMessage(sender);
//        }
//        return false;
//    }
//
//
//    // 调整世界边界
//    private void setWorldBorderCommand(CommandSender sender, String[] args) {
//        if (args.length != 5) {
//            sender.sendMessage(Component.text("参数错误", NamedTextColor.RED));
//            return;
//        }
//        try {
//            String worldName = args[1];
//            double centerX = Double.parseDouble(args[2]);
//            double centerZ = Double.parseDouble(args[3]);
//            double size = Double.parseDouble(args[4]);
//
//            World world = Bukkit.getWorld(worldName);
//            if (world != null) {
//                world.getWorldBorder().setCenter(centerX, centerZ);
//                world.getWorldBorder().setSize(size);
//                sender.sendMessage(Component.text("世界边界已更新", NamedTextColor.GREEN));
//            } else {
//                sender.sendMessage(Component.text("未找到世界", NamedTextColor.RED));
//            }
//
//            plugin.getConfig().set("world-border." + worldName + ".centerX", centerX);
//            plugin.getConfig().set("world-border." + worldName + ".centerZ", centerZ);
//            plugin.getConfig().set("world-border." + worldName + ".size", size);
//            plugin.saveConfig();
//
//        } catch (NumberFormatException e) {
//            sender.sendMessage(Component.text("参数错误", NamedTextColor.RED));
//        }
//    }
//
//}
