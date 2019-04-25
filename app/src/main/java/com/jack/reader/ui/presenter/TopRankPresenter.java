/**
 * Copyright 2016 JustWayward Team
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jack.reader.ui.presenter;

import com.jack.reader.api.BookApi;
import com.jack.reader.base.RxPresenter;
import com.jack.reader.bean.BannerBean;
import com.jack.reader.bean.DefaultBean;
import com.jack.reader.bean.RankingList;
import com.jack.reader.ui.contract.TopRankContract;
import com.jack.reader.utils.LogUtils;
import com.jack.reader.utils.RxUtil;
import com.jack.reader.utils.StringUtils;

import javax.inject.Inject;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author yuyh.
 * @date 16/9/1.
 */
public class TopRankPresenter extends RxPresenter<TopRankContract.View> implements TopRankContract.Presenter<TopRankContract.View> {

    private BookApi bookApi;

    @Inject
    public TopRankPresenter(BookApi bookApi) {
        this.bookApi = bookApi;
    }

    @Override
    public void getRankList() {
        String key = StringUtils.creatAcacheKey("book-ranking-list");
        Observable<RankingList> fromNetWork = bookApi.getRanking()
                .compose(RxUtil.<RankingList>rxCacheBeanHelper(key));

        //依次检查disk、network
        Subscription rxSubscription = Observable.concat(RxUtil.rxCreateDiskObservable(key, RankingList.class), fromNetWork)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RankingList>() {
                    @Override
                    public void onNext(RankingList data) {
                        if (data != null && mView != null) {
                            mView.showRankList(data);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getRankList:" + e.toString());
                        mView.complete();
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
                        LogUtils.e("getBannerList:" + e.toString());
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
    public void updatauserinfo(RequestBody body) {
        Subscription rxSubscription = bookApi.updatauserinfo(body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DefaultBean>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("updatauserinfo:" + e.toString());
                        mView.updatauserinfoError(e.toString());
                    }

                    @Override
                    public void onNext(DefaultBean defaultBean) {
                        if (defaultBean.getErrno() == 0) {
                            mView.updatauserinfoSuccess();
                        } else {
                            mView.updatauserinfoError(defaultBean.getMsg());
                        }
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
