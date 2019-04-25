/**
 * Copyright 2016 JustWayward Team
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jack.reader.ui.presenter;

import android.content.Context;

import com.jack.reader.api.BookApi;
import com.jack.reader.base.RxPresenter;
import com.jack.reader.bean.BannerBean;
import com.jack.reader.bean.BookMixAToc;
import com.jack.reader.bean.ChapterBean;
import com.jack.reader.bean.ChapterInfoBean;
import com.jack.reader.bean.ChapterRead;
import com.jack.reader.bean.DefaultBean;
import com.jack.reader.ui.contract.BookReadContract;
import com.jack.reader.utils.LogUtils;
import com.jack.reader.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import okhttp3.RequestBody;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author lfh.
 * @date 2016/8/7.
 */
public class BookReadPresenter extends RxPresenter<BookReadContract.View>
        implements BookReadContract.Presenter<BookReadContract.View> {

    private Context mContext;
    private BookApi bookApi;

    @Inject
    public BookReadPresenter(Context mContext, BookApi bookApi) {
        this.mContext = mContext;
        this.bookApi = bookApi;
    }

    @Override
    public void getBookMixAToc(RequestBody body) {
        Subscription rxSubscription = bookApi.getBookMixAToc(body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ChapterBean>() {
                    @Override
                    public void onNext(ChapterBean data) {
                        if (data != null) {
                            ChapterBean.ChapterData data1 = data.getData();
                            int errno = data.getErrno();
                            String msg = data.getMsg();
                            if (errno == 0) {
                                if (data1 != null) {
                                    List<ChapterBean.ChapterData.NewChapter> chapters = data1.getChapters();
                                    String ismybook = data1.getIsmybook();
                                    if (chapters != null && chapters.size() > 0) {
                                        List<BookMixAToc.mixToc.Chapters> list = new ArrayList<BookMixAToc.mixToc.Chapters>();
                                        list.clear();
                                        for (int i = 0; i < chapters.size(); i++) {
                                            list.add(new BookMixAToc.mixToc.Chapters(chapters.get(i).getT(), chapters.get(i).getC(), ismybook));
                                        }
                                        mView.showBookToc(list);
                                    } else {
                                        LogUtils.e("onError: 集合为空");
                                        mView.netError(0);
                                    }
                                } else {
                                    LogUtils.e("onError: data1为空");
                                    mView.netError(0);
                                }
                            } else {
                                LogUtils.e("onError: " + msg);
                                mView.netError(0);
                            }
                        } else {
                            LogUtils.e("onError: data为空");
                            mView.netError(0);
                        }
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("onError: " + e);
                        mView.netError(0);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getChapterRead(RequestBody body, final int chapter, final String title) {
        Subscription rxSubscription = bookApi.getChapterRead(body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ChapterInfoBean>() {
                    @Override
                    public void onNext(ChapterInfoBean data) {
                        if (data != null) {
                            ChapterInfoBean.ChapterInfoData data1 = data.getData();
                            int errno = data.getErrno();
                            String msg = data.getMsg();
                            if (errno == 0) {
                                if (data1 != null) {
                                    if (StringUtils.isNotEmpty(data1.getContent())) {
                                        mView.showChapterRead(new ChapterRead.Chapter(title, data1.getContent(), data1.getContent()), chapter);
                                    } else {
                                        LogUtils.e("onError: content为空");
                                        mView.netError(chapter);
                                    }
                                } else {
                                    LogUtils.e("onError: data1为空");
                                    mView.netError(chapter);
                                }
                            } else {
                                LogUtils.e("onError: " + msg);
                                mView.netError(chapter);
                            }
                        } else {
                            LogUtils.e("onError: data为空");
                            mView.netError(chapter);
                        }
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("onError: " + e);
                        mView.netError(chapter);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getBannerList(RequestBody body) {
        Subscription rxSubscription = bookApi.getBannerList(body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BannerBean>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showBannerListError(e.toString());
                    }

                    @Override
                    public void onNext(BannerBean bannerBean) {
                        if (bannerBean != null) {
                            BannerBean.BannerData data = bannerBean.getData();
                            int errno = bannerBean.getErrno();
                            if (errno == 0) {
                                if (data != null) {
                                    if (data.getList() != null && data.getList().size() > 0) {
                                        mView.showBannerList(data.getList());
                                    } else {
                                        mView.showBannerListError("集合为空");
                                    }
                                } else {
                                    mView.showBannerListError("data为空");
                                }

                            } else {
                                mView.showBannerListError(bannerBean.getMsg());
                            }
                        } else {
                            mView.showBannerListError("bannerBean为空");
                        }
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void updatamybook(RequestBody body) {
        Subscription rxSubscription = bookApi.updatamybook(body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DefaultBean>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.updatamybookError(e.toString());
                    }

                    @Override
                    public void onNext(DefaultBean defaultBean) {
                        if (defaultBean != null) {
                            int errno = defaultBean.getErrno();
                            if (errno == 0) {
                                mView.updatamybookSuccess();
                            } else {
                                mView.updatamybookError(defaultBean.getMsg());
                            }
                        } else {
                            mView.updatamybookError("defaultBean为空");
                        }
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void countread(RequestBody body) {
        Subscription rxSubscription = bookApi.countread(body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DefaultBean>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.countreadError(e.toString());
                    }

                    @Override
                    public void onNext(DefaultBean defaultBean) {
                        if (defaultBean != null) {
                            int errno = defaultBean.getErrno();
                            if (errno == 0) {
                                mView.countreadSuccess();
                            } else {
                                mView.countreadError(defaultBean.getMsg());
                            }
                        } else {
                            mView.countreadError("defaultBean为空");
                        }
                    }
                });
        addSubscrebe(rxSubscription);
    }
}