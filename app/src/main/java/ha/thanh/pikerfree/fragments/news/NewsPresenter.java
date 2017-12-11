package ha.thanh.pikerfree.fragments.news;


import android.content.Context;

public class NewsPresenter {

    private NewsInterface.RequiredViewOps mView;

    NewsPresenter(Context context, NewsInterface.RequiredViewOps mView) {
        this.mView = mView;

    }

}
