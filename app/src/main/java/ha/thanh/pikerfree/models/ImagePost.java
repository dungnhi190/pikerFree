package ha.thanh.pikerfree.models;

/**
 * Created by HaVan on 9/21/2017.
 */

public class ImagePost {
    private String pathLocal;
    private boolean isUploadDone;
    private String name;

    public ImagePost(String pathLocal, String name) {
        this.pathLocal = pathLocal;
        this.isUploadDone = false;
        this.name = name;
    }

    public ImagePost(String pathLocal, boolean isUploadDone, String name) {
        this.pathLocal = pathLocal;
        this.isUploadDone = isUploadDone;
        this.name = name;
    }

    public String getPathLocal() {
        return pathLocal;
    }

    public void setPathLocal(String pathLocal) {
        this.pathLocal = pathLocal;
    }

    public boolean isUploadDone() {
        return isUploadDone;
    }

    public void setUploadDone(boolean uploadDone) {
        isUploadDone = uploadDone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
