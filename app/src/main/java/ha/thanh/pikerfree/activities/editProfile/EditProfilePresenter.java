package ha.thanh.pikerfree.activities.editProfile;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import ha.thanh.pikerfree.models.User;


class EditProfilePresenter {

    private EditProfileInterface.RequiredViewOps mView;
    private EditProfileModel mModel;
    private Context context;

    private StorageReference mStorageRef;
    private DatabaseReference databaseReference;

    private User dataUser;

    private String userId;
    private String userName;
    private String userAddress;
    private String userPhone;

    private boolean isImagesChanged = false;
    private boolean isUploadDone = false;
    private boolean isUpdatedDatabase = false;

    private Handler handler;

    EditProfilePresenter(Context context, EditProfileInterface.RequiredViewOps mView) {
        this.mView = mView;
        this.context = context;
        mModel = new EditProfileModel(context);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        userId = mModel.getUserIdFromSharePf();
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);
        handler = new Handler();
        dataUser = new User();
        updateDataUser();
    }

    void setImagesChanged() {
        this.isImagesChanged = true;
    }


    void getLocalData() {
        userName = mModel.getUserNameStringFromSharePf();
        userAddress = mModel.getUserAddressStringFromSharePf();
        userPhone = mModel.getUserPhoneFromSharePf();
        mView.onLocalDataReady(userName, userAddress, userPhone, mModel.getLocalImageStringFromSharePf());
    }

    void addTextChangeListener(final EditText etUserName, final EditText etUserAddress, final EditText etUserPhone) {
        userAddress = etUserAddress.getText().toString();
        userName = etUserName.getText().toString();
        etUserAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                userAddress = etUserAddress.getText().toString();
            }
        });
        etUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                userName = etUserName.getText().toString();
            }
        });
        etUserPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                userPhone = etUserPhone.getText().toString();
            }
        });
    }

    void saveDatabaseSetting() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                updateDataUser();
                databaseReference.child("avatarLink").setValue(dataUser.getAvatarLink());
                databaseReference.child("address").setValue(dataUser.getAddress());
                databaseReference.child("name").setValue(dataUser.getName());
                databaseReference.child("phoneNumber").setValue(dataUser.getPhoneNumber());
                isUpdatedDatabase = true;
                checkIfCanHideDialog();
            }
        });
    }

    private void updateDataUser() {
        dataUser.setId(userId);
        dataUser.setAvatarLink("userImages/" + userId + ".jpg");
        dataUser.setAddress(userAddress);
        dataUser.setPhoneNumber(userPhone);
        dataUser.setName(userName);
    }

    void saveLocal(Bitmap bitmapImage) {

        ContextWrapper cw = new ContextWrapper(context.getApplicationContext());

        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);

        File myPath = new File(directory, "profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        mModel.saveLocal(userName, userAddress, myPath.getAbsolutePath(), userPhone);
    }

    void uploadFile(final Uri filePath) {

        if (!isImagesChanged) {
            isUploadDone = true;
            checkIfCanHideDialog();
            return;
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (filePath != null) {

                    StorageReference riversRef = mStorageRef.child("userImages/" + userId + ".jpg");
                    riversRef.putFile(filePath)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    isUploadDone = true;
                                    checkIfCanHideDialog();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    isUploadDone = true;
                                    checkIfCanHideDialog();
                                    Log.e("editProfile", " upload file to server get error");
                                }
                            });
                }
            }
        });

    }

    private void checkIfCanHideDialog() {
        if (isUpdatedDatabase && isUploadDone)
            mView.hideDialog();
    }
}
