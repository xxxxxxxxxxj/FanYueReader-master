package com.jack.reader.ui.contract;

import com.jack.reader.base.BaseContract;
import com.jack.reader.bean.PageHomeBean;

import java.util.List;

import okhttp3.RequestBody;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2019/4/23 17:05
 */
public interface BookListContract {
    interface View extends BaseContract.BaseView {
        void bookListSuccess(List<PageHomeBean.PageHomeData.PageHomeBooks> data);

        void bookListError(String msg);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void bookListClass(RequestBody build);

        void bookListBangDan(RequestBody build);

        void bookListSerch(RequestBody build);

        void bookListCollect(RequestBody build);
    }
}
