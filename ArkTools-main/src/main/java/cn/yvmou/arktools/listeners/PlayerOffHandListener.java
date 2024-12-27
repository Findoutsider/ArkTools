package cn.yvmou.arktools.listeners;

import cn.yvmou.arktools.ArkTools;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import xyz.jpenilla.squaremap.api.Squaremap;
import xyz.jpenilla.squaremap.api.SquaremapProvider;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


public class PlayerOffHandListener implements Listener {

    private final Squaremap mapAPI = SquaremapProvider.get();
    private final ArkTools plugin;
    private ItemStack offHandItem;
    // 是否隐藏在地图上
    private final Set<UUID> hideOnMapPlayers = new HashSet<>();

    public PlayerOffHandListener(ArkTools plugin) {
        this.plugin = plugin;
    }


    // 玩家切换主手物品事件
    @EventHandler
    public void onPlayerCloseInventory(InventoryCloseEvent event) {
        Player player = (Player)event.getPlayer();
        handlePlayer(player);
    }

    // 玩家切换副手物品事件
    @EventHandler
    public void onPlayerOffHand(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        handlePlayer(player);
    }

    // 处理玩家
    public void handlePlayer(Player player) {
        offHandItem = player.getInventory().getItemInOffHand();
        UUID playerId = player.getUniqueId();
        boolean shouldHide = offHandItem != null && offHandItem.getType() == Material.valueOf(plugin.getConfig().getString("hideOnMap.Item.material")) && offHandItem.getItemMeta().getDisplayName().equals(plugin.getConfig().getString("hideOnMap.Item.name"));

        if (shouldHide && !hideOnMapPlayers.contains(playerId)) {
            hideOnMapPlayers.add(playerId);
            mapAPI.playerManager().hide(playerId);
            player.sendMessage("§a你已经消失在地图上了！");
        } else if (!shouldHide && hideOnMapPlayers.contains(playerId)) {
            hideOnMapPlayers.remove(playerId);
            mapAPI.playerManager().show(playerId);
            boolean res = changeNBT(offHandItem);
            if (res) player.sendMessage("§c你的隐藏雷达已损毁！");
            player.sendMessage("§c你已出现在地图上！");
        }
    }

    private boolean changeNBT(ItemStack item) {

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, "useCount");
        int currentValue = pdc.has(key, PersistentDataType.INTEGER) ? pdc.get(key, PersistentDataType.INTEGER) : 0;
        if (currentValue == (int) plugin.config.get("hideOnMap.Item.useCount")) {
            item.setAmount(0);
            return true;
        }
        else {
            pdc.set(key, PersistentDataType.INTEGER, currentValue + 1);
            return false;
        }
    }
}
