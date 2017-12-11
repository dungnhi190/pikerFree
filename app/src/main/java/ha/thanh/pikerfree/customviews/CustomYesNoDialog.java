package ha.thanh.pikerfree.customviews;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ha.thanh.pikerfree.R;

/**
 * Created by HaVan on 8/31/2017.
 */

public class CustomYesNoDialog {

    private Dialog alertDialog;
    private YesNoInterFace interFace;


    public interface YesNoInterFace {

        void onYesClicked();

        void onNoClicked();
    }

    public CustomYesNoDialog(Activity activity, YesNoInterFace yesNoInterFace) {
        alertDialog = new Dialog(activity);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setContentView(R.layout.view_yes_no_dialog);
        this.interFace = yesNoInterFace;
    }

    public void showAlertDialog(String title, String message) {

        CustomTextView tvContent = (CustomTextView) alertDialog.findViewById(R.id.tv_content);
        CustomTextView tvTitle = (CustomTextView) alertDialog.findViewById(R.id.tv_title);
        CustomTextView tvCancel = (CustomTextView) alertDialog.findViewById(R.id.btn_cancel_dialog);
        CustomTextView tvOK = (CustomTextView) alertDialog.findViewById(R.id.btn_ok_dialog);
        tvTitle.setText(title);
        tvContent.setText(message);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                interFace.onNoClicked();
            }
        });


        tvOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                interFace.onYesClicked();
            }
        });


        alertDialog.show();


    }
}
