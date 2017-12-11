package ha.thanh.pikerfree.models.categoryItem;

import ha.thanh.pikerfree.models.categoryItem.Item;

/**
 * Created by HaVan on 9/22/2017.
 */

public class EntryItem implements Item {
    public final String title;

    public EntryItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean isSection() {
        return false;
    }
}

