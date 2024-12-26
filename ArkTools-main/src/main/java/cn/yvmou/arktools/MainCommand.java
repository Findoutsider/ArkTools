package cn.yvmou.arktools;

import cn.yvmou.arktools.commands.DeathPunishCommand;
import cn.yvmou.arktools.commands.HelpCommand;
import cn.yvmou.arktools.commands.ReloadCommand;
import cn.yvmou.arktools.commands.WorldBorderCommand;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MainCommand implements CommandExecutor, TabCompleter {
    // 插件实例
    private final ArkTools plugin;

    public MainCommand(ArkTools plugin) {
        this.plugin = plugin;
    }

    // 执行命令
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length >= 1) {
            String subcommand = args[0];
            return switch (subcommand) {
                case "help" -> new HelpCommand().onCommand(sender, command, label, args);
                case "reload" -> new ReloadCommand(plugin).onCommand(sender, command, label, args);
                case "setworldborder" -> new WorldBorderCommand(plugin).onCommand(sender, command, label, args);
                case "deathpunish" -> new DeathPunishCommand(plugin).onCommand(sender, command, label, args);
                default -> {
                    sender.sendMessage("未知子命令.");
                    yield false;
                }
            };
        } else {
            new HelpCommand().onCommand(sender, command, label, args);
        }
        return false;
    }


    // Tab 补全
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("arktools")) {
            if (args.length == 1) {
                return List.of("setworldborder", "deathpunish", "reload", "help");
            } else if (args[0].equalsIgnoreCase("setworldborder")) {
                return getWorldBorderTabComplete(args);
            } else if (args[0].equalsIgnoreCase("deathpunish")) {
                return getDeathPunishTabComplete(args);
            }
        }
        return List.of();
    }

    private List<String> getWorldBorderTabComplete(String[] args) {
        return switch (args.length) {
            case 2 -> plugin.getServer().getWorlds().stream().map(World::getName).toList();
            case 3 -> List.of("<centerX>");
            case 4 -> List.of("<centerZ>");
            case 5 -> List.of("<size>");
            default -> List.of();
        };
    }

    private List<String> getDeathPunishTabComplete(String[] args) {
        return switch (args.length) {
            case 2 -> List.of("setmaxhealth", "add", "get");
            case 3 -> plugin.getServer().getOnlinePlayers().stream().map(Player::getName).toList();
            case 4 -> List.of(args[1].equalsIgnoreCase("setmaxhealth") ? "<maxHealth>" : "<addHealth>");
            default -> List.of();
        };
    }
}
//        if (args.length >= 1) {
//            String subcommand = args[0];
//            return switch (subcommand) {
//                case "help" -> {
//                    if (sender.hasPermission("arktools.help")) {
//                        MessageUtil.sendHelpMessage(sender);
//                    } else {
//                        sender.sendMessage(Component.text("你没有权限执行此命令.", NamedTextColor.RED));
//                    }
//                    yield true;
//                }
//                case "reload" -> {
//                    if (sender.hasPermission("arktools.reload")) {
//                        plugin.reloadConfig();
//                        ReloadConfigUtil.reloadWorldBorders(plugin); // 重载世界边界
//                        sender.sendMessage(Component.text("配置文件已重新加载.", NamedTextColor.GREEN));
//                    } else {
//                        sender.sendMessage(Component.text("你没有权限执行此命令.", NamedTextColor.RED));
//                    }
//                    yield true;
//                }
//                case "setworldborder" -> {
//                    yield new WorldBorderCommand(plugin).onCommand(sender, command, label, args);
//                }
//                default -> {
//                    sender.sendMessage(Component.text("未知子命令.", NamedTextColor.RED));
//                    yield false;
//                }
//            };
//        } else {
//            MessageUtil.sendHelpMessage(sender);
//        }
//        return false;
//    }


//    @Override
//    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
//        if (command.getName().equalsIgnoreCase("arktools")) {
//            if (args.length == 1) {
//                return List.of("setworldborder", "deathpunish", "reload", "help");
//            } else if (args[0].equalsIgnoreCase("setworldborder")) {
//                return getWorldBorderTabComplete(args);
//            } else if (args[0].equalsIgnoreCase("deathpunish")) {
//                return getDeathPunishTabComplete(args);
//            }
//        }
//        return List.of();
//    }
//
//    private List<String> getWorldBorderTabComplete(String[] args) {
//        return switch (args.length) {
//            case 2 -> Bukkit.getWorlds().stream().map(World::getName).toList();
//            case 3 -> List.of("<centerX>");
//            case 4 -> List.of("<centerZ>");
//            case 5 -> List.of("<size>");
//            default -> List.of();
//        };
//    }
//
//    private List<String> getDeathPunishTabComplete(String[] args) {
//        return switch (args.length) {
//            case 2 -> List.of("setmaxhealth", "add", "get");
//            case 3 -> Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
//            case 4 -> List.of(args[1].equalsIgnoreCase("setmaxhealth") ? "<maxHealth>" : "<addHealth>");
//            default -> List.of();
//        };
//    }
//}