package ha.thanh.pikerfree.models.categoryItem;

/**
 * Created by HaVan on 9/22/2017.
 */

public class SectionItem implements Item {
    private final String title;

    public SectionItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean isSection() {
        return true;
    }
}