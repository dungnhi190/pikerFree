package ha.thanh.pikerfree.activities.editPost;


import ha.thanh.pikerfree.models.Post;

class EditPostInterface {
    interface RequiredViewOps {

        void onPostDone();

        void onUploadSingleImageDone();

        void getPostDone(Post post);

        void getLinkDone();

        void getPostFail();
    }
}
