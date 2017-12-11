package ha.thanh.pikerfree.models.Messages;


public class Message {
    private int id;
    private String author;
    private String text;
    private long time;

    public Message() {
    }

    public Message(int id, String author, String text, long time) {
        this.id = id;
        this.author = author;
        this.text = text;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
