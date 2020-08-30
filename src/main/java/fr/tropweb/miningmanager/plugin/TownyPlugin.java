package fr.tropweb.miningmanager.plugin;

import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.PlayerCache;
import com.palmergames.bukkit.towny.object.TownyPermission;
import com.palmergames.bukkit.towny.utils.PlayerCacheUtil;
import com.palmergames.bukkit.towny.war.common.WarZoneConfig;
import com.palmergames.bukkit.towny.war.eventwar.WarUtil;
import com.palmergames.bukkit.towny.war.flagwar.FlagWarConfig;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class TownyPlugin {
    private final Towny towny;

    public TownyPlugin(final Plugin plugin) {
        final Object townyObject = plugin.getServer().getPluginManager().getPlugin("Towny");
        if (townyObject != null && townyObject instanceof Towny) {
            this.towny = (Towny) townyObject;
            plugin.getLogger().info("Towny plugin has been loaded.");
        } else {
            this.towny = null;
            plugin.getLogger().info("Towny plugin is not loaded.");
        }
    }

    public boolean isEnabled() {
        return this.towny != null && this.towny.isEnabled();
    }

    /**
     * Use the permission of the <a href="https://github.com/TownyAdvanced/Towny/blob/master/src/com/palmergames/bukkit/towny/listeners/TownyBlockListener.java">towny block destroy listener</a>.
     *
     * @param player
     * @param block
     * @return
     */
    public boolean canDestroy(final Player player, final Block block) {

        // check if player can destroy the block
        if (PlayerCacheUtil.getCachePermission(player, block.getLocation(), block.getType(), TownyPermission.ActionType.DESTROY))
            return true;

        // get data from cache
        final PlayerCache cache = this.towny.getCache(player);

        // return if player can edit
        return (flagWar(cache) || eventWar(cache, player)) && WarZoneConfig.isEditableMaterialInWarZone(block.getType());
    }

    private static boolean flagWar(final PlayerCache cache) {
        return cache.getStatus() == PlayerCache.TownBlockStatus.WARZONE && FlagWarConfig.isAllowingAttacks();
    }

    private static boolean eventWar(final PlayerCache cache, final Player player) {
        return TownyAPI.getInstance().isWarTime() && cache.getStatus() == PlayerCache.TownBlockStatus.WARZONE && !WarUtil.isPlayerNeutral(player);
    }
}
