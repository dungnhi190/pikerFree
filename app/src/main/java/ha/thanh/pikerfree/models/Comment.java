package ha.thanh.pikerfree.models;

/**
 * Created by HaVan on 12/10/2017.
 */

public class Comment {
    private String idUser;
    private String comment;
    private long time;

    public Comment() {
    }

    public Comment(String idUser, String comment, long time) {
        this.idUser = idUser;
        this.comment = comment;
        this.time = time;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
