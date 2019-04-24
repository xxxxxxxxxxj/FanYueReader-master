package com.jack.reader.ui.contract;

import com.jack.reader.base.BaseContract;
import com.jack.reader.bean.MyIndexBean;

import okhttp3.RequestBody;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2019/4/23 14:42
 */
public interface MyFragMentContract {
    interface View extends BaseContract.BaseView {
        void myIndexSuccess(MyIndexBean.MyIndexData data);

        void myIndexError(String msg);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void myIndex(RequestBody build);
    }
}
