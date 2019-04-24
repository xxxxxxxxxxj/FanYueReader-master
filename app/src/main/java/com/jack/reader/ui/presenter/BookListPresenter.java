package com.jack.reader.ui.presenter;

import com.jack.reader.api.BookApi;
import com.jack.reader.base.RxPresenter;
import com.jack.reader.bean.NewBookListBean;
import com.jack.reader.ui.contract.BookListContract;

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
 * @date zhoujunxia on 2019/4/23 17:05
 */
public class BookListPresenter extends RxPresenter<BookListContract.View> implements BookListContract.Presenter<BookListContract.View> {
    private BookApi bookApi;

    @Inject
    public BookListPresenter(BookApi bookApi) {
        this.bookApi = bookApi;
    }

    @Override
    public void bookListClass(RequestBody build) {
        Subscription rxSubscription = bookApi.bookListClass(build).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewBookListBean>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.bookListError(e.toString());
                    }

                    @Override
                    public void onNext(NewBookListBean newBookListBean) {
                        if (newBookListBean.getErrno() == 0) {
                            if (newBookListBean.getData() != null) {
                                if (newBookListBean.getData().getList() != null && newBookListBean.getData().getList().size() > 0) {
                                    mView.bookListSuccess(newBookListBean.getData().getList());
                                } else {
                                    mView.bookListError("集合为空");
                                }
                            } else {
                                mView.bookListError("data为null");
                            }
                        } else {
                            mView.bookListError(newBookListBean.getMsg());
                        }
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void bookListBangDan(RequestBody build) {
        Subscription rxSubscription = bookApi.bookListBangDan(build).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewBookListBean>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.bookListError(e.toString());
                    }

                    @Override
                    public void onNext(NewBookListBean newBookListBean) {
                        if (newBookListBean.getErrno() == 0) {
                            if (newBookListBean.getData() != null) {
                                if (newBookListBean.getData().getList() != null && newBookListBean.getData().getList().size() > 0) {
                                    mView.bookListSuccess(newBookListBean.getData().getList());
                                } else {
                                    mView.bookListError("集合为空");
                                }
                            } else {
                                mView.bookListError("data为null");
                            }
                        } else {
                            mView.bookListError(newBookListBean.getMsg());
                        }
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void bookListSerch(RequestBody build) {
        Subscription rxSubscription = bookApi.bookListSerch(build).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewBookListBean>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.bookListError(e.toString());
                    }

                    @Override
                    public void onNext(NewBookListBean newBookListBean) {
                        if (newBookListBean.getErrno() == 0) {
                            if (newBookListBean.getData() != null) {
                                if (newBookListBean.getData().getList() != null && newBookListBean.getData().getList().size() > 0) {
                                    mView.bookListSuccess(newBookListBean.getData().getList());
                                } else {
                                    mView.bookListError("集合为空");
                                }
                            } else {
                                mView.bookListError("data为null");
                            }
                        } else {
                            mView.bookListError(newBookListBean.getMsg());
                        }
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void bookListCollect(RequestBody build) {
        Subscription rxSubscription = bookApi.bookListCollect(build).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewBookListBean>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.bookListError(e.toString());
                    }

                    @Override
                    public void onNext(NewBookListBean newBookListBean) {
                        if (newBookListBean.getErrno() == 0) {
                            if (newBookListBean.getData() != null) {
                                if (newBookListBean.getData().getList() != null && newBookListBean.getData().getList().size() > 0) {
                                    mView.bookListSuccess(newBookListBean.getData().getList());
                                } else {
                                    mView.bookListError("集合为空");
                                }
                            } else {
                                mView.bookListError("data为null");
                            }
                        } else {
                            mView.bookListError(newBookListBean.getMsg());
                        }
                    }
                });
        addSubscrebe(rxSubscription);
    }
}