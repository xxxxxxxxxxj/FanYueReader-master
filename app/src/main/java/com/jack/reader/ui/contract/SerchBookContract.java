package com.jack.reader.ui.contract;

import com.jack.reader.base.BaseContract;
import com.jack.reader.bean.KeywordListBean;

import java.util.List;

import okhttp3.RequestBody;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2019/4/23 18:32
 */
public interface SerchBookContract {
    interface View extends BaseContract.BaseView {
        void keywordSuccess(List<KeywordListBean.KeywordListData.Keyword> data);

        void keywordError(String msg);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void keyword(RequestBody build);
    }
}
