package dev.ethereal.nocrystaldamage;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class NoCrystalDamage extends JavaPlugin implements Listener {

    private volatile boolean enabled = true;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadSettings();
        getServer().getPluginManager().registerEvents(this, this);
    }

    private void loadSettings() {
        enabled = getConfig().getBoolean("enabled", true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDamage(EntityDamageByEntityEvent e) {
        if (enabled && e.getEntity() instanceof Player && e.getDamager() instanceof EnderCrystal) {
            e.setCancelled(true);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            reloadConfig();
            loadSettings();
            sender.sendMessage("§aNoCrystalDamage reloaded. Player crystal damage is " + (enabled ? "§aBLOCKED" : "§cALLOWED") + "§a.");
            return true;
        }
        sender.sendMessage("§7NoCrystalDamage: player crystal damage is " + (enabled ? "§aBLOCKED" : "§cALLOWED") + "§7. Use /" + label + " reload after editing config.yml.");
        return true;
    }
}
