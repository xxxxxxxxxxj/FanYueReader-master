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

import com.jack.reader.R;
import com.jack.reader.base.BaseRVFragment;
import com.jack.reader.bean.BookLists;
import com.jack.reader.bean.support.TagEvent;
import com.jack.reader.component.AppComponent;
import com.jack.reader.component.DaggerFindComponent;
import com.jack.reader.manager.SettingManager;
import com.jack.reader.ui.activity.SubjectBookListDetailActivity;
import com.jack.reader.ui.contract.SubjectFragmentContract;
import com.jack.reader.ui.easyadapter.SubjectBookListAdapter;
import com.jack.reader.ui.presenter.SubjectFragmentPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * 主题书单
 *
 * @author yuyh.
 * @date 16/9/1.
 */
public class SubjectFragment extends BaseRVFragment<SubjectFragmentPresenter, BookLists.BookListsBean> implements SubjectFragmentContract.View {

    public final static String BUNDLE_TAG = "tag";
    public final static String BUNDLE_TAB = "tab";

    public String currendTag;
    public int currentTab;

    public String duration = "";
    public String sort = "";

    public static SubjectFragment newInstance(String tag, int tab) {
        SubjectFragment fragment = new SubjectFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TAG, tag);
        bundle.putInt(BUNDLE_TAB, tab);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.common_easy_recyclerview;
    }

    @Override
    public void initDatas() {
        EventBus.getDefault().register(this);

        currentTab = getArguments().getInt(BUNDLE_TAB);
        switch (currentTab) {
            case 0:
                duration = "last-seven-days";
                sort = "collectorCount";
                break;
            case 1:
                duration = "all";
                sort = "created";
                break;
            case 2:
            default:
                duration = "all";
                sort = "collectorCount";
                break;
        }
    }

    @Override
    public void configViews() {
        initAdapter(SubjectBookListAdapter.class, true, true);
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
    public void showBookList(List<BookLists.BookListsBean> bookLists, boolean isRefresh) {
        if (isRefresh) {
            mAdapter.clear();
            start = 0;
        }
        mAdapter.addAll(bookLists);
        start = start + bookLists.size();
    }

    @Override
    public void showError() {
        loaddingError();
    }

    @Override
    public void complete() {
        mRecyclerView.setRefreshing(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initCategoryList(TagEvent event) {
        currendTag = event.tag;
        if (getUserVisibleHint()) {
            mPresenter.getBookLists(duration, sort, 0, limit, currendTag, SettingManager.getInstance().getUserChooseSex());
        }
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Override
    public void onItemClick(int position) {
        SubjectBookListDetailActivity.startActivity(activity, mAdapter.getItem(position));
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.getBookLists(duration, sort, 0, limit, currendTag, SettingManager.getInstance().getUserChooseSex());
    }

    @Override
    public void onLoadMore() {
        mPresenter.getBookLists(duration, sort, start, limit, currendTag, SettingManager.getInstance().getUserChooseSex());
    }
}
