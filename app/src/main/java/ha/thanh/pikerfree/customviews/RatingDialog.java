package ha.thanh.pikerfree.customviews;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;

import ha.thanh.pikerfree.R;

public class RatingDialog {

    private Dialog alertDialog;
    private optionInterface interFace;


    public interface optionInterface {
        void onReview(double rating);
    }

    public RatingDialog(Activity activity, optionInterface interFace) {
        alertDialog = new Dialog(activity);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.setContentView(R.layout.view_rating_dialog);
        this.interFace = interFace;
    }

    public void showRatingDialog() {

        CustomTextView rate1 = (CustomTextView) alertDialog.findViewById(R.id.rate_1);
        CustomTextView rate2 = (CustomTextView) alertDialog.findViewById(R.id.rate_2);
        CustomTextView rate3 = (CustomTextView) alertDialog.findViewById(R.id.rate_3);
        CustomTextView rate4 = (CustomTextView) alertDialog.findViewById(R.id.rate_4);
        CustomTextView rate5 = (CustomTextView) alertDialog.findViewById(R.id.rate_5);

        rate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                interFace.onReview(1);
            }
        });
        rate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                interFace.onReview(2);
            }
        });
        rate3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                interFace.onReview(3);
            }
        });
        rate4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                interFace.onReview(4);
            }
        });
        rate5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                interFace.onReview(5);
            }
        });
        alertDialog.show();
    }
}
