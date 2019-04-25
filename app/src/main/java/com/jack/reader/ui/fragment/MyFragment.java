package com.jack.reader.ui.fragment;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.jack.reader.ui.activity.BookListActivity;
import com.jack.reader.R;
import com.jack.reader.base.BaseRVFragment;
import com.jack.reader.bean.MyIndexBean;
import com.jack.reader.bean.PageHomeBean;
import com.jack.reader.bean.Recommend;
import com.jack.reader.bean.RefershFranmentEvent;
import com.jack.reader.bean.RefreshMyBookEvent;
import com.jack.reader.component.AppComponent;
import com.jack.reader.component.DaggerFindComponent;
import com.jack.reader.ui.activity.ReadActivity;
import com.jack.reader.ui.adapter.MyBookAdapter;
import com.jack.reader.ui.contract.MyFragMentContract;
import com.jack.reader.ui.presenter.MyFragMentPresenter;
import com.jack.reader.utils.AppUtils;
import com.jack.reader.utils.LogUtils;
import com.jack.reader.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2019/4/23 14:20
 */
public class MyFragment extends BaseRVFragment<MyFragMentPresenter, PageHomeBean.PageHomeData.PageHomeBooks> implements MyFragMentContract.View, AMapLocationListener {
    private final static String TAG = MyFragment.class.getSimpleName();
    @Bind(R.id.tv_myfrag_booknum)
    TextView tvMyfragBooknum;
    @Bind(R.id.tv_myfrag_type)
    TextView tvMyfragType;
    @Bind(R.id.iv_myfrag_bookimg)
    ImageView ivMyfragBookimg;
    @Bind(R.id.tv_myfrag_booktitle)
    TextView tvMyfragBooktitle;
    @Bind(R.id.tv_myfrag_bookdesc)
    TextView tvMyfragBookdesc;
    @Bind(R.id.tv_myfrag_bookmsg)
    TextView tvMyfragBookmsg;
    @Bind(R.id.tv_myfrag_book_more)
    TextView tvMyfragBookMore;
    @Bind(R.id.tv_myfrag_book_nobook)
    TextView tvMyfragBookNobook;
    @Bind(R.id.ll_myfrag_books)
    LinearLayout ll_myfrag_books;
    //声明mlocationClient对象
    private AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    private AMapLocationClientOption mLocationOption;
    private String current_book_id;
    private String current_book_coverimg;
    private String current_book_title;
    private String current_book_summary;
    private String current_book_author;
    private String current_book_book_typename;
    private String current_book_status;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_my;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(RefreshMyBookEvent event) {
        if (event != null && event.isRefresh()) {
            onRefresh();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(RefershFranmentEvent event) {
        if (event != null && event.getPosition() == 3) {
            onRefresh();
        }
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerFindComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void attachView() {

    }

    @Override
    public void initDatas() {
        if (!EventBus.getDefault().isRegistered(this)) {//加上判断
            EventBus.getDefault().register(this);
        }
        tvMyfragBookMore.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tvMyfragBookMore.getPaint().setAntiAlias(true);//抗锯齿
    }

    @Override
    public void configViews() {
        setLocation();
        initAdapter(MyBookAdapter.class, false, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mPresenter.attachView(this);
        onRefresh();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
                mPresenter.myIndex(build);
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                LogUtils.d(TAG, "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
                MultipartBody.Builder builder = AppUtils.getDefaultBody(mContext);
                builder.addFormDataPart("reg_lat", "0");
                builder.addFormDataPart("reg_lng", "0");
                RequestBody build = builder.build();
                mPresenter.myIndex(build);
            }
        }
    }

    @Override
    public void myIndexSuccess(MyIndexBean.MyIndexData data) {
        MyIndexBean.MyIndexData.MyIndexList list = data.getList();
        if (StringUtils.isNotEmpty(spUtil.getString("current_book_id", ""))) {
            current_book_id = spUtil.getString("current_book_id", "");
            current_book_coverimg = spUtil.getString("current_book_coverimg", "");
            current_book_title = spUtil.getString("current_book_title", "");
            current_book_summary = spUtil.getString("current_book_summary", "");
            current_book_author = spUtil.getString("current_book_author", "");
            current_book_book_typename = spUtil.getString("current_book_book_typename", "");
            current_book_status = spUtil.getString("current_book_status", "");
            tvMyfragType.setText("当前在看的");
            Glide.with(mContext).load(spUtil.getString("current_book_coverimg", "")).placeholder(R.drawable.cover_default).into(ivMyfragBookimg);
            StringUtils.setText(tvMyfragBooktitle, spUtil.getString("current_book_title", ""), "", View.VISIBLE, View.VISIBLE);
            StringUtils.setText(tvMyfragBookdesc, spUtil.getString("current_book_summary", ""), "", View.VISIBLE, View.VISIBLE);
            StringUtils.setText(tvMyfragBookmsg, spUtil.getString("current_book_author", "") + " | " +
                    spUtil.getString("current_book_book_typename", "") + " | " + (Integer.parseInt(spUtil.getString("current_book_status", ""))
                    == 1 ? "连载中" : "完结"), "", View.VISIBLE, View.VISIBLE);
        } else {
            PageHomeBean.PageHomeData.PageHomeBooks recommendBook = data.getRecommendBook();
            if (recommendBook != null) {
                current_book_id = recommendBook.getId();
                current_book_coverimg = recommendBook.getCoverimg();
                current_book_title = recommendBook.getTitle();
                current_book_summary = recommendBook.getSummary();
                current_book_author = recommendBook.getAuthor();
                current_book_book_typename = recommendBook.getBook_typename();
                current_book_status = recommendBook.getStatus();
                tvMyfragType.setText("推荐最新");
                Glide.with(mContext).load(recommendBook.getCoverimg()).placeholder(R.drawable.cover_default).into(ivMyfragBookimg);
                StringUtils.setText(tvMyfragBooktitle, recommendBook.getTitle(), "", View.VISIBLE, View.VISIBLE);
                StringUtils.setText(tvMyfragBookdesc, recommendBook.getSummary(), "", View.VISIBLE, View.VISIBLE);
                StringUtils.setText(tvMyfragBookmsg, recommendBook.getAuthor() + " | " + recommendBook.getBook_typename() + " | " + (Integer.parseInt(recommendBook.getStatus()) == 1 ? "连载中" : "完结"), "", View.VISIBLE, View.VISIBLE);
            }
        }
        if (list != null) {
            String bookcount = list.getBookcount();
            List<PageHomeBean.PageHomeData.PageHomeBooks> bookslist =
                    list.getBookslist();
            StringUtils.setText(tvMyfragBooknum, bookcount + "部", "", View.VISIBLE, View.VISIBLE);
            if (bookslist != null && bookslist.size() > 0) {
                ll_myfrag_books.setVisibility(View.VISIBLE);
                tvMyfragBookNobook.setVisibility(View.GONE);
                mAdapter.clear();
                mAdapter.addAll(bookslist);
            } else {
                ll_myfrag_books.setVisibility(View.GONE);
                tvMyfragBookNobook.setVisibility(View.VISIBLE);
            }
        } else {
            ll_myfrag_books.setVisibility(View.GONE);
            tvMyfragBookNobook.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void myIndexError(String msg) {
        LogUtils.e(TAG, "myIndexError Error, msg:" + msg);
    }

    @Override
    public void showError() {

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

    @OnClick({R.id.tv_myfrag_book_more, R.id.ll_myfrag_bottom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_myfrag_book_more:
                Intent intent = new Intent(mContext, BookListActivity.class);
                intent.putExtra("type", 4);
                intent.putExtra("loadmoreable", true);
                intent.putExtra("title", "收藏列表");
                startActivity(intent);
                break;
            case R.id.ll_myfrag_bottom:
                ReadActivity.startActivity(mContext,
                        new Recommend.RecommendBooks(current_book_id, current_book_title,
                                current_book_coverimg, current_book_summary,
                                current_book_author, current_book_book_typename,
                                current_book_status, false));
                break;
        }
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
