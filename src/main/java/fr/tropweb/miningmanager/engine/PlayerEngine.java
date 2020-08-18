package fr.tropweb.miningmanager.engine;

import fr.tropweb.miningmanager.pojo.PlayerLite;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class PlayerEngine {
    private final Engine engine;

    private final Map<UUID, PlayerLite> playerLiteMap = new HashMap<>();

    public PlayerEngine(Engine engine) {
        this.engine = engine;
    }

    public Map<UUID, PlayerLite> getPlayerLiteMap() {
        return playerLiteMap;
    }

    public PlayerLite getPlayerLite(Player player) {
        if (!playerLiteMap.containsKey(player.getUniqueId())) {
            playerLiteMap.put(player.getUniqueId(), new PlayerLite(player));
        }
        return playerLiteMap.get(player.getUniqueId());
    }

    public boolean isInventoryFree(Inventory inventory, Material material) {
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
