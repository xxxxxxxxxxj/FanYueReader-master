package com.jack.reader;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.jack.reader.base.BaseRVActivity;
import com.jack.reader.bean.PageHomeBean;
import com.jack.reader.bean.Recommend;
import com.jack.reader.component.AppComponent;
import com.jack.reader.component.DaggerFindComponent;
import com.jack.reader.ui.activity.ReadActivity;
import com.jack.reader.ui.contract.BookListContract;
import com.jack.reader.ui.easyadapter.BookrackAdapter;
import com.jack.reader.ui.fragment.MyFragment;
import com.jack.reader.ui.presenter.BookListPresenter;
import com.jack.reader.utils.AppUtils;
import com.jack.reader.utils.LogUtils;

import java.util.List;

import javax.inject.Inject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class BookListActivity extends BaseRVActivity<PageHomeBean.PageHomeData.PageHomeBooks> implements BookListContract.View, AMapLocationListener {
    private final static String TAG = MyFragment.class.getSimpleName();
    private String id;
    private int type;
    private int pn = 1;
    private int rn = 10;
    //声明mlocationClient对象
    private AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    private AMapLocationClientOption mLocationOption;
    @Inject
    BookListPresenter mPresenter;
    private String kw;
    private double lat;
    private double lng;

    @Override
    public int getLayoutId() {
        return R.layout.activity_book_list;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerFindComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void initToolBar() {
        id = getIntent().getStringExtra("id");
        kw = getIntent().getStringExtra("kw");
        type = getIntent().getIntExtra("type", 0);
        mCommonToolbar.setTitle(getIntent().getStringExtra("title"));
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {
        setLocation();
        initAdapter(BookrackAdapter.class, true, getIntent().getBooleanExtra("loadmoreable", false));
        mPresenter.attachView(this);
        onRefresh();
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
        ReadActivity.startActivity(mContext,
                new Recommend.RecommendBooks(mAdapter.getItem(position).getId(), mAdapter.getItem(position).getTitle(),
                        mAdapter.getItem(position).getCoverimg(), mAdapter.getItem(position).getSummary(),
                        mAdapter.getItem(position).getAuthor(), mAdapter.getItem(position).getBook_typename(),
                        mAdapter.getItem(position).getStatus(), false));
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
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
                lat = amapLocation.getLatitude();//获取纬度
                lng = amapLocation.getLongitude();//获取经度
                amapLocation.getAddress();
                LogUtils.d(TAG, "定位成功lat = "
                        + lat + ", lng = "
                        + lng + ",address = " + amapLocation.getAddress());
                MultipartBody.Builder builder = AppUtils.getDefaultBody(mContext);
                builder.addFormDataPart("reg_lat", String.valueOf(lat));
                builder.addFormDataPart("reg_lng", String.valueOf(lng));
                if (type == 1) {//分类进入列表
                    builder.addFormDataPart("type", id);
                    RequestBody build = builder.build();
                    mPresenter.bookListClass(build);
                } else if (type == 2) {//榜单进入列表
                    builder.addFormDataPart("type", id);
                    RequestBody build = builder.build();
                    mPresenter.bookListBangDan(build);
                } else if (type == 3) {//搜索结果页
                    builder.addFormDataPart("kw", kw);
                    pn = 1;
                    builder.addFormDataPart("pn", String.valueOf(pn));
                    builder.addFormDataPart("rn", String.valueOf(rn));
                    RequestBody build = builder.build();
                    mPresenter.bookListSerch(build);
                } else if (type == 4) {//我的收藏列表
                    pn = 1;
                    builder.addFormDataPart("pn", String.valueOf(pn));
                    builder.addFormDataPart("rn", String.valueOf(rn));
                    RequestBody build = builder.build();
                    mPresenter.bookListCollect(build);
                }
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                LogUtils.d(TAG, "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
                MultipartBody.Builder builder = AppUtils.getDefaultBody(mContext);
                builder.addFormDataPart("reg_lat", "0");
                builder.addFormDataPart("reg_lng", "0");
                if (type == 1) {//分类进入列表
                    builder.addFormDataPart("type", id);
                    RequestBody build = builder.build();
                    mPresenter.bookListClass(build);
                } else if (type == 2) {//榜单进入列表
                    builder.addFormDataPart("type", id);
                    RequestBody build = builder.build();
                    mPresenter.bookListBangDan(build);
                } else if (type == 3) {//搜索结果页
                    builder.addFormDataPart("kw", kw);
                    pn = 1;
                    builder.addFormDataPart("pn", String.valueOf(pn));
                    builder.addFormDataPart("rn", String.valueOf(rn));
                    RequestBody build = builder.build();
                    mPresenter.bookListSerch(build);
                } else if (type == 4) {//我的收藏列表
                    pn = 1;
                    builder.addFormDataPart("pn", String.valueOf(pn));
                    builder.addFormDataPart("rn", String.valueOf(rn));
                    RequestBody build = builder.build();
                    mPresenter.bookListCollect(build);
                }
            }
        }
    }

    @Override
    public void onLoadMore() {
        MultipartBody.Builder builder = AppUtils.getDefaultBody(mContext);
        builder.addFormDataPart("reg_lat", String.valueOf(lat));
        builder.addFormDataPart("reg_lng", String.valueOf(lng));
        if (type == 3) {//搜索结果页
            builder.addFormDataPart("kw", kw);
            builder.addFormDataPart("pn", String.valueOf(pn));
            builder.addFormDataPart("rn", String.valueOf(rn));
            RequestBody build = builder.build();
            mPresenter.bookListSerch(build);
        } else if (type == 4) {//我的收藏列表
            builder.addFormDataPart("pn", String.valueOf(pn));
            builder.addFormDataPart("rn", String.valueOf(rn));
            RequestBody build = builder.build();
            mPresenter.bookListCollect(build);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
    }

    @Override
    public void bookListSuccess(List<PageHomeBean.PageHomeData.PageHomeBooks> data) {
        mRecyclerView.showRecycler();
        if (pn == 1) {
            mAdapter.clear();
        }
        if (data.size() >= rn) {
            pn++;
            mAdapter.resumeMore();
        }else{
            mAdapter.stopMore();
        }
        mAdapter.addAll(data);
    }

    @Override
    public void bookListError(String msg) {
        LogUtils.d(TAG, "bookListError Error, msg:" + msg);
        if (type == 3 || type == 4) {
            if (pn == 1) {
                mRecyclerView.showEmpty();
            } else {
                mRecyclerView.showRecycler();
            }
        } else {
            mRecyclerView.showEmpty();
        }
    }
}
