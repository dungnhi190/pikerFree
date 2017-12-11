package ha.thanh.pikerfree.customviews;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;

import ha.thanh.pikerfree.R;

public class UserInforDialog {

    private Dialog alertDialog;
    private optionInterface interFace;


    public interface optionInterface {

        void onViewProfile(String id);

        void onSendMess(String id);

        void onChoose(String id);
    }

    public UserInforDialog(Activity activity, optionInterface yesNoInterFace) {
        alertDialog = new Dialog(activity);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.setContentView(R.layout.view_multiple_dialog);
        this.interFace = yesNoInterFace;
    }

    public void showAlertDialog(String title, final String userID) {

        CustomTextView tvTitle = (CustomTextView) alertDialog.findViewById(R.id.tv_title);
        CustomTextView btnViewProfile = (CustomTextView) alertDialog.findViewById(R.id.btn_view_profile);
        CustomTextView btnSendMess = (CustomTextView) alertDialog.findViewById(R.id.btn_send_mes);
        CustomTextView btnChoose = (CustomTextView) alertDialog.findViewById(R.id.btn_choose);
        tvTitle.setText(title);
        btnViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                interFace.onViewProfile(userID);
            }
        });


        btnSendMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                interFace.onSendMess(userID);
            }
        });

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                interFace.onChoose(userID);
            }
        });
        alertDialog.show();
    }
}
