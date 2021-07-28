package unsw.loopmania;

import unsw.loopmania.items.Item;

public interface RareItem {
    // Interface used to group rare items
    public void setConfusingItem(Item item);
    public Item getConfusingItem();
}
