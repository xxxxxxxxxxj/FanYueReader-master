package com.jack.reader.ui.easyadapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jack.reader.R;
import com.jack.reader.bean.PageHomeBean;
import com.jack.reader.view.recyclerview.adapter.BaseViewHolder;
import com.jack.reader.view.recyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by 山鸡 on 2017/9/9.
 */

public class BookrackAdapter extends RecyclerArrayAdapter<PageHomeBean.PageHomeData.PageHomeBooks> {

    public BookrackAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder<PageHomeBean.PageHomeData.PageHomeBooks>(parent, R.layout.item_sub_category_list) {
            @Override
            public void setData(PageHomeBean.PageHomeData.PageHomeBooks item) {
                super.setData(item);
                holder.setRoundImageUrl(R.id.ivSubCateCover, item.getCoverimg(),
                        R.drawable.cover_default);
                holder.setText(R.id.tvSubCateTitle, item.getTitle())
                        .setText(R.id.tvSubCateShort, item.getSummary())
                        .setText(R.id.tvSubCateMsg, item.getAuthor() + " | " + item.getBook_typename() + " | " + (Integer.parseInt(item.getStatus()) == 1 ? "连载中" : "完结"));
            }
        };
    }
}
