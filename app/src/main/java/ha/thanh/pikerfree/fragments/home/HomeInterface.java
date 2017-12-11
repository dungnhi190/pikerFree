package ha.thanh.pikerfree.fragments.home;

import ha.thanh.pikerfree.models.User;

class HomeInterface {
    interface RequiredViewOps {

        void onLocalDataReady(String name, String address, String filepath);

        void onGetUserDataDone(User user);

        void onGetUserPostsDone();
    }
}
