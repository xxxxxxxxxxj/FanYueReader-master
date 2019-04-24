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
package com.jack.reader.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jack.reader.R;
import com.jack.reader.base.BaseFragment;
import com.jack.reader.bean.FindImageBean;
import com.jack.reader.common.OnRvItemClickListener;
import com.jack.reader.component.AppComponent;
import com.jack.reader.ui.activity.SubOtherHomeRankActivity;
import com.jack.reader.ui.adapter.FindAdapter;
import com.jack.reader.view.SupportDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 发现
 *
 * @author yuyh.
 * @date 16/9/1.
 */
public class FindFragment extends BaseFragment implements OnRvItemClickListener<FindImageBean> {

    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;
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

    private FindAdapter mAdapter;
    private List<FindImageBean> mList = new ArrayList<>();

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_find;
    }

    @Override
    public void initDatas() {
        mList.clear();
        mList.add(new FindImageBean("本周最热", R.drawable.rank_imge1, R.drawable.hotest_week_tag, "54d42e72d9de23382e6877fb"));
        mList.add(new FindImageBean("本月最热", R.drawable.rank_image2, R.drawable.hotest_month_tag, "564eee3ea82e3ada6f14b195"));
        mList.add(new FindImageBean("本季最热", R.drawable.rank_image3, R.drawable.hotest_season_tag, "564eeeabed24953671f2a577"));
        mList.add(new FindImageBean("本年最热", R.drawable.rank_image4, R.drawable.hotest_year_tag, "564d8a004a15bb8369d9e28d"));
    }

    @Override
    public void configViews() {
        titleRl.setVisibility(View.VISIBLE);
        titleIcon.setImageResource(R.drawable.find_selected);
        titleText.setText("发现");
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new SupportDividerItemDecoration(mContext, LinearLayoutManager.VERTICAL, true));

        mAdapter = new FindAdapter(mContext, mList, this);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void attachView() {

    }

    @Override
    public void onItemClick(View view, int position, FindImageBean data) {
        SubOtherHomeRankActivity.startActivity(mContext, mList.get(position).get_id(), mList.get(position).getTitle());
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
        ButterKnife.unbind(this);
    }
}
