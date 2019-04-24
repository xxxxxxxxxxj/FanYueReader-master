package com.jack.reader.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jack.reader.R;
import com.jack.reader.bean.PageHomeBean;
import com.jack.reader.view.recyclerview.adapter.BaseViewHolder;
import com.jack.reader.view.recyclerview.adapter.RecyclerArrayAdapter;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2019/4/23 15:42
 */
public class MyBookAdapter extends RecyclerArrayAdapter<PageHomeBean.PageHomeData.PageHomeBooks> {

    public MyBookAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder<PageHomeBean.PageHomeData.PageHomeBooks>(parent, R.layout.item_myfrag_book) {
            @Override
            public void setData(PageHomeBean.PageHomeData.PageHomeBooks item) {
                super.setData(item);
                holder.setRoundImageUrl(R.id.iv_item_myfrag_bookimg, item.getCoverimg(),
                        R.drawable.cover_default);
                holder.setText(R.id.tv_item_myfrag_booktitle, item.getTitle());
            }
        };
    }
}
