package ha.thanh.pikerfree.activities.newPost;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.vlk.multimager.utils.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ha.thanh.pikerfree.models.ImagePost;
import ha.thanh.pikerfree.models.Messages.MyGeoLocation;
import ha.thanh.pikerfree.models.Post;
import ha.thanh.pikerfree.models.User;
import ha.thanh.pikerfree.utils.Utils;


class NewPostPresenter {

    private NewPostInterface.RequiredViewOps mView;
    private NewPostModel mModel;

    private List<ImagePost> imagePostList;
    private StorageReference mStorageRef;
    private FirebaseDatabase database;
    private FirebaseUser firebaseUser;

    private ValueEventListener eventListener;
    private int postCount = -1;
    private int imageCount = 0;
    private User dataUser;
    private Post post;
    private Handler handler;
    private boolean isUpdatedPostDatabase = false;
    private boolean isUpdatedUserDatabase = false;
    private boolean isGetDataUser = false;
    private boolean isGetPostCount = false;
    private ArrayList<Integer> postList;
    int selectedCategory = 8;

    NewPostPresenter(Context context, NewPostInterface.RequiredViewOps mView) {

        this.mView = mView;
        this.imagePostList = new ArrayList<>();
        imagePostList.add(new ImagePost("", true, "image_no_0"));
        mModel = new NewPostModel(context);
        handler = new Handler();
        dataUser = new User();
        postList = new ArrayList<>();
        mStorageRef = FirebaseStorage.getInstance().getReference().child("postImages");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        getCurrentPostCount();
    }

    void startUploadImages() {
        imagePostList.remove(imagePostList.size() - 1);
        imageCount--;
        for (int i = 0; i < imagePostList.size(); i++) {
            upLoadSingleImage(imagePostList.get(i));
        }
    }

    void addAllImage(ArrayList<Image> imagesList) {
        for (int i = imageCount; i < imagesList.size(); i++) {
            imagePostList.add(
                    new ImagePost(imagesList.get(i).imagePath,
                            "image_no_" + String.valueOf(imageCount + 1) + ".jpg"));
            imageCount++;
        }
        imagePostList.remove(0);
        imagePostList.add(new ImagePost("", true, "image_no_0"));

    }

    void uploadPostToDatabase(String title, String description) {
        isUpdatedPostDatabase = false;
        isUpdatedUserDatabase = false;
        getCurrentUser();
        createPost(title, description, selectedCategory);
        uploadPostData();
        updateCurrentPostCount();
    }

    private void getCurrentUser() {

        handler.post(new Runnable() {
            @Override
            public void run() {
                DatabaseReference userPref;
                userPref = database.getReference("users").child(firebaseUser.getUid());
                userPref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        dataUser = dataSnapshot.getValue(User.class);
                        isGetDataUser = true;
                        if (dataUser.getPosts() != null)
                            postList = dataUser.getPosts();
                        postList.add(postCount);
                        dataUser.setPosts(postList);
                        updateUserData();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void createPost(String title, String description, int category) {
        post = new Post();
        post.setTitle(title);
        post.setDescription(description);
        post.setCategory(category);
        post.setLocation(new MyGeoLocation(mModel.getUserLat(), mModel.getUserLng()));
        post.setPostId(postCount + 1);
        post.setOwnerId(firebaseUser.getUid());
        post.setTimePosted(Utils.getCurrentTimestamp());
    }

    private void uploadPostData() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                DatabaseReference postPref;
                postPref = database.getReference("posts").child(postCount + "");
                postPref.setValue(post);
                isUpdatedPostDatabase = true;
                Log.e("thanh", "done save database post to server");
                checkIfCanHideDialog();
            }
        });
    }

    private void updateUserData() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (isGetDataUser) {
                    DatabaseReference userPostPref;
                    userPostPref = database.getReference("users").child(firebaseUser.getUid()).child("posts");
                    userPostPref.setValue(dataUser.getPosts());
                    isUpdatedUserDatabase = true;
                    checkIfCanHideDialog();
                } else {
                    handler.postDelayed(this, 100);
                }
            }
        });
    }

    List<ImagePost> getItemList() {
        return imagePostList;
    }


    private void upLoadSingleImage(final ImagePost imagePost) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (imagePost.getPathLocal() != null) {
                    Uri file = Uri.fromFile(new File(imagePost.getPathLocal()));
                    StorageReference riversRef
                            = mStorageRef.child(String.valueOf(postCount + 1) + "/" + imagePost.getName());
                    riversRef.putFile(file)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    imagePostList.get(getImagePostIndexFromName(imagePost.getName()))
                                            .setUploadDone(true);
                                    mView.onUploadSingleImageDone();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    Log.e("thanh", " it's not over yet bitch");
                                }
                            });
                }
            }
        });
    }

    private int getImagePostIndexFromName(String name) {
        for (int i = 0; i < imagePostList.size(); i++) {
            if (imagePostList.get(i).getName().equalsIgnoreCase(name))
                return i;
        }
        return -1;
    }

    private void checkIfCanHideDialog() {
        if (isUpdatedPostDatabase && isUpdatedUserDatabase)
            mView.onPostDone();
    }

    private void getCurrentPostCount() {
        DatabaseReference postCountRef;
        postCountRef = database.getReference().child("postCount");
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                postCount = dataSnapshot.getValue(int.class);
                Log.e("thanh", " get count = " + postCount);
                isGetPostCount = true;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        if (!isGetPostCount)
            postCountRef.addListenerForSingleValueEvent(eventListener);
        else postCountRef.removeEventListener(eventListener);
    }

    private void updateCurrentPostCount() {
        DatabaseReference postCountRef;
        postCount++;
        postCountRef = database.getReference().child("postCount");
        postCountRef.setValue(postCount);
    }
}
