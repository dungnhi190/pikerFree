package ha.thanh.pikerfree.constants;


public class DummyData {
    private static DummyData ourInstance = null;
    public static String linkForImagesTemp = "http://ssl-product-images.www8-hp.com/digmedialib/prodimg/lowres/c05145861.png";

    public static DummyData getIns() {
        if (ourInstance == null) {
            ourInstance = new DummyData();
        }
        return ourInstance;
    }

    private DummyData() {
    }

    public String getUri() {
        return linkForImagesTemp;
    }

}
