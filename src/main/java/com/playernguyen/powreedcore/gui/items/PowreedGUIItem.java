package com.playernguyen.powreedcore.gui.items;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface PowreedGUIItem {

    /**
     * The item stack which represent the item will display to an inventory of {@link PowreedGUIItem}
     *
     * @param player a specify player to interact with the item
     * @return item stack will display to inventory
     */
    ItemStack getItemStack(Player player);



}
