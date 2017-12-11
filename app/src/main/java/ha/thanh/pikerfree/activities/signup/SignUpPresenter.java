package ha.thanh.pikerfree.activities.signup;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import ha.thanh.pikerfree.models.User;

class SignUpPresenter {

    private SignUpInterface.RequiredViewOps mView;
    private SignUpModel mModel;
    private Activity activity;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private User dataUser;

    SignUpPresenter(Activity context, SignUpInterface.RequiredViewOps mView) {
        this.mView = mView;
        this.activity = context;
        mModel = new SignUpModel(context);
        auth = FirebaseAuth.getInstance();
    }

    void doSignUp(final String email, String password, final String userName) {

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            getCurrentUser(userName, email);
                            updateUserDataToServer(userName);
                            saveDataLocal(userName, auth.getCurrentUser().getUid());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        {
                            mView.onHideWaiting();
                            mView.onShowInforDialog("Error", e.getMessage());
                        }
                    }
                });
    }

    private void saveDataLocal(String userName, String userId) {
        mModel.saveLocal(userName, userId);
    }

    private void getCurrentUser(String userName, String email) {
        // get Firebase user and convert to dataUser to push to database
        firebaseUser = auth.getCurrentUser();
        dataUser = new User();
        dataUser.setName(userName);
        dataUser.setEmail(email);
        dataUser.setId(firebaseUser.getUid());
    }

    private void updateDataBase() {
        // update to database;
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users");
        DatabaseReference usersRef = ref.child(firebaseUser.getUid());
        usersRef.setValue(dataUser);
        String instanceId = FirebaseInstanceId.getInstance().getToken();
        database.getReference().child("users").child(dataUser.getId()).child("instanceId").setValue(instanceId);
        mView.onDoneProcess();
    }

    private void updateUserDataToServer(String userName) {

        // update Auth data
        if (auth != null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(userName)
                    .setPhotoUri(Uri.parse(dataUser.getAvatarLink()))
                    .build();
            firebaseUser.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                updateDataBase();
                            }
                        }
                    });
        }
    }
}
