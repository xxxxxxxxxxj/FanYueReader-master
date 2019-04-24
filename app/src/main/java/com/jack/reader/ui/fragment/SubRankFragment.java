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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jack.reader.R;
import com.jack.reader.base.BaseRVFragment;
import com.jack.reader.bean.BooksByCats;
import com.jack.reader.component.AppComponent;
import com.jack.reader.component.DaggerFindComponent;
import com.jack.reader.ui.activity.BookDetailActivity;
import com.jack.reader.ui.contract.SubRankContract;
import com.jack.reader.ui.easyadapter.SubCategoryAdapter;
import com.jack.reader.ui.presenter.SubRankPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 二级排行榜
 *
 * @author yuyh.
 * @date 16/9/1.
 */
public class SubRankFragment extends BaseRVFragment<SubRankPresenter, BooksByCats.BooksBean> implements SubRankContract.View {

    public final static String BUNDLE_ID = "_id";
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

    public static SubRankFragment newInstance(String id) {
        SubRankFragment fragment = new SubRankFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_ID, id);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String id;

    @Override
    public int getLayoutResId() {
        return R.layout.common_easy_recyclerview;
    }

    @Override
    public void initDatas() {
        titleRl.setVisibility(View.GONE);
        id = getArguments().getString(BUNDLE_ID);

    }

    @Override
    public void configViews() {
        initAdapter(SubCategoryAdapter.class, true, false);
        onRefresh();
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
        mAdapter.clear();
        mAdapter.addAll(data.books);
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
        BookDetailActivity.startActivity(activity, mAdapter.getItem(position)._id);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.getRankList(id);
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
