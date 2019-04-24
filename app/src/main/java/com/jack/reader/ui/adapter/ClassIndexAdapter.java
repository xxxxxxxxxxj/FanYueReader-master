package com.jack.reader.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jack.reader.R;
import com.jack.reader.bean.ClassIndexBean;
import com.jack.reader.view.recyclerview.adapter.BaseViewHolder;
import com.jack.reader.view.recyclerview.adapter.RecyclerArrayAdapter;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2019/4/23 11:39
 */
public class ClassIndexAdapter extends RecyclerArrayAdapter<ClassIndexBean.ClassIndexData.ClassIndexType> {

    public ClassIndexAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder<ClassIndexBean.ClassIndexData.ClassIndexType>(parent, R.layout.item_classindex) {
            @Override
            public void setData(ClassIndexBean.ClassIndexData.ClassIndexType item) {
                super.setData(item);
                holder.setRoundImageUrl(R.id.iv_item_classindex_img, item.getImg(),
                        R.drawable.cover_default);
                holder.setText(R.id.tv_item_classindex_title, item.getBook_typename())
                        .setText(R.id.tv_item_classindex_num, item.getNum()+"本");
            }
        };
    }
}