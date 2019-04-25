package com.jack.reader.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.jack.reader.ui.activity.BookListActivity;
import com.jack.reader.R;
import com.jack.reader.base.BaseRVFragment;
import com.jack.reader.bean.BooksByCats;
import com.jack.reader.bean.ClassIndexBean;
import com.jack.reader.bean.PageHomeBean;
import com.jack.reader.bean.RankListBean;
import com.jack.reader.bean.RefershFranmentEvent;
import com.jack.reader.component.AppComponent;
import com.jack.reader.component.DaggerFindComponent;
import com.jack.reader.ui.adapter.RankListTypeAdapter;
import com.jack.reader.ui.contract.BookrackContract;
import com.jack.reader.ui.presenter.BookrackPresenter;
import com.jack.reader.utils.AppUtils;
import com.jack.reader.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by 山鸡 on 2017/9/8.
 */

public class RankListFragment extends BaseRVFragment<BookrackPresenter, RankListBean.RankListData.RankListType> implements BookrackContract.View, AMapLocationListener {
    private final static String TAG = BookrackFragment.class.getSimpleName();
    @Bind(R.id.image_back)
    ImageView imageBack;
    @Bind(R.id.title_icon)
    ImageView titleIcon;
    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.back_rl)
    RelativeLayout backRl;
    @Bind(R.id.title_rl)
    RelativeLayout titleRl;
    //声明mlocationClient对象
    private AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    private AMapLocationClientOption mLocationOption;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(RefershFranmentEvent event) {
        if (event != null && event.getPosition() == 2) {
            onRefresh();
        }
    }

    @Override
    public void initDatas() {
        if (!EventBus.getDefault().isRegistered(this)) {//加上判断
            EventBus.getDefault().register(this);
        }
        titleRl.setVisibility(View.VISIBLE);
        titleIcon.setImageResource(R.drawable.toplist_selected);
        titleText.setText("榜单");
    }

    @Override
    public void configViews() {
        setLocation();
        initAdapter(RankListTypeAdapter.class, true, false);
        mPresenter.attachView(this);
        onRefresh();
    }


    @Override
    public int getLayoutResId() {
        return R.layout.common_easy_recyclerview;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerFindComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void showRankList(BooksByCats data) {
    }

    @Override
    public void pagehomeError(String msg) {

    }

    @Override
    public void pagehomeSuccess(PageHomeBean.PageHomeData data) {

    }

    @Override
    public void classIndexError(String msg) {

    }

    @Override
    public void classIndexSuccess(ClassIndexBean.ClassIndexData data) {
    }

    @Override
    public void rankListSuccess(RankListBean.RankListData data) {
        List<RankListBean.RankListData.RankListType> list = data.getList();
        if (list != null && list.size() > 0) {
            mAdapter.clear();
            mAdapter.addAll(list);
        }
    }

    @Override
    public void rankListError(String msg) {
        LogUtils.e(TAG, "rankListError Error, msg:" + msg);
    }

    @Override
    public void showError() {
        loaddingError();
    }

    @Override
    public void complete() {
        mRecyclerView.setRefreshing(false);
    }

    @Override
    public void onItemClick(int position) {
        RankListBean.RankListData.RankListType item = mAdapter.getItem(position);
        Intent intent = new Intent(mContext, BookListActivity.class);
        intent.putExtra("id", item.getId());
        intent.putExtra("type", 2);
        intent.putExtra("loadmoreable", false);
        intent.putExtra("title", item.getTitle());
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        //启动定位
        mlocationClient.startLocation();
    }

    private void setLocation() {
        mlocationClient = new AMapLocationClient(mContext);
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
                MultipartBody.Builder builder = AppUtils.getDefaultBody(mContext);
                builder.addFormDataPart("reg_lat", String.valueOf(lat));
                builder.addFormDataPart("reg_lng", String.valueOf(lng));
                RequestBody build = builder.build();
                mPresenter.rankList(build);
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                LogUtils.d(TAG, "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
                MultipartBody.Builder builder = AppUtils.getDefaultBody(mContext);
                builder.addFormDataPart("reg_lat", "0");
                builder.addFormDataPart("reg_lng", "0");
                RequestBody build = builder.build();
                mPresenter.rankList(build);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if (EventBus.getDefault().isRegistered(this)) {//加上判断
            EventBus.getDefault().unregister(this);
        }
    }
}
