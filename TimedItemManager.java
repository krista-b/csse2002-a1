package bms.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class which manages all the timed items.
 */
public class TimedItemManager implements TimedItem {
    private List<TimedItem> timedItems = new ArrayList<>();
    private static TimedItemManager instance = null;

    /**
     * Returns the singleton instance of the timed item manager.
     *
     * @return singleton instance
     */
    public static TimedItemManager getInstance() {
        if (instance == null) {
            instance = new TimedItemManager();
        }
        return instance;
    }

    /**
     * Registers a timed item with the manager.
     *
     * @param timedItem a timed item to register with the manager
     */
    public void registerTimedItem(TimedItem timedItem) {
        timedItems.add(timedItem);
    }

    @Override
    public void elapseOneMinute() {
        for (TimedItem timedItem : timedItems) {
            timedItem.elapseOneMinute();
         }
    }
}