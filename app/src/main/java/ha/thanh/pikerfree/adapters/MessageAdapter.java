package ha.thanh.pikerfree.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.models.Messages.Message;
import ha.thanh.pikerfree.utils.Utils;


public class MessageAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    private Context mContext;
    private List<Message> mMessageList;

    public MessageAdapter(Context context, List<Message> messageList) {

        mContext = context;
        mMessageList = messageList;
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    @Override
    public int getItemViewType(int position) {

        Message message = mMessageList.get(position);
        if (message.getAuthor().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_mine, parent, false);
            return new MessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_their, parent, false);
            return new MessageHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Message message = mMessageList.get(position);

        ((MessageHolder) holder).bind(message, position);

    }

    private class MessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        MessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.tv_mess_content);
            timeText = (TextView) itemView.findViewById(R.id.tv_mess_time);
        }

        void bind(Message message, int position) {
            messageText.setText(message.getText());
            timeText.setText(Utils.getTimeInHour(message.getTime()));
            try {
                if (mMessageList.get(position + 1).getAuthor().equalsIgnoreCase(mMessageList.get(position).getAuthor()))
                    timeText.setVisibility(View.GONE);
                else timeText.setVisibility(View.VISIBLE);
            } catch (IndexOutOfBoundsException e) {
                e.getMessage();
            }
        }
    }
}
