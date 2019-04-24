package com.jack.reader.ui.presenter;

import com.jack.reader.api.BookApi;
import com.jack.reader.base.RxPresenter;
import com.jack.reader.bean.MyIndexBean;
import com.jack.reader.ui.contract.MyFragMentContract;

import javax.inject.Inject;

import okhttp3.RequestBody;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2019/4/23 14:40
 */
public class MyFragMentPresenter extends RxPresenter<MyFragMentContract.View> implements MyFragMentContract.Presenter<MyFragMentContract.View> {
    private BookApi bookApi;

    @Inject
    public MyFragMentPresenter(BookApi bookApi) {
        this.bookApi = bookApi;
    }

    @Override
    public void myIndex(RequestBody build) {
        Subscription rxSubscription = bookApi.myIndex(build).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MyIndexBean>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.myIndexError(e.toString());
                    }

                    @Override
                    public void onNext(MyIndexBean myIndexBean) {
                        if (myIndexBean.getErrno() == 0) {
                            if (myIndexBean.getData() != null) {
                                mView.myIndexSuccess(myIndexBean.getData());
                            } else {
                                mView.myIndexError("data为null");
                            }
                        } else {
                            mView.myIndexError(myIndexBean.getMsg());
                        }
                    }
                });
        addSubscrebe(rxSubscription);
    }
}