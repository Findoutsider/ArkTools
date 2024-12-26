package cn.yvmou.arktools.listeners;

import cn.yvmou.arktools.ArkTools;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Objects;

public class CustomItemListener implements Listener {

    // 插件实例
    private final ArkTools plugin;

    public CustomItemListener(ArkTools plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        ItemStack item = event.getItem();
        List<String> potionEffects = plugin.getConfig().getStringList("customItems.heal_item.potion_effects");
        if (item.getType() == Material.ENCHANTED_GOLDEN_APPLE) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null && meta.hasDisplayName() && Objects.requireNonNull(meta.displayName()).toString().equalsIgnoreCase("§6生命果实")) {
                event.setCancelled(true); // 取消默认效果
                AttributeInstance maxHealthAttribute = event.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH);
                if (maxHealthAttribute != null) {
                    double newMaxHealth = maxHealthAttribute.getBaseValue() + plugin.getConfig().getDouble("customItems.heal_item.heal_amount");
                    maxHealthAttribute.setBaseValue(newMaxHealth); // 增加最大生命值
                    event.getPlayer().setHealth(newMaxHealth); // 恢复当前生命值
                }
                event.getPlayer().setFoodLevel(20);
                if (maxHealthAttribute != null && maxHealthAttribute.getBaseValue() > plugin.getConfig().getInt("customItems.heal_item.maxHealth")) {
                    maxHealthAttribute.setBaseValue(20);
                    event.getPlayer().sendMessage(Objects.requireNonNull(plugin.getConfig().getString("customItems.heal_item.eatWithoutHealMsg")));
                } else {
                    event.getPlayer().sendMessage(Objects.requireNonNull(plugin.getConfig().getString("customItems.heal_item.eatMsg")));
                }
                for (String effect : potionEffects) {
                    String[] parts = effect.split(" ");
                    PotionEffectType type = PotionEffectType.getByName(parts[0].toUpperCase());
                    if (type != null) {
                        int duration = Integer.parseInt(parts[1]);
                        int amplifier = Integer.parseInt(parts[2]);
                        event.getPlayer().addPotionEffect(new PotionEffect(type, duration, amplifier));
                    }
                }
                ItemStack mainItem = event.getPlayer().getInventory().getItemInMainHand();
                ItemStack offItem = event.getPlayer().getInventory().getItemInOffHand();
                ItemStack handItem = mainItem.isSimilar(item) ? mainItem : offItem;
                int amount = handItem.getAmount();
                if (amount > 1) {
                    handItem.setAmount(amount - 1);
                } else {
                    if (mainItem.isSimilar(item)) {
                        event.getPlayer().getInventory().setItemInMainHand(null);
                    } else {
                        event.getPlayer().getInventory().setItemInOffHand(null);
                    }
                }
            }
        }
    }
}