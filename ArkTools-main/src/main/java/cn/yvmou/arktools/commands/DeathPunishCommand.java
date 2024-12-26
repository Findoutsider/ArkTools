package cn.yvmou.arktools.commands;

import cn.yvmou.arktools.ArkTools;
import cn.yvmou.arktools.utils.MessageUtil;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class DeathPunishCommand implements CommandExecutor {
    // 插件实例
    private final ArkTools plugin;

    public DeathPunishCommand(ArkTools plugin) {
        this.plugin = plugin;
    }

    // 执行命令
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length >= 1) {
            if (args[0].equals("deathpunish")) {
                if (sender.hasPermission("arktools.deathpunish")) {
                    deathPunishCommand(sender, args);
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

    // 死亡惩罚命令
    private void deathPunishCommand(CommandSender sender, String[] args) {
        if (args.length >= 2) {
            switch (args[1]) {
                case "setmaxhealth" -> {
                    if (args.length == 4) {
                        setMaxHealth(sender, args);
                    } else {
                        sender.sendMessage("参数错误");
                    }
                }
                case "add" -> {
                    if (args.length == 4) {
                        addMaxHealth(sender, args);
                    } else {
                        sender.sendMessage("参数错误");
                    }
                }
                case "get" -> {
                    if (args.length == 3) {
                        getMaxHealth(sender, args);
                    } else {
                        sender.sendMessage("参数错误");
                    }
                }
                default -> sender.sendMessage("未知子命令.");
            }
        } else {
            MessageUtil.sendHelpMessage(sender);
        }
    }

    // 设置玩家生命上限
    private void setMaxHealth(CommandSender sender, String[] args) {
        try {
            String playerName = args[2];
            double maxHealth = Double.parseDouble(args[3]);
            Player player = plugin.getServer().getPlayer(playerName);
            if (player != null) {
                AttributeInstance maxHealthAttribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                if (maxHealthAttribute != null) {
                    maxHealthAttribute.setBaseValue(maxHealth);
                    player.setHealth(maxHealth); // 确保玩家当前生命值也被更新
                    sender.sendMessage("玩家生命上限已设置");
                } else {
                    sender.sendMessage("无法获取玩家的最大生命值属性");
                }
            } else {
                sender.sendMessage("玩家未找到");
            }
        } catch (NumberFormatException e) {
            sender.sendMessage("参数错误");
        }
    }

    // 增加玩家生命上限
    private void addMaxHealth(CommandSender sender, String[] args) {
        try {
            String playerName = args[2];
            double addHealth = Double.parseDouble(args[3]);
            Player player = plugin.getServer().getPlayer(playerName);
            if (player != null) {
                AttributeInstance maxHealthAttribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                if (maxHealthAttribute != null) {
                    double newMaxHealth = Math.max(maxHealthAttribute.getBaseValue() + addHealth, 1.0);
                    maxHealthAttribute.setBaseValue(newMaxHealth);
                    player.setHealth(newMaxHealth); // 确保玩家当前生命值也被更新
                    sender.sendMessage("玩家生命上限已增加");
                } else {
                    sender.sendMessage("无法获取玩家的最大生命值属性");
                }
            } else {
                sender.sendMessage("玩家未找到");
            }
        } catch (NumberFormatException e) {
            sender.sendMessage("参数错误");
        }
    }

    // 获取玩家生命上限
    private void getMaxHealth(CommandSender sender, String[] args) {
        String playerName = args[2];
        Player player = plugin.getServer().getPlayer(playerName);
        if (player != null) {
            AttributeInstance maxHealthAttribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            if (maxHealthAttribute != null) {
                double maxHealth = maxHealthAttribute.getBaseValue();
                sender.sendMessage("玩家生命上限为" + maxHealth);
            } else {
                sender.sendMessage("无法获取玩家的最大生命值属性");
            }
        } else {
            sender.sendMessage("玩家未找到");
        }
    }
}