package ha.thanh.pikerfree.models;

/**
 * Created by HaVan on 8/27/2017.
 */

public class Conversation {

    private String idUser1;
    private String idUser2;
    private String conversationId;
    private int lastMessId;

    public Conversation() {
    }

    public Conversation(String idUser1, String idUser2, String conversationId, int lastMessId) {
        this.idUser1 = idUser1;
        this.idUser2 = idUser2;
        this.conversationId = conversationId;
        this.lastMessId = lastMessId;
    }

    public String getIdUser1() {
        return idUser1;
    }

    public void setIdUser1(String idUser1) {
        this.idUser1 = idUser1;
    }

    public String getIdUser2() {
        return idUser2;
    }

    public void setIdUser2(String idUser2) {
        this.idUser2 = idUser2;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public int getLastMessId() {
        return lastMessId;
    }

    public void setLastMessId(int lastMessId) {
        this.lastMessId = lastMessId;
    }
}
