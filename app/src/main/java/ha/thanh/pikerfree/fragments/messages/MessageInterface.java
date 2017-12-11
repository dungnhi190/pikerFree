package ha.thanh.pikerfree.fragments.messages;


class MessageInterface {
    interface RequiredViewOps {

        void onGetConversationDone();

        void onGetConversationFail(String error);
    }
}
