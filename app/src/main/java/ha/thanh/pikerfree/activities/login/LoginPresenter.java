package ha.thanh.pikerfree.activities.login;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

import ha.thanh.pikerfree.models.User;


public class LoginPresenter implements LoginInterface.RequiredPresenterOps {

    private LoginInterface.RequiredViewOps mView;
    private LoginModel mModel;
    private Activity activity;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase database;
    private User dataUser;
    private ValueEventListener eventListener;
    private DatabaseReference pref;
    private boolean isGotData = false;


    LoginPresenter(Activity activity, LoginInterface.RequiredViewOps mView) {
        this.mView = mView;
        this.activity = activity;
        mModel = new LoginModel(activity.getApplicationContext(), this);
        auth = FirebaseAuth.getInstance();
    }


    void checkLogIn() {
        if (auth.getCurrentUser() != null) {
            mView.onLogInSuccess();
        }

    }

    void doLogin(String email, final String password) {
        //authenticate user
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            // there was an error
                            if (password.length() < 6) {
                                mView.onPasswordWeek();
                                mView.onHideWaitingDialog();
                            }
                        } else {
                            // success
                            getDataFromServer();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        {
                            mView.onHideWaitingDialog();
                            mView.onShowAlert("Error", e.getMessage());
                        }
                    }
                });
    }

    private void getDataFromServer() {
        if (!isGotData) {
            isGotData = true;
            firebaseUser = auth.getCurrentUser();
            dataUser = new User();
            database = FirebaseDatabase.getInstance();
            pref = database.getReference().child("users").child(firebaseUser.getUid());
            eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    dataUser = dataSnapshot.getValue(User.class);
                    Log.d("Login", " get Data from server " + dataUser.toString());
                    saveDataToLocal();
                    String instanceId = FirebaseInstanceId.getInstance().getToken();
                    database.getReference().child("users").child(dataUser.getId()).child("instanceId").setValue(instanceId);
                    mView.onHideWaitingDialog();
                    mView.onLogInSuccess();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            pref.addValueEventListener(eventListener);
        }
    }

    void removeListener() {
        if (pref != null)
            pref.removeEventListener(eventListener);
    }

    private void saveDataToLocal() {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference pathReference = storageRef.child("userImages/" + dataUser.getId() + ".jpg");

        ContextWrapper cw = new ContextWrapper(activity.getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File myPath = new File(directory, "profile.jpg");

        pathReference.getFile(myPath).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Log.d("Login", "Got profile pic from server");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("Login", exception.toString());
            }
        });
        mModel.saveDataLocal(dataUser, myPath.getAbsolutePath());
    }

}
