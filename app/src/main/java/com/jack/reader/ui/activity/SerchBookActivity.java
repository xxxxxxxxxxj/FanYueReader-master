package com.jack.reader.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.jack.reader.BookListActivity;
import com.jack.reader.R;
import com.jack.reader.base.BaseActivity;
import com.jack.reader.bean.KeywordListBean;
import com.jack.reader.component.AppComponent;
import com.jack.reader.component.DaggerFindComponent;
import com.jack.reader.ui.contract.SerchBookContract;
import com.jack.reader.ui.presenter.SerchBookPresenter;
import com.jack.reader.utils.AppUtils;
import com.jack.reader.utils.LogUtils;
import com.jack.reader.utils.StringUtils;
import com.jack.reader.view.ClearEditText;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SerchBookActivity extends BaseActivity implements SerchBookContract.View, AMapLocationListener {
    @Bind(R.id.cet_serchbook)
    ClearEditText cetSerchbook;
    @Bind(R.id.tfl_serchbook)
    TagFlowLayout tflSerchbook;
    private final static String TAG = SerchBookActivity.class.getSimpleName();
    //声明mlocationClient对象
    private AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    private AMapLocationClientOption mLocationOption;
    @Inject
    SerchBookPresenter mPresenter;
    private List<String> bqList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_serch_book;
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

    }

    @Override
    public void initDatas() {
        cetSerchbook.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH && StringUtils.isNotEmpty(StringUtils.checkEditText(cetSerchbook))) {
                    AppUtils.goneJP(SerchBookActivity.this);
                    Intent intent = new Intent(mContext, BookListActivity.class);
                    intent.putExtra("type", 3);
                    intent.putExtra("kw", StringUtils.checkEditText(cetSerchbook));
                    intent.putExtra("loadmoreable", true);
                    intent.putExtra("title", "搜索结果");
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void configViews() {
        mPresenter.attachView(this);
        setLocation();
    }

    @OnClick({R.id.tv_serchbook_cancle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_serchbook_cancle:
                finish();
                break;
        }
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
                MultipartBody.Builder builder = AppUtils.getDefaultBody(mContext);
                builder.addFormDataPart("reg_lat", String.valueOf(lat));
                builder.addFormDataPart("reg_lng", String.valueOf(lng));
                RequestBody build = builder.build();
                mPresenter.keyword(build);
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                LogUtils.d(TAG, "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
                MultipartBody.Builder builder = AppUtils.getDefaultBody(mContext);
                builder.addFormDataPart("reg_lat", "0");
                builder.addFormDataPart("reg_lng", "0");
                RequestBody build = builder.build();
                mPresenter.keyword(build);
            }
        }
    }

    @Override
    public void keywordSuccess(List<KeywordListBean.KeywordListData.Keyword> data) {
        bqList.clear();
        for (int i = 0; i < data.size(); i++) {
            bqList.add(data.get(i).getHotkeyword());
        }
        tflSerchbook.setAdapter(new TagAdapter<String>(bqList) {
            @Override
            public View getView(FlowLayout parent, int position, final String s) {
                View view = (View) View.inflate(mContext, R.layout.item_serch_bq,
                        null);
                TextView tv_item_card_bq = (TextView) view.findViewById(R.id.tv_item_serch_bq);
                tv_item_card_bq.setText(s);
                tv_item_card_bq.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, BookListActivity.class);
                        intent.putExtra("type", 3);
                        intent.putExtra("kw", s);
                        intent.putExtra("loadmoreable", true);
                        intent.putExtra("title", "搜索结果");
                        startActivity(intent);
                    }
                });
                return view;
            }
        });
    }

    @Override
    public void keywordError(String msg) {
        LogUtils.d(TAG, "keywordError Error, msg:" + msg);
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }
}
