package ha.thanh.pikerfree.activities.signup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.activities.login.LoginActivity;
import ha.thanh.pikerfree.activities.main.MainActivity;
import ha.thanh.pikerfree.customviews.CustomAlertDialog;
import ha.thanh.pikerfree.customviews.WaitingDialog;

public class SignUpActivity extends AppCompatActivity implements SignUpInterface.RequiredViewOps {

    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_pass)
    EditText etPass;
    @BindView(R.id.et_pass_confirm)
    EditText etPassConfirm;

    SignUpPresenter presenter;
    private WaitingDialog waitingDialog;
    private CustomAlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        presenter = new SignUpPresenter(this, this);
        waitingDialog = new WaitingDialog(this);
        alertDialog = new CustomAlertDialog(this);

    }


    @OnClick(R.id.btn_sign_up)
    public void doSignUp() {

        String email = etEmail.getText().toString().trim();
        String username = etUserName.getText().toString().trim();
        String password = etPass.getText().toString().trim();
        String passwordConfirm = etPassConfirm.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            alertDialog.showAlertDialog("Whoop!", "Email must not be empty");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            alertDialog.showAlertDialog("Whoop!", "Password must not be empty");
            return;
        }

        if (password.length() < 6) {
            alertDialog.showAlertDialog("Whoop!", "Password must not be empty... and at least 6 characters");
            return;
        }
        if (!password.equals(passwordConfirm)) {
            alertDialog.showAlertDialog("Whoop!", "Password confirm does not match");
            return;
        }
        if (TextUtils.isEmpty(username)) {
            alertDialog.showAlertDialog("Whoop!", "Username must not be empty");
            return;
        }
        waitingDialog.showDialog();
        presenter.doSignUp(email, password, username);

    }

    @OnClick(R.id.btn_log_in)
    public void goToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void onHideWaiting() {
        waitingDialog.hideDialog();
    }

    @Override
    public void onShowInforDialog(String title, String mess) {
        alertDialog.showAlertDialog(title, mess);
    }

    @Override
    public void onDoneProcess() {
        waitingDialog.hideDialog();
        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
        finish();
    }
}
