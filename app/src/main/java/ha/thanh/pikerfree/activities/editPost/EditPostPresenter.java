package ha.thanh.pikerfree.activities.editPost;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.activities.editPost.EditPostInterface;
import ha.thanh.pikerfree.activities.editPost.EditPostModel;
import ha.thanh.pikerfree.constants.Constants;
import ha.thanh.pikerfree.models.ImagePost;
import ha.thanh.pikerfree.models.Post;
import ha.thanh.pikerfree.models.User;
import ha.thanh.pikerfree.utils.Utils;

import static android.app.Activity.RESULT_OK;

/**
 * Created by HaVan on 10/16/2017.
 */

public class EditPostPresenter {


    private EditPostInterface.RequiredViewOps mView;
    private EditPostModel mModel;
    private List<String> imagePostList;
    private StorageReference mStorageRef;
    private FirebaseDatabase database;
    private List<ImagePost> imageLocalList;
    private Post post;
    private Handler handler;
    private Context con;
    private int postID;
    public int selectedCategory = 8;

    EditPostPresenter(Context context, EditPostInterface.RequiredViewOps mView) {
        this.mView = mView;
        this.con = context;
        mModel = new EditPostModel(context);
        initData();
    }

    List<String> getImagePostList() {
        return imagePostList;
    }

    private void initData() {
        imagePostList = new ArrayList<>();
        imageLocalList = new ArrayList<>();
        mStorageRef = FirebaseStorage
                .getInstance()
                .getReference()
                .child("postImages");
        database = FirebaseDatabase.getInstance();
        post = new Post();
        handler = new Handler();
    }

    private int imageCount = 0;

    void addAllImage(ArrayList<Image> imagesList) {
        for (int i = 0; i < imagesList.size(); i++) {
            imageLocalList.add(
                    new ImagePost(imagesList.get(i).imagePath,
                            "image_no_" + String.valueOf(imageCount + 1) + ".jpg"));
            imageCount++;
        }
        imageLocalList.add(new ImagePost("", true, "image_no_0"));

    }

    private int getImagePostIndexFromName(String name) {
        for (int i = 0; i < imagePostList.size(); i++) {
            if (imageLocalList.get(i).getName().equalsIgnoreCase(name))
                return i;
        }
        return -1;
    }

    void startUploadImages() {
        for (int i = 0; i < imageLocalList.size() - 1; i++) {
            upLoadSingleImage(imageLocalList.get(i));
        }
    }

    void uploadPostToDatabase(String title, String description) {
        createPost(title, description, selectedCategory);
        uploadPostData();
    }

    private void createPost(String title, String description, int category) {

        post.setTitle(title);
        post.setDescription(description);
        post.setCategory(category);
        post.setTimePosted(Utils.getCurrentTimestamp());
    }

    private void uploadPostData() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                DatabaseReference postPref;
                postPref = database.getReference("posts").child(postID + "");
                postPref.setValue(post);
                mView.onPostDone();
            }
        });
    }

    private void upLoadSingleImage(final ImagePost imagePost) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (imagePost.getPathLocal() != null) {
                    Uri file = Uri.fromFile(new File(imagePost.getPathLocal()));
                    StorageReference riversRef
                            = mStorageRef.child(String.valueOf(postID) + "/" + imagePost.getName());
                    riversRef.putFile(file)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    imageLocalList.get(getImagePostIndexFromName(imagePost.getName()))
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

    List<ImagePost> getItemList() {
        return imageLocalList;
    }

    void getPostData(final String postId) {

        postID = Integer.parseInt(postId);
        handler.post(new Runnable() {
            @Override
            public void run() {
                DatabaseReference postRef;
                postRef = database
                        .getReference("posts")
                        .child(postId);
                postRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        post = dataSnapshot.getValue(Post.class);
                        mView.getPostDone(post);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                        mView.getPostFail();
                    }
                });
            }
        });
    }

    String getStatus() {
        if (post.getStatus() == 1)
            return "Opening";
        return "Closed";
    }

    void changeStatus(int status) {
        post.setStatus(status);
    }

    void getImageLinksFromId(final String postId) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 6; i++) {
                    mStorageRef = FirebaseStorage
                            .getInstance()
                            .getReference()
                            .child("postImages")
                            .child(postId)
                            .child("image_no_" + i + ".jpg");
                    mStorageRef.getDownloadUrl()
                            .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    imagePostList.add(uri.toString());
                                    mView.getLinkDone();
                                }
                            });
                }
            }
        });

    }
}
