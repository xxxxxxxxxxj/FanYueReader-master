package com.jack.reader.ui.presenter;

import com.jack.reader.api.BookApi;
import com.jack.reader.base.RxPresenter;
import com.jack.reader.bean.KeywordListBean;
import com.jack.reader.ui.contract.SerchBookContract;

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
 * @date zhoujunxia on 2019/4/23 18:32
 */
public class SerchBookPresenter extends RxPresenter<SerchBookContract.View> implements SerchBookContract.Presenter<SerchBookContract.View> {
    private BookApi bookApi;

    @Inject
    public SerchBookPresenter(BookApi bookApi) {
        this.bookApi = bookApi;
    }

    @Override
    public void keyword(RequestBody build) {
        Subscription rxSubscription = bookApi.keyword(build).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<KeywordListBean>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.keywordError(e.toString());
                    }

                    @Override
                    public void onNext(KeywordListBean keywordListBean) {
                        if (keywordListBean.getErrno() == 0) {
                            if (keywordListBean.getData() != null) {
                                if (keywordListBean.getData().getList() != null && keywordListBean.getData().getList().size() > 0) {
                                    mView.keywordSuccess(keywordListBean.getData().getList());
                                } else {
                                    mView.keywordError("集合为空");
                                }
                            } else {
                                mView.keywordError("data为null");
                            }
                        } else {
                            mView.keywordError(keywordListBean.getMsg());
                        }
                    }
                });
        addSubscrebe(rxSubscription);
    }
}