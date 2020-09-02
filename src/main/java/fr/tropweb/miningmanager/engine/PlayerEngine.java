package fr.tropweb.miningmanager.engine;

import fr.tropweb.miningmanager.pojo.PlayerData;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class PlayerEngine {
    private final Engine engine;

    private final Map<UUID, PlayerData> playerLiteMap = new HashMap<>();

    public PlayerEngine(final Engine engine) {
        this.engine = engine;
    }

    public Map<UUID, PlayerData> getPlayerLiteMap() {
        return playerLiteMap;
    }

    public PlayerData getPlayerLite(final Player player) {
        if (!playerLiteMap.containsKey(player.getUniqueId())) {
            playerLiteMap.put(player.getUniqueId(), new PlayerData(player));
        }
        return playerLiteMap.get(player.getUniqueId());
    }

    public boolean isChunkAlreadyMined(final Chunk chunk) {
        // check all players fro memory
        for (final PlayerData otherPlayer : this.playerLiteMap.values()) {

            // get chunk from others players
            final Chunk oChunk = otherPlayer.getMiningTask().getChunk();

            // check if chunks are same
            if (oChunk != null && oChunk.equals(chunk)) {
                return true;
            }
        }
        return false;
    }

    public boolean isInventoryFree(final Inventory inventory, final Material material) {
        final ItemStack[] itemStacks = inventory.getContents();

        // get to the user inventory
        for (int i = 1; i < itemStacks.length; i++) {

            // if a stack is free, next
            if (itemStacks[i] == null) {
                return true;
            }

            // if stack have the same type and not to the max, next
            else if (material == itemStacks[i].getType() && itemStacks[i].getAmount() < itemStacks[i].getMaxStackSize()) {
                return true;
            }
        }
        return false;
    }
}
