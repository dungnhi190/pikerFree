package ha.thanh.pikerfree.activities.forgetPassword;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.customviews.CustomAlertDialog;
import ha.thanh.pikerfree.customviews.WaitingDialog;

public class ForgetPassActivity extends AppCompatActivity {

    @BindView(R.id.et_email_reset)
    EditText emailReset;

    private FirebaseAuth auth;
    CustomAlertDialog alertDialog;
    WaitingDialog waitingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        ButterKnife.bind(this);
        auth = FirebaseAuth.getInstance();
        alertDialog = new CustomAlertDialog(this);
        waitingDialog = new WaitingDialog(this);

    }

    @OnClick(R.id.btn_reset)
    public void resetPass() {
        {
            String email = emailReset.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                alertDialog.showAlertDialog("Error", "Email must not be empty!");
                return;
            }
            waitingDialog.showDialog();
            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                alertDialog.showAlertDialog("Done", "We have sent you instructions to reset your password!");
                            } else {
                                alertDialog.showAlertDialog("Failed", "Failed to send reset email!");
                            }

                            waitingDialog.hideDialog();
                        }
                    });
        }
    }

}
