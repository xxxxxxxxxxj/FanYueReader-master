package com.jack.reader.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.jack.reader.R;
import com.jack.reader.bean.RankListBean;
import com.jack.reader.utils.StringUtils;
import com.jack.reader.view.ChangeTextViewSpace;
import com.jack.reader.view.recyclerview.adapter.BaseViewHolder;
import com.jack.reader.view.recyclerview.adapter.RecyclerArrayAdapter;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2019/4/23 13:24
 */
public class RankListTypeAdapter extends RecyclerArrayAdapter<RankListBean.RankListData.RankListType> {

    public RankListTypeAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder<RankListBean.RankListData.RankListType>(parent, R.layout.item_ranklist) {
            @Override
            public void setData(RankListBean.RankListData.RankListType item) {
                super.setData(item);
                ChangeTextViewSpace tv_item_ranklist = holder.getView(R.id.tv_item_ranklist);
                tv_item_ranklist.setSpacing(20);
                StringUtils.setText(tv_item_ranklist, item.getTitle(), "", View.VISIBLE, View.VISIBLE);
                tv_item_ranklist.setVisibility(View.GONE);
                holder.setImageUrl(R.id.iv_item_ranklist, item.getImgurl(),
                        R.drawable.cover_default);
            }
        };
    }
}