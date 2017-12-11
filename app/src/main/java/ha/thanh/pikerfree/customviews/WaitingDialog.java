package ha.thanh.pikerfree.customviews;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import ha.thanh.pikerfree.R;

/**
 * Created by HaVan on 8/30/2017.
 */

public class WaitingDialog {
    Dialog alertDialog;

    public WaitingDialog(Activity activity) {
        alertDialog = new Dialog(activity);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setContentView(R.layout.view_waiting_dialog);
    }

    public void showDialog() {
        alertDialog.show();
    }

    public void hideDialog() {
        alertDialog.dismiss();
    }
}
