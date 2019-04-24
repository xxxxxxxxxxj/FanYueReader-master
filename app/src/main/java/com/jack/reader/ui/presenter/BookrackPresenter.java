package com.jack.reader.ui.presenter;

import com.jack.reader.api.BookApi;
import com.jack.reader.base.RxPresenter;
import com.jack.reader.bean.BooksByCats;
import com.jack.reader.bean.ClassIndexBean;
import com.jack.reader.bean.PageHomeBean;
import com.jack.reader.bean.RankListBean;
import com.jack.reader.bean.Rankings;
import com.jack.reader.ui.contract.BookrackContract;
import com.jack.reader.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import okhttp3.RequestBody;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by 山鸡 on 2017/9/9.
 */

public class BookrackPresenter extends RxPresenter<BookrackContract.View> implements BookrackContract.Presenter<BookrackContract.View> {

    private BookApi bookApi;


    @Inject
    public BookrackPresenter(BookApi bookApi) {
        this.bookApi = bookApi;
    }

    @Override
    public void getRankList(String id) {
        Subscription rxSubscription = bookApi.getRanking(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Rankings>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getRankList:" + e.toString());
                        mView.showError();
                    }

                    @Override
                    public void onNext(Rankings ranking) {
                        List<Rankings.RankingBean.BooksBean> books = ranking.ranking.books;

                        BooksByCats cats = new BooksByCats();
                        cats.books = new ArrayList<>();
                        for (Rankings.RankingBean.BooksBean bean : books) {
                            cats.books.add(new BooksByCats.BooksBean(bean._id, bean.cover, bean.title, bean.author, bean.cat, bean.shortIntro, bean.latelyFollower, bean.retentionRatio));
                        }
                        mView.showRankList(cats);
//                        mView.showBannerList(null);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    public void pagehome(RequestBody build) {
        Subscription rxSubscription = bookApi.pagehome(build).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PageHomeBean>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.pagehomeError(e.toString());
                    }

                    @Override
                    public void onNext(PageHomeBean pageHomeBean) {
                        if (pageHomeBean.getErrno() == 0) {
                            if (pageHomeBean.getData() != null) {
                                mView.pagehomeSuccess(pageHomeBean.getData());
                            } else {
                                mView.pagehomeError("data为null");
                            }
                        } else {
                            mView.pagehomeError(pageHomeBean.getMsg());
                        }
                    }
                });
        addSubscrebe(rxSubscription);
    }

    public void classIndex(RequestBody build) {
        Subscription rxSubscription = bookApi.classIndex(build).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ClassIndexBean>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.classIndexError(e.toString());
                    }

                    @Override
                    public void onNext(ClassIndexBean classIndexBean) {
                        if (classIndexBean.getErrno() == 0) {
                            if (classIndexBean.getData() != null) {
                                mView.classIndexSuccess(classIndexBean.getData());
                            } else {
                                mView.classIndexError("data为null");
                            }
                        } else {
                            mView.classIndexError(classIndexBean.getMsg());
                        }
                    }
                });
        addSubscrebe(rxSubscription);
    }

    public void rankList(RequestBody build) {
        Subscription rxSubscription = bookApi.rankList(build).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RankListBean>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.classIndexError(e.toString());
                    }

                    @Override
                    public void onNext(RankListBean rankListBean) {
                        if (rankListBean.getErrno() == 0) {
                            if (rankListBean.getData() != null) {
                                mView.rankListSuccess(rankListBean.getData());
                            } else {
                                mView.rankListError("data为null");
                            }
                        } else {
                            mView.rankListError(rankListBean.getMsg());
                        }
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
