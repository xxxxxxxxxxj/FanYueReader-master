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
package com.jack.reader.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.jack.reader.R;
import com.jack.reader.ReaderApplication;
import com.jack.reader.bean.BannerBean;
import com.jack.reader.bean.RankingList;
import com.jack.reader.component.DaggerFindComponent;
import com.jack.reader.ui.contract.TopRankContract;
import com.jack.reader.ui.presenter.TopRankPresenter;
import com.jack.reader.utils.AppUtils;
import com.jack.reader.utils.LogUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SplashActivity extends AppCompatActivity implements TopRankContract.View, AMapLocationListener {
    private final static String TAG = SplashActivity.class.getSimpleName();
    @Bind(R.id.tvSkip)
    TextView tvSkip;
    @Bind(R.id.image_bg)
    ImageView imageBg;
    private boolean flag = false;
    private Runnable runnable;

    @Inject
    TopRankPresenter mPresenter;
    //声明mlocationClient对象
    private AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    private AMapLocationClientOption mLocationOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        DaggerFindComponent.builder()
                .appComponent(ReaderApplication.getsInstance().getAppComponent())
                .build()
                .inject(this);
        runnable = new Runnable() {
            @Override
            public void run() {
                goHome();
            }
        };

        tvSkip.postDelayed(runnable, 5000);

        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });
        mPresenter.attachView(this);
        setLocation();
    }

    private void setLocation() {
        mlocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            mlocationClient.stopLocation();
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                double lat = amapLocation.getLatitude();//获取纬度
                double lng = amapLocation.getLongitude();//获取经度
                amapLocation.getAddress();
                LogUtils.d(TAG, "定位成功lat = "
                        + lat + ", lng = "
                        + lng + ",address = " + amapLocation.getAddress());
                MultipartBody.Builder builder = AppUtils.getDefaultBody(SplashActivity.this);
                builder.addFormDataPart("reg_lat", String.valueOf(lat));
                builder.addFormDataPart("reg_lng", String.valueOf(lng));
                RequestBody build = builder.build();
                mPresenter.updatauserinfo(build);

                MultipartBody.Builder builder1 = AppUtils.getDefaultBody(SplashActivity.this);
                builder1.addFormDataPart("reg_lat", String.valueOf(lat));
                builder1.addFormDataPart("reg_lng", String.valueOf(lng));
                builder1.addFormDataPart("type", "4");
                RequestBody build1 = builder1.build();
                mPresenter.getBannerList(build1);
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                LogUtils.d(TAG, "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
                MultipartBody.Builder builder = AppUtils.getDefaultBody(SplashActivity.this);
                builder.addFormDataPart("reg_lat", "0");
                builder.addFormDataPart("reg_lng", "0");
                RequestBody build = builder.build();
                mPresenter.updatauserinfo(build);

                MultipartBody.Builder builder1 = AppUtils.getDefaultBody(SplashActivity.this);
                builder1.addFormDataPart("reg_lat", "0");
                builder1.addFormDataPart("reg_lng", "0");
                builder1.addFormDataPart("type", "4");
                RequestBody build1 = builder1.build();
                mPresenter.getBannerList(build1);
            }
        }
    }

    private synchronized void goHome() {
        if (!flag) {
            flag = true;
            startActivity(new Intent(SplashActivity.this, NewMainActivity.class));
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        flag = true;
        tvSkip.removeCallbacks(runnable);
        ButterKnife.unbind(this);
    }

    @Override
    public void showRankList(RankingList rankingList) {

    }

    @Override
    public void updatauserinfoSuccess() {
        LogUtils.e(TAG, "updatauserinfoSuccess");
    }

    @Override
    public void showBannerList(final List<BannerBean.BannerData.NewBanner> imageList) {
        if (imageList != null && imageList.size() > 0) {
            Glide.with(this).load(imageList.get(0).getImgurl()).into(imageBg);
            imageBg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (imageList.get(0).getLinkurl() == null || imageList.get(0).getLinkurl().isEmpty()) {
                        return;
                    } else {
                        startActivity(new Intent(SplashActivity.this, NewMainActivity.class));
                        flag = true;
                        if (imageList.get(0).getType() == 1) {
                            Uri uri = Uri.parse(imageList.get(0).getLinkurl());
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        } else {
                            AppUtils.goToMarket(SplashActivity.this, imageList.get(0).getLinkurl());
                        }
                        finish();
                    }
                }
            });
        } else {
            goHome();
        }
    }

    @Override
    public void showError() {

    }

    @Override
    public void updatauserinfoError(String msg) {
        LogUtils.e(TAG, "updatauserinfoError Error, msg:" + msg);
    }

    @Override
    public void showBannerListError(String msg) {
        LogUtils.e(TAG, "showBannerListError Error, msg:" + msg);
        goHome();
    }

    @Override
    public void complete() {

    }
}
