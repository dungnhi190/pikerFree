package ha.thanh.pikerfree.services;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import ha.thanh.pikerfree.R;


public class DialogMessage {
    AlertDialog alertDialog;
    Context context;
    TextView tvMessage, tvActionNagetive, tvActionPositive;
    OnClickButton onClickButton;


    public DialogMessage(Context context) {
        this.context = context;
        LayoutInflater _inflater = LayoutInflater.from(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = _inflater.inflate(R.layout.dialog_message, null);
        tvMessage = (TextView) view.findViewById(R.id.tv_message);
        tvActionNagetive = (TextView) view.findViewById(R.id.tv_action_nagetive);
        tvActionPositive = (TextView) view.findViewById(R.id.tv_action_positive);

        tvActionNagetive.setOnClickListener(onClickListener);
        tvActionPositive.setOnClickListener(onClickListener);
        builder.setView(view);
        alertDialog = builder.create();
    }

    public void setOnClickButton(OnClickButton onClickButton) {
        this.onClickButton = onClickButton;
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (onClickButton != null) {
                switch (view.getId()) {
                    case R.id.tv_action_nagetive:
                        onClickButton.onClickNegative();
                        break;
                    case R.id.tv_action_positive:
                        onClickButton.onClickPositive();
                        break;
                }
            }
            alertDialog.dismiss();
        }
    };


    public void setMessage(String message) {
        tvMessage.setText(message);
    }

    public void setTextNagetiveButton(int resource) {
        tvActionNagetive.setText(resource);
    }

    public void setTextPositiveButton(int resource) {
        tvActionPositive.setText(resource);
    }


    public void showDialog() {
        alertDialog.show();
    }


    public interface OnClickButton {
        void onClickNegative();

        void onClickPositive();
    }

}
