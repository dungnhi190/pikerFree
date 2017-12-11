package ha.thanh.pikerfree.activities.viewListPost;

import android.content.Context;
import android.os.Handler;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ha.thanh.pikerfree.constants.Constants;
import ha.thanh.pikerfree.constants.Globals;
import ha.thanh.pikerfree.models.Post;
import ha.thanh.pikerfree.utils.Utils;


class ViewListPostPresenter {

    private ViewListPostInterface.RequiredViewOps mView;
    private ViewListPostModel mModel;
    private FirebaseDatabase database;
    private List<Post> postList;
    private Handler handler;
    private int currentCategory = 8;
    private boolean hasCategory = false;
    private boolean hasCount = false;
    private int postCount = -1;

    void setCurrentCategory(int currentCategory) {
        this.currentCategory = currentCategory;
        hasCategory = true;
        checkIfCanGetData();
    }

    int getCurrentCategory() {
        return currentCategory;
    }

    private void checkIfCanGetData() {
        if (hasCategory && hasCount)
            getPostData();
    }

    List<Post> getPostList() {
        return postList;
    }

    ViewListPostPresenter(Context context, ViewListPostInterface.RequiredViewOps mView) {
        this.mView = mView;
        mModel = new ViewListPostModel(context);
        postList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        handler = new Handler();
        getCurrentPostCount();
    }

    private void getCurrentPostCount() {
        DatabaseReference postCountRef;
        postCountRef = database.getReference().child("postCount");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                postCount = dataSnapshot.getValue(int.class);
                hasCount = true;
                checkIfCanGetData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        postCountRef.addListenerForSingleValueEvent(eventListener);
    }

    double getUserLat() {
        return mModel.getUserLat();
    }

    double getUserLng() {
        return mModel.getUserLng();
    }

    private void getPostData() {
        if (currentCategory < 9) {
            searchCategory();
        } else if (currentCategory == Constants.CATE_RECENT) {
            // search recent posts
            searchRecent();
        } else if (currentCategory == Constants.CATE_NEAR_BY) {
            // search near by posts
            searchNearBy();
        }
    }

    private void searchCategory() {
        handler.post(new Runnable() {
            @Override
            public void run() {

                Query query = database.getReference()
                        .child("posts")
                        .orderByChild("category")
                        .equalTo(currentCategory);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot issue : dataSnapshot.getChildren()) {
                                postList.add(issue.getValue(Post.class));
                                mView.onGetPostDone();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void searchRecent() {
        handler.post(new Runnable() {
            @Override
            public void run() {

                Query query = database.getReference()
                        .child("posts")
                        .orderByChild("timePosted")
                        .endAt(Utils.getCurrentTimestamp() - 86400 * Globals.getIns().getConfig().getLastDay());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot issue : dataSnapshot.getChildren()) {
                                postList.add(issue.getValue(Post.class));
                                mView.onGetPostDone();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void searchNearBy() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                GeoFire geoFire;
                final DatabaseReference mDatabase = database.getReference().child("posts");
                geoFire = new GeoFire(database.getReference().child("posts"));
                GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(
                                mModel.getUserLat(),
                                mModel.getUserLng()),
                        Globals.getIns().getConfig().getDistanceSearch()
                );
                geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
                    @Override
                    public void onKeyEntered(String key, GeoLocation location) {

                        mDatabase.child(key).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                Post post = snapshot.getValue(Post.class);
                                postList.add(post);
                                mView.onGetPostDone();
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {

                            }
                        });
                    }

                    @Override
                    public void onKeyExited(String key) {
                    }

                    @Override
                    public void onKeyMoved(String key, GeoLocation location) {

                    }

                    @Override
                    public void onGeoQueryReady() {
                    }

                    @Override
                    public void onGeoQueryError(DatabaseError error) {

                    }
                });
            }
        });
    }
}
