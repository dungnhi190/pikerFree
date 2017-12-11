package ha.thanh.pikerfree.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;

import ha.thanh.pikerfree.constants.Constants;
import ha.thanh.pikerfree.models.Messages.MyGeoLocation;

@IgnoreExtraProperties
public class Post {
    private int postId;
    private String title = "No title found";
    private String description = "No description found";
    private List<String> requestingUser;
    private String grantedUser;
    private int status = Constants.STATUS_OPEN;
    private String ownerId;
    private MyGeoLocation location;
    private long timePosted;
    private int category = 8;
    private List<Comment> comments;

    public Post() {
    }

    public Post(int postId, String title, String description, String ownerId, MyGeoLocation location, long timePosted, int category) {
        this.postId = postId;
        this.title = title;
        this.description = description;
        this.ownerId = ownerId;
        this.location = location;
        this.timePosted = timePosted;
        this.category = category;
        requestingUser = new ArrayList<>();
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getGrantedUser() {
        return grantedUser;
    }

    public void setGrantedUser(String grantedUser) {
        this.grantedUser = grantedUser;
    }

    public MyGeoLocation getLocation() {
        return location;
    }

    public void setLocation(MyGeoLocation location) {
        this.location = location;
    }

    public List<String> getRequestingUser() {
        return requestingUser;
    }

    public void setRequestingUser(List<String> requestingUser) {
        this.requestingUser = requestingUser;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public long getTimePosted() {
        return timePosted;
    }

    public void setTimePosted(long timePosted) {
        this.timePosted = timePosted;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}
