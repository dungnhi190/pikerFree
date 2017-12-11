package ha.thanh.pikerfree.fragments.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.activities.information.AboutActivity;
import ha.thanh.pikerfree.activities.information.HelpActivity;
import ha.thanh.pikerfree.activities.information.ManageActivity;
import ha.thanh.pikerfree.activities.information.NotificationActivity;
import ha.thanh.pikerfree.activities.information.TermActivity;
import ha.thanh.pikerfree.activities.login.LoginActivity;
import ha.thanh.pikerfree.constants.Constants;
import ha.thanh.pikerfree.customviews.CustomYesNoDialog;


public class SettingFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.view_privacy)
    public void goToPrivacy() {
        Intent intent = new Intent(this.getContext(), TermActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.view_about)
    public void goToAbout() {
        Intent intent = new Intent(this.getContext(), AboutActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.view_help)
    public void toToHelp() {
        Intent intent = new Intent(this.getContext(), HelpActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.view_log_out)
    public void LogOut() {
        CustomYesNoDialog customYesNoDialog = new CustomYesNoDialog(this.getActivity(), new CustomYesNoDialog.YesNoInterFace() {
            @Override
            public void onYesClicked() {
                doLogOut();
            }

            @Override
            public void onNoClicked() {

            }
        });
        customYesNoDialog.showAlertDialog("Confirm", "Are you sure you want to log out?");
    }

    private void doLogOut() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.SETTING_CONFIG, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(Constants.USER_ID, null);
        edit.putString(Constants.USER_ADDRESS, null);
        edit.putString(Constants.USER_LAT, null);
        edit.putString(Constants.USER_LNG, null);
        edit.putString(Constants.USER_NAME, null);
        edit.putString(Constants.USER_PROFILE_PIC_PATH, null);

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this.getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @OnClick(R.id.view_notification)
    public void goToNotification() {
        Intent intent = new Intent(this.getContext(), NotificationActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.view_manage)
    public void goToManage() {
        Intent intent = new Intent(this.getContext(), ManageActivity.class);
        startActivity(intent);
    }

}