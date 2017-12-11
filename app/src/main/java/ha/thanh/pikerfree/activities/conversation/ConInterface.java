package ha.thanh.pikerfree.activities.conversation;

import android.net.Uri;

import ha.thanh.pikerfree.models.Messages.Message;
import ha.thanh.pikerfree.models.User;

/**
 * Created by HaVan on 10/16/2017.
 */

public class ConInterface {
    interface RequiredViewOps {
        void getOPDone(User user);

        void getOwnerImageDone(Uri uri);

        void onGetMessDone(Message message);

        void onEndOfConversation();

        void onPullDone();
    }

}
