package ha.thanh.pikerfree.activities.newPost;


class NewPostInterface {
    interface RequiredViewOps {

        void onPostDone();

        void onUploadSingleImageDone();

        void onPostFail(String error);
    }

}
