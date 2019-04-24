package com.jack.reader.ui.easyadapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jack.reader.R;
import com.jack.reader.bean.support.FindBean;
import com.jack.reader.view.recyclerview.adapter.BaseViewHolder;
import com.jack.reader.view.recyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by 山鸡 on 2017/9/9.
 */

public class RankListAdapter extends RecyclerArrayAdapter<FindBean> {

    public RankListAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder<FindBean>(parent, R.layout.item_find) {
            @Override
            public void setData(FindBean item) {
                super.setData(item);
                holder.setText(R.id.tvTitle, item.getTitle());
            }
        };
    }
}
