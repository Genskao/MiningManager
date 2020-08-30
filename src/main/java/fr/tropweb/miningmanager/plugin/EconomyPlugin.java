package fr.tropweb.miningmanager.plugin;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

public class EconomyPlugin {
    private final Economy economy;

    /**
     * The constructor use the logic of <a href="https://github.com/MilkBowl/VaultAPI/tree/1.7#implementing-vault">this code</a>.
     *
     * @param plugin
     */
    public EconomyPlugin(final Plugin plugin) {
        final Server server = plugin.getServer();
        if (server.getPluginManager().getPlugin("Vault") != null) {
            final RegisteredServiceProvider<Economy> registration = server.getServicesManager().getRegistration(Economy.class);
            if (registration != null) {
                plugin.getLogger().info("Vault plugin has been found, Economy is ready.");
                this.economy = registration.getProvider();
                return;
            }
        }

        plugin.getLogger().info("Vault plugin is not loaded. Command will free for players.");
        this.economy = null;
    }

    public boolean isEnabled() {
        return this.economy != null && this.economy.isEnabled();
    }

    public boolean isEnabled(final double amount) {
        return this.isEnabled() && amount > 0D;
    }

    public boolean takeMoney(final Player player, final double amount) {
        return this.economy.withdrawPlayer(player, Math.abs(amount)).transactionSuccess();
    }

    public void giveMoney(final Player player, final double amount) {
        this.economy.withdrawPlayer(player, amount);
    }
}
