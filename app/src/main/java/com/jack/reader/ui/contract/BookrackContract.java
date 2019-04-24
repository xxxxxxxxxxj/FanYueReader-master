package com.jack.reader.ui.contract;

import com.jack.reader.base.BaseContract;
import com.jack.reader.bean.BooksByCats;
import com.jack.reader.bean.ClassIndexBean;
import com.jack.reader.bean.PageHomeBean;
import com.jack.reader.bean.RankListBean;

import okhttp3.RequestBody;

/**
 * Created by 山鸡 on 2017/9/9.
 */

public interface BookrackContract {


    interface View extends BaseContract.BaseView {
        void showRankList(BooksByCats data);
        void pagehomeError(String msg);
        void pagehomeSuccess(PageHomeBean.PageHomeData data);

        void classIndexError(String msg);

        void classIndexSuccess(ClassIndexBean.ClassIndexData data);

        void rankListSuccess(RankListBean.RankListData data);

        void rankListError(String msg);
    }


    interface Presenter<T> extends BaseContract.BasePresenter<T> {

        void getRankList(String id);
        void pagehome(RequestBody build);
        void classIndex(RequestBody build);
        void rankList(RequestBody build);
    }
}
