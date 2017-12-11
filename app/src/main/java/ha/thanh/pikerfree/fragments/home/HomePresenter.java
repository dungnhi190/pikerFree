package ha.thanh.pikerfree.fragments.home;

import android.content.Context;
import android.os.Handler;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ha.thanh.pikerfree.models.Post;
import ha.thanh.pikerfree.models.User;


class HomePresenter {

    private HomeInterface.RequiredViewOps mView;
    private HomeModel mModel;
    private Handler handler;
    private FirebaseDatabase database;
    private List<Post> postList;

    List<Post> getPostList() {
        return postList;
    }

    private User user;

    HomePresenter(Context context, HomeInterface.RequiredViewOps mView) {
        this.mView = mView;
        mModel = new HomeModel(context);
        handler = new Handler();
        postList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();

    }

    double getUserLat() {
        return mModel.getUserLat();
    }

    double getUserLng() {
        return mModel.getUserLng();
    }


    void loadAllMyPost() {
        postList.clear();
        handler.post(new Runnable() {
            @Override
            public void run() {
                DatabaseReference userPref;
                userPref = database
                        .getReference("users")
                        .child(mModel.getUserIdFromSharePf());
                userPref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        user = dataSnapshot.getValue(User.class);
                        mView.onGetUserDataDone(user);
                        getPostData(user.getPosts());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    void getLocalData() {
        String userName;
        String userAddress;
        userName = mModel.getUserNameStringFromSharePf();
        userAddress = mModel.getUserAddressStringFromSharePf();
        mView.onLocalDataReady(userName, userAddress, mModel.getLocalImageStringFromSharePf());
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
