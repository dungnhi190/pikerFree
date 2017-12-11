package ha.thanh.pikerfree.models;


import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;

@IgnoreExtraProperties
public class User {

    private String id;
    private String name;
    private String address;
    private String phoneNumber;
    private String avatarLink = "userImages/default_profile.jgp";
    private String email = "";
    private ArrayList<Integer> posts;
    private ArrayList<String> mess;
    private String instanceId;
    private boolean isOnline;
    private double rating = 5;
    private List<String> ratedUsers;
    private List<String> followingUsers;

    public User() {
    }

    public List<String> getFollowingUsers() {
        return followingUsers;
    }

    public void setFollowingUsers(List<String> followingUsers) {
        this.followingUsers = followingUsers;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public List<String> getRatedUsers() {
        return ratedUsers;
    }

    public void setRatedUsers(List<String> ratedUsers) {
        this.ratedUsers = ratedUsers;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public ArrayList<String> getMess() {
        return mess;
    }

    public void setMess(ArrayList<String> mess) {
        this.mess = mess;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatarLink() {
        return avatarLink;
    }

    public void setAvatarLink(String avatarLink) {
        this.avatarLink = avatarLink;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Integer> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Integer> posts) {
        this.posts = posts;
    }
}
