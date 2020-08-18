package fr.tropweb.miningmanager.engine;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public abstract class EventEngine {

    public abstract void onBlock(Player player, Block block);
}
