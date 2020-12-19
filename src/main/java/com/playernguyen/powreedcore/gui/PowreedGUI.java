package com.playernguyen.powreedcore.gui;

import com.google.common.base.Preconditions;
import com.playernguyen.powreedcore.gui.items.PowreedGUIItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * The GUI is a map of item
 */
public interface PowreedGUI extends Iterable<Map.Entry<Integer, PowreedGUIItem>> {

    /**
     * A name of menu (GUI). Unless the name was found, the display name will be replaced by an UUID of that inventory.
     *
     * @return a name of menu (GUI)
     */
    String getName();

    /**
     * A size of this GUI.
     *
     * @return a size of this GUI
     */
    PowreedGUISize getSize();

    /**
     * The item map which will store item and the index of that item into it.
     * Whether the GUI API build an inventory, data inside map will be put into that inventory.
     *
     * @return the map item to put when an inventory generates
     */
    Map<Integer, PowreedGUIItem> getItemMap();

    /**
     * Set the item to the GUI. Whether the index was defined, the item will be replaced by current item
     *
     * @param index an index of that item
     * @param item  an item to be set
     */
    default void setItem(int index, PowreedGUIItem item) {
        Preconditions.checkNotNull(getItemMap(), "The map was not initiated");
        this.getItemMap().put(index, item);
    }

    /**
     * Get empty (null) item slot in the map
     *
     * @return an empty slot which was not use
     */
    default Set<Integer> getEmptyIndex() {
        Set<Integer> array = new TreeSet<>();
        for (int i = 0; i < this.getSize().getSize(); i++) {
            if (this.getItemMap().get(i) == null) {
                array.add(i);
            }
        }
        return array;
    }

    /**
     * Build a Bukkit inventory
     *
     * @param player the player own this inventory and to create an event to that player
     * @return an inventory which was built
     */
    default Inventory buildInventory(Player player) {
        // Make an inventory
        Inventory inventory = Bukkit.createInventory(player, this.getSize().getSize(), this.getName());
        // Put items into the inventory
        for (Map.Entry<Integer, PowreedGUIItem> entry : this) {
            inventory.setItem(entry.getKey(), entry.getValue().getItemStack(player));
        }
        // Return the inventory
        return inventory;
    }

}
