package cn.yvmou.arktools.utils;

import cn.yvmou.arktools.ArkTools;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class TimerUtil {
    private BukkitTask task;
    private Runnable runnable;
    private long startTime;
    private int elapsedTimeInSeconds;

    public void start(Plugin plugin, long delayTicks, long periodTicks) {
        if (runnable == null) {
            throw new IllegalStateException("必须在启动计时器之前设置一项任务！");
        }
        startTime = System.currentTimeMillis();
        task = new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTaskTimer(plugin, delayTicks, periodTicks);
    }

    public int stop() {
        if (task != null) {
            task.cancel();
            task = null;
            long currentTime = System.currentTimeMillis(); // 获取当前时间
            long elapsedTime = currentTime - startTime; // 计算时间差（毫秒）
            return (int) (elapsedTime / 1000.0);
        }
        return 0;
    }

    public void setTask(Runnable task) {
        this.runnable = task;
    }
}
