package ha.thanh.pikerfree.activities.viewPost;

import android.graphics.Bitmap;
import android.net.Uri;

import java.util.List;

import ha.thanh.pikerfree.models.Post;
import ha.thanh.pikerfree.models.User;


class PostInterface {

    interface RequiredViewOps {

        void getPostDone(Post post);

        void getLinkDone();

        void OnGoToEdit(int id);

        void getPostFail();

        void getOwnerDone(User user);

        void getOwnerImageDone(Uri uri);

        void onUserIsOwner();

        void onDeleteDone();

        void onAlreadyRequested();

        void onRequestSent();

        void showConfirmDialog(String mess);

        void onGetRequestingUserDone(int type);

        void OnStartConversation(String id1, String id2);

        void onPostFb(List<Bitmap> bms);

        void onGrantedDone(String userId);

        void onShowError(String error);

        void onGetCommentDone();
    }
}
