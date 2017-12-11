package ha.thanh.pikerfree.activities.viewProfile;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import ha.thanh.pikerfree.models.Post;
import ha.thanh.pikerfree.models.User;
import ha.thanh.pikerfree.services.GPSTracker;

public class ViewProfilePresenter {
    private ViewProfileInterface.RequiredViewOps mView;
    private Handler handler;
    private FirebaseDatabase database;
    private List<Post> postList;
    private String currentUserId;
    private GPSTracker gpsTracker;
    private User user;

    List<Post> getPostList() {
        return postList;
    }

    ViewProfilePresenter(Context context, ViewProfileInterface.RequiredViewOps mView, String currentUserId) {
        this.mView = mView;
        this.currentUserId = currentUserId;
        handler = new Handler();
        postList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        gpsTracker = new GPSTracker(context);
    }

    void updateRating(double rate) {
        List<String> list;
        list = user.getRatedUsers();
        if (user.getRatedUsers() != null) {
            for (int i = 0; i < list.size(); i++) {
                if (FirebaseAuth.getInstance().getCurrentUser().getUid().equalsIgnoreCase(list.get(i))) {
                    mView.onRatingFail("You already review this user before");
                    return;
                }
            }
            list.add(FirebaseAuth.getInstance().getCurrentUser().getUid());
            user.setRatedUsers(list);
            double newRate = (rate + list.size() * user.getRating()) / (list.size() + 1);
            user.setRating(newRate);
            updateUser(newRate);
        } else {
            list = new ArrayList<>();
            list.add(FirebaseAuth.getInstance().getCurrentUser().getUid());
            double newRate = (rate + 5) / 2;
            user.setRating(newRate);
            user.setRatedUsers(list);
            updateUser(newRate);
        }
    }

    private void updateUser(double newRate) {
        DatabaseReference userRatingPref;
        userRatingPref = database.getReference("users").child(user.getId()).child("rating");
        userRatingPref.setValue(user.getRating());
        DatabaseReference ratedUserPref;
        ratedUserPref = database.getReference("users").child(user.getId()).child("ratedUsers");
        ratedUserPref.setValue(user.getRatedUsers());
        mView.onRatingDone(newRate);
    }

    public double getUserLat() {
        return gpsTracker.getLatitude();
    }

    public double getUserLng() {
        return gpsTracker.getLongitude();
    }

    void loadAllMyPost() {
        postList.clear();
        handler.post(new Runnable() {
            @Override
            public void run() {
                final DatabaseReference userPref;
                userPref = database
                        .getReference("users")
                        .child(currentUserId);
                userPref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        user = dataSnapshot.getValue(User.class);
                        user.setOnline((Boolean) dataSnapshot.child("isOnline").getValue());
                        checkFollowStatus();
                        mView.onGetUserDataDone(user);
                        getUserImageLink(user.getAvatarLink());
                        getPostData(user.getPosts());

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void checkFollowStatus(){
        List<String> followingUsers = new ArrayList<>();
        if (user.getFollowingUsers() != null)
            followingUsers = user.getFollowingUsers();

        if(followingUsers.contains(FirebaseAuth.getInstance().getCurrentUser().getUid()))
            mView.onAlreadyFollow();
    }

    void followUser() {
        List<String> followingUsers = new ArrayList<>();
        if (user.getFollowingUsers() != null)
            followingUsers = user.getFollowingUsers();
        followingUsers.add(FirebaseAuth.getInstance().getCurrentUser().getUid());
        user.setFollowingUsers(followingUsers);
        DatabaseReference databaseReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("users")
                .child(user.getId())
                .child("followingUsers");
        databaseReference.setValue(followingUsers);
        mView.onFollowSuccess("You now following this user, you will get notification when this user have new post.");
    }

    void unfollowUser() {

        List<String> followingUsers = new ArrayList<>();
        followingUsers = user.getFollowingUsers();
        followingUsers.remove(FirebaseAuth.getInstance().getCurrentUser().getUid());
        DatabaseReference databaseReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("users")
                .child(user.getId())
                .child("followingUsers");
        databaseReference.setValue(followingUsers);
        mView.onUnFollowSuccess("You now remove following to this user");
    }

    private void getUserImageLink(String link) {

        StorageReference mStorageRef;
        mStorageRef = FirebaseStorage.getInstance()
                .getReference().child(link);
        mStorageRef.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        mView.getOwnerImageDone(uri);
                    }
                });
    }

    private void getPostData(final ArrayList<Integer> posts) {
        if (posts != null) {
            for (int i = 0; i < posts.size(); i++) {
                DatabaseReference postRef;
                postRef = database
                        .getReference("posts")
                        .child(posts.get(i).toString());
                postRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        postList.add(dataSnapshot.getValue(Post.class));
                        mView.onGetUserPostsDone();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
    }
}
