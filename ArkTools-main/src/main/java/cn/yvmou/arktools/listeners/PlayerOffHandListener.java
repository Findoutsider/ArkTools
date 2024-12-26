package cn.yvmou.arktools.listeners;

import cn.yvmou.arktools.ArkTools;
import cn.yvmou.arktools.utils.TimerUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import xyz.jpenilla.squaremap.api.Squaremap;
import xyz.jpenilla.squaremap.api.SquaremapProvider;

import java.util.List;


public class PlayerOffHandListener implements Listener {
    private final ArkTools plugin;
    private final Squaremap mapAPI = SquaremapProvider.get();
    private final TimerUtil timer;
    private ItemStack item;
    private List<String> lore;
    private int leftTime;
    private Player player;

    public PlayerOffHandListener(ArkTools plugin) {
        this.plugin = plugin;
        this.timer = new TimerUtil();
    }

    private void setupTimer() {
        timer.setTask(() -> {
            // 定时任务逻辑：每10秒发送一条信息给玩家
            if (leftTime <= 0) {
                item.setAmount(0);
            }
            lore.set(3, String.valueOf(leftTime - 10));
        });
    }
    
    @EventHandler
    public void onPlayerOffHand(PlayerSwapHandItemsEvent event) {
        setupTimer();
        item = event.getOffHandItem();
        player = event.getPlayer();

        if (item.getType() == Material.valueOf(plugin.config.getString("hideOnMap.Item.material"))
                && item.getItemMeta().getItemName().equals(plugin.config.getString("hideOnMap.Item.name"))) 
        {
            lore = item.getItemMeta().getLore();
            List<String> configLore = plugin.config.getStringList("hideOnMap.Item.lore");
            if (lore != null) {
                int count = 0;
                for (String s : lore) {
                    for (String s1 : configLore) {
                        if (!s.equals(s1)) {
                            // 现形
                            mapAPI.playerManager().show(player.getUniqueId());
                            player.sendMessage("§c你已出现在地图上！");
                            return;
                        }
                        count++;
                        if (count == 3) break;
                    }
                }
            }
            // 判断通过，隐藏玩家
            leftTime = Integer.parseInt(lore.get(3));
            mapAPI.playerManager().hidden(player.getUniqueId());
            timer.start(plugin, 0L, 200L);
            player.sendMessage("§a你已经消失在地图上了！");
        } else {
            // 现形
            mapAPI.playerManager().show(player.getUniqueId());
            int time = timer.stop();
            lore.set(3, String.valueOf(leftTime - 10));
            if (time == 0) item.setAmount(0);
            player.sendMessage("§c你已出现在地图上！");
        }
    }
}
